package com.decagon.android.sq007.implementation1.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.decagon.android.sq007.implementation1.repository.Repository

class ViewModelProviderFactory(val app: Application, val appRepository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        //Factory for List ViewModel
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            return ListViewModel(app, appRepository) as T
        }
        //Factory for details view model
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(app, appRepository) as T
        }
        //Factory for create post
        if (modelClass.isAssignableFrom(CreatePostViewModel::class.java)) {
            return CreatePostViewModel( appRepository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}