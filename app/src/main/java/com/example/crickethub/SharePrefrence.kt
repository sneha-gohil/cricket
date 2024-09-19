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

    fun saveCharge(charge: String) {
        val editor = share?.edit()
        editor?.putString("charge", charge)
        editor?.apply()
    }

    fun getCharge(): String? {
        return share?.getString("charge", "")
    }

    fun saveNoOfPlayers(noOfPlayers: String) {
        val editor = share?.edit()
        editor?.putString("no_of_players", noOfPlayers)
        editor?.apply()
    }

    fun getNoOfPlayers(): String? {
        return share?.getString("no_of_players", "")
    }



}