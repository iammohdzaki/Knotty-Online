package com.zaphlabs.knotty_online.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zaphlabs.knotty_online.data.DataManager

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory (private val manager: DataManager) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(manager) as T
    }
}