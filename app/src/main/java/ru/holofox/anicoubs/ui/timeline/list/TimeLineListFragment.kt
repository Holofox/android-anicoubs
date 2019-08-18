package ru.holofox.anicoubs.ui.timeline.list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation

import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

import ru.holofox.anicoubs.R
import ru.holofox.anicoubs.databinding.TimelineListFragmentBinding
import ru.holofox.anicoubs.ui.extensions.indefiniteSnackbar

class TimeLineListFragment : Fragment(), KodeinAware {

    override val kodein by closestKodein()

    private lateinit var binding: TimelineListFragmentBinding

    private val viewModel: TimeLineListViewModel by lazy {
        ViewModelProviders.of(this, viewModelProvider)
            .get(TimeLineListViewModel::class.java)
    }

    private val viewModelProvider: TimeLineListViewModelProvider by instance()
    private var viewModelAdapter: TimelineAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        updateActionBarTitle(getString(R.string.menu_timeline))
        observeTimeline()
    }

    private fun updateActionBarTitle(title: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = title
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.timeline_list_fragment,
            container,
            false
        )

        binding.lifecycleOwner = viewLifecycleOwner
        binding.timelineListViewModel = viewModel

        viewModelAdapter = TimelineAdapter().apply {
            setOnItemViewClickListener({ view, _, _ ->
                // showTimelineDetail(view)
            }, R.id.cardView_timeline)
        }

        binding.apply {
            recyclerView.adapter = viewModelAdapter
            swipeRefreshLayout.setColorSchemeResources(R.color.colorGreenLight)
        }

        observeNetworkError()

        return binding.root
    }

    private fun observeNetworkError() {
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
    }

    private fun observeTimeline() {
        viewModel.timeline.observe(viewLifecycleOwner, Observer { entries ->
            if (entries == null) return@Observer

            viewModelAdapter?.submitList(entries)
        })
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            binding.constraintLayout.indefiniteSnackbar(
                message = R.string.dialog_message_no_internet_connection,
                actionText = R.string.dialog_button_retry) {
                viewModel.refreshDataFromRepository()
            }
            viewModel.onNetworkErrorShown()
        }
    }

    private fun showTimelineDetail(view: View) {
        val actionDetail = TimeLineListFragmentDirections.actionTimelineListToTimelineDetail()
        Navigation.findNavController(view).navigate(actionDetail)
    }

}