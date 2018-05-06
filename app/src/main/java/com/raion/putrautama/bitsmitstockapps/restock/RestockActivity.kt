package com.raion.putrautama.bitsmitstockapps.restock

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.raion.putrautama.bitsmitstockapps.R
import kotlinx.android.synthetic.main.activity_restock.*


class RestockActivity : AppCompatActivity() {

    var stock : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restock)

        val idKategori = intent?.extras?.getString("kategoriId")
        val idBarang = intent?.extras?.getString("barangId")
        val namaBarang = intent?.extras?.getString("barangName")
        val jumlahBarang = intent?.extras?.getInt("barangJumlah")
        val hargaBarang = intent?.extras?.getInt("barangHarga")
        val fotoBarang = intent?.extras?.getString("barangFoto")

        supportActionBar?.title = namaBarang
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Glide.with(this)
                .load(fotoBarang)
                .into(foto_barang)

        val harga = "Rp " + hargaBarang
        harga_barang_tv.setText(harga)
        kuantitas_tv.setText(jumlahBarang.toString())

        stock = jumlahBarang!!

        btnPlus.setOnClickListener {
            stock++
            kuantitas_tv.text = stock.toString()
        }

        btnMinus.setOnClickListener {
            if (stock != 0) {
                stock--
            }
            kuantitas_tv.text = stock.toString()
        }

        btnSend.setOnClickListener {
            updateStockBarang(idBarang!!)
        }

    }

    private fun updateStockBarang(idBarang: String) {
        val mRef = FirebaseDatabase.getInstance().reference.child("barang")

        mRef.child(idBarang).child("jumlah").setValue(stock).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Stock barang berhasil diubah", Toast.LENGTH_SHORT).show()
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
