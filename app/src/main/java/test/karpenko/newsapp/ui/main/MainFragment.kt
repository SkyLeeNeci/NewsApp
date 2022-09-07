package test.karpenko.newsapp.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import test.karpenko.newsapp.databinding.FragmentMainBinding
import test.karpenko.newsapp.ui.adapters.NewsAdapter
import test.karpenko.newsapp.utils.Resource

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val mBinding
        get() = _binding!!

    private val viewModel by viewModels<MainViewModel>()
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater, container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        observeViewModel()
    }

    private fun initAdapter() {
        newsAdapter = NewsAdapter()
        mBinding.newsRecyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }

    private fun observeViewModel(){

        viewModel.newsLiveData.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    mBinding.progressBar.visibility = View.INVISIBLE
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles)
                        Log.d("Loading News", "${it.articles}")
                    }
                }
                is Resource.Error -> {
                    mBinding.progressBar.visibility = View.INVISIBLE
                    response.data?.let {
                        Log.d("Loading News", "Main Fragment error: ${it}")
                    }
                }
                is Resource.Loading -> {
                    mBinding.progressBar.visibility = View.VISIBLE
                    Log.d("Loading News", "Main Fragment Loading")
                }
            }
        }

    }

}