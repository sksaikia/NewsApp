package com.example.newsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.core.di.viewModel.ViewModelFactory
import com.example.newsapp.MainApplication
import com.example.newsapp.common.presentation.NewsViewModel
import com.example.newsapp.common.presentation.adapter.NewsAdapter
import com.example.newsapp.databinding.FragmentArticleBinding
import com.example.newsapp.databinding.FragmentSearchNewsBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArticleFragment : Fragment() {

    private lateinit var newsAdapter: NewsAdapter
    private lateinit var binding: FragmentArticleBinding
    private lateinit var viewModel : NewsViewModel
    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    val args: ArticleFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentArticleBinding.inflate(layoutInflater)
        (activity?.application as MainApplication).component.inject(this)
        viewModel = initiateViewModel(ViewModelProvider(this, viewModelFactory))

        val article = args.article

        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }

        binding.fab.setOnClickListener {
            viewModel.saveArticle(article)
            view?.let { it1 -> Snackbar.make(it1,"Article Saved Successfully",Snackbar.LENGTH_SHORT).show() }
        }


        return binding.root
    }


    fun initiateViewModel(viewModelProvider: ViewModelProvider) =
        viewModelProvider[NewsViewModel::class.java]

}