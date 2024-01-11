package com.example.moniheart.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.moniheart.NavigationActivity
import com.example.moniheart.databinding.ActivityAwalBinding
import com.example.moniheart.util.prefs
import java.util.*

class AwalActivity : AppCompatActivity() {
    private var _binding: ActivityAwalBinding? = null
    private val binding get() = _binding!!
    lateinit var timer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAwalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        val s = prefs(this)
        val status = s.getIsLogin()
        if (status == true){
            val intent = Intent(this@AwalActivity, NavigationActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
        Log.d("login", status.toString())

    }
}