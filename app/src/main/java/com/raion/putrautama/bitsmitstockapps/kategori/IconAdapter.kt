package com.raion.putrautama.bitsmitstockapps.kategori

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.raion.putrautama.bitsmitstockapps.R

class IconAdapter(val listIcon : ArrayList<Icon>, val clickListener: (Icon) -> Unit) : RecyclerView.Adapter<IconAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.icon_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listIcon.size
    }

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        Glide.with(holder?.itemView?.context)
                .load(listIcon.get(position).iconUrl)
                .into(holder?.iconImageView)

        holder?.bind(listIcon[position], clickListener)

    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val iconImageView = itemView?.findViewById<ImageView>(R.id.kategori_icon)

        fun bind(icon: Icon,
                 clickListener: (Icon) -> Unit) {

            itemView.setOnClickListener{clickListener(icon)}

        }

    }
}