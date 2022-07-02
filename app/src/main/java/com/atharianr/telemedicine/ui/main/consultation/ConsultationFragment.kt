package com.atharianr.telemedicine.ui.main.consultation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.databinding.FragmentConsultationBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class ConsultationFragment : Fragment() {

    private var _binding: FragmentConsultationBinding? = null
    private val binding get() = _binding as FragmentConsultationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConsultationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val sectionsPagerAdapter =
                SectionsPagerAdapter(requireActivity().supportFragmentManager, this.lifecycle)

            binding.viewPager2.adapter = sectionsPagerAdapter
            TabLayoutMediator(
                binding.tabs,
                binding.viewPager2
            ) { tab: TabLayout.Tab, position: Int ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        _binding = null
//    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.message, R.string.doctor)
    }
}