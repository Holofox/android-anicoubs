package ru.holofox.anicoubs.ui.postponed.list

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner

import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

import ru.holofox.anicoubs.R
import ru.holofox.anicoubs.data.db.unitlocalized.vk.UnitSpecificVKWallMinimalEntry
import ru.holofox.anicoubs.databinding.PostponedListFragmentBinding
import ru.holofox.anicoubs.internal.observer.EventObserver

import ru.holofox.anicoubs.ui.extensions.indefiniteSnackbar
import ru.holofox.anicoubs.ui.extensions.longSnackbar

class PostponedListFragment : Fragment(), KodeinAware {

    override val kodein by closestKodein()

    private lateinit var binding: PostponedListFragmentBinding

    private val viewModel: PostponedListViewModel by lazy {
        ViewModelProviders.of(this, viewModelProvider)
            .get(PostponedListViewModel::class.java)
    }
    private val viewModelProvider: PostponedListViewModelProvider by instance()
    private var viewModelAdapter: PostponedListAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observeEventNetworkError()
        observeResponseWall()
        observeSnackbar()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.postponed_list_fragment,
            container,
            false
        )

        viewModelAdapter = PostponedListAdapter().apply {
            setOnItemViewClickListener({view, item, _ ->
                when (view.id) {
                   // R.id.cardView_postponed -> onShowPostponedDetail(view)
                    R.id.imageView_more -> onItemMore(view, item)
                }
            }, R.id.cardView_postponed, R.id.imageView_more)
        }

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            postponedViewModel = viewModel
            recyclerView.adapter = viewModelAdapter

            swipeRefreshLayout.setColorSchemeResources(R.color.colorGreenLight)
        }

        return binding.root
    }

    private fun observeEventNetworkError() {
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
    }

    private fun observeResponseWall() {
        viewModel.wall.observe(viewLifecycleOwner, Observer { entries ->
            entries?.let {
                viewModelAdapter?.submitList(entries)
            }
        })
    }

    private fun observeSnackbar() {
        viewModel.snackbar.observe(viewLifecycleOwner, EventObserver { message ->
            binding.root.longSnackbar(message)
        })
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            binding.root.indefiniteSnackbar(
                message = R.string.dialog_message_no_internet_connection,
                actionText = R.string.dialog_button_retry) {
                viewModel.refreshDataFromRepository()
            }
            viewModel.onNetworkErrorShown()
        }
    }

    private fun onItemMore(view: View, item: UnitSpecificVKWallMinimalEntry) {
        PopupMenu(view.context, view).apply {
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.option_item_publish -> {
                        onDialogConfirmation(
                            title = R.string.dialog_title_publish_entry,
                            message = R.string.dialog_message_confirm_publish) {
                            viewModel.onItemPublish(item)
                        }
                        true
                    }
                    R.id.option_item_show_post -> {
                        browse(item.wallGroupUrl())
                        true
                    }
                    R.id.option_item_delete -> {
                        onDialogConfirmation(
                            title = R.string.dialog_title_delete_entry,
                            message = R.string.dialog_message_confirm_delete) {
                            viewModel.onItemDelete(item)
                        }
                        true
                    }
                    else -> false
                }
            }
            inflate(R.menu.postponed_option)
            show()
        }
    }

    private fun onDialogConfirmation(
        @StringRes title: Int,
        @StringRes message: Int,
        positiveCallback: () -> Unit
    ) {
        context?.let {
            MaterialDialog(it).show {
                lifecycleOwner(viewLifecycleOwner)
                title(title)
                message(message)
                positiveButton(R.string.dialog_button_confirm) {
                    positiveCallback()
                }
                negativeButton(R.string.dialog_button_cancel)
            }
        }
    }

    private fun onShowPostponedDetail(view: View) {
        val actionDetail = PostponedListFragmentDirections.actionPostponedListToPostponedDetail()
        Navigation.findNavController(view).navigate(actionDetail)
    }

    private fun browse(url: String) =
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))

}
