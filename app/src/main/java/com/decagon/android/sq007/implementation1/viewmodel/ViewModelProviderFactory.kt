package com.decagon.android.sq007.implementation1.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.decagon.android.sq007.implementation1.repository.Repository

class ViewModelProviderFactory(val app: Application, val appRepository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            return ListViewModel(app, appRepository) as T
        }

        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(app, appRepository) as T
        }
        if (modelClass.isAssignableFrom(CreatePostViewModel::class.java)) {
            return CreatePostViewModel(app, appRepository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}