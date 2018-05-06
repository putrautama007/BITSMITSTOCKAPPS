package com.raion.putrautama.bitsmitstockapps.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.raion.putrautama.bitsmitstockapps.R
import com.raion.putrautama.bitsmitstockapps.model.Barang

class BarangAdapter(val listBarang : ArrayList<Barang>) : RecyclerView.Adapter<BarangAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.list_barang, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listBarang.size
    }

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        holder?.namaBarangTv?.text = listBarang.get(position).nama

        val harga = "Rp "+listBarang.get(position).harga
        holder?.hargaBarangTv?.text = harga

        holder?.stockBarangTv?.text = listBarang.get(position).jumlah.toString()
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val namaBarangTv = itemView?.findViewById<TextView>(R.id.nama_barang_tv)
        val hargaBarangTv = itemView?.findViewById<TextView>(R.id.harga_barang_tv)
        val stockBarangTv = itemView?.findViewById<TextView>(R.id.stock_barang_tv)
    }
}