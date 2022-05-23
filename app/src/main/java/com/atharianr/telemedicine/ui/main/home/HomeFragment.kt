package com.atharianr.telemedicine.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding as FragmentHomeBinding

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
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}