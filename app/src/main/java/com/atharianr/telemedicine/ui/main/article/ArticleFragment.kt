package com.atharianr.telemedicine.ui.main.article

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.atharianr.telemedicine.data.source.remote.response.ArticleData
import com.atharianr.telemedicine.data.source.remote.response.vo.StatusResponse
import com.atharianr.telemedicine.databinding.FragmentArticleBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding as FragmentArticleBinding

    private val articleViewModel: ArticleViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            getAllArticles()
            setupSearch()
        }
    }

    private fun getAllArticles() {
        isLoading(loading = true, empty = true)
        articleViewModel.getAllArticles().observe(viewLifecycleOwner) {
            when (it.status) {
                StatusResponse.SUCCESS -> {
                    val data = it.body?.data
                    if (data != null) {
                        initRecyclerView(data)
                    }
                    isLoading(loading = false, empty = false)
                }

                StatusResponse.ERROR -> {
                    Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                    isLoading(loading = false, empty = true)
                    return@observe
                }

                else -> {
                    isLoading(loading = false, empty = true)
                    return@observe
                }
            }
        }
    }

    private fun getSearchArticles(keyword: String) {
        isLoading(loading = true, empty = true)
        articleViewModel.getSearchArticles(keyword).observe(viewLifecycleOwner) {
            Log.d(ArticleFragment::class.simpleName, "${it.status}")
            when (it.status) {
                StatusResponse.SUCCESS -> {
                    val data = it.body?.data
                    Log.d(ArticleFragment::class.simpleName, "$data")
                    if (data != null) {
                        initRecyclerView(data)
                        isLoading(loading = false, empty = false)
                    } else {
                        binding.tvEmptyArticle.text = "Pencarian '$keyword' tidak ditemukan."
                        isLoading(loading = false, empty = true)
                    }
                }

                StatusResponse.ERROR -> {
                    Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                    isLoading(loading = false, empty = true)
                    return@observe
                }

                else -> {
                    isLoading(loading = false, empty = true)
                    return@observe
                }
            }
        }
    }


    private fun initRecyclerView(listArticle: List<ArticleData>) {
        val articleAdapter = ArticleAdapter()
        articleAdapter.setData(listArticle)

        binding.rvArticle.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = articleAdapter
        }
    }

    private fun setupSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    getSearchArticles(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    getSearchArticles(newText)
                }
                return true
            }
        })
    }

    private fun isLoading(loading: Boolean, empty: Boolean) {
        Log.d(ArticleFragment::class.simpleName, "$loading, $empty")
        binding.apply {
            if (loading) {
                rvArticle.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
                tvEmptyArticle.visibility = View.GONE
            } else {
                if (empty) {
                    rvArticle.visibility = View.GONE
                    tvEmptyArticle.visibility = View.VISIBLE
                } else {
                    rvArticle.visibility = View.VISIBLE
                    tvEmptyArticle.visibility = View.GONE
                }
                progressBar.visibility = View.GONE
            }
        }
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        _binding = null
//    }
}