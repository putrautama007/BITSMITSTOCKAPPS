package com.raion.putrautama.bitsmitstockapps

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class SectionsPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> {
                return AllItemsFragment()
            }
            1 -> {
                return SellFragment()
            }
            2 -> {
                return RestockFragment()
            }
            3 -> {
                return KategoriFragment()
            }
            else -> {
                return AllItemsFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 4;
    }
}