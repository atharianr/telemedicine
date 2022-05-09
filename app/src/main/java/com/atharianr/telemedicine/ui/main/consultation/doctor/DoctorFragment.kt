package com.atharianr.telemedicine.ui.main.consultation.doctor

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.databinding.DialogFilterBinding
import com.atharianr.telemedicine.databinding.FragmentDoctorBinding
import com.atharianr.telemedicine.utils.DummyData

class DoctorFragment : Fragment() {

    private var _binding: FragmentDoctorBinding? = null
    private var _dialogBinding: DialogFilterBinding? = null
    private val binding get() = _binding as FragmentDoctorBinding
    private val dialogBinding get() = _dialogBinding as DialogFilterBinding

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

            val doctorAdapter = DoctorAdapter()
            doctorAdapter.setData(DummyData.getDoctor())

            binding.apply {
                btnFilter.setOnClickListener {
                    dialog.show()
                }

                rvDoctor.apply {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = doctorAdapter
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}