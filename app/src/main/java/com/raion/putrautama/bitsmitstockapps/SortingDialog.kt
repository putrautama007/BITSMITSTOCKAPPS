package com.raion.putrautama.bitsmitstockapps

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.sorting_dialog.*

class SortingDialog : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tutup_btn.setOnClickListener{
            dialog.dismiss()
        }

        nama_barang_tv.sendDataToFragmentAllItems("nama")
        stock_terbanyak_tv.sendDataToFragmentAllItems("jumlah1")
        stock_tersedikit_tv.sendDataToFragmentAllItems("jumlah")
        harga_tertinggi_tv.sendDataToFragmentAllItems("harga1")
        harga_terendah_tv.sendDataToFragmentAllItems("harga")
    }

    companion object {
        fun newInstance() : SortingDialog{
            return  SortingDialog()
        }
    }

    fun TextView.sendDataToFragmentAllItems(sorting : String){
        this.setOnClickListener{
            val intent = Intent()
            intent.putExtra("sorting",sorting)
            targetFragment!!.onActivityResult(
                    targetRequestCode, android.app.Activity.RESULT_OK, intent)
            dialog.dismiss()
        }
    }
}