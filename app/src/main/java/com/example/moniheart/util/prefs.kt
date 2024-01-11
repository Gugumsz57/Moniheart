package com.example.moniheart.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class prefs(activity: Activity) {
    private var sp : SharedPreferences? = null
    private val login = "login"
    val user_id = 0
    val nama = "nama"
    val email = "email"
    val jk = "jk"
    val umur = "umur"
    val alamat = "alamat"


    init{
        sp = activity.getSharedPreferences("MYPREF", Context.MODE_PRIVATE)
    }

    fun setIsLogin(value: Boolean){
        sp!!.edit().putBoolean(login, value).apply()
    }

    fun getIsLogin(): Boolean {
        return sp!!.getBoolean(login,false)
    }

    fun setString(key:String, value: String){
        sp!!.edit()!!.putString(key, value).apply()
    }

    fun getString(key:String): String{
        return sp!!.getString(key, "")!!
    }

    fun setInt(key:String, value: Int){
        sp!!.edit().putInt(key,value).apply()
    }

    fun setInt2(KEY_NAME: String, value: Int) {
        val editor: SharedPreferences.Editor = sp!!.edit()
        editor.putInt(KEY_NAME, value)
        editor.commit()
    }


    fun getInt(key: String): Int{
        return sp!!.getInt(key, 0)
    }

    fun getInt2(KEY_NAME: String): Int {

        return sp!!.getInt(KEY_NAME, 0)
    }
}
