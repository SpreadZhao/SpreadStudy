package com.example.picphone

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.picphone.people.PersonItem
import com.example.picphone.util.Caller

class PicAdapter(
    private val pictures: List<PersonItem>,
    private val caller: Caller
) : RecyclerView.Adapter<PicAdapter.PicHolder>() {

    inner class PicHolder(item: View) : RecyclerView.ViewHolder(item) {

        private val imageView: ImageView? = item.findViewById(R.id.page_item_pic)
        private val phoneNumberView: TextView? = item.findViewById(R.id.page_item_num)

        fun setImage(drawable: Drawable?) {
            imageView?.setImageDrawable(drawable)
        }

        fun onClick(onClick: View.OnClickListener) {
            imageView?.setOnClickListener(onClick)
        }

        fun setPhoneNumber(num: String) {
            phoneNumberView?.text = num
        }

    }

    private var inflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        val view = inflater?.inflate(R.layout.pager_item, parent, false)
        val res = view ?: ImageView(parent.context)
        return PicHolder(res)
    }

    override fun onBindViewHolder(holder: PicHolder, position: Int) {
        val person = pictures[position]
        person.picture?.let { holder.setImage(it) }
        holder.setPhoneNumber(person.phoneNum)
        holder.onClick { caller.makeCall(person.phoneNum) }
    }

    override fun getItemCount() = pictures.size
}