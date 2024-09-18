package com.example.crickethub.com.example.crickethub

import android.content.Context
import android.content.SharedPreferences

class SharePrefrence(context: Context) {
    private val share : SharedPreferences? = context.getSharedPreferences("CricketHub",Context.MODE_PRIVATE)

    fun storeUserId(userid:String){
        val editor = share?.edit()
        if (editor != null) {
            editor.putString("user_id",userid)
            editor.apply()
        }

    }

    fun getUserId(): String? {
        return share?.getString("user_id","")
    }

    fun venuename(name: String){
        val editor = share?.edit()
        if (editor != null) {
            editor.putString("venuName",name)
            editor.apply()
        }

    }



}