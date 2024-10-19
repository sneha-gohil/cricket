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

    fun getvenuename(name: String): String? {
        return share?.getString("v_name","")
    }

    fun saveDate(date: String) {
        val editor = share?.edit()
        editor?.putString("date", date)
        editor?.apply()
    }

    fun getDate(): String? {
        return share?.getString("date", "")
    }

    fun saveStartTime(startTime: String) {
        val editor = share?.edit()
        editor?.putString("start_time", startTime)
        editor?.apply()
    }

    fun getStartTime(): String? {
        return share?.getString("start_time", "")
    }

    fun saveEndTime(endTime: String) {
        val editor = share?.edit()
        editor?.putString("end_time", endTime)
        editor?.apply()
    }

    fun getEndTime(): String? {
        return share?.getString("end_time", "")
    }


    fun saveNoOfPlayers(noOfPlayers: String) {
        val editor = share?.edit()
        editor?.putString("no_of_players", noOfPlayers)
        editor?.apply()
    }

    fun getNoOfPlayers(): String? {
        return share?.getString("no_of_players", "")
    }

    fun charges(charge: String) {
        val editor = share?.edit()
        editor?.putString("charge", charge)
        editor?.apply()
    }
    fun charges(): String? {
        return share?.getString("charges", "")
    }

    fun saveBookId(bookId: String) {
        val editor = share?.edit()
        editor?.putString("book_id", bookId)
        editor?.apply()
    }

    fun getBookId(): String? {
        return share?.getString("book_id", "")
    }

    fun savepayid(pay_id:String){
       val editor=share?.edit()
        editor?.putString("pay_id",pay_id)
        editor?.apply()
    }

    fun getpayid(): String? {
        return share?.getString("pay_id", "")
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

