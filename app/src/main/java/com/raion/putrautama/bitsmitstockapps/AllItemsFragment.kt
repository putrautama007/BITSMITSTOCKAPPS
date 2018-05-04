package com.raion.putrautama.bitsmitstockapps

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.raion.putrautama.bitsmitstockapps.adapter.BarangAdapter
import com.raion.putrautama.bitsmitstockapps.model.Barang
import kotlinx.android.synthetic.main.fragment_all_items.*

class AllItemsFragment : Fragment() {

    var listItems: ArrayList<Barang> = arrayListOf()
    lateinit var mAdapter : BarangAdapter
    lateinit var nama : String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_all_items, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(context)


        val mRef = FirebaseDatabase.getInstance().reference
        mRef.child("barang").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot?) {
                listItems.clear()
                for (data in p0!!.children) {

                    for(barang in data.children){
                        nama = barang.child("nama_barang").value.toString()
                        val foto = barang.child("foto").value.toString()
                        val jumlah = barang.child("jumlah").value.toString()
                        val harga = barang.child("harga").value.toString()

                        listItems.add(Barang(foto, harga, jumlah, nama))

                    }

                }

                mAdapter = BarangAdapter(listItems)
                recyclerView.adapter = mAdapter
                mAdapter.notifyDataSetChanged()

            }
        })


    }
}
