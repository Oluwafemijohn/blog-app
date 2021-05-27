package com.decagon.android.sq007.implementation1.data.api

import com.bumptech.glide.load.engine.Resource
import com.decagon.android.sq007.implementation1.model.commenstmodel.CommentModel
import com.decagon.android.sq007.implementation1.model.commenstmodel.CommentModelItem
import com.decagon.android.sq007.implementation1.model.postmodel.PostModel
import com.decagon.android.sq007.implementation1.model.postmodel.PostModelItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PostApiInterface {
    @GET("posts")
    suspend fun getPosts(): Response<List<PostModelItem>>


    @GET("posts/{position}/comments")
    suspend fun getComments(@Path("position") position: Int): Response<CommentModel>


//    @POST("posts/{postId}/comments")
//    suspend fun postComment(@Path("postId") postId:Int, @Body post: PostModel): Response<PostModel>

    @POST("posts")
    suspend fun addPost(@Body post: PostModelItem): Response<PostModelItem>

    @POST("posts")
    suspend fun addComment(@Body comment: CommentModelItem): Response<CommentModelItem>
}