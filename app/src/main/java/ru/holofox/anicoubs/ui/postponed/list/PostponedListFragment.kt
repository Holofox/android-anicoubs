package ru.holofox.anicoubs.ui.postponed.list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

import com.google.android.material.snackbar.Snackbar

import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

import ru.holofox.anicoubs.R
import ru.holofox.anicoubs.databinding.PostponedListFragmentBinding
import ru.holofox.anicoubs.internal.observer.EventObserver
import ru.holofox.anicoubs.ui.base.DataBindingAdapter

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
            setOnItemClickListener(object : DataBindingAdapter.OnItemClickListener {
                override fun onItemClick(view: View) {
                    //TODO: Implement
                }
            })
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

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Snackbar.make(binding.recyclerView, getString(R.string.no_internet_connection),
                Snackbar.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

  /*  private val onItemMore: (View) -> Unit = {
        PopupMenu(it.context, it).apply {
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.option_item_publish -> {
                        //viewModel.onMenuItemPublish()
                        true
                    }

                    R.id.option_item_show_post -> {
                        // Show
                        true
                    }

                    R.id.option_item_delete -> {
                        // viewModel.onMenuItemDelete()
                        true
                    }

                    else -> false
                }
            }
            inflate(R.menu.postponed_option)
            show()
        }
    }
  */
}
