package com.atharianr.telemedicine.ui.main.consultation.doctor

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.data.source.remote.response.DoctorData
import com.atharianr.telemedicine.data.source.remote.response.vo.StatusResponse
import com.atharianr.telemedicine.databinding.DialogFilterBinding
import com.atharianr.telemedicine.databinding.FragmentDoctorBinding
import com.atharianr.telemedicine.ui.main.article.ArticleFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DoctorFragment : Fragment() {

    private var _binding: FragmentDoctorBinding? = null
    private var _dialogBinding: DialogFilterBinding? = null
    private val binding get() = _binding as FragmentDoctorBinding
    private val dialogBinding get() = _dialogBinding as DialogFilterBinding

    private val doctorViewModel: DoctorViewModel by viewModel()

    private var searchKeyword = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoctorBinding.inflate(inflater, container, false)
        _dialogBinding = DialogFilterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val dialog = Dialog(requireActivity())
            dialog.setContentView(dialogBinding.root)
            dialog.window?.setBackgroundDrawableResource(R.drawable.rounded_box_16)

            binding.apply {
                btnFilter.setOnClickListener {
                    dialog.show()
                }
            }

            dialogBinding.apply {
                btnCancel.setOnClickListener {
                    dialog.dismiss()
                }

                btnUse.setOnClickListener {
                    useFilter()
                    dialog.dismiss()
                }

                btnDelete.setOnClickListener {
                    clearFilter()
                    dialog.dismiss()
                }
            }

            getAllDoctors()
            setupSearch()
            setupFilter()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getAllDoctors() {
        isLoading(loading = true, empty = false)
        doctorViewModel.getAllDoctors().observe(viewLifecycleOwner) {
            when (it.status) {
                StatusResponse.SUCCESS -> {
                    val data = it.body?.data
                    if (data != null) {
                        initRecyclerView(data)
                        isLoading(loading = false, empty = false)
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

    private fun getSearchArticles(keyword: String, filter: String = "") {
        isLoading(loading = true, empty = false)
        doctorViewModel.getSearchDoctors(keyword, filter).observe(viewLifecycleOwner) {
            when (it.status) {
                StatusResponse.SUCCESS -> {
                    val data = it.body?.data
                    if (data != null) {
                        initRecyclerView(data)
                        isLoading(loading = false, empty = false)
                    } else {
                        binding.tvEmptyDoctor.text = "Pencarian '$keyword' tidak ditemukan."
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

    private fun setupSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchKeyword = query
                    dialogBinding.lvFilter.clearChoices()
                    getSearchArticles(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    searchKeyword = newText
                    dialogBinding.lvFilter.clearChoices()
                    getSearchArticles(newText)
                }
                return true
            }
        })
    }

    private fun setupFilter() {
        val filterList = arrayListOf("Dokter Umum", "Dokter Gigi", "Dokter Mata")

        val filterAdapter = ArrayAdapter(
            requireActivity(),
            R.layout.component_checked_text_view,
            filterList
        )

        dialogBinding.lvFilter.adapter = filterAdapter
    }

    private fun useFilter() {
        dialogBinding.apply {
            var filter = ""
            for (i in 0..lvFilter.count) {
                if (lvFilter.isItemChecked(i)) {
                    filter += "${lvFilter.getItemAtPosition(i)}-"
                }
            }
            filter = filter.dropLast(1)
            Log.d(DoctorFragment::class.simpleName, "filter -> $filter")
            getSearchArticles(searchKeyword, filter)
        }
    }

    private fun clearFilter() {
        dialogBinding.lvFilter.clearChoices()
        getSearchArticles(searchKeyword)
    }

    private fun initRecyclerView(listDoctor: List<DoctorData>) {
        val doctorAdapter = DoctorAdapter()
        doctorAdapter.setData(listDoctor)

        binding.rvDoctor.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = doctorAdapter
        }
    }

    private fun isLoading(loading: Boolean, empty: Boolean) {
        Log.d(ArticleFragment::class.simpleName, "$loading, $empty")
        binding.apply {
            if (loading) {
                rvDoctor.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
                tvEmptyDoctor.visibility = View.GONE
            } else {
                if (empty) {
                    rvDoctor.visibility = View.GONE
                    tvEmptyDoctor.visibility = View.VISIBLE
                } else {
                    rvDoctor.visibility = View.VISIBLE
                    tvEmptyDoctor.visibility = View.GONE
                }
                progressBar.visibility = View.GONE
            }
        }
    }
}