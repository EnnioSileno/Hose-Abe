package ch.ennio.sileno.hoseabe

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlayerCounterViewModel : ViewModel() {
    val playerCounterLiveData: MutableLiveData<Int> = MutableLiveData<Int>()

    fun setValue(newValue: Int) {
        playerCounterLiveData.value = newValue
    }

    fun getValue(): Int {
        return playerCounterLiveData.value!!
    }
}