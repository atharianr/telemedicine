package com.atharianr.telemedicine.ui.main.consultation.doctor

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.data.source.remote.response.DoctorData
import com.atharianr.telemedicine.data.source.remote.response.vo.StatusResponse
import com.atharianr.telemedicine.databinding.DialogFilterBinding
import com.atharianr.telemedicine.databinding.FragmentDoctorBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DoctorFragment : Fragment() {

    private var _binding: FragmentDoctorBinding? = null
    private var _dialogBinding: DialogFilterBinding? = null
    private val binding get() = _binding as FragmentDoctorBinding
    private val dialogBinding get() = _dialogBinding as DialogFilterBinding

    private val doctorViewModel: DoctorViewModel by viewModel()

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
                    dialog.dismiss()
                }

                btnDelete.setOnClickListener {
                    dialog.dismiss()
                }
            }
        }

        getAllDoctors()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getAllDoctors() {
        doctorViewModel.getAllDoctors().observe(this) {
            when (it.status) {
                StatusResponse.SUCCESS -> {
                    val data = it.body?.data
                    if (data != null) {
                        initRecyclerView(data)
                    }
                    isLoading(false)
                }

                StatusResponse.ERROR -> {
                    Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                    isLoading(false)
                    return@observe
                }

                else -> {
                    isLoading(false)
                    return@observe
                }
            }
        }
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

    private fun isLoading(loading: Boolean) {
        binding.apply {
            if (loading) {
                rvDoctor.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            } else {
                rvDoctor.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        }
    }
}