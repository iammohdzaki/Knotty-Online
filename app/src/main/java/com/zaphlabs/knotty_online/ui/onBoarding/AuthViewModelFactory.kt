package com.zaphlabs.knotty_online.ui.onBoarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zaphlabs.knotty_online.data.DataManager

@Suppress("UNCHECKED_CAST")
class AuthViewModelFactory (private val manager: DataManager) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(manager) as T
    }
}