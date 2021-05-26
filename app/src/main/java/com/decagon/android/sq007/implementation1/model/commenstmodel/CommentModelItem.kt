package com.decagon.android.sq007.implementation1.model.commenstmodel

import java.io.Serializable

data class CommentModelItem(
    val body: String,
    val email: String,
    val id: Int,
    val name: String,
    val postId: Int
):Serializable