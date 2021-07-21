package com.example.testcovidapi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testcovidapi.databinding.FragmentCovidDetailsBinding
import com.example.testcovidapi.model.CountryData


class CovidDetailsFragment : Fragment() {

    private val binding: FragmentCovidDetailsBinding by lazy {
        FragmentCovidDetailsBinding.inflate(layoutInflater)
    }

    companion object {
        private const val EXTRA_COUNTRY = "country"
        fun createFragment(data: CountryData?): CovidDetailsFragment {
            val bundle = Bundle().apply {
                putParcelable(EXTRA_COUNTRY, data)
            }
            return CovidDetailsFragment().apply {
                arguments = bundle
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as? MainActivity)?.setTitle()
        val country: CountryData? = arguments?.getParcelable(EXTRA_COUNTRY)

        country?.also {
            binding.newConfirmedTextView.text = getString(R.string.new_confirmed, it.newConfirmed)
            binding.totalConfirmedTextView.text =
                getString(R.string.total_confirmed, it.totalConfirmed)
            binding.newDeathsTextView.text = getString(R.string.new_deaths, it.newDeaths)
            binding.totalDeathsTextView.text = getString(R.string.total_deaths, it.totalDeaths)
            binding.newRecoveredTextView.text = getString(R.string.new_recovered, it.newRecovered)
            binding.totalRecoveredTextView.text =
                getString(R.string.total_recovered, it.totalRecovered)
            binding.countryNameTextView.text = it.country
            binding.dateUpdateTextView.text = it.date
        }

    }

}