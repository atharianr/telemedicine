package com.atharianr.telemedicine.ui.landing.landing

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.databinding.FragmentLandingBinding
import com.atharianr.telemedicine.ui.landing.login.LoginFragment
import com.atharianr.telemedicine.ui.landing.register.RegisterFragment

class LandingFragment : Fragment() {

    private var _binding: FragmentLandingBinding? = null
    private val binding get() = _binding as FragmentLandingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLandingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            binding.apply {
                btnRegister.setOnClickListener {
                    loadFragment(RegisterFragment())
                }

                btnLogin.setOnClickListener {
                    loadFragment(LoginFragment())
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    val appName =
                        getColoredSpanned("Tele", "#D44090") + getColoredSpanned(
                            "medicine",
                            "#407CD4"
                        )
                    tvAppName.text = Html.fromHtml(appName, HtmlCompat.FROM_HTML_MODE_LEGACY)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getColoredSpanned(text: String, color: String): String? {
        return "<font color=$color>$text</font>"
    }

    private fun loadFragment(fragment: Fragment?) {
        if (fragment != null) {
            val tag = fragment::class.java.simpleName
            val ft = requireActivity().supportFragmentManager.beginTransaction()
            ft.replace(R.id.fragment_landing, fragment, tag)
                .addToBackStack(tag)
                .commit()
        }
    }
}