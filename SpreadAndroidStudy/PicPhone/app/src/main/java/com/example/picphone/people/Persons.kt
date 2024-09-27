package com.example.picphone.people

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.picphone.util.Picture
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

val DATA_KEY_PERSONS = stringPreferencesKey("persons")

val Context.dataStore by preferencesDataStore(name = "settings")

object Persons {

    private val personList = mutableListOf<PersonItem>()
    val list: List<PersonItem> get() = personList
    private val gson = Gson()

    suspend fun loadPersons(context: Context) {
        val jsonStr = context.dataStore.data.firstOrNull()?.get(DATA_KEY_PERSONS) ?: return
        val type = object : TypeToken<List<String>>() {}.type
        val numberList: List<String> = gson.fromJson(jsonStr, type)
        val cw = ContextWrapper(context)
        val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
        val pictures = directory.listFiles() ?: return
        val map = mutableMapOf<String, Drawable?>()
        for (number in numberList) {
            map[number] = null
        }
        for (picture in pictures) {
            val bitmap = BitmapFactory.decodeFile(picture.absolutePath)
            val drawable = BitmapDrawable(context.resources, bitmap)
            val picNum = picture.name.removeSuffix(".jpg")
            if (map.containsKey(picNum)) {
                map[picNum] = drawable
            }
        }
        this.personList.addAll(PersonItem.fromMap(map))
    }

    fun addPerson(phoneNum: String, picture: Drawable? = null) {
        personList.add(PersonItem(phoneNum, picture))
    }

    fun saveImages(context: Context) {
        var success = true
        for (person in list) {
            val drawable = person.picture ?: continue
            success = success and saveImage(context, person.phoneNum, Picture.drawableToBitmap(drawable))
        }
        if (success) {
            Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT).show()
        }
    }

    fun clear(context: Context, owner: LifecycleOwner) {
        val directory = context.getDir("imageDir", Context.MODE_PRIVATE)
        clear(directory)
        val files = directory.listFiles()
        owner.lifecycleScope.launch {
            clearPhoneNumbers(context)
            if (files == null || files.isEmpty() && isPhoneNumbersEmpty(context)) {
                Toast.makeText(context, "清理成功", Toast.LENGTH_SHORT).show()
            }
        }
    }

    suspend fun savePersons(context: Context) {
        list.takeIf { it.isNotEmpty() }?.let { list ->
            val numberList = list.map { it.phoneNum }
            val jsonString = gson.toJson(numberList)
            context.dataStore.edit { preferences ->
                preferences[DATA_KEY_PERSONS] = jsonString
            }
        }
    }

    private fun saveImage(context: Context, name: String, bitmap: Bitmap): Boolean {
        val cw = ContextWrapper(context)
        val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
        val file = File(directory, "${name}.jpg")
        FileOutputStream(file).use {
            return bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
    }

    private fun clear(directory: File) {
        if (directory.isDirectory) {
            val children = directory.listFiles()
            if (children != null) {
                for (child in children) {
                    if (child.isDirectory) {
                        clear(child)
                    } else {
                        child.delete()
                    }
                }
            }
        }
    }

    private suspend fun clearPhoneNumbers(context: Context) {
        context.dataStore.edit { preferences ->
            preferences.remove(DATA_KEY_PERSONS)
        }
    }

    private suspend fun isPhoneNumbersEmpty(context: Context): Boolean {
        return context.dataStore.data.firstOrNull()?.get(DATA_KEY_PERSONS) == null

//        去掉了withContext(Dispatchers.IO)，因为dataStore.data本身是一个挂起函数，已经在IO线程中运行。
//        使用firstOrNull()代替collect，因为我们只需要获取一次数据，而不是流式处理。
//        var str: String? = null
//        withContext(Dispatchers.IO) {
//            context.dataStore.data.map { preferences ->
//                preferences[DATA_KEY_PERSONS]
//            }.collect {
//                str = it
//            }
//        }
//        return str == null
    }

}