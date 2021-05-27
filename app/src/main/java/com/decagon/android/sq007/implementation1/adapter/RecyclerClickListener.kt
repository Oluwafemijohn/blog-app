package com.decagon.android.sq007.implementation1.adapter

import com.decagon.android.sq007.implementation1.model.postmodel.PostModelItem

interface RecyclerClickListener {
    fun onItemClicked(post: PostModelItem)
}