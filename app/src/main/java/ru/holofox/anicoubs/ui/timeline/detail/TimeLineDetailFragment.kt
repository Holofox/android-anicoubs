package ru.holofox.anicoubs.ui.timeline.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.holofox.anicoubs.R

class TimeLineDetailFragment : Fragment() {

    companion object {
        fun newInstance() = TimeLineDetailFragment()
    }

    private lateinit var viewModel: TimeLineDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.timeline_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TimeLineDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
