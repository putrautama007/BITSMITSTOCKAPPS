package com.raion.putrautama.bitsmitstockapps.kategori


import android.content.Intent
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
import com.raion.putrautama.bitsmitstockapps.R
import com.raion.putrautama.bitsmitstockapps.R.id.add_kategori
import com.raion.putrautama.bitsmitstockapps.R.id.recyclerView
import kotlinx.android.synthetic.main.fragment_kategori.*


class KategoriFragment : Fragment() {

    var listKategori = arrayListOf<Kategori>()
    lateinit var mAdapter : KategoriAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_kategori, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

                mAdapter = KategoriAdapter(listKategori)
                recyclerView.adapter = mAdapter
                mAdapter.notifyDataSetChanged()

            }
        })

        add_kategori.setOnClickListener{
            val intent = Intent(context, AddKategoriActivity::class.java)
            startActivity(intent)
        }

    }


}
