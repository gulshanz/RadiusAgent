package taksande.gulshan.radiusagent.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import taksande.gulshan.radiusagent.databinding.FragmentListFacilitiesBinding
import taksande.gulshan.radiusagent.ui.adapters.FacilityAdapter
import taksande.gulshan.radiusagent.ui.viewmodels.MainViewModel


@AndroidEntryPoint
class ListFacilities : Fragment() {

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var binding: FragmentListFacilitiesBinding
    private lateinit var adapter: FacilityAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentListFacilitiesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchData()
        setupAdapter()

    }

    private fun setupAdapter() {

        adapter = FacilityAdapter()
        binding.rvFacilities.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFacilities.adapter = adapter


        viewModel.apiResponse.observe(viewLifecycleOwner) { apiResponse ->
            if (apiResponse.facilities.isNotEmpty()) {
                adapter.setData(apiResponse.facilities)
                adapter.setExclusion(apiResponse.exclusions)
            } else {
                showError(viewModel.error.value)
            }

        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            showError(viewModel.error.value)
        }

        viewModel.isLoading.observe(viewLifecycleOwner){isLoading ->
            if (isLoading)
                binding.loader.visibility = View.VISIBLE
            else
                binding.loader.visibility = View.GONE
        }

    }

    private fun showError(value: String?) {
        binding.textError.visibility = View.VISIBLE
        Toast.makeText(requireContext(), value!!, Toast.LENGTH_LONG).show()
    }

}