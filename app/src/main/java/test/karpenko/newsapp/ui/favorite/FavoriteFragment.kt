package test.karpenko.newsapp.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import test.karpenko.newsapp.R
import test.karpenko.newsapp.databinding.FragmentDetailsBinding
import test.karpenko.newsapp.databinding.FragmentFavoriteBinding
import test.karpenko.newsapp.ui.adapters.NewsAdapter
import test.karpenko.newsapp.ui.main.MainViewModel

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val mBinding
        get() = _binding!!

    private val viewModel by viewModels<FavoriteViewModel>()
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        observeViewModel()
        swipeToDeleteFromFavorite()
    }



    private fun initAdapter() {
        newsAdapter = NewsAdapter()
        mBinding.newsRecyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun observeViewModel(){
        viewModel.getFavoriteNews().observe(viewLifecycleOwner){
            mBinding.progressBar.visibility = View.INVISIBLE
            newsAdapter.differ.submitList(it)
        }
    }

    private fun swipeToDeleteFromFavorite(){
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewModel.deleteFromFavoriteArticle(article)
                view?.let { Snackbar.make(it, "Successfully deleted article", Snackbar.LENGTH_SHORT)
                    .apply {
                        setAction("Undo"){
                            viewModel.saveToFavoriteArticle(article)
                        }
                        show()
                    }}
            }

        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(mBinding.newsRecyclerView)
        }

    }

}