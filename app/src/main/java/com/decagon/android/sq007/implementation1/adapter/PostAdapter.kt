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

    inner class PostsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val postCardLayout = itemView.findViewById<ConstraintLayout>(R.id.post_card_layout)
    }

//    private val differCallback = object : DiffUtil.ItemCallback<PostModelItem>() {
//        override fun areItemsTheSame(oldItem: PostModelItem, newItem: PostModelItem): Boolean {
//            return oldItem.id == newItem.id
//        }
//        override fun areContentsTheSame(oldItem: PostModelItem, newItem: PostModelItem): Boolean {
//
//            return oldItem == newItem
//        }
//    }
//    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostsViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.post_card_view, parent, false)
    )
    override fun getItemCount() =  postList.size

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
        Log.d("PostListAdapt", "getPictures: ${postList.size}")
        notifyDataSetChanged()

    }

    fun setUpPost(post : MutableList<PostModelItem>){
        this.postList = post as ArrayList<PostModelItem>
        Log.d("PostListAdapt", "getPictures: ${postList.size}")
    }
}































//class PostAdapter(private val listener: OnItemClickListener, private val context: Context):
//    RecyclerView.Adapter<PostAdapter.CardViewHolder>() {
//
//    var items: PostModelItem  = PostModelItem("", 0, "",0)
//        inner class CardViewHolder constructor(val binding: PostCardViewBinding):RecyclerView.ViewHolder(binding.root),
//            View.OnClickListener{
//            fun bind(posts:PostModelItem){
//
//            }
//
//            init {
//                itemView.setOnClickListener(this)
//            }
//            override fun onClick(v: View?) {
//                val position: Int = bindingAdapterPosition
//                if (position != RecyclerView.NO_POSITION) {
//                    listener.onItemClick(position, items)
//                }
//            }
//
//        }
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
//        val binding = PostCardViewBinding .inflate(LayoutInflater.from(parent.context), parent, false)
//        return CardViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
//        holder.bind(items[position] )
//    }
//
//    override fun getItemCount(): Int {
//        return items
//    }
//
//}
//interface OnItemClickListener {
//    fun onItemClick(position: Int, items: PostModelItem)
//}
