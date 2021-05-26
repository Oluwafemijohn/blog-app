package com.decagon.android.sq007.implementation1.repository

import com.decagon.android.sq007.implementation1.data.api.RetrofitBuilder
import com.decagon.android.sq007.implementation1.model.commenstmodel.CommentModelItem
import com.decagon.android.sq007.implementation1.model.postmodel.PostModelItem

class Repository{

    suspend fun getPosts() = RetrofitBuilder.apiService.getPosts()

    suspend fun getComments(position : Int) = RetrofitBuilder.apiService.getComments(position = position)

    suspend fun addPost(post:PostModelItem)= RetrofitBuilder.apiService.addPost(post  = post)
    suspend fun addComment(comment: CommentModelItem)= RetrofitBuilder.apiService.addComment(comment  = comment)

}