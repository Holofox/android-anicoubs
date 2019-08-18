package ru.holofox.anicoubs.ui.suggest.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ru.holofox.anicoubs.R

class SuggestDetailFragment : Fragment() {

    companion object {
        fun newInstance() = SuggestDetailFragment()
    }

    private lateinit var viewModel: SuggestDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.suggest_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SuggestDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
