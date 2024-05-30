package com.project.registerloginforgot

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager private constructor(context: Context?) {
    private val my_shared_preferences = "my_shared_preferences"
    private val sharedPreferences: SharedPreferences? = context?.getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE)

    companion object {
        private var mSharedPrefManager: SharedPrefManager? = null
        const val TAG_ID = "id"
        const val FIRST_OPEN = "first_open"

        @Synchronized
        fun getInstance(context: Context?): SharedPrefManager {
            if (mSharedPrefManager == null) {
                mSharedPrefManager = SharedPrefManager(context)
            }
            return mSharedPrefManager!!
        }
    }

    fun userLogin(id: String?): Boolean? {
        val editor = sharedPreferences!!.edit()
        editor.putString(TAG_ID, id)
        editor.apply()
        return true
    }

    fun isLogin(): Boolean {
        return sharedPreferences!!.getString(TAG_ID, null) != null
    }

    fun logout(): Boolean {
        val editor = sharedPreferences!!.edit()
        editor.clear()
        editor.apply()
        return true
    }

    fun getUserId(): String? {
        return sharedPreferences!!.getString(TAG_ID, null)
    }

    fun getAkun(akun : String?): String? {
        return sharedPreferences!!.getString(akun, null)
    }

    fun setAkun(akun : String?, nilai: String?): Boolean {
        val editor = sharedPreferences!!.edit()
        editor.putString(akun, nilai)
        editor.apply()
        return true
    }
}