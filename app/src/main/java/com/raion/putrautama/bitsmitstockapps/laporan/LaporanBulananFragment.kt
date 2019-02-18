package com.raion.putrautama.bitsmitstockapps.laporan


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.raion.putrautama.bitsmitstockapps.R


class LaporanBulananFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_laporan_bulanan, container, false)
    }


}
