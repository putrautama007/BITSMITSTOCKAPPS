package com.raion.putrautama.bitsmitstockapps

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.image_chooser_dialog.*

class ImageChooserDialog  : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.image_chooser_dialog, container,
                false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        camera.setOnClickListener {
            val intent = Intent()
            intent.putExtra("pickImage", "camera")
            targetFragment!!.onActivityResult(
                    targetRequestCode, Activity.RESULT_OK, intent)
            dialog.dismiss()
        }

        gallery.setOnClickListener{

        }

    }



}