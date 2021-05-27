package com.decagon.android.sq007.implementation1.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.R
import com.decagon.android.sq007.implementation1.model.commenstmodel.CommentModelItem


//class CommentAdapter() : RecyclerView.Adapter<CommentAdapter.PostsViewHolder>() {
//
//
//    inner class PostsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
//
//    private val differCallback = object : DiffUtil.ItemCallback<CommentModelItem>() {
//        override fun areItemsTheSame(
//            oldItem: CommentModelItem,
//            newItem: CommentModelItem
//        ): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(
//            oldItem: CommentModelItem,
//            newItem: CommentModelItem
//        ): Boolean {
//
//            return oldItem == newItem
//        }
//    }
//     val differ = AsyncListDiffer(this, differCallback)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostsViewHolder(
//        LayoutInflater.from(parent.context).inflate(R.layout.comment_card_view, parent, false)
//    )
//
//    override fun getItemCount() = differ.currentList.size
//
//    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
//        val comment = differ.currentList[position]
//        holder.itemView.apply {
//            val commetName = findViewById<TextView>(R.id.comment_name)
//            val commentEmail = findViewById<TextView>(R.id.comment_email)
//            val commentBody = findViewById<TextView>(R.id.post_body)
//            commetName.text = comment.name
//            commentEmail.text = comment.email
//            commentBody.text = comment.body
//
//
//        }
//    }
//}

























class CommentAdapter(private val currentList : List<CommentModelItem>):RecyclerView.Adapter<CommentAdapter.CardViewHolder>() {

    //inner class
    class CardViewHolder (itemView : View):RecyclerView.ViewHolder(itemView){
        private val comment_name: TextView = itemView.findViewById(R.id.comment_name)
        private val  comment_email = itemView.findViewById<TextView>(R.id.comment_email)
        private val post_body  = itemView.findViewById<AppCompatTextView>(R.id.comment_body)
        //Binding the data with the view
        fun  bind(commentModelItem: CommentModelItem){
            comment_name.text = commentModelItem.name
            comment_email.text = commentModelItem.email
            post_body.text = commentModelItem.body

        }
    }

    //Creating view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.comment_card_view, parent, false)
        )
    }
    //Binding the view
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
    //Getting the item cout size
    override fun getItemCount(): Int {
        return currentList.size
    }
}

