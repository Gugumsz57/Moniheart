package com.example.moniheart.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.moniheart.NavigationActivity
import com.example.moniheart.databinding.ActivityProfileBinding
import com.example.moniheart.ui.login.AwalActivity
import com.example.moniheart.util.prefs

class ProfileActivity : AppCompatActivity() {
    private var _binding: ActivityProfileBinding? = null
    private val binding get() = _binding!!

    lateinit var s: prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        s = prefs(this)
        val tvnama: TextView = binding.tvNama
        val tvemail: TextView = binding.tvEmail
        val tvjk: TextView = binding.tvJk
        val tvumur: TextView = binding.tvUmur
        val tvalamat: TextView = binding.tvAlamat
        val btnlogout: Button = binding.btnLogout

        Log.d("nama", "onCreate: "+s.getString(s.email))

        tvnama.text = s.getString(s.nama)
        tvemail.text = s.getString(s.email)
        tvjk.text = s.getString(s.jk)
        tvumur.text = s.getString(s.umur)
        tvalamat.text = s.getString(s.alamat)

        btnlogout.setOnClickListener(){
            s.setIsLogin(false)
            val intent = Intent(this@ProfileActivity, AwalActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }



    }
}