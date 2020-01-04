package com.zaphlabs.knotty_online.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zaphlabs.knotty_online.data.DataManager

/**
 * Developer : Mohammad Zaki
 * Created On : 04-01-2020
 */

class FavoriteViewModelFactory(private val manager: DataManager) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavoriteViewModel(manager) as T
    }
}