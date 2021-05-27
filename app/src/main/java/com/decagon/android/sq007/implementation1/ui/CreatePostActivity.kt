package com.decagon.android.sq007.implementation1.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.decagon.android.sq007.databinding.ActivityCreatePostBinding
import com.decagon.android.sq007.implementation1.model.postmodel.PostModelItem
import com.decagon.android.sq007.implementation1.repository.Repository
import com.decagon.android.sq007.implementation1.viewmodel.CreatePostViewModel
import com.decagon.android.sq007.implementation1.viewmodel.ViewModelProviderFactory
import com.decagon.android.sq007.implementation1.adapter.PostAdapter
import com.decagon.android.sq007.implementation1.utils.ObjectOfResponse

class CreatePostActivity : AppCompatActivity() {

    lateinit var binding: ActivityCreatePostBinding
    lateinit var viewModel:CreatePostViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)



        var repository = Repository()
        val factory = ViewModelProviderFactory(application, repository)
        //Adding new post
        viewModel = ViewModelProvider(this, factory).get(CreatePostViewModel::class.java)
        binding.addPostButton.setOnClickListener {
            val postTitle = binding.addPostTitle.text.toString()
            val postBody = binding.addPostBody.text.toString()
            val postId = binding.addPostId.text.toString()
            if(postBody.isNotEmpty() && postTitle.isNotEmpty() && postId.isNotEmpty()){
                    //adding to the view model
                    viewModel.addPost(postTitle = postTitle, postBody = postBody, postId = postId)
                    viewModel.newPost.observe(this, {
                        val intent = Intent(this, PostListActivity::class.java)
                        intent.putExtra("posts", it)
                        startActivity(intent)
                        finish()
                    })
                }else{
                    Toast.makeText(this, "Please Enter details", Toast.LENGTH_SHORT).show()
            }

        }
    }
}