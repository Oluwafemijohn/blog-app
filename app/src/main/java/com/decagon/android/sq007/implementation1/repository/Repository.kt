package com.decagon.android.sq007.implementation1.repository

import com.decagon.android.sq007.implementation1.data.api.RetrofitBuilder
import com.decagon.android.sq007.implementation1.model.commenstmodel.CommentModelItem
import com.decagon.android.sq007.implementation1.model.postmodel.PostModelItem

class Repository{
    //Getting the post
    suspend fun getPosts() = RetrofitBuilder.apiService.getPosts()
    //Getting the comments
    suspend fun getComments(position : Int) = RetrofitBuilder.apiService.getComments(position = position)
    //Adding post
    suspend fun addPost(post:PostModelItem)= RetrofitBuilder.apiService.addPost(post  = post)
    //Adding comment
    suspend fun addComment(comment: CommentModelItem)= RetrofitBuilder.apiService.addComment(comment  = comment)

}