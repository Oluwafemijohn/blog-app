package com.decagon.android.sq007.implementation1.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.R
import com.decagon.android.sq007.implementation1.model.postmodel.PostModelItem
import com.decagon.android.sq007.implementation1.ui.RecyclerClickListener



class PostAdapter(private val listener: RecyclerClickListener) : RecyclerView.Adapter<PostAdapter.PostsViewHolder>() {

    var postList = arrayListOf<PostModelItem>()

    //inner class
    inner class PostsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val postCardLayout = itemView.findViewById<ConstraintLayout>(R.id.post_card_layout)
    }
    //On create view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostsViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.post_card_view, parent, false)
    )
    //get item count
    override fun getItemCount() =  postList.size
    //BInfding the view
    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        val posts = postList[position]
        holder.itemView.apply {
        val title = findViewById<TextView>(R.id.post_title)
            val body = findViewById<TextView>(R.id.post_bodywee)
            title.text = posts.title
            body.text = posts.body
        }

        holder.postCardLayout.setOnClickListener {
            listener.onItemClicked(posts)
        }
    }

    fun addNewPost(post: PostModelItem){
        postList.add(post)
        notifyDataSetChanged()

    }

    //getting data from the activity
    fun setUpPost(post : MutableList<PostModelItem>){
        this.postList = post as ArrayList<PostModelItem>
    }
}



























