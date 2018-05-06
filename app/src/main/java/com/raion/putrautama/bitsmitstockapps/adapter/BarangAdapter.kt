package com.raion.putrautama.bitsmitstockapps.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.raion.putrautama.bitsmitstockapps.R
import com.raion.putrautama.bitsmitstockapps.allitems.EditBarangActivity
import com.raion.putrautama.bitsmitstockapps.model.Barang




class BarangAdapter(val listBarang : ArrayList<Barang>, val context : Context) : RecyclerView.Adapter<BarangAdapter.MyViewHolder>() {

    private var position: Int = 0

    fun getPosition(): Int {
        return position
    }

    fun setPosition(position: Int) {
        this.position = position
    }

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

        holder?.itemView?.setOnLongClickListener {
            val popup = PopupMenu(context, holder.itemView)

            popup.inflate(R.menu.barang_menu)

            popup.setOnMenuItemClickListener {it ->

                    when (it.getItemId()) {
                        R.id.edit_item -> {
                            val intent = Intent(context, EditBarangActivity::class.java)
                            intent.putExtra("nama", listBarang.get(position).nama)
                            intent.putExtra("harga", listBarang.get(position).harga)
                            intent.putExtra("foto", listBarang.get(position).foto)
                            intent.putExtra("jumlah", listBarang.get(position).jumlah)
                            intent.putExtra("barangId", listBarang.get(position).barang_id)
                            context.startActivity(intent)
                        }
                        R.id.delete_item -> {
                            val alertBuilder = AlertDialog.Builder(context)
                                    .setMessage("Apakah Anda Yakin Ingin Menghapus?")
                                    .setPositiveButton("Ya", { dialog, which ->
                                        deleteBarang(listBarang.get(position).barang_id)
                                    })
                                    .setNegativeButton("Tidak", {dialog, which ->
                                        dialog.dismiss()
                                    })

                            val alertDialog = alertBuilder.create()
                            alertDialog.show()
                        }
                    }
                    false

            }

            popup.show()
            true
        }


    }

    private fun deleteBarang(barang_id: String) {
        val mRef = FirebaseDatabase.getInstance().reference
        mRef.child("barang").child(barang_id).removeValue()
        Toast.makeText(context, "Barang berhasil dihapus", Toast.LENGTH_SHORT).show()

    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val namaBarangTv = itemView?.findViewById<TextView>(R.id.nama_barang_tv)
        val hargaBarangTv = itemView?.findViewById<TextView>(R.id.harga_barang_tv)
        val stockBarangTv = itemView?.findViewById<TextView>(R.id.stock_barang_tv)
        val fotoBarang = itemView?.findViewById<ImageView>(R.id.barang_image)



    }
}