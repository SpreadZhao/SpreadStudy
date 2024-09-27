package com.example.picphone.people

import android.graphics.drawable.Drawable

data class PersonItem(var phoneNum: String, var picture: Drawable?) {

    companion object {
        fun fromMap(map: Map<String, Drawable?>): List<PersonItem> {
            val list = mutableListOf<PersonItem>()
            for ((number, drawable) in map) {
                list.add(PersonItem(number, drawable))
            }
            return list
        }
    }
}

//infix fun Int.to(phoneNum: String) = PicPhoneItem(this, phoneNum)