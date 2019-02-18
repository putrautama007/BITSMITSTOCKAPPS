package com.raion.putrautama.bitsmitstockapps.sell

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.raion.putrautama.bitsmitstockapps.R
import com.raion.putrautama.bitsmitstockapps.model.Transaksi
import kotlinx.android.synthetic.main.activity_sell.*
import java.util.*
import java.text.SimpleDateFormat


class SellActivity : AppCompatActivity() {

    var terjual = 0
    var namaBarang : String?= ""
    var hargaBarang : Int? = 0
    var fotoBarang : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sell)

        val idBarang = intent?.extras?.getString("barangId")
        namaBarang = intent?.extras?.getString("barangName")
        val jumlahBarang = intent?.extras?.getInt("barangJumlah")
        hargaBarang = intent?.extras?.getInt("barangHarga")
        fotoBarang = intent?.extras?.getString("barangFoto")

        supportActionBar?.title = namaBarang
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Glide.with(this)
                .load(fotoBarang)
                .into(foto_barang)

        val harga = "Rp "+hargaBarang
        harga_barang_tv.setText(harga)
        kuantitas_tv.setText(jumlahBarang.toString())

        btnPlus.setOnClickListener {
            if(terjual < jumlahBarang!!.toInt()){
                terjual++
            }
            terjual_tv.text = terjual.toString()
        }

        btnMinus.setOnClickListener {
            if (terjual != 0) {
                terjual--
            }
            terjual_tv.text = terjual.toString()
        }

        btnSend.setOnClickListener{
            updateStockBarang(idBarang!!, jumlahBarang!!)
        }

    }

    private fun updateStockBarang(idBarang: String, jumlahBarang: Int) {
        val mRef = FirebaseDatabase.getInstance().reference.child("barang")

        mRef.child(idBarang).child("jumlah").setValue(jumlahBarang - terjual).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                sendSellTransaction()
            } else {
                Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun sendSellTransaction() {
        val mRef = FirebaseDatabase.getInstance().reference.child("transaksi")

        val totalHarga = terjual * hargaBarang!!
        val waktu = Calendar.getInstance().time
        val dateformat = SimpleDateFormat("yyyy-MM-dd")
        val date = dateformat.format(waktu)

        val transaksi_id = mRef.push().key

        val transaksi = Transaksi(transaksi_id, namaBarang!!, terjual, hargaBarang!!, totalHarga, fotoBarang!!, date)

        mRef.child(transaksi_id).setValue(transaksi).addOnCompleteListener{task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Transaksi berhasil disimpan", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }

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
