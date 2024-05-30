package com.project.registerloginforgot

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide


class DetailWisataActivity : AppCompatActivity() {
    lateinit var btnBack : ImageView
    lateinit var tvNama : TextView
    lateinit var tvDesc : TextView
    lateinit var imgvImage : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_wisata)

        val name = intent.getStringExtra("nama")
        val desc = intent.getStringExtra("desc")
        val image = intent.getIntExtra("image", R.drawable.wisata)

        btnBack = findViewById(R.id.btn_back)
        tvNama = findViewById(R.id.tv_nama)
        tvDesc = findViewById(R.id.tv_desc)
        imgvImage = findViewById(R.id.imgv_image)

        tvNama.text = name
        tvDesc.text = desc

        Glide.with(this@DetailWisataActivity).load(image).into(imgvImage)


        btnBack.setOnClickListener  {
            finish()
        }

    }
}