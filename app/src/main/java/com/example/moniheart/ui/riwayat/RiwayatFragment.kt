package com.example.moniheart.ui.riwayat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moniheart.adapter.AdapterArtikel
import com.example.moniheart.adapter.AdapterRiwayat
import com.example.moniheart.app.ApiConfig
import com.example.moniheart.databinding.FragmentRiwayatBinding
import com.example.moniheart.model.ResponModelRiwayat
import com.example.moniheart.model.Riwayat
import com.example.moniheart.util.prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RiwayatFragment : Fragment() {

            private var _binding: FragmentRiwayatBinding? = null

            lateinit var rvriwayat: RecyclerView
            lateinit var tvmaxbpm: TextView
            lateinit var tvminbpm: TextView
            lateinit var tvratabpm: TextView
            var lisbpm = arrayListOf<Int>()

            // This property is only valid between onCreateView and
            // onDestroyView.
            private val binding get() = _binding!!

            override fun onCreateView(
                inflater: LayoutInflater,
                container: ViewGroup?,
                savedInstanceState: Bundle?
            ): View {
                val notificationsViewModel =
                    ViewModelProvider(this).get(RiwayatViewModel::class.java)

                _binding = FragmentRiwayatBinding.inflate(inflater, container, false)
                val root: View = binding.root

                val textView: TextView = binding.textNotifications
                notificationsViewModel.text.observe(viewLifecycleOwner) {
                    textView.text = it
                }

                rvriwayat = binding.rvriwayat
                tvmaxbpm = binding.tvMaxbpm
                tvminbpm = binding.tvMinbpm
                tvratabpm = binding.tvRatabpm


                getriwayat()

                Log.d("array bpm = ", "onCreateView: "+lisbpm)
                return root

            }

            fun getriwayat(){

                val prefs = prefs(requireActivity())
                val id = prefs.getInt2("user_id")

                ApiConfig.ApiConfig.instanceRetrofit.getriwayat(id).enqueue( object :
                    Callback<ResponModelRiwayat> {

            override fun onResponse(
                call: Call<ResponModelRiwayat>,
                response: Response<ResponModelRiwayat>
            ) {
                val res = response.body()
                if (res != null ) {
                    displayRiwayat(res.riwayats)

                    for (i in res.riwayats){

                        lisbpm.add(i.bpm)

                    }

                    if (res.riwayats.size != 0){
                        tvmaxbpm.setText(lisbpm.max().toString())
                        tvminbpm.setText(lisbpm.min().toString())
                        tvratabpm.setText(lisbpm.average().toInt().toString())
                    }else{
                        tvmaxbpm.setText("0")
                        tvminbpm.setText("0")
                        tvratabpm.setText("0")
                    }

                }else{
                    Log.d("coba", "onResponse: gagal")
                }

            }

            override fun onFailure(call: Call<ResponModelRiwayat>, t: Throwable) {

                Log.d("coba", "onResponse: gagal")

            }
        })
    }

    fun displayRiwayat(riwayats: ArrayList<Riwayat>){
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        rvriwayat.adapter = AdapterRiwayat(riwayats)
        rvriwayat.layoutManager = layoutManager
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}