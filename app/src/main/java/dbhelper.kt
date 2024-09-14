package com.example.crickethub

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.sql.Date
import java.sql.Time

class dbhelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(TABLE_USER_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS User")
        onCreate(db)
    }

    // Insert a new user into the database
    fun insertUser(userName: String, age: String, email: String, contact: String, password: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("user_name", userName)
            put("age", age)
            put("email", email)
            put("contact", contact)
            put("password", password)
        }
        return db.insert("User", null, values)
    }

    fun insertvenue(v_id: String, v_name: String): Long{
        val db=writableDatabase
        val values= ContentValues().apply{
            put("v_id", v_id)
            put("v_name", v_name)
        }
        return db.insert("venue",null,values)
    }

    fun insertbook(book_id: String, v_id: String, v_name: String, date: String, startTime: String, endtime: String, charge: String, no_of_player: String): Long {
        val db=writableDatabase
        val values=ContentValues().apply {
            put("book_id", book_id)
            put("v_id", v_id)
            put("v_name", v_name)
            put("date", date)
            put("start_time", startTime)
            put("endtime", endtime)
            put("charge", charge)
            put("no_of_player", no_of_player)
        }
        return db.insert("book",null,values)
    }


    // Check if a user exists by username and password
    fun checkUserCredentials(userName: String, password: String): Boolean {
        val db = readableDatabase
        val projection = arrayOf("user_id")
        val selection = "user_name = ? AND password = ?"
        val selectionArgs = arrayOf(userName, password)

        val cursor: Cursor? = db.query(
            "User",
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        val exists = cursor?.use { it.count > 0 }
        return exists ?: false
    }

    companion object {
        private const val DATABASE_NAME = "mydatabase.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_USER_CREATE = """
            CREATE TABLE User (
                user_id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_name TEXT,
                age INTEGER,
                email TEXT NOT NULL UNIQUE,
                contact INTEGER UNIQUE,
                password TEXT NOT NULL
            );
        """

        private const val TABLE_VENUE_CREATE= """
            CREATE TABLE venue(
                v_id Integer PRIMARY KEY, 
                 v_name TEXT
            );
        """

        private const val TABLE_BOOK_CREATE= """
            CREATE TABLE Book(
            book_id INTEGER PRIMARY KEY AUTOINCREMENT,
            FOREIGN KEY (v_id) REFERENCES venue(v_id),
            FOREIGN KEY (v_name) REFERENCES venue(v_name),
            date TEXT NOT NULL,
            startTime TEXT NOT NULL,
            endtime TEXT NOT NULL,
            charge INTEGER NOT NULL,
            no_of_player INTEGER NOT NULL
            );
        """
    }
}
