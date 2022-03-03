package com.atharianr.telemedicine.ui.main

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.databinding.ActivityMainBinding
import com.atharianr.telemedicine.ui.main.article.ArticleFragment
import com.atharianr.telemedicine.ui.main.consultation.ConsultationFragment
import com.atharianr.telemedicine.ui.main.home.HomeFragment
import com.atharianr.telemedicine.ui.main.profile.ProfileFragment
import java.util.*

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding as ActivityMainBinding
    private var integerDeque: Deque<Int> = ArrayDeque(3)
    private var flag = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        integerDeque.push(R.id.home)

        loadFragment(HomeFragment())
        setupBottomNav()
    }

    private fun setupBottomNav() {
        binding.apply {

            bottomNav.selectedItemId = R.id.home

            bottomNav.setOnItemSelectedListener {

                val id = it.itemId
                if (integerDeque.contains(id)) {
                    if (id == R.id.home) {
                        if (integerDeque.size != 1) {
                            if (flag) {
                                integerDeque.addFirst(R.id.home)
                                flag = !flag
                            }
                        }
                    }
                    integerDeque.remove(id)
                }
                integerDeque.push(id)
                loadFragment(getFragment(id))

                return@setOnItemSelectedListener true
            }

            bottomNav.setOnItemReselectedListener {
                return@setOnItemReselectedListener
            }
        }
    }

    private fun getFragment(itemId: Int?): Fragment {
        binding.apply {
            when (itemId) {
                R.id.home -> {
                    bottomNav.menu.getItem(0).isChecked = true
                    return HomeFragment()
                }
                R.id.consultation -> {
                    bottomNav.menu.getItem(1).isChecked = true
                    return ConsultationFragment()
                }

                R.id.article -> {
                    bottomNav.menu.getItem(2).isChecked = true
                    return ArticleFragment()
                }
                R.id.profile -> {
                    bottomNav.menu.getItem(3).isChecked = true
                    return ProfileFragment()
                }
            }
            bottomNav.menu.getItem(0).isChecked = true
            return HomeFragment()
        }
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            val tag = fragment::class.java.simpleName
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.fragment, fragment, tag)
                .commit()
            return true
        }
        return false
    }

    override fun onBackPressed() {
        integerDeque.pop()
        if (!integerDeque.isEmpty()) {
            loadFragment(getFragment(integerDeque.peek()))
        } else {
            finish()
        }

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(
            binding.root.windowToken,
            InputMethodManager.RESULT_UNCHANGED_SHOWN
        )
    }
}