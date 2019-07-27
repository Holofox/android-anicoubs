package ru.holofox.anicoubs.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.ui.setupActionBarWithNavController

import kotlinx.android.synthetic.main.activity_main.*
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
import ru.holofox.anicoubs.databinding.ActivityMainBinding
import ru.holofox.anicoubs.internal.enums.UnitDialog
import ru.holofox.anicoubs.internal.enums.UnitDialog.*
import ru.holofox.anicoubs.internal.observer.EventObserver
import ru.holofox.anicoubs.ui.extensions.setupWithNavController
import ru.holofox.anicoubs.ui.main.MainViewModel
import ru.holofox.anicoubs.ui.main.MainViewProvider

class MainActivity : AppCompatActivity(), KodeinAware {

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

        observeEventDialogs()

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

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    private fun observeEventDialogs() {
     viewModel.dialog.observe(this, EventObserver {
             onDialogShow(it)
             Log.i("observeEventDialogs", it.toString())
         })
    }

    private fun onDialogShow(state: UnitDialog) {
        when (state) {
            INPUT_DIALOG -> onInputDialog()
            LIST_DIALOG -> onListDialog()
            DATETIME_DIALOG -> onDateTimeDialog()
        }
    }

    private fun onInputDialog() {

        MaterialDialog(this).show {
            title(R.string.dialog_title_add)
            input(
                hint = getString(R.string.dialog_input_url_placeholder),
                waitForPositiveButton = false,
                maxLength = 28
            ) { dialog, text ->
                val inputField = dialog.getInputField()
                val regex = Regex("^http(s)?://coub.com/view/(\\w{5,6})$")
                val isValid = regex.containsMatchIn(input = text)

                inputField.error = if (isValid) null else getString(
                    R.string.dialog_warning_not_valid_url)

                dialog.setActionButtonEnabled(WhichButton.POSITIVE, isValid)
            }
            checkBoxPrompt(R.string.dialog_checkbox_postponed, isCheckedDefault = true) { postponed ->
                //toast("Checked? $checked")
            }
            negativeButton(R.string.dialog_button_cancel)
            positiveButton(R.string.dialog_button_confirm) {
                viewModel.onDialogShown(LIST_DIALOG)
            }
            lifecycleOwner(this@MainActivity)
        }
    }

    private fun onListDialog() {
        MaterialDialog(this).show {
            title(R.string.dialog_title_choose_category)
            listItemsSingleChoice(R.array.dialog_categories_list, initialSelection = 0)
            positiveButton(R.string.dialog_button_choose) {
                viewModel.onDialogShown(DATETIME_DIALOG)
            }
            lifecycleOwner(this@MainActivity)
        }
    }

    private fun onDateTimeDialog() {
        MaterialDialog(this).show {
            title(R.string.dialog_title_select_datetime)
            dateTimePicker(
                requireFutureDateTime = true,
                show24HoursView = true) { _, dateTime ->
                // Use dateTime (Calendar)
            }
            negativeButton(R.string.dialog_button_cancel)
            positiveButton(R.string.dialog_button_ok)

            lifecycleOwner(this@MainActivity)
        }
    }

    companion object {
        private const val TAG = "MainActivity"

        fun startFrom(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}
