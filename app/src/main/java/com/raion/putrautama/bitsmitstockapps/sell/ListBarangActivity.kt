package com.raion.putrautama.bitsmitstockapps.sell

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.raion.putrautama.bitsmitstockapps.R
import com.raion.putrautama.bitsmitstockapps.model.Barang

class ListBarangActivity : AppCompatActivity() {

    var listItems: ArrayList<Barang> = arrayListOf()
    lateinit var mAdapter : ListBarangAdapter
    lateinit var nama : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_barang)

        val namaKategori = intent?.extras?.getString("kategoriName")
        val idKategori = intent?.extras?.getString("kategoriId")

        supportActionBar?.title = namaKategori
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)


        val mRef = FirebaseDatabase.getInstance().reference
        mRef.child("barang").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                listItems.clear()
                for (data in p0.children) {
                    if(data.child("kategori_id").value.toString().equals(idKategori)){
                        nama = data.child("nama").value.toString()
                        val barandId = data.child("barang_id").value.toString()
                        val foto = data.child("foto").value.toString()
                        val jumlah = data.child("jumlah").value.toString()
                        val harga = data.child("harga").value.toString()

                        listItems.add(Barang(foto, harga.toInt(), jumlah.toInt(), nama, "", barandId))
                    }
                }

                mAdapter = ListBarangAdapter(listItems, {barang -> intentToSell(barang) })
                recyclerView.adapter = mAdapter
                mAdapter.notifyDataSetChanged()

            }
        })

    }

    fun intentToSell(barang: Barang){
        val intent =  Intent(this, SellActivity::class.java)
        intent.putExtra("barangId", barang.barang_id)
        intent.putExtra("barangName", barang.nama)
        intent.putExtra("barangFoto", barang.foto)
        intent.putExtra("barangJumlah", barang.jumlah)
        intent.putExtra("barangHarga", barang.harga)
        startActivity(intent)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }


}
