package com.atharianr.telemedicine.ui.main.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.atharianr.telemedicine.databinding.FragmentArticleBinding
import com.atharianr.telemedicine.utils.DummyData

class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding as FragmentArticleBinding

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
            val articleAdapter = ArticleAdapter()
            articleAdapter.setData(DummyData.getArticle())

            binding.rvArticle.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = articleAdapter
            }
        }
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        _binding = null
//    }
}