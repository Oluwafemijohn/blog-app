package com.decagon.android.sq007.implementation1.model.commenstmodel

import java.io.Serializable

data class CommentModelItem(
    val body: String = "",
    val email: String = "",
    val id: Int = 0,
    val name: String = "",
    val postId: Int = 0
):Serializable