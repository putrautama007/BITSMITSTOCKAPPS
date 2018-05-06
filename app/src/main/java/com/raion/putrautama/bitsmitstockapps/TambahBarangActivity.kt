package com.raion.putrautama.bitsmitstockapps

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import kotlinx.android.synthetic.main.activity_tambah_barang.*
import java.io.IOException

class TambahBarangActivity : AppCompatActivity() {

    var stock = 0
    private val PICK_IMAGE_REQUEST = 1
    var photoUri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_barang)

        btnPlus.setOnClickListener{
            stock++
            kuantitas_tv.text = stock.toString()
        }

        btnMinus.setOnClickListener{
            if (stock != 0){
                stock--
            }
            kuantitas_tv.text = stock.toString()
        }

        add_photo.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == Activity.RESULT_OK && data != null && data.data != null){
            photoUri = data.data

            val cropIntent = Intent("com.android.camera.action.CROP")

            try {
                val bitmap = MediaStore.Images.Media
                        .getBitmap(this.getContentResolver(), photoUri)

                foto_barang.setImageBitmap(bitmap)
            }catch (e : IOException){
                e.printStackTrace()
            }
        }
    }
}
