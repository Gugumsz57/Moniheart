package com.example.moniheart.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.moniheart.app.ApiConfig
import com.example.moniheart.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast
import com.example.moniheart.R
import com.example.moniheart.model.ResponModel

class RegisterActivity : AppCompatActivity() {
    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var editnama = binding.editNama
        var editemail = binding.editEmail
        var editjk = binding.editJk
        var editumur = binding.editEditUmur
        var editalamat = binding.editAlamat
        var editpass = binding.editPassword

        fun register(){
            if(editnama.text!!.isEmpty()){
                editnama.error = "Kolom tidak boleh kosong"
                editnama.requestFocus()
                return
            }

            if(editemail.text!!.isEmpty()){
                editemail.error = "Kolom tidak boleh kosong"
                editemail.requestFocus()
                return
            }

            if(editjk.text!!.isEmpty()){
                editjk.error = "Kolom tidak boleh kosong"
                editjk.requestFocus()
                return
            }

            if(editumur.text!!.isEmpty()){
                editumur.error = "Kolom tidak boleh kosong"
                editumur.requestFocus()
                return
            }

            if(editalamat.text!!.isEmpty()){
                editalamat.error = "Kolom tidak boleh kosong"
                editalamat.requestFocus()
                return
            }

            if(editpass.text!!.isEmpty()){
                editpass.error = "Kolom tidak boleh kosong"
                editpass.requestFocus()
                return
            }


            ApiConfig.ApiConfig.instanceRetrofit.register(editnama.text.toString(), editemail.text.toString(), editjk.text.toString(), editumur.text.toString(), editalamat.text.toString(), editpass.text.toString()).enqueue( object : Callback<ResponModel>{
                override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {

                    if (response.isSuccessful ) {
                        var respon = response.body()!!
                        if (respon.code == 200) {
                            // berhasil
                            Toast.makeText(
                                this@RegisterActivity,
                                "Success:"+ respon.success+" " + respon.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }else if(respon.code == 400){
                            Toast.makeText(
                                this@RegisterActivity,
                                "Error:" + respon.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else if (response.body() == null){
                        var respon = response.body()
                        Toast.makeText(
                            this@RegisterActivity,
                            "Gagal : " + "Email sudah ada",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                    Toast.makeText(this@RegisterActivity, "Error:" + t.message, Toast.LENGTH_SHORT).show()
                }


            })

        }

        binding.btnRegister.setOnClickListener {
            register()
        }









    }


}