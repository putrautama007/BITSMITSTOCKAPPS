package com.raion.putrautama.bitsmitstockapps.kategori

import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.raion.putrautama.bitsmitstockapps.ItemOffsetDecoration
import com.raion.putrautama.bitsmitstockapps.R
import kotlinx.android.synthetic.main.kategori_dialog.*


class KategoriIconDialog : DialogFragment() {

    interface OnIconSelected{
        fun sendIconUrl(url : String)
    }

    lateinit var mListener : OnIconSelected

    var iconList: ArrayList<Icon> = arrayListOf()
    lateinit var mAdapter: IconAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.kategori_dialog, container, false)
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_close.setOnClickListener {
            dialog.dismiss()
        }

        recyclerView.layoutManager = GridLayoutManager(activity, 4)
        recyclerView.addItemDecoration(ItemOffsetDecoration(4, 30, false))

        val mRef = FirebaseDatabase.getInstance().reference
        mRef.child("icon").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                iconList.clear()
                for (data in p0.children) {
                    val iconId = data.key
                    val iconUrl = data.child("icon_url").value.toString()

                    iconList.add(Icon(iconId, iconUrl))

                }

                mAdapter = IconAdapter(iconList, { icon: Icon -> sendKategoriIcon(icon) })
                recyclerView.adapter = mAdapter
                mAdapter.notifyDataSetChanged()

            }
        })

    }

    fun sendKategoriIcon(icon : Icon){
        mListener.sendIconUrl(icon.iconUrl)
        dialog.dismiss()
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.window!!.setWindowAnimations(R.style.DialogAnimation)
        return dialog
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setLayout(width, height)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            this.mListener = activity as OnIconSelected
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement OnCompleteListener")
        }

    }


}