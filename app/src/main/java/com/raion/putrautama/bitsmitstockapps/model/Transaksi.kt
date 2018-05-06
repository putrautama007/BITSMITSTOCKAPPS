package com.raion.putrautama.bitsmitstockapps.model

data class Transaksi(val transaksi_id : String, val nama_barang : String,
                     val jumlah : Int, val harga : Int, val total_harga : Int,
                     val foto : String, val waktu : String)