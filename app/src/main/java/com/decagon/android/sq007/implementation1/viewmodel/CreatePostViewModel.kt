package com.decagon.android.sq007.implementation1.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.decagon.android.sq007.implementation1.model.postmodel.PostModelItem
import com.decagon.android.sq007.implementation1.repository.Repository
import com.decagon.android.sq007.implementation1.utils.Resource
import kotlinx.coroutines.launch
import com.decagon.android.sq007.implementation1.utils.ObjectOfResponse

class CreatePostViewModel(app: Application, private val appRepository: Repository) : AndroidViewModel(app)  {
    private var _newPost = MutableLiveData<PostModelItem>()
    val newPost : LiveData<PostModelItem> get() = _newPost

    fun addPost(postTitle:String, postBody:String, postId:String){
        viewModelScope.launch {
            val postModel = PostModelItem(postBody, id = null, postTitle, postId.toInt())
            val response = appRepository.addPost(postModel)
            _newPost.value = response.body()
        }

    }
}