package com.decagon.android.sq007.implementation1.ui

import android.os.Bundle
import android.util.Log
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
import com.decagon.android.sq007.implementation1.viewmodel.DetailsViewModel
import com.decagon.android.sq007.implementation1.viewmodel.ViewModelProviderFactory
import com.google.android.material.snackbar.Snackbar
import com.decagon.android.sq007.implementation1.utils.errorSnack

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

         post = intent.getSerializableExtra("POST") as PostModelItem

        binding.postDetailsTitle.text = post.title.toString()
        binding.postDetailBody.text = post.body.toString()
        init()


        binding.addCommentBtn.setOnClickListener {
            var postId = post.id
            var body = binding.addComment.text.toString()
            if (postId != null) {
                detailsViewModel.addComment(body, email = null, id = null, name = null, postId)
            }else{
                Toast.makeText(this, "Please enter a comment", Toast.LENGTH_SHORT).show()
            }
        }


    }




    private fun init() {
        binding.postDetailRecyclerview.setHasFixedSize(true)
        binding.postDetailRecyclerview.layoutManager = LinearLayoutManager(this)


        setupViewModel()
    }



    private fun setupViewModel() {
        val repository = Repository()
        val factory = ViewModelProviderFactory(application, repository)
        detailsViewModel = ViewModelProvider(this, factory).get(DetailsViewModel::class.java)
        detailsViewModel.getCommentsId(post.id!!)
        getPosts()
    }

    private fun getPosts() {
        detailsViewModel.commentData.observe(this, Observer { response ->
            Log.d("CommentAdapter3", "getPictures: $response")
            when (response) {
                is Resource.Success -> {
                  //  hideProgressBar()
                    response.data?.let { postResponse ->
                        postResponse
                        binding.postDetailRecyclerview.adapter = CommentAdapter(postResponse)
                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        binding.commentLayoutRoot.errorSnack(message, Snackbar.LENGTH_LONG)
                    }

                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar() {
        binding.commentProgressLayout.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.commentLayoutRoot.visibility = View.VISIBLE
    }


    fun onProgressClick(view: View) {
        //Preventing Click during loading
    }

}