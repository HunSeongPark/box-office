package hunseong.com.box_office.screen.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    open fun fetchData():Job = viewModelScope.launch {  }

    companion object {
        const val PREFERENCE_NAME_KEY = "prference_name_key"
    }
}