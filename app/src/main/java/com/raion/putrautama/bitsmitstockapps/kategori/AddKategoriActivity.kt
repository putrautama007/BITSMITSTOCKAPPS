package com.raion.putrautama.bitsmitstockapps.kategori

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.raion.putrautama.bitsmitstockapps.R
import kotlinx.android.synthetic.main.activity_add_kategori.*


class AddKategoriActivity : AppCompatActivity(), KategoriIconDialog.OnIconSelected {

    var kategoriIcon = ""

    override fun sendIconUrl(url: String) {
        Glide.with(this)
                .load(url)
                .into(kategori_icon)
        kategoriIcon = url
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_kategori)

        supportActionBar?.setTitle("Tambah Kategori")
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        kategori_icon.setOnClickListener{
            showKategoriDialog()
        }

        button_selesai.setOnClickListener{
            addNewKategori()
        }


    }

    private fun addNewKategori() {
        val formRef = FirebaseDatabase.getInstance().reference.child("kategori")

        val nama = kategori_et.text.toString().trim()
        val key = formRef.push().key

        if(TextUtils.isEmpty(nama)){
            Toast.makeText(this, "Masukkan Nama Kategori", Toast.LENGTH_SHORT).show()
        }else{
            val kategori = Kategori(kategoriIcon, nama, key)

            formRef.child(key).setValue(kategori).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Barang berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }

    }

    private fun showKategoriDialog() {
        val kategoriIconDialog = KategoriIconDialog()
        kategoriIconDialog.show(fragmentManager,
                "add_photo_dialog_fragment")
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
