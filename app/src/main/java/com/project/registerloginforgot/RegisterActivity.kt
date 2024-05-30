package com.project.registerloginforgot

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.project.registerloginforgot.database.DatabaseHelper

class RegisterActivity : AppCompatActivity() {
    lateinit var btnBack : ImageView
    lateinit var btnRegister : Button
    lateinit var etName : EditText
    lateinit var etEmail : EditText
    lateinit var etPassword : EditText
    lateinit var etUsername : EditText
    lateinit var etPasswordConfirm : EditText
    lateinit var db: DatabaseHelper
    lateinit var context : Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        context = this@RegisterActivity

        db = DatabaseHelper(context)

        btnBack= findViewById(R.id.btn_back)
        btnRegister= findViewById(R.id.btn_register)
        etName = findViewById(R.id.et_nama)
        etUsername = findViewById(R.id.et_username)
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        etPasswordConfirm = findViewById(R.id.et_password_confirm)

        btnBack.setOnClickListener {
            finish()
        }

        btnRegister.setOnClickListener {
            if(etName.text.toString().isEmpty()){
                Toast.makeText(context, "Masukkan nama", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(etEmail.text.toString().isEmpty()){
                Toast.makeText(context, "Masukkan email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(etUsername.text.toString().isEmpty()){
                Toast.makeText(context, "Masukkan username", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(etPassword.text.toString().isEmpty()){
                Toast.makeText(context, "Masukkan password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(etPassword.text.toString().length < 5){
                Toast.makeText(context, "Password minimal 5 karakter", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(!isValidEmail(etEmail.text.toString())){
                Toast.makeText(context, "Email tidak valid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(etPassword.text.toString() != etPasswordConfirm.text.toString()){
                Toast.makeText(context, "Konfirmasi password tidak sama", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(db.insertUser(etName.text.toString(), etUsername.text.toString(), etEmail.text.toString(), etPassword.text.toString()) == -1){
                Toast.makeText(context, "Username atau email sudah ada", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(context, "Berhasil register silahkan login", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$".toRegex()
        return email.matches(emailRegex)
    }

}