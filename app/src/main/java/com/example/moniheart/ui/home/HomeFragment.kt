package com.example.moniheart.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moniheart.R
import com.example.moniheart.adapter.AdapterArtikel
import com.example.moniheart.app.ApiConfig
import com.example.moniheart.databinding.FragmentHomeBinding
import com.example.moniheart.model.Artikel
import com.example.moniheart.model.ResponModelRiwayat
import com.example.moniheart.ui.login.AwalActivity
import com.example.moniheart.ui.profile.ProfileActivity
import com.example.moniheart.util.prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var s: prefs

    lateinit var rvArtikel: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        s = prefs(requireActivity())

        val tvnama: TextView = binding.tvNama
        val ivprofil: ImageView = binding.ivProfil
        val btndetail: Button = binding.btnDetail
        val tvbpmakhir: TextView = binding.tvBpmakhir
        tvnama.text = s.getString(s.nama)
        rvArtikel = binding.rvartikel

        ivprofil.setOnClickListener{
            val intent = Intent(activity, ProfileActivity::class.java)
            requireActivity().startActivity(intent)
        }

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL

        rvArtikel.adapter = AdapterArtikel(arrArtikel)
        rvArtikel.layoutManager = layoutManager

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

                    if(res.riwayats.size != 0){
                        val bpmakhir = res.riwayats[res.riwayats.size - 1].bpm
                        tvbpmakhir.text = bpmakhir.toString()
                    } else{
                        tvbpmakhir.text = "0"
                    }

                }else{
                    Log.d("coba", "onResponse: gagal")
                }

            }

            override fun onFailure(call: Call<ResponModelRiwayat>, t: Throwable) {

                Log.d("coba", "onResponse: gagal")

            }
        })

        return root
    }

    // recycle view artikel
    val arrArtikel: ArrayList<Artikel>get(){
        var arr = ArrayList<Artikel>()
        val a1 = Artikel()
        a1.judul = "Cara Menjaga Kesehatan Jantung"
        a1.body = "..."
        a1.gambar = R.drawable.gambar_jantung

        val a2 = Artikel()
        a2.judul = "Jantung harus di monitoring"
        a2.body = "..."
        a2.gambar = R.drawable.gambar_jantung2

        val a3 = Artikel()
        a3.judul = "Hidup sehat tanpa penyakit jantung"
        a3.body = "..."
        a3.gambar = R.drawable.gambar_jantung3

        arr.add(a1)
        arr.add(a2)
        arr.add(a3)

        return arr
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}