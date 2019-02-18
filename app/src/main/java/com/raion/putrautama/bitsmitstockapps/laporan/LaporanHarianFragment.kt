package com.raion.putrautama.bitsmitstockapps.laporan


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
import com.raion.putrautama.bitsmitstockapps.adapter.LaporanAdapter
import com.raion.putrautama.bitsmitstockapps.model.Transaksi
import kotlinx.android.synthetic.main.fragment_laporan_harian.*


class LaporanHarianFragment : Fragment() {

    var listTransaksi = arrayListOf<Transaksi>()
    lateinit var mAdapter : LaporanAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_laporan_harian, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_laporan_harian.layoutManager = LinearLayoutManager(context)
        val mRef = FirebaseDatabase.getInstance().reference
        mRef.child("transaksi").addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                listTransaksi.clear()
                for (data in p0.children){
                    val id = data.child("transaksi_id").value.toString()
                    val foto= data.child("foto").value.toString()
                    val namaBarang = data.child("nama_barang").value.toString()
                    val hargaPerSatuan = data.child("harga").value.toString()
                    val totalHarga = data.child("total_harga").value.toString()
                    val jumlahBarang = data.child("jumlah").value.toString()
                    val tglTransaksi = data.child("waktu").value.toString()

                    listTransaksi.add(Transaksi(id,namaBarang,jumlahBarang.toInt()
                            ,hargaPerSatuan.toInt(),totalHarga.toInt(),foto,tglTransaksi))
                }
                mAdapter = LaporanAdapter(listTransaksi,context)
                rv_laporan_harian.adapter = mAdapter
                mAdapter.notifyDataSetChanged()
            }
        })
    }
}
