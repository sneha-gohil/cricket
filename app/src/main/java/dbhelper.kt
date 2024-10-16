package com.example.crickethub

import BookingData
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class dbhelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(TABLE_USER_CREATE)
        db.execSQL(TABLE_BOOK_CREATE)
        db.execSQL(TABLE_PAYMENT_CREATE)
        db.execSQL(TABLE_PLAYER_CREATE)
        db.execSQL(TABLE_BILL_CREATE)
        db.execSQL(TABLE_VENUE_CREATE)
        db.execSQL(TABLE_ADMIN_CREATE)
        db.execSQL(TABLE_ADMINPAGE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS venue")
        db.execSQL("DROP TABLE IF EXISTS bill")
        db.execSQL("DROP TABLE IF EXISTS book")
        db.execSQL("DROP TABLE IF EXISTS payment")
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
        return try {
            val rowId = db.insertOrThrow("book", null, values)
            rowId // This is the auto-incremented book_id
        } catch (e: Exception) {
            Log.e("DBHelper", "Error inserting data: ${e.message}")
            -1L  // Return -1 if an error occurs
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


    fun getUserDetails(userId: String?): User? {
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

    //for inserting payment
    fun insertPayment(book_id: String, date: String, pay_method: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("book_id", book_id)        // book_id as a String
            put("date", date)              // date as a String
            put("pay_method", pay_method)  // pay_method as a String
        }
        return db.insert("payment", null, contentValues)
    }


    data class BookingDetails(
        val userId: String,
        val bookId: String,
        val payId: String,
        val paymentMethod: String,
        val date: String
    )

    @SuppressLint("Range")
    fun updateUser(userId: String, userName: String, age: String, email: String, contact: String): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("user_name", userName)
            put("age", age)
            put("email", email)
            put("contact", contact)
        }
        // Update the user record with the matching user_id
        return db.update("User", values, "user_id = ?", arrayOf(userId))
    }


    fun getPaymentDetails(payId: String): Map<String, String> {
        val db = this.readableDatabase
        val paymentDetails = mutableMapOf<String, String>()

        // Query to get payment details based on pay_id
        val cursor = db.rawQuery("SELECT pay_method, date FROM payment WHERE p_id = ?", arrayOf(payId))

        if (cursor.moveToFirst()) {
            paymentDetails["pay_method"] = cursor.getString(cursor.getColumnIndexOrThrow("pay_method"))
            paymentDetails["date"] = cursor.getString(cursor.getColumnIndexOrThrow("date"))
        }
        cursor.close()
        return paymentDetails
    }

    fun getLastPaymentId(bookId: String): String {
        val db = this.readableDatabase
        var payId = ""

        val cursor = db.rawQuery("SELECT p_id FROM payment WHERE book_id = ? ORDER BY p_id DESC LIMIT 1", arrayOf(bookId))

        if (cursor.moveToFirst()) {
            payId = cursor.getString(cursor.getColumnIndexOrThrow("p_id"))
        }
        cursor.close()
        return payId
    }

    fun getLastBookingDetails(): Map<String, String>? {
        val db = this.readableDatabase
        val bookingDetails = mutableMapOf<String, String>()

        // Query to get the last booking details based on the highest book_id
        val cursor = db.rawQuery("SELECT user_id, book_id, date FROM book ORDER BY book_id DESC LIMIT 1", null)

        if (cursor.moveToFirst()) {
            bookingDetails["user_id"] = cursor.getString(cursor.getColumnIndexOrThrow("user_id"))
            bookingDetails["book_id"] = cursor.getString(cursor.getColumnIndexOrThrow("book_id"))
            bookingDetails["date"] = cursor.getString(cursor.getColumnIndexOrThrow("date"))
        }

        cursor.close()
        db.close()

        return if (bookingDetails.isNotEmpty()) bookingDetails else null
    }

    fun getAllBookings(): List<BookingData> {
        val bookings = mutableListOf<BookingData>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT user_id, book_id, pay_method, date FROM adminpage", null)

        if (cursor.moveToFirst()) {
            do {
                val userId = cursor.getString(cursor.getColumnIndexOrThrow("user_id"))
                val bookId = cursor.getString(cursor.getColumnIndexOrThrow("book_id"))
                val payMethod = cursor.getString(cursor.getColumnIndexOrThrow("pay_method"))
                val date = cursor.getString(cursor.getColumnIndexOrThrow("date"))
                bookings.add(BookingData(userId, bookId, payMethod, date))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return bookings
    }


    fun getBillDetails(payId: Long): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery(
            "SELECT u.user_id, b.book_id, p.pay_id, p.pay_method, p.date " +
                    "FROM user_table u " +
                    "JOIN booking_table b ON u.user_id = b.user_id " +
                    "JOIN payment_table p ON b.book_id = p.book_id " +
                    "WHERE p.pay_id = ?", arrayOf(payId.toString())
        )
    }


    //user table
    companion object {
        private const val DATABASE_NAME = "mydatabase.db"
        private const val DATABASE_VERSION = 2
        private const val TABLE_USER_CREATE =( """
            CREATE TABLE User (
                user_id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_name TEXT,
                age INTEGER,
                email TEXT NOT NULL UNIQUE,
                contact INTEGER UNIQUE,
                password TEXT NOT NULL
            );
        """)


        //venue table
        private const val TABLE_VENUE_CREATE=( """
            CREATE TABLE venue(
                 v_name TEXT
            );
        """)

        //book table
        private const val TABLE_BOOK_CREATE= ("""
            CREATE TABLE book (
                book_id INTEGER PRIMARY KEY AUTOINCREMENT,
                v_name TEXT NOT NULL,
                date TEXT NOT NULL,
                start_time TEXT NOT NULL,
                end_time TEXT NOT NULL,
                charge REAL NOT NULL,
                no_of_player INTEGER NOT NULL
            );

        """)

        //player table
        private const val TABLE_PLAYER_CREATE=("""
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
        """)
        //payment table
        private const val TABLE_PAYMENT_CREATE=("""
            CREATE TABLE payment(
            p_id INTEGER PRIMARY KEY AUTOINCREMENT,
            book_id INTEGER NOT NULL,
            date DATE NOT NULL,
            pay_method TEXT NOT NULL,
            FOREIGN KEY (book_id) REFERENCES book(book_id)
            FOREIGN KEY (date) REFERENCES book(date)
            );
        """)

        //bill table
        private const val TABLE_BILL_CREATE=("""
            CREATE TABLE bill(
            user_id INTEGER NOT NULL,
            p_id INTEGER NOT NULL,
            pay_method TEXT NOT NULL,
            book_id INTEGER NOT NULL,
            date TEXT NOT NULL,
            FOREIGN KEY (user_id) REFERENCES User(user_id),
            FOREIGN KEY (book_id) REFERENCES book(book_id),
            FOREIGN KEY (p_id) REFERENCES payment(p_id),
            FOREIGN KEY (pay_method) REFERENCES payment(pay_method)
            );
        """)

        private const val TABLE_ADMIN_CREATE=("""
                CREATE TABLE admin (
                admin_id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT NOT NULL,
                password TEXT NOT NULL
                );
                """)

        private const val TABLE_ADMINPAGE_CREATE=("""
            CREATE TABLE adminpage(
            user_id INTEGER NOT NULL,
            book_id INTEGER NOT NULL,
            pay_method TEXT NOT NULL,
            date TEXT NOT NULL,
            FOREIGN KEY (user_id) REFERENCES User(user_id),
            FOREIGN KEY (book_id) REFERENCES book(book_id),
            FOREIGN KEY (pay_method) REFERENCES payment(pay_method)
            FOREIGN KEY (date) REFERENCES payment(date)
            )
        """)
    }
}
