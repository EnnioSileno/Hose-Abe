package ch.ennio.sileno.hoseabe

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlayerListViewModel : ViewModel() {
    private val playerListViewModel: MutableLiveData<ArrayList<String>> = MutableLiveData<ArrayList<String>>()

    init {
        playerListViewModel.value = ArrayList()
    }

    fun getPlayerList(): MutableLiveData<ArrayList<String>> {
        return playerListViewModel
    }
}