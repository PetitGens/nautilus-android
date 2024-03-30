package fr.iut.gon.info.td3.projetAndroid

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NavigationViewModel : ViewModel() {
    val currentPage: MutableLiveData<NautilusPage> by lazy {
        MutableLiveData<NautilusPage>()
    }

    init {
        currentPage.value = NautilusPage.LOGIN
    }
}