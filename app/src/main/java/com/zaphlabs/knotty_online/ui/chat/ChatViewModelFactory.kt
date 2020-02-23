package com.zaphlabs.knotty_online.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zaphlabs.knotty_online.data.DataManager

/**
 * Developer : Mohammad Zaki
 * Created On : 23-02-2020
 */

@Suppress("UNCHECKED_CAST")
class ChatViewModelFactory (private val manager: DataManager) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChatViewModel(manager) as T
    }
}