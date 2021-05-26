package com.decagon.android.sq007.implementation1.model.postmodel

import java.io.Serializable

data class PostModelItem(
    val body: String?,
    val id: Int?,
    val title: String?,
    val userId: Int?
):Serializable