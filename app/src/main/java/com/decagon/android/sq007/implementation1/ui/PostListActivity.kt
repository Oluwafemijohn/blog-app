 package com.decagon.android.sq007.implementation1.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.decagon.android.sq007.databinding.ActivityPostListBinding
import com.decagon.android.sq007.implementation1.adapter.PostAdapter
import com.decagon.android.sq007.implementation1.model.postmodel.PostModelItem
import com.decagon.android.sq007.implementation1.repository.Repository
import com.decagon.android.sq007.implementation1.utils.Resource
import com.decagon.android.sq007.implementation1.utils.errorSnack
import com.decagon.android.sq007.implementation1.viewmodel.ListViewModel
import com.decagon.android.sq007.implementation1.viewmodel.ViewModelProviderFactory
import com.google.android.material.snackbar.Snackbar

 class PostListActivity : AppCompatActivity(),RecyclerClickListener {
    lateinit var viewModel:ListViewModel
    lateinit var myAdapter: PostAdapter

    private lateinit var binding: ActivityPostListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        //Adding Post
        binding.addPost.setOnClickListener{
                startActivity(Intent(this, CreatePostActivity::class.java))
        }

        //Searching the List
        binding.listSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query.let { viewModel.searchPostList(it!!) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText.let { viewModel.searchPostList(it!!) }
                return false
            }

        })
    }


     // initialising the recycler view
    private fun init() {
        binding.postRecyclerView.setHasFixedSize(true)
        binding.postRecyclerView.layoutManager = LinearLayoutManager(this)
        setupViewModel()
    }

     //Setting up the View model fatory and the view model
    private fun setupViewModel() {
        val repository = Repository()
        val factory = ViewModelProviderFactory(application, repository)
        viewModel = ViewModelProvider(this, factory).get(ListViewModel::class.java)
        getPosts()
    }
     //Getting the post
    private fun getPosts() {
         //Observing the data in the view model
        viewModel.postData.observe(this, Observer { response ->
            when (response) {
                //Success response
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { postResponse ->
                        myAdapter = PostAdapter(this)
                        if (intent.getSerializableExtra("posts") != null){
                            val post = intent.getSerializableExtra("posts") as PostModelItem
                            postResponse.add(post)
                        }
                        //passing data to the method in the view model
                        myAdapter.setUpPost(postResponse)
                        binding.postRecyclerView.adapter = myAdapter
                    }
                }
                //On error response
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        binding.postlistRootLayout.errorSnack(message, Snackbar.LENGTH_LONG)
                    }

                }
                //On Loading
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

     //Hiding the progrss bar
    private fun hideProgressBar() {
        binding.postProgressLayout.visibility = View.GONE
    }
     //Showing the progress bar
    private fun showProgressBar() {
        binding.postProgressLayout.visibility = View.VISIBLE
    }
     
    fun onProgressClick(view: View) {
        //Preventing Click during loading
    }


     override fun onItemClicked(post: PostModelItem) {
         var intent = Intent(this, PostDetailsActivity::class.java)
         intent.putExtra("POST", post)
         startActivity(intent)
     }


 }


