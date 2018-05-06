package com.raion.putrautama.bitsmitstockapps.sell


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.raion.putrautama.bitsmitstockapps.R
import com.raion.putrautama.bitsmitstockapps.kategori.Kategori


class SellFragment : Fragment() {

    var listKategori = arrayListOf<Kategori>()
    lateinit var mAdapter : Kategori2Adapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sell, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        val mRef = FirebaseDatabase.getInstance().reference
        mRef.child("kategori").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                listKategori.clear()
                for (data in p0.children) {
                    val nama = data.child("nama").value.toString()
                    val icon = data.child("icon").value.toString()

                    listKategori.add(Kategori(icon, nama, data.key))

                }

                mAdapter = Kategori2Adapter(listKategori,
                        {kategori -> intentToListBarang(kategori) })
                recyclerView.adapter = mAdapter
                mAdapter.notifyDataSetChanged()

            }
        })

    }

    fun intentToListBarang(kategori: Kategori){
        val intent =  Intent(context, ListBarangActivity::class.java)
        intent.putExtra("kategoriName", kategori.nama)
        intent.putExtra("kategoriId", kategori.kategoriId)
        startActivity(intent)

    }

}
