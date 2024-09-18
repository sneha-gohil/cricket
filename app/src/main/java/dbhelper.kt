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
        db.execSQL(TABLE_BOOK_CREATE)
        db.execSQL(TABLE_PAYMENT_CREATE)
        db.execSQL(TABLE_PLAYER_CREATE)
        db.execSQL(TABLE_BILL_CREATE)
        db.execSQL(TABLE_VENUE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS player")
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


    // Check if a user exists by username and password
    fun checkUserCredentials(userName: String, password: String): String? {
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
        var user_id = ""
        if (cursor != null) {
            if (cursor.moveToFirst()){
                 user_id = cursor?.getString(cursor.getColumnIndexOrThrow("user_id")).toString()
                return user_id
            }else{
                return ""
            }
        }
        return user_id
    }

    //inserting venue table details
    fun insertvenue(v_id: String, v_name: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("v_id", v_id)
            put("v_name", v_name)
        }
        return db.insert("venue", null, values)
    }


    //inserting booking table venues
    fun insertbook(v_name: String, date: String, startTime: String, endtime: String, charge: String, no_of_player: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("v_name", v_name)
            put("date", date)
            put("start_time", startTime)
            put("end_time", endtime)
            put("charge", charge)
            put("no_of_player", no_of_player)
        }
        return db.insert("book", null, values)
    }

    //selecting by venue name
    fun getVenueIdByName(v_name: String): String? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT v_id FROM venue WHERE v_name = ?", arrayOf(v_name))
        return if (cursor.moveToFirst()) {
            cursor.getString(cursor.getColumnIndexOrThrow("v_id"))
        } else {
            null
        }.also {
            cursor.close()
        }
    }

    //inserting player details
    fun insertplayer(user_id:String,player_name: String, age: String, gender: String, batsman: String, bowler: String, contact: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("user_id",user_id)
          put("player_name", player_name)
            put("age", age)
            put("gender", gender)
            put("batsman", batsman)
            put("bowler", bowler)
            put("contact", contact)
        }
        return db.insert("player", null, values)
    }


    fun getUserDetails(userId: Int): User? {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT user_name, age, email, contact FROM User WHERE user_id = ?",
            arrayOf(userId.toString())
        )

        return if (cursor.moveToFirst()) {
            val userName = cursor.getString(cursor.getColumnIndexOrThrow("user_name"))
            val age = cursor.getInt(cursor.getColumnIndexOrThrow("age"))
            val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
            val contact = cursor.getString(cursor.getColumnIndexOrThrow("contact"))
            User(userName, age.toString(), email, contact)
        } else {
            null
        }.also {
            cursor.close()
        }
    }

    // Define the User data class
    data class User(
        val name: String,
        val age: String,
        val email: String,
        val contact: String
    )



    //user table
    companion object {
        private const val DATABASE_NAME = "mydatabase.db"
        private const val DATABASE_VERSION = 2
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


        //venue table
        private const val TABLE_VENUE_CREATE= """
            CREATE TABLE venue(
                v_id Integer PRIMARY KEY, 
                 v_name TEXT
            );
        """

        //book table
        private const val TABLE_BOOK_CREATE= """
            CREATE TABLE book (
                book_id INTEGER PRIMARY KEY AUTOINCREMENT,
                v_id INTEGER NOT NULL, 
                v_name TEXT NOT NULL,
                date TEXT NOT NULL,
                start_time TEXT NOT NULL,
                end_time TEXT NOT NULL,
                charge REAL NOT NULL,
                no_of_player INTEGER NOT NULL,
                FOREIGN KEY (v_id) REFERENCES venue(v_id)
            );

        """

        //player table
        private const val TABLE_PLAYER_CREATE="""
            CREATE TABLE player(
            player_name TEXT NOT NULL,
            user_id INTEGER NOT NULL,
            age INTEGER NOT NULL,
            gender INTEGER NOT NULL,
            batsman INTEGER NOT NULL,
            bowler INTEGER NOT NULL,
            contact INTEGER NOT NULl,
            FOREIGN KEY (user_id) REFERENCES User(user_id)
            );
        """
        //payment table
        private const val TABLE_PAYMENT_CREATE="""
            CREATE TABLE payment(
            p_id INTEGER PRIMARY KEY AUTOINCREMENT,
            book_id INTEGER NOT NULL,
            date DATE NOT NULL,
            pay_method TEXT NOT NULL,
            FOREIGN KEY (book_id) REFERENCES book(book_id)
            );
        """

        //bill table
        private const val TABLE_BILL_CREATE="""
            CREATE TABLE bill(
            user_id INTEGER NOT NULL,
            p_id INTEGER NOT NULL,
            pay_method TEXT NOT NULL,
            book_id INTEGER NOT NULL,
            date TEXT NOT NULL,
            msg TEXT NOT NULL,
            FOREIGN KEY (user_id) REFERENCES User(user_id),
            FOREIGN KEY (book_id) REFERENCES book(book_id),
            FOREIGN KEY (p_id) REFERENCES payment(p_id),
            FOREIGN KEY (pay_method) REFERENCES payment(pay_method)
            );
        """

    }
}
