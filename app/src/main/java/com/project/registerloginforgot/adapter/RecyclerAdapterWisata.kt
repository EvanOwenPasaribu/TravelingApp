package com.project.registerloginforgot.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.registerloginforgot.R
import com.project.registerloginforgot.model.Wisata
import java.util.*
import kotlin.collections.ArrayList

class RecyclerAdapterWisata(list: ArrayList<Wisata>, context: Context) :
    RecyclerView.Adapter<RecyclerAdapterWisata.ViewHolder>() {
    var dataList: ArrayList<Wisata>
    private var onItemClickCallback: OnItemClickCallback? = null
    var ctx: Context
    fun setOnclickCallback(onItemClickCallback: OnItemClickCallback?) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View =
            layoutInflater.inflate(R.layout.row_item_wisata, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val item = dataList[position];
        holder.nama.text = item.nama
        var desc = item.desc;
        if(desc!!.length > 120){
            desc = desc.substring(0, 119) + "..."
        }
        holder.desc.text = desc
        holder.image.setImageResource(item.image.toString().toInt())
        holder.itemView.setOnClickListener {
            onItemClickCallback!!.onItemClick(
                item,
                position
            )
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    init {
        dataList = list
        ctx = context
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nama: TextView
        var desc: TextView
        var image : ImageView

        init {
            nama = itemView.findViewById(R.id.tv_nama)
            desc = itemView.findViewById(R.id.tv_desc)
            image = itemView.findViewById(R.id.imgv_image)
        }
    }

    interface OnItemClickCallback {
        fun onItemClick(item: Wisata?, position: Int)
    }

}