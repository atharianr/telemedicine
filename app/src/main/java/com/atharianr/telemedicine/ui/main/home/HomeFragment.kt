package com.atharianr.telemedicine.ui.main.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.data.source.remote.response.vo.StatusResponse
import com.atharianr.telemedicine.databinding.FragmentHomeBinding
import com.atharianr.telemedicine.utils.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding as FragmentHomeBinding

    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref =
            requireActivity().getSharedPreferences(Constant.USER_DATA, Context.MODE_PRIVATE)
        val token = sharedPref.getString(Constant.TOKEN, null)

        if (activity != null) {
            val listImages = arrayListOf<Int>()
            listImages.add(R.drawable.consultation)
            listImages.add(R.drawable.resume)
            listImages.add(R.drawable.hospital)
            listImages.add(R.drawable.pharmacy)

            val listTitles = arrayListOf<String>()
            listTitles.add("Konsultasi")
            listTitles.add("Resum Medis")
            listTitles.add("Rumah Sakit")
            listTitles.add("Apotek")

            val listDescriptions = arrayListOf<String>()
            listDescriptions.add("Chat dengan dokter")
            listDescriptions.add("BPJS, Data Riwayat penyakit")
            listDescriptions.add("RS terdekat dan RS Rujukan")
            listDescriptions.add("Toko Kesehatan")

            val homeAdapter = HomeAdapter()
            homeAdapter.setData(
                listImages,
                listTitles,
                listDescriptions,
                requireActivity().supportFragmentManager
            )

            binding.rvHome.apply {
                layoutManager = GridLayoutManager(
                    requireActivity().applicationContext,
                    2,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                setHasFixedSize(true)
                adapter = homeAdapter
                isFocusable = false
            }

            getUserDetail(token)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getUserDetail(token: String?) {
        val bearerToken = "Bearer $token"
        homeViewModel.getUserDetail(bearerToken).observe(requireActivity()) {
            when (it.status) {
                StatusResponse.SUCCESS -> {
                    val name = it.body?.data?.name
                    binding.tvGreetings.text = "Halo, $name"
                }

                StatusResponse.ERROR -> {
                    Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                    return@observe
                }

                else -> return@observe
            }
        }
    }
}