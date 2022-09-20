package test.karpenko.newsapp.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import test.karpenko.newsapp.databinding.FragmentDetailsBinding

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val mBinding
    get() = _binding!!

    private val navArguments: DetailsFragmentArgs by navArgs()
    private val viewModel by viewModels<DetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(layoutInflater, container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpArgumentsFromBundle()
    }

    private fun setUpArgumentsFromBundle() {
        val articleArgs = navArguments.article
        articleArgs.let { article ->
            article.urlToImage.let {
                Glide.with(this).load(it).into(mBinding.headerImage)
            }
            mBinding.headerImage.clipToOutline = true
            mBinding.titleTextView.text = article.title
            mBinding.descriptionText.text = article.description

            mBinding.openWebSiteButton.setOnClickListener {
                Intent().setAction(Intent.ACTION_VIEW)
                    .addCategory(Intent.CATEGORY_BROWSABLE)
                    .setData(Uri.parse(takeIf { URLUtil.isValidUrl(article.url)}
                        ?.let { article.url }?: "https://www.google.com" )).let {
                            ContextCompat.startActivity(requireContext(), it, null)
                    }
            }

            mBinding.likeImageView.setOnClickListener {
                viewModel.saveToFavoriteArticle(article)
                Snackbar.make(it, "Article №${article.id}, ${article.source} saved", Snackbar.LENGTH_SHORT).show()
            }
        }

    }

}