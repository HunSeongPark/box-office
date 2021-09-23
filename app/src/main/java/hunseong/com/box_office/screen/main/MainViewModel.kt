package hunseong.com.box_office.screen.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import hunseong.com.box_office.data.preference.PreferenceManager
import hunseong.com.box_office.screen.base.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel(
    private val preference : PreferenceManager
) : BaseViewModel() {

    private val _nameLiveData = MutableLiveData<String?>(null)
    val nameLiveData: LiveData<String?> = _nameLiveData

    fun getName() = viewModelScope.launch {
        val name = preference.getName(PREFERENCE_NAME_KEY)
        _nameLiveData.postValue(name)
    }

    fun setName(name: String) = viewModelScope.launch {
        preference.setName(PREFERENCE_NAME_KEY, name)
        _nameLiveData.postValue(name)
    }
}