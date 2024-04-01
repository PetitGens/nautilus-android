package fr.iut.gon.info.td3.projetAndroid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DivesViewModel : ViewModel(){
    private val dives : MutableLiveData<List<DiveDataclass>> by lazy{
        MutableLiveData<List<DiveDataclass>>()
    }

    fun divesLiveData(): LiveData<List<DiveDataclass>>{
        if(dives.value == null){
            dives.value = emptyList()
        }

        if(dives.value!!.isEmpty()){
            Thread{
                try {
                    fetchDives()
                } catch (ignored: Exception){}
            }.start()
        }
        return dives
    }

    fun getDives(): List<DiveDataclass>{
        if(dives.value != null && ! dives.value!!.isEmpty()){
            return divesLiveData().value!!
        }
        return emptyList()
    }

    fun fetchDives(){
        dives.postValue(APICall.fetchDives())
    }
}