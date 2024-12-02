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

    fun saveDate(date: String) {
        val editor = share?.edit()
        editor?.putString("date", date)
        editor?.apply()
    }

    fun saveBookId(bookId: String) {
        val editor = share?.edit()
        editor?.putString("book_id", bookId)
        editor?.apply()
    }
    
    fun getBookId(): String? {
        return share?.getString("book_id", "")
    }

    fun savevname(vname: String) {
        val editor = share?.edit()
        editor?.putString("v_name", vname)
        editor?.apply()
    }

    fun getvname(): String? {
        return share?.getString("v_name", "")
    }

    fun savepayid(pay_id:String){
       val editor=share?.edit()
        editor?.putString("pay_id",pay_id)
        editor?.apply()
    }

    fun getpayid(): String? {
        return share?.getString("pay_id", "")
    }

    fun getdate(): String? {
        return share?.getString("date", "")
    }


    fun savePaymentMethod(paymentMethod: String) {
        val editor = share?.edit()
        editor?.putString("pay_method", paymentMethod)
        editor?.apply()
    }

    fun getPaymentMethod(): String? {
        return share?.getString("pay_method", "")
    }

}

