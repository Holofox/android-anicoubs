package ru.holofox.anicoubs.ui.postponed.detail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ru.holofox.anicoubs.R

class PostponedDetailFragment : Fragment() {

    companion object {
        fun newInstance() = PostponedDetailFragment()
    }

    private lateinit var viewModel: PostponedDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.postponed_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PostponedDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
