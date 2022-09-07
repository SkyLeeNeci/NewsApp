package test.karpenko.newsapp.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import test.karpenko.newsapp.databinding.FragmentSearchBinding
import test.karpenko.newsapp.ui.adapters.NewsAdapter
import test.karpenko.newsapp.utils.Resource

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val mBinding
        get() = _binding!!

    private val viewModel by viewModels<SearchViewModel>()
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        observeViewModel()
        searchListener()
    }

    private fun searchListener() {
        var job: Job? = null
        mBinding.searchEditText.addTextChangedListener { text ->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                text?.let {
                    if (it.toString().isNotEmpty()){
                        viewModel.getSearchNews(query = it.toString())
                    }
                }
            }
        }
    }

    private fun initAdapter() {
        newsAdapter = NewsAdapter()
        mBinding.searchRecyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }

    private fun observeViewModel(){

        viewModel.searchNewsLiveData.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    mBinding.searchProgressBar.visibility = View.INVISIBLE
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles)
                        Log.d("Loading News", "${it.articles}")
                    }
                }
                is Resource.Error -> {
                    mBinding.searchProgressBar.visibility = View.INVISIBLE
                    response.data?.let {
                        Log.d("Loading News", "Main Fragment error: ${it}")
                    }
                }
                is Resource.Loading -> {
                    mBinding.searchProgressBar.visibility = View.VISIBLE
                    Log.d("Loading News", "Main Fragment Loading")
                }
            }
        }

    }

}