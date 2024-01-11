package com.example.moniheart.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moniheart.R
import com.example.moniheart.model.Artikel
import com.example.moniheart.model.Riwayat

class AdapterArtikel(var data: ArrayList<Artikel>):RecyclerView.Adapter<AdapterArtikel.Holder>() {

    class Holder(view: View):RecyclerView.ViewHolder(view){

        val tvjudul = view.findViewById<TextView>(R.id.tv_judul_artikel)
        val ivartikel = view.findViewById<ImageView>(R.id.iv_artikel)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_artikel, parent,false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.tvjudul.text = data[position].judul
        holder.ivartikel.setImageResource(data[position].gambar)

    }

    override fun getItemCount(): Int {
        return data.size
    }
}