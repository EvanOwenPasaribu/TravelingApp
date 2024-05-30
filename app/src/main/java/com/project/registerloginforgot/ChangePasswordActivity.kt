package com.project.registerloginforgot

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.project.registerloginforgot.database.DatabaseHelper

class ChangePasswordActivity : AppCompatActivity() {
    lateinit var btnBack : ImageView
    lateinit var btnChange : Button
    lateinit var etPassword : EditText
    lateinit var etPasswordConfirm : EditText
    lateinit var db: DatabaseHelper
    lateinit var context : Context
    var email = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        context = this@ChangePasswordActivity
        db = DatabaseHelper(context)

        email = intent.getStringExtra("email") as String

        btnBack= findViewById(R.id.btn_back)
        btnChange= findViewById(R.id.btn_change)
        etPassword = findViewById(R.id.et_new_password)
        etPasswordConfirm = findViewById(R.id.et_password_confirm)

        btnBack.setOnClickListener {
            finish()
        }

        btnChange.setOnClickListener {
            if(etPassword.text.toString().isEmpty()){
                Toast.makeText(context, "Masukkan password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(etPassword.text.toString().length < 5){
                Toast.makeText(context, "Password minimal 5 karakter", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(etPassword.text.toString() != etPasswordConfirm.text.toString()){
                Toast.makeText(context, "Konfirmasi password tidak sama", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(email != ""){
                if(db.updateUserPassword(email, etPassword.text.toString()) == -1){
                    Toast.makeText(context, "Email tidak ditemukan", Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(context, "Berhasil ubah password", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                }
            }else {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$".toRegex()
        return email.matches(emailRegex)
    }

}