package com.atharianr.telemedicine.ui.main.consultation.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.atharianr.telemedicine.databinding.FragmentMessageBinding
import com.atharianr.telemedicine.utils.DummyData


class MessageFragment : Fragment() {
    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding as FragmentMessageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val messageAdapter = MessageAdapter()
            messageAdapter.setData(DummyData.getMessage())

            binding.rvMessage.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = messageAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}