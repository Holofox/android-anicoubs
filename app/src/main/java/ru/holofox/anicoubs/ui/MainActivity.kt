package ru.holofox.anicoubs.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.text.isDigitsOnly
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.ui.setupActionBarWithNavController

import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.abs
import ru.holofox.anicoubs.R

import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.setActionButtonEnabled
import com.afollestad.materialdialogs.checkbox.checkBoxPrompt
import com.afollestad.materialdialogs.datetime.dateTimePicker
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.afollestad.materialdialogs.list.listItemsSingleChoice

import com.google.android.material.bottomnavigation.BottomNavigationView
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import ru.holofox.anicoubs.data.db.entity.vk.builder.VKParameters
import ru.holofox.anicoubs.data.network.response.coub.CoubChannelResponse
import ru.holofox.anicoubs.data.network.response.vk.VKVideoSaveResponse
import ru.holofox.anicoubs.databinding.ActivityMainBinding
import ru.holofox.anicoubs.internal.Constants
import ru.holofox.anicoubs.internal.observer.EventObserver
import ru.holofox.anicoubs.ui.base.LocaleAppCombatActivity
import ru.holofox.anicoubs.ui.extensions.longSnackbar
import ru.holofox.anicoubs.ui.extensions.setupWithNavController
import ru.holofox.anicoubs.ui.main.MainViewModel
import ru.holofox.anicoubs.ui.main.MainViewProvider

class MainActivity : LocaleAppCombatActivity(), KodeinAware {

    override val kodein by closestKodein()

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, viewModelProvider)
            .get(MainViewModel::class.java)
    }

    private val viewModelProvider: MainViewProvider by instance()
    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.apply {
            lifecycleOwner = this@MainActivity
            mainViewModel = viewModel
        }

        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        } // Else, need to wait for onRestoreInstanceState

        observeIsDialogShown()
        observeSnackbar()
        observeCoubResponse()
        observeChannelResponse()
        observeChannelIsBanned()
        observeUserResponse()
        observeVideoResponse()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)

        val navGraphIds = listOf(R.navigation.timeline, R.navigation.postponed,
            R.navigation.suggest, R.navigation.settings)

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this, Observer { navController ->
            setupActionBarWithNavController(navController)
        })
        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean =
        currentNavController?.value?.navigateUp() ?: false

    private fun observeIsDialogShown() {
        viewModel.isDialogShown.observe(this, EventObserver { isDialogShown ->
            if (isDialogShown) onInputDialog()
        })
    }

    private fun observeSnackbar() {
        viewModel.snackbar.observe(this, EventObserver { message ->
            binding.root.longSnackbar(message)
        })
    }

    private fun observeCoubResponse() {
        viewModel.coubResponse.observe(this, EventObserver { coub ->
            viewModel.checkInBlackList(coub.channelId)
        })
    }

    private fun observeChannelIsBanned() {
        viewModel.channelIsBanned.observe(this, Observer { isBanned ->
            isBanned?.let {
                channelIsBanned(isBanned)
            }
        })
    }

    private fun channelIsBanned(isBanned: Boolean) {
        if (isBanned)
            binding.root.longSnackbar(R.string.snackbar_channel_is_blocked)
        else {
            val channelId = viewModel.coubResponse.value!!.peekContent().channelId
            viewModel.getChannelInfo(channelId)
        }
    }

    private fun observeChannelResponse() {
        viewModel.channelResponse.observe(this, EventObserver { channel ->
            if (channel.meta.vkontakte == null) {
                saveVideo(channel)
            } else {
                channel.meta.vkontakte?.let {
                    if (it.isEmpty() || it.matches(REGEX_NUMERIC_CHANNEL_ID))
                        saveVideo(channel)
                    else
                        viewModel.getUser(it)
                }
            }
        })
    }

    private fun observeUserResponse() {
        viewModel.userResponse.observe(this, EventObserver { user ->
            val channel = viewModel.channelResponse.value!!.peekContent()
                channel.meta.vkontakte = "id"+user.response[0].id.toString()

            saveVideo(channel)
        })
    }

    @SuppressLint("DefaultLocale")
    private fun saveVideo(channel: CoubChannelResponse) {
        var author = channel.title
            .replace(REGEX_ALL_BRACKETS, "")
            .capitalize()

        channel.meta.vkontakte?.let {
            if (it.matches(REGEX_NUMERIC_CHANNEL_ID)) {
                author = "[$it|$author]"
            }
        }

        var description = String.format(Constants.VK_VIDEO_DESC_TEMPLATE, author)
        val coub = viewModel.coubResponse.value!!.peekContent()

        if (coub.tags.isNotEmpty()) {
            val filteredTags = coub.tags.asSequence()
                .mapIndexed { _, s ->
                    s.title.replace(REGEX_ALPHABETIC, "")
                }
                .filterNot { it.isDigitsOnly() || it in RESERVED_TAGS || it.isEmpty()}
                .joinToString(separator = ", #", prefix = "#", postfix = ".")

            description = "$description\n\nТеги: $filteredTags"
        }

        val coubLink = viewModel.coubLink.value!!
        val parameters = VKParameters.Builder()
            .name(coub.title.capitalize())
            .description(description)
            .link(coubLink)

        when (channel.id) {
            Constants.TARGET_CHANNEL_ID -> {
                parameters.albumId(Constants.TARGET_ALBUM)
                parameters.groupId(abs(Constants.TARGET_GROUP_ID))
            }
            else -> {
                val categoryId = viewModel.categoryId.value!!
                parameters.albumId(categoryId.inc())
                parameters.groupId(abs(Constants.TARGET_REPOSITORY_ID))
            }
        }

        viewModel.saveVideo(parameters.build())
    }

    private fun observeVideoResponse() {
        viewModel.videoSaveResponse.observe(this, EventObserver { video ->
            publishPost(video)
        })
    }

    @SuppressLint("DefaultLocale")
    private fun publishPost(video: VKVideoSaveResponse) {
        val coub = viewModel.coubResponse.value!!.peekContent()

        val category = resources.getStringArray(
            R.array.dialog_categories_list)[viewModel.categoryId.value!!]

        val title = coub.title
            .replace(REGEX_ALL_BRACKETS, "")
            .replace("#","")
            .capitalize()

        val attachment = "video" + video.ownerId.toString() + "_" + video.videoId

        val parameters = VKParameters.Builder()
            .ownerId(Constants.TARGET_GROUP_ID)
            .fromGroup(true)
            .attachments(attachment)
            .message("$category\n$title")
            .muteNotification(true)

        if (viewModel.isPostponedPublish.value!!) {
            val publishCalendarDate = viewModel.publishDate.value!!
            val publishDate = publishCalendarDate.timeInMillis / 1000L

            parameters.publishDate(publishDate)
        }

        viewModel.publishPost(parameters = parameters.build(), video = video)
    }

    private fun onAddCoub() {
        val coubLink = viewModel.coubLink.value!!
        val permalink = coubLink.split("/")

        viewModel.getCoub(permalink[4])
    }

    private fun onInputDialog() {
        MaterialDialog(this).show {
            lifecycleOwner(this@MainActivity)
            title(R.string.dialog_title_add)
            input(
                hint = getString(R.string.dialog_input_url_placeholder),
                waitForPositiveButton = false,
                maxLength = 28,
                prefill = viewModel.coubLink.value
            ) { dialog, text ->
                val inputField = dialog.getInputField()
                val isValid = REGEX_COUB_URL.containsMatchIn(input = text)

                inputField.error = if (isValid) null else getString(R.string.dialog_warning_not_valid_url)
                if (isValid) viewModel.onCoubLinkEntered(text.toString())
                dialog.setActionButtonEnabled(WhichButton.POSITIVE, isValid)
            }
            checkBoxPrompt(
                R.string.dialog_checkbox_postponed,
                isCheckedDefault = viewModel.isPostponedPublish.value!!) {
                viewModel.onPostponedPublish(it)
            }
            negativeButton(R.string.dialog_button_cancel)
            positiveButton(R.string.dialog_button_confirm) {
                onListDialog()
            }
        }
    }

    private fun onListDialog() {
        MaterialDialog(this).show {
            lifecycleOwner(this@MainActivity)
            title(R.string.dialog_title_choose_category)
            listItemsSingleChoice(
                R.array.dialog_categories_list,
                initialSelection = viewModel.categoryId.value!!
            ) { _, index, _ ->
                viewModel.onSelectedCategory(index)
            }
            positiveButton(R.string.dialog_button_choose) {
                if (viewModel.isPostponedPublish.value!!)
                    onDateTimeDialog()
                else
                    onAddCoub()
            }
        }
    }

    private fun onDateTimeDialog() {
        MaterialDialog(this).show {
            lifecycleOwner(this@MainActivity)
            title(R.string.dialog_title_select_datetime)
            dateTimePicker(
                requireFutureDateTime = true,
                currentDateTime = viewModel.publishDate.value,
                show24HoursView = true
            ) { _, calendar ->
                viewModel.onPublishDate(calendar)
            }
            negativeButton(R.string.dialog_button_cancel)
            positiveButton(R.string.dialog_button_ok) {
                onAddCoub()
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
        private val RESERVED_TAGS = listOf(
            "preview", "cool", "sad", "fun", "nice", "scary", "news", "happy", "music")
        private val REGEX_COUB_URL = "^http(s)?://coub.com/view/(\\w{5,6})$".toRegex()
        private val REGEX_ALL_BRACKETS = "([\\[({][A-Za-zА-Яа-я0-9ё]*[])}])".toRegex()
        private val REGEX_NUMERIC_CHANNEL_ID = "^id[0-9]+\$".toRegex()
        private val REGEX_ALPHABETIC = "[^A-Za-zА-Яа-я0-9ё]".toRegex()

        fun startFrom(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}
