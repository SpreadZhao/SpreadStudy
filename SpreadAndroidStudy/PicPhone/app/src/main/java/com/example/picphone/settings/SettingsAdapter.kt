package com.example.picphone.settings

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.picphone.people.PersonItem
import com.example.picphone.R

class SettingsAdapter(
    private val personList: List<PersonItem>,
    private val activity: Activity
) : RecyclerView.Adapter<SettingsAdapter.SettingItemHolder>() {

    companion object {
        private const val TAG = "SettingsAdapter"
    }

    private var positionToModify = Int.MAX_VALUE

    inner class SettingItemHolder(item: View) : RecyclerView.ViewHolder(item) {
        val textView = item.findViewById<TextView>(R.id.settings_phone)
        val imageView = item.findViewById<ImageView>(R.id.settings_image)
    }

    private var inflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingItemHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        val view = inflater!!.inflate(R.layout.settings_item, parent, false)
        return SettingItemHolder(view)
    }

    override fun onBindViewHolder(holder: SettingItemHolder, position: Int) {
        val person = personList[position]
        holder.textView.text = person.phoneNum
        if (person.picture != null) {
            holder.imageView.setImageDrawable(person.picture)
        }
        holder.textView.setOnClickListener {
            val rootView = FrameLayout(activity)
            val editText = EditText(activity)
            editText.textSize = 30f
            rootView.addView(editText)
            val dialog = AlertDialog.Builder(activity)
                .setView(rootView)
                .setTitle("修改电话号")
                .setPositiveButton("确定") { _, _ ->
                    val newPhoneNum = editText.text
                    Log.d(TAG, "you pressed ok: $newPhoneNum")
                    person.phoneNum = "$newPhoneNum"
                    notifyItemChanged(holder.adapterPosition)
                }
                .setNegativeButton("取消") { _, _ ->
                    Log.d(TAG, "you clicked cancel")
                }
            dialog.show()
        }
        holder.imageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            this.positionToModify = holder.adapterPosition
            activity.startActivityForResult(intent, 2)
        }
    }

    override fun getItemCount(): Int {
        return personList.size
    }

    fun changePicture(picture: Drawable) {
        val position = this.positionToModify
        if (position !in personList.indices) {
            return
        }
        personList[position].picture = picture
        notifyItemChanged(position)
    }
}