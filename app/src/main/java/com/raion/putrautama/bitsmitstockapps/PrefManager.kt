package com.raion.putrautama.bitsmitstockapps

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity

class PrefManager {

    lateinit var con : Context
    lateinit var pref : SharedPreferences

    constructor(con: Context) {
        this.con = con
        getSP()
    }

    private fun getSP() {
        pref = con.getSharedPreferences(con.getString(R.string.pref_name),Context.MODE_PRIVATE)
    }
    fun writeSP(){
        var editor : SharedPreferences.Editor = pref.edit()
        editor.putString(con.getString(R.string.pref_key), "NEXT")
        editor.apply()
    }
    fun checkPreference() : Boolean{
        var status : Boolean = false
        if (pref.getString(con.getString(R.string.pref_key),"null").equals("null")){
            status = false
        }else{
            status = true
        }
        return status
    }
    fun clearPreference(){
        pref.edit().clear().apply()

        con.startActivity(Intent(con,LoginActivity::class.java))
        (con as AppCompatActivity).finish()
    }
}