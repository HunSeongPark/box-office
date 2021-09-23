package hunseong.com.box_office.data.preference

import android.content.SharedPreferences
import androidx.core.content.edit

class SharedPreferenceManager(
    private val preferences: SharedPreferences
) : PreferenceManager {

    override fun getName(key: String): String? =
        preferences.getString(key, "unknown")

    override fun setName(key: String, value: String) {
        preferences.edit {
            putString(key, value)
        }
    }
}