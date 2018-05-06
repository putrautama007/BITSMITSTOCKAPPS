package com.raion.putrautama.bitsmitstockapps


import android.app.Activity
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
import com.raion.putrautama.bitsmitstockapps.adapter.BarangAdapter
import com.raion.putrautama.bitsmitstockapps.model.Barang
import kotlinx.android.synthetic.main.fragment_all_items.*

import java.util.ArrayList




class AllItemsFragment : Fragment() {

    var listItems: ArrayList<Barang> = arrayListOf()
    lateinit var mAdapter: BarangAdapter
    lateinit var nama: String

    val SORTING = 100

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_all_items, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(context)


        val mRef = FirebaseDatabase.getInstance().reference
        mRef.child("barang").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                listItems.clear()
                for (data in p0.children) {
                    nama = data.child("nama").value.toString()
                    val foto = data.child("foto").value.toString()
                    val jumlah = data.child("jumlah").value.toString()
                    val harga = data.child("harga").value.toString()

                    listItems.add(Barang(foto, harga, jumlah.toInt(), nama, ""))

                }

                mAdapter = BarangAdapter(listItems)
                recyclerView.adapter = mAdapter
                mAdapter.notifyDataSetChanged()

            }
        })

        urutkanBtn.setOnClickListener {
            showSortingDialog()
        }

        tambahBtn.setOnClickListener {
            startActivity(Intent(context, TambahBarangActivity::class.java))
        }

    }

    private fun showSortingDialog() {
        val sortingDialog = SortingDialog.newInstance()
        sortingDialog.setTargetFragment(this, SORTING);
        sortingDialog.show(fragmentManager,
                "add_photo_dialog_fragment")
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            SORTING -> {
                if (resultCode == Activity.RESULT_OK) {
                    var sortMode = data?.getStringExtra("sorting")
                    when (sortMode) {
                        "nama", "jumlah", "harga" -> {
                            sortItemsAsc(sortMode)
                        }
                        else -> {
                            sortItemDsc(sortMode)
                        }
                    }
                }
            }
        }
    }

    private fun sortItemDsc(sortMode: String?) {
        var sort = ""
        if (sortMode.equals("jumlah1")) {
            sort = "jumlah"
        } else if (sortMode.equals("harga1")) {
            sort = "harga"
        }

        val layoutManager = LinearLayoutManager(context)
        layoutManager.setReverseLayout(true)
        layoutManager.setStackFromEnd(true)
        recyclerView.layoutManager = layoutManager

        val mRef = FirebaseDatabase.getInstance().reference
        mRef.child("barang").orderByChild(sort).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                listItems.clear()
                for (data in p0.children) {
                    nama = data.child("nama").value.toString()
                    val foto = data.child("foto").value.toString()
                    val jumlah = data.child("jumlah").value.toString()
                    val harga = data.child("harga").value.toString()

                    listItems.add(Barang(foto, harga, jumlah.toInt(), nama, ""))

                }

                mAdapter = BarangAdapter(listItems)
                recyclerView.adapter = mAdapter
                mAdapter.notifyDataSetChanged()

            }
        })

    }

    private fun sortItemsAsc(sort: String) {

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        val mRef = FirebaseDatabase.getInstance().reference
        mRef.child("barang").orderByChild(sort).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                listItems.clear()
                for (data in p0.children) {
                    nama = data.child("nama").value.toString()
                    val foto = data.child("foto").value.toString()
                    val jumlah = data.child("jumlah").value.toString()
                    val harga = data.child("harga").value.toString()

                    listItems.add(Barang(foto, harga, jumlah.toInt(), nama, ""))

                }

                mAdapter = BarangAdapter(listItems)
                recyclerView.adapter = mAdapter
                mAdapter.notifyDataSetChanged()

            }
        })
    }

}