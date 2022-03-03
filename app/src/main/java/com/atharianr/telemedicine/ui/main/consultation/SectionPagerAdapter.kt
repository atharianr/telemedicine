package com.atharianr.telemedicine.ui.main.consultation

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.ui.main.consultation.doctor.DoctorFragment
import com.atharianr.telemedicine.ui.main.consultation.message.MessageFragment

class SectionsPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int = TAB_TITLES.size

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> MessageFragment()
            1 -> DoctorFragment()
            else -> Fragment()
        }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.message, R.string.doctor)
    }
}