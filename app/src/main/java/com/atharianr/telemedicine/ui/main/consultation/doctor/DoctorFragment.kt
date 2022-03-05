package com.atharianr.telemedicine.ui.main.consultation.doctor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.atharianr.telemedicine.databinding.FragmentDoctorBinding
import com.atharianr.telemedicine.utils.DummyData

class DoctorFragment : Fragment() {

    private var _binding: FragmentDoctorBinding? = null
    private val binding get() = _binding as FragmentDoctorBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoctorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val doctorAdapter = DoctorAdapter()
            doctorAdapter.setData(DummyData.getDoctor())

            binding.rvDoctor.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = doctorAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}