package com.raion.putrautama.bitsmitstockapps.kategori

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.raion.putrautama.bitsmitstockapps.R


class KategoriAdapter(val listBarang : ArrayList<Kategori>) : RecyclerView.Adapter<KategoriAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.kategori_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listBarang.size
    }

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        Glide.with(holder?.itemView?.context)
                .load(listBarang.get(position).icon)
                .into(holder?.iconImageView)

        holder?.kategoriTextView?.text = listBarang.get(position).nama

    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val iconImageView = itemView?.findViewById<ImageView>(R.id.kategori_icon)
        val kategoriTextView = itemView?.findViewById<TextView>(R.id.kategori_tv)


    }
}