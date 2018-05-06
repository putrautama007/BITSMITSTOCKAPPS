package com.raion.putrautama.bitsmitstockapps.sell

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.raion.putrautama.bitsmitstockapps.R
import com.raion.putrautama.bitsmitstockapps.model.Barang

class ListBarangAdapter(val listBarang : ArrayList<Barang>, val clickListener: (Barang) -> Unit) : RecyclerView.Adapter<ListBarangAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.list_barang, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listBarang.size
    }

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        Glide.with(holder?.itemView?.context)
                .load(listBarang.get(position).foto)
                .into(holder?.fotoBarang)

        holder?.namaBarangTv?.text = listBarang.get(position).nama

        val harga = "Rp "+listBarang.get(position).harga
        holder?.hargaBarangTv?.text = harga

        holder?.stockBarangTv?.text = listBarang.get(position).jumlah.toString()

        holder?.bind(listBarang.get(position), clickListener)

    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val namaBarangTv = itemView?.findViewById<TextView>(R.id.nama_barang_tv)
        val hargaBarangTv = itemView?.findViewById<TextView>(R.id.harga_barang_tv)
        val stockBarangTv = itemView?.findViewById<TextView>(R.id.stock_barang_tv)
        val fotoBarang = itemView?.findViewById<ImageView>(R.id.barang_image)

        fun bind(barang: Barang,
                 clickListener: (Barang) -> Unit) {

            itemView.setOnClickListener{clickListener(barang)}

        }

    }
}