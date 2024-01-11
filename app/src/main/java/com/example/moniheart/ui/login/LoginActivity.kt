package com.example.moniheart.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.moniheart.NavigationActivity
import com.example.moniheart.app.ApiConfig
import com.example.moniheart.databinding.ActivityLoginBinding
import com.example.moniheart.model.ResponModel
import com.example.moniheart.util.prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val s = prefs(this)
        val editemail = binding.editEmail
        val editpass = binding.editPassword

        fun login(){

            if(editemail.text!!.isEmpty()){
                editemail.error = "Kolom tidak boleh kosong"
                editemail.requestFocus()
                return
            }

            if(editpass.text!!.isEmpty()){
                editpass.error = "Kolom tidak boleh kosong"
                editpass.requestFocus()
                return
            }

            binding.pb.visibility = View.VISIBLE

            ApiConfig.ApiConfig.instanceRetrofit.login(editemail.text.toString(), editpass.text.toString()).enqueue( object :
                Callback<ResponModel> {

                override fun onResponse(
                    call: Call<ResponModel>,
                    response: Response<ResponModel>
                ) {
                    binding.pb.visibility = View.GONE
                    if (response.body() != null ) {
                        if (response.isSuccessful) {
                            var respon = response.body()!!
                            // berhasil
                            s.setIsLogin(true)
                            s.setInt2("user_id", respon.data.id)
                            s.setString(s.nama, respon.data.nama)
                            s.setString(s.email, respon.data.email)
                            s.setString(s.jk, respon.data.jk)
                            s.setString(s.umur, respon.data.umur)
                            s.setString(s.alamat, respon.data.alamat)
                            val intent = Intent(this@LoginActivity, NavigationActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(intent)
                            finish()
                            Toast.makeText(
                                this@LoginActivity,
                                "Selamat datang " + respon.data.nama,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Email atau Password salah",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Error:" + t.message, Toast.LENGTH_SHORT).show()
                    binding.pb.visibility = View.GONE
                }
            })

        }

        binding.btnLogin.setOnClickListener {
            login()
        }


    }
}