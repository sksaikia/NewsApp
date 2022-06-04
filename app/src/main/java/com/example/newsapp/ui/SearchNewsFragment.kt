package com.example.newsapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.core.di.viewModel.ViewModelFactory
import com.example.newsapp.MainApplication
import com.example.newsapp.common.data.remote.entity.NewsResponse
import com.example.newsapp.common.presentation.NewsViewModel
import com.example.newsapp.common.presentation.adapter.NewsAdapter
import com.example.newsapp.core.network.Resource
import com.example.newsapp.databinding.FragmentBreakingNewsBinding
import com.example.newsapp.databinding.FragmentSearchNewsBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchNewsFragment : Fragment() {

    private lateinit var viewModel : NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var binding: FragmentSearchNewsBinding
    private val SEARCH_NEWS_DELAY = 500L

    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    private val TAG = "SearchNewsFragment"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentSearchNewsBinding.inflate(layoutInflater)
        (activity?.application as MainApplication).component.inject(this)

        viewModel = initiateViewModel(ViewModelProvider(this, viewModelFactory))

     //   viewModel.getBreakingNews("us")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        setUpObserver()

        var job : Job? = null
        binding.etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()){
                        viewModel.searchNews(editable.toString())
                    }
                }
            }
        }

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(
                com.example.newsapp.R.id.action_searchNewsFragment_to_articleFragment,
                bundle
            )
        }


    }

    private fun setUpObserver() {
        viewModel.searchNews.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> showNewsOnFeed(response)
                is Resource.Error -> handleError(response)
                is Resource.Loading -> handleLoading()
            }
        })
    }

    private fun handleLoading() {
        showProgressBar()
    }

    private fun handleError(response: Resource.Error<NewsResponse>) {
        hideProgressBar()
        response.message?.let { message ->
            Log.e(TAG, "handleError: $message")
        }
    }

    private fun showNewsOnFeed(response: Resource.Success<NewsResponse>) {
        hideProgressBar()
        response.data?.let {
            newsAdapter.differ.submitList(it.articles)
        }
    }

    private fun hideProgressBar(){
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar(){
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setUpRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }



    fun initiateViewModel(viewModelProvider: ViewModelProvider) =
        viewModelProvider[NewsViewModel::class.java]

}