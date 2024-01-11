package com.example.moniheart.adapter

import android.location.GnssAntennaInfo.Listener
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moniheart.R
import com.example.moniheart.model.Artikel
import com.example.moniheart.model.Riwayat

class AdapterRiwayat(var data:ArrayList<Riwayat>):RecyclerView.Adapter<AdapterRiwayat.Holder>() {

    class Holder(view: View):RecyclerView.ViewHolder(view){

        val tvbpm = view.findViewById<TextView>(R.id.tv_Riwayatbpm)
        val tvkondisi = view.findViewById<TextView>(R.id.tv_Riwayatkondisi)
        val tvwaktu = view.findViewById<TextView>(R.id.tv_Riwayatwaktu)
        val tvcatatan = view.findViewById<TextView>(R.id.tv_Riwayatcatatan)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_riwayat, parent,false)
        return Holder(view)

    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.tvbpm.text = data[position].bpm.toString()
        //Log.d("BPM ", "onBindViewHolder: "+data[position].bpm)
        holder.tvkondisi.text = data[position].kondisi
        holder.tvwaktu.text = data[position].waktu
        holder.tvcatatan.text = data[position].catatan

    }

    override fun getItemCount(): Int {
        return data.size
    }
}