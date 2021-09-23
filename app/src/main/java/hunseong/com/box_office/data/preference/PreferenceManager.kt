package hunseong.com.box_office.data.preference

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

interface PreferenceManager {

    fun getName(key: String): String?

    fun setName(key: String, value:String)
}