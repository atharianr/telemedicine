package com.atharianr.telemedicine.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.databinding.FragmentHomeBinding
import com.atharianr.telemedicine.utils.Constant
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


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
            val window = requireActivity().window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor =
                ContextCompat.getColor(requireActivity(), R.color.blue_2B95F6)

            val listImages = arrayListOf<Int>()
            listImages.add(R.drawable.consultation)
            listImages.add(R.drawable.resume)
            listImages.add(R.drawable.hospital)
            listImages.add(R.drawable.pharmacy)

            val listTitles = arrayListOf<String>()
            listTitles.add("Konsultasi")
            listTitles.add("Layanan BPJS")
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
                requireActivity(),
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

            getBundle()
        }
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        _binding = null
//    }

    private fun getBundle() {
        val name = arguments?.getString(Constant.USER_NAME, "")
        val photo = arguments?.getString(Constant.USER_PHOTO, "")

        binding.apply {
            if (photo != null || photo != "") {
                Glide.with(this@HomeFragment)
                    .load(Constant.USER_PHOTO_BASE_URL + photo)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.profile_pic_placeholder)
                    .centerCrop()
                    .into(ivProfile)
            }
            binding.tvGreetings.text = "Halo, $name"
        }
    }
}