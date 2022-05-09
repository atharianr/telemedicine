package com.atharianr.telemedicine.ui.landing.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.databinding.FragmentLoginBinding
import com.atharianr.telemedicine.ui.landing.register.RegisterFragment
import com.atharianr.telemedicine.ui.main.MainActivity

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding as FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            binding.apply {
                btnBack.setOnClickListener {
                    requireActivity().onBackPressed()
                }

                btnLogin.setOnClickListener {
                    val intent =
                        Intent(requireActivity().applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }

                btnRegister.setOnClickListener {
                    loadFragment(RegisterFragment())
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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