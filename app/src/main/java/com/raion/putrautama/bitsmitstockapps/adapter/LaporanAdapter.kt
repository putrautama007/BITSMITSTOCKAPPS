package com.raion.putrautama.bitsmitstockapps.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RatingBar
import android.widget.TextView
import com.raion.putrautama.bitsmitstockapps.R
import com.raion.putrautama.bitsmitstockapps.model.Transaksi

class LaporanAdapter(val listTransaksi : ArrayList<Transaksi>, val context : Context) : RecyclerView.Adapter<LaporanAdapter.LaporanViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): LaporanAdapter.LaporanViewHolder {
        val view = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.list_laporan, parent, false)
        return LaporanAdapter.LaporanViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listTransaksi.size
    }

    override fun onBindViewHolder(holder: LaporanAdapter.LaporanViewHolder?, position: Int) {
        holder?.namaBarang?.text = listTransaksi.get(position).nama_barang
        val harga = listTransaksi.get(position).harga.toString()
        val total = listTransaksi.get(position).total_harga.toString()
        holder?.hargaPerSatuan?.text = "RP. $harga ,-/pcs"
        holder?.totalHarga?.text = "Rp. $total"
        holder?.jumlahBarang?.text = listTransaksi.get(position).jumlah.toString()
        holder?.tglTransaksi?.text = listTransaksi.get(position).waktu
    }

    class LaporanViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val namaBarang = itemView.findViewById<TextView>(R.id.nama_item)
        val hargaPerSatuan = itemView.findViewById<TextView>(R.id.harga_barang)
        val totalHarga = itemView.findViewById<TextView>(R.id.total_harga_barang)
        val jumlahBarang = itemView.findViewById<TextView>(R.id.jumlah)
        val tglTransaksi = itemView.findViewById<TextView>(R.id.tgl_transaksi)
    }
}