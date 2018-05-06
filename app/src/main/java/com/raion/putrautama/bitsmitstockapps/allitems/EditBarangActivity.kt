package com.raion.putrautama.bitsmitstockapps.allitems

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.raion.putrautama.bitsmitstockapps.R
import com.raion.putrautama.bitsmitstockapps.kategori.Kategori
import com.raion.putrautama.bitsmitstockapps.model.Barang
import kotlinx.android.synthetic.main.activity_edit_barang.*
import java.io.IOException
import java.util.*

class EditBarangActivity : AppCompatActivity() {

    var kategori = ""
    var stock = 0
    var barangId = ""
    var foto = ""

    private val PICK_IMAGE_REQUEST = 1

    var photoUri: Uri? = null

    var listKategori = arrayListOf<Kategori>()
    val listKategoriName = arrayListOf<String>()

    lateinit var adapterKategori: ArrayAdapter<String>
    lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_barang)

        supportActionBar?.setTitle("Tambah Barang")
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        getListKategori()
        setupSpinnerKategori()
        setBarangData()


        add_photo.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
        }

        btnSend.setOnClickListener {
            showProgressDialog()
            if(photoUri == null){
                sendBarangToFirebase(foto)
            }else{
                uploadImageToFirebase()
            }

        }

    }

    private fun setBarangData() {
        val namaBarang = intent?.extras?.getString("nama")
        val harga = intent?.extras?.getInt("harga")
        barangId = intent?.extras!!.getString("barangId")
        foto = intent?.extras!!.getString("foto")
        stock = intent?.extras!!.getInt("jumlah")


        nama_barang_et.setText(namaBarang)
        harga_et.setText(harga.toString())

        Glide.with(this)
                .load(foto)
                .into(foto_barang)


    }


    private fun setupSpinnerKategori() {

        adapterKategori = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, listKategoriName)

        adapterKategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner_kategori.adapter = adapterKategori

        spinner_kategori.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, i: Int, id: Long) {
                spinner_kategori.setSelection(i)
                kategori = adapterKategori.getItem(i)
            }

        }
    }


    private fun uploadImageToFirebase() {
        val storageReference = FirebaseStorage.getInstance().reference
        val filepath = storageReference.child("barang").child(UUID.randomUUID().toString() + ".jpg")
        filepath.putFile(photoUri!!).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                sendBarangToFirebase(task.result.downloadUrl!!.toString())

            } else {
                alertDialog.dismiss()
                Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun sendBarangToFirebase(photoUrl: String) {

        val namaBarang = nama_barang_et.text.toString().trim()
        val harga = harga_et.text.toString().trim()
        val kategoriID = getKategoriId()

        val mRef = FirebaseDatabase.getInstance().reference.child("barang")

        val barang = Barang(photoUrl, harga.toInt(), stock, namaBarang, kategoriID, barangId)

        mRef.child(barangId).setValue(barang).addOnCompleteListener { task ->
            alertDialog.dismiss()
            if (task.isSuccessful) {
                Toast.makeText(this, "Barang berhasil diubah", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getKategoriName(kategoriId : String): String {
        for (data in listKategori) {
            if (data.kategoriId.equals(kategoriId)) {
                return data.nama
            }
        }
        return ""
    }

    private fun getKategoriId(): String {
        for (data in listKategori) {
            if (data.nama.equals(kategori)) {
                return data.kategoriId
            }
        }
        return ""
    }


    private fun getListKategori() {
        val mRef = FirebaseDatabase.getInstance().reference
        mRef.child("kategori").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                listKategori.clear()
                for (data in p0.children) {
                    val nama = data.child("nama").value.toString()
                    val key = data.key
                    listKategori.add(Kategori("", nama, key))
                    listKategoriName.add(nama)

                }

                adapterKategori.notifyDataSetChanged()
            }
        })


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.data != null) {

            photoUri = data.data


            try {
                val bitmap = MediaStore.Images.Media
                        .getBitmap(this.getContentResolver(), photoUri)

                foto_barang.setImageBitmap(bitmap)

            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    fun showProgressDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.getLayoutInflater()
        val dialogView = inflater.inflate(R.layout.progress_dialog, null)
        dialogBuilder.setView(dialogView)
        dialogBuilder.setCancelable(false)
        alertDialog = dialogBuilder.create()
        alertDialog.show()
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

