package com.project.registerloginforgot.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.project.registerloginforgot.model.User
import com.project.registerloginforgot.model.Wisata

class DatabaseHelper(context: Context?) : SQLiteOpenHelper(context, "SQLite.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE wisata(id integer PRIMARY KEY AUTOINCREMENT, nama text, image text, description text)")
        db.execSQL("CREATE TABLE user(id integer PRIMARY KEY AUTOINCREMENT, nama text, username text, email text, password text)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS wisata")
        db.execSQL("DROP TABLE IF EXISTS user")
        onCreate(db)
    }

    fun login(username: String, password: String): Int {
        var userId: Int = -1
        try {
            val db = this.readableDatabase
            val cursor = db.rawQuery(
                "SELECT id FROM user WHERE username = ? AND password = ?",
                arrayOf(username, password)
            )
            if (cursor.moveToFirst()) {
                userId = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            }
            cursor.close()
        } catch (e: SQLiteException) {
            Log.e("Login", "Error while checking login: $e")
        }
        return userId
    }

    // Insert Wisata
    fun insertWisata(
        nama: String?,
        image: String?,
        desc: String?,
    ): Long {
        var id_insert = 0L
        try {
            val db = this.writableDatabase
            val contentValues = ContentValues()
            contentValues.put("nama", nama)
            contentValues.put("description", desc)
            contentValues.put("image", image)
            id_insert = db.insert("wisata", null, contentValues)
            Log.d("Insert data", "Record inserted with id $id_insert")
        } catch (e: SQLiteException) {
            Log.e("Insert data", "Error while inserting data: $e")
        }
        return id_insert
    }

    // Get Wisata by ID
    fun getWisataById(id: String): Wisata {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM wisata WHERE id = ?", arrayOf(id))
        var nama: String? = ""
        var desc: String? = ""
        var image: String? = ""
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(cursor.getColumnIndexOrThrow("id"))
                nama = cursor.getString(cursor.getColumnIndexOrThrow("nama"))
                desc = cursor.getString(cursor.getColumnIndexOrThrow("description"))
                image = cursor.getString(cursor.getColumnIndexOrThrow("image"))
                val wisata = Wisata(id, image, nama, desc)
                return wisata
            } while (cursor.moveToNext())
        }
        return Wisata("", "", "", "")
    }

    // Get all Wisata data
    fun dataWisata(): ArrayList<Wisata> {
        val listWisata: ArrayList<Wisata> = ArrayList<Wisata>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM wisata", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(cursor.getColumnIndexOrThrow("id"))
                val nama = cursor.getString(cursor.getColumnIndexOrThrow("nama"))
                val desc = cursor.getString(cursor.getColumnIndexOrThrow("description"))
                val image = cursor.getString(cursor.getColumnIndexOrThrow("image"))
                val wisata = Wisata(id, image, nama, desc)
                listWisata.add(wisata)
            } while (cursor.moveToNext())
        }
        return listWisata
    }

    // Insert User
    fun insertUser(
        nama: String?,
        username: String?,
        email: String?,
        password: String?
    ): Int {
        var id_insert = -1
        try {
            val db = this.writableDatabase

            // Check if username or email already exists
            val cursor = db.rawQuery("SELECT COUNT(*) FROM user WHERE username = ? OR email = ?", arrayOf(username, email))
            if (cursor.moveToFirst() && cursor.getInt(0) > 0) {
                cursor.close()
                Log.e("Insert data", "Username or email already exists")
                return -1
            }
            cursor.close()

            // Insert new user
            val contentValues = ContentValues()
            contentValues.put("nama", nama)
            contentValues.put("username", username)
            contentValues.put("email", email)
            contentValues.put("password", password)
            id_insert = db.insert("user", null, contentValues).toInt()
            Log.d("Insert data", "User inserted with id $id_insert")
        } catch (e: SQLiteException) {
            Log.e("Insert data", "Error while inserting user: $e")
        }
        return id_insert
    }

    fun getUserById(id: String): User? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM user WHERE id = ?", arrayOf(id))
        var user: User? = null
        if (cursor.moveToFirst()) {
            val userId = cursor.getString(cursor.getColumnIndexOrThrow("id"))
            val nama = cursor.getString(cursor.getColumnIndexOrThrow("nama"))
            val username = cursor.getString(cursor.getColumnIndexOrThrow("username"))
            val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
            val password = cursor.getString(cursor.getColumnIndexOrThrow("password"))
            user = User(userId, nama, username, email, password)
        }
        cursor.close()
        return user
    }

    fun getUserByEmail(emails: String): User? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM user WHERE email = ?", arrayOf(emails))
        var user: User? = null
        if (cursor.moveToFirst()) {
            val userId = cursor.getString(cursor.getColumnIndexOrThrow("id"))
            val nama = cursor.getString(cursor.getColumnIndexOrThrow("nama"))
            val username = cursor.getString(cursor.getColumnIndexOrThrow("username"))
            val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
            val password = cursor.getString(cursor.getColumnIndexOrThrow("password"))
            user = User(userId, nama, username, email, password)
        }
        cursor.close()
        return user
    }

    // Update User Password
    fun updateUserPassword(email: String, newPassword: String): Int {
        var rowsUpdated = -1
        try {
            val db = this.writableDatabase
            val contentValues = ContentValues()
            contentValues.put("password", newPassword)
            rowsUpdated = db.update("user", contentValues, "email = ?", arrayOf(email))
        } catch (e: SQLiteException) {
            Log.e("Update data", "Error while updating password: $e")
        }
        return rowsUpdated
    }

    fun updateWisata(id: String, ukuran: String, warna: String, total: Int): Boolean {
        var db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM wisata WHERE idProduk = ? AND ukuran = ? AND warna = ?",
            arrayOf(id, ukuran, warna)
        )
        var totalEnd = total
        if (cursor.moveToFirst()) {
            totalEnd += cursor.getInt(cursor.getColumnIndexOrThrow("total"))
        }
        db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("total", totalEnd)
        val update = db.update(
            "wisata",
            contentValues,
            "idProduk = ? AND ukuran = ? AND warna = ?",
            arrayOf(id, ukuran, warna)
        ).toLong()
        return update != -1L
    }

    fun updateWisataTotal(id: String, total: Int): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("total", total)
        val update = db.update("wisata", contentValues, "id = ?", arrayOf(id)).toLong()
        return update != -1L
    }


    fun hapusDataWisata(id: String): Boolean {
        val db = this.writableDatabase
        return db.delete("wisata", "id ='$id'", null) > 0
    }
}