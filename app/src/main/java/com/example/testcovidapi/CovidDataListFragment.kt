package com.example.testcovidapi

import android.app.ProgressDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testcovidapi.api.CovidInterface
import com.example.testcovidapi.databinding.CountryRowBinding
import com.example.testcovidapi.databinding.FragmentCovidDataListBinding
import com.example.testcovidapi.model.Country
import com.example.testcovidapi.model.CountryData
import com.example.testcovidapi.retrofit.SetRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CovidDataListFragment : Fragment() {

    private var data: MutableList<CountryData>? = null
    private var recycleAdapter: RecycleViewAdapter? = null
    private var mCovidService: CovidInterface? = null
    private var pg: ProgressDialog? = null

    private val binding: FragmentCovidDataListBinding by lazy {
        FragmentCovidDataListBinding.inflate(layoutInflater)
    }

    companion object {
        fun createFragment(): CovidDataListFragment {
            return CovidDataListFragment()
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
        pg = ProgressDialog(requireContext())
        recycleAdapter = RecycleViewAdapter()

        binding.covidRv.also {
            it.adapter = recycleAdapter
            it.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val newData: MutableList<CountryData> = mutableListOf()
                data?.forEach {
                    if (binding.searchCountryEditText.text.toString()
                            .lowercase() in it.country?.lowercase() ?: ""
                    ) {
                        newData.add(it)
                    }
                }

                recycleAdapter?.also {
                    it.setData(newData)
                }
            }

        }

        binding.searchCountryEditText.addTextChangedListener(textWatcher)

        mCovidService = SetRetrofit.retrofitService
        if (data == null){
            getCovidData()
        }
    }

    private fun getCovidData() {
        pg?.show()
        mCovidService?.getCaseData()?.enqueue(object : Callback<Country> {
            override fun onResponse(call: Call<Country>, response: Response<Country>) {
                if (response.isSuccessful) {
                    pg?.dismiss()
                    data = response.body().Countries
                    recycleAdapter?.setData(response.body().Countries)
                }
            }

            override fun onFailure(call: Call<Country>?, t: Throwable?) {

            }

        })
    }

    inner class RecycleViewAdapter :
        RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {
        private var data = mutableListOf<CountryData>()
        private lateinit var binding: CountryRowBinding
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): RecycleViewAdapter.ViewHolder {
            binding = CountryRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding.root)
        }

        override fun onBindViewHolder(holder: RecycleViewAdapter.ViewHolder, position: Int) {
            holder.setIsRecyclable(false)
            val currentData = data[position]
            holder.bind(currentData)
        }

        override fun getItemCount() = data.count()

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(data: CountryData) {
                binding.countryTextView.text = data.country
                binding.dateTextView.text = data.totalDeaths.toString()
                binding.rowData.setOnClickListener {
                    requireActivity().supportFragmentManager.beginTransaction().apply {
                        replace(R.id.listContainer, CovidDetailsFragment.createFragment(data))
                        addToBackStack(null)
                        commit()
                    }
                }
            }
        }

        fun setData(data: MutableList<CountryData>) {
            this.data = data
            notifyDataSetChanged()
        }

    }


}