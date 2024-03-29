package com.atharianr.telemedicine.ui.main.article

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.data.source.remote.response.ArticleData
import com.atharianr.telemedicine.data.source.remote.response.vo.StatusResponse
import com.atharianr.telemedicine.databinding.FragmentArticleBinding
import com.atharianr.telemedicine.utils.Constant
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
            val window = requireActivity().window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor =
                ContextCompat.getColor(requireActivity(), R.color.white)

            getAllArticles(getBearerToken())
            setupSearch()
        }
    }

    private fun getAllArticles(token: String?) {
        isLoading(loading = true, empty = true)
        if (token != null) {
            articleViewModel.getAllArticles(token).observe(viewLifecycleOwner) {
                when (it.status) {
                    StatusResponse.SUCCESS -> {
                        val data = it.body?.data
                        if (data != null) {
                            initRecyclerView(data)
                        }
                        isLoading(loading = false, empty = false)
                    }

                    StatusResponse.ERROR -> {
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
    }

    private fun getSearchArticles(token: String?, keyword: String) {
        isLoading(loading = true, empty = true)
        if (token != null) {
            articleViewModel.getSearchArticles(token, keyword).observe(viewLifecycleOwner) {
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
                    getSearchArticles(getBearerToken(), query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    getSearchArticles(getBearerToken(), newText)
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

    private fun getBearerToken(): String? {
        val sharedPref =
            requireActivity().getSharedPreferences(Constant.USER_DATA, Context.MODE_PRIVATE)
        return sharedPref.getString(Constant.TOKEN, "")
    }
}