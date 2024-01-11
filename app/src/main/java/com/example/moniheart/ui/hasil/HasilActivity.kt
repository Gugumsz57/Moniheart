package com.example.moniheart.ui.hasil

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.moniheart.NavigationActivity
import com.example.moniheart.R
import com.example.moniheart.app.ApiConfig
import com.example.moniheart.databinding.ActivityHasilBinding
import com.example.moniheart.model.ResponModelRiwayat
import com.example.moniheart.ui.pengukuran.PengukuranFragment
import com.example.moniheart.ui.riwayat.RiwayatFragment
import com.example.moniheart.util.prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HasilActivity : AppCompatActivity() {
    private var _binding: ActivityHasilBinding? = null
    private val binding get() = _binding!!
    lateinit var tvbpm: TextView
    lateinit var tvkondisi: TextView
    lateinit var tvwaktu: TextView
    lateinit var btnhapus: Button
    lateinit var btnsimpan: Button

    lateinit var getkondisi: String

    lateinit var s: prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHasilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        s = prefs(this)

        tvbpm = binding.tvBpm
        tvkondisi = binding.tvKondisi
        tvwaktu = binding.tvWaktu
        btnhapus = binding.btnHapus
        btnsimpan = binding.btnSimpan

        val items = listOf("istirahat", "olahraga", "duduk", "bekerja")
        val adapter = ArrayAdapter(this@HasilActivity, R.layout.dropdown_item, items)
        binding.materialSpiner.setAdapter(adapter)

        val getuser_id = s.getInt2("user_id")
        val getbpm = intent.getIntExtra("bpm", 0)
        if (getbpm <60){
            getkondisi = "Rendah"
        }else if(getbpm > 60 && getbpm < 100){
            getkondisi = "Normal"
        }else{
            getkondisi = "Tinggi"
        }
        val getwaktu = intent.getStringExtra("waktu")

        tvbpm.setText(getbpm.toString())
        tvkondisi.setText(getkondisi)
        tvwaktu.setText(getwaktu)

        btnhapus.setOnClickListener{
            val intent = Intent(this@HasilActivity, NavigationActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }

        fun postriwayat(){

            val mySpinner = binding.materialSpiner as Spinner
            val catatan = mySpinner.selectedItem.toString()

            ApiConfig.ApiConfig.instanceRetrofit.riwayat(getuser_id.toString(), getbpm.toString(), getkondisi.toString(), getwaktu.toString(), catatan).enqueue( object :
                Callback<ResponModelRiwayat> {
                override fun onResponse(call: Call<ResponModelRiwayat>, response: Response<ResponModelRiwayat>) {

                    if (response.isSuccessful ) {
                        var respon = response.body()!!
                        if (respon.code == 200) {
                            // berhasil
                            val intent = Intent(this@HasilActivity, RiwayatFragment::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

                            Toast.makeText(
                                this@HasilActivity,
                                "Success:"+ respon.success+" " + respon.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }else if(respon.code == 400){
                            Toast.makeText(
                                this@HasilActivity,
                                "Error:" + respon.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else if (response.body() == null){
                        var respon = response.body()
                        Toast.makeText(
                            this@HasilActivity,
                            "Gagal : " + "Email sudah ada",
                            Toast.LENGTH_SHORT

                        ).show()
                    }
                }

                override fun onFailure(call: Call<ResponModelRiwayat>, t: Throwable) {
                    Toast.makeText(this@HasilActivity, "Error:" + t.message, Toast.LENGTH_SHORT).show()
                }


            })

        }

        btnsimpan.setOnClickListener{

            postriwayat()

        }
    }
}