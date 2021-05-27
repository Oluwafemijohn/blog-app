package com.decagon.android.sq007.implementation1.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.decagon.android.sq007.databinding.ActivityPostDetailsBinding
import com.decagon.android.sq007.implementation1.adapter.CommentAdapter
import com.decagon.android.sq007.implementation1.model.postmodel.PostModelItem
import com.decagon.android.sq007.implementation1.repository.Repository
import com.decagon.android.sq007.implementation1.utils.Resource
import com.decagon.android.sq007.implementation1.utils.errorSnack
import com.decagon.android.sq007.implementation1.viewmodel.DetailsViewModel
import com.decagon.android.sq007.implementation1.viewmodel.ViewModelProviderFactory
import com.google.android.material.snackbar.Snackbar

class PostDetailsActivity : AppCompatActivity() {

    lateinit var detailsViewModel:DetailsViewModel
    lateinit var commentAdapter:CommentAdapter

//    lateinit var viewModel:ListViewModel
    lateinit var post: PostModelItem
    lateinit var binding: ActivityPostDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Getting data
        post = intent.getSerializableExtra("POST") as PostModelItem
        binding.postDetailsTitle.text = post.title.toString()
        binding.postDetailBody.text = post.body.toString()
        init()


        // Add post
        binding.addCommentBtn.setOnClickListener {
            var postId = post.id
            var body = binding.addComment.text.toString()
            if (body.isNotEmpty()) {
                detailsViewModel.addComment(body = body, postId = postId!!)
                binding.addComment.text.clear()

            }else{
                Toast.makeText(this, "Please enter a comment", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Initialising the recycler view
    private fun init() {
        binding.postDetailRecyclerview.setHasFixedSize(true)
        binding.postDetailRecyclerview.layoutManager = LinearLayoutManager(this)
        setupViewModel()
    }

    //Setting up the View model Factory and the View model
    private fun setupViewModel() {
        val repository = Repository()
        val factory = ViewModelProviderFactory(application, repository)
        detailsViewModel = ViewModelProvider(this, factory).get(DetailsViewModel::class.java)
        detailsViewModel.getCommentsId(post.id!!)
        getPosts()
    }

    //
    private fun getPosts() {
        detailsViewModel.commentData.observe(this, Observer { response ->
            when (response) {
                //Success response
                is Resource.Success -> {
                    response.data?.let { postResponse ->
                        detailsViewModel.comment.observe(this, {newComment ->
                              newComment.data?.let {
                                  postResponse.add( it)
                           }
                        })
                        //Attaching the recycler view
                        binding.postDetailRecyclerview.adapter = CommentAdapter(postResponse)
                    }
                }
                //Error response
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        binding.commentLayoutRoot.errorSnack(message, Snackbar.LENGTH_LONG)
                    }
                }
                //On Loading
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }
    //Progrss bar
    private fun hideProgressBar() {
        binding.commentProgressLayout.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.commentLayoutRoot.visibility = View.VISIBLE
    }

    fun onProgressClick(view: View) {
        Toast.makeText(this, "Pelase wait while loading", Toast.LENGTH_SHORT)
    }

}