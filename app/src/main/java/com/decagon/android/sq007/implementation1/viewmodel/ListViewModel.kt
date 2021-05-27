package com.decagon.android.sq007.implementation1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.decagon.android.sq007.implementation1.utils.Resource
import com.decagon.android.sq007.implementation1.model.postmodel.PostModel
import com.decagon.android.sq007.implementation1.repository.Repository
import com.decagon.android.sq007.implementation1.utils.Util.hasInternetConnection
import retrofit2.Response
import com.decagon.android.sq007.implementation1.app.MyApplication
import java.io.IOException
import com.decagon.android.sq007.R
import androidx.lifecycle.viewModelScope
import com.decagon.android.sq007.implementation1.model.postmodel.PostModelItem

import kotlinx.coroutines.launch
import com.decagon.android.sq007.implementation1.utils.ObjectOfResponse
import kotlinx.coroutines.flow.MutableStateFlow

class ListViewModel(app: Application, private val appRepository: Repository) : AndroidViewModel(app)  {
    private val _postData: MutableLiveData<Resource<ArrayList<PostModelItem>>> = MutableLiveData()
    // Extended list
     val postData  get() = _postData

    init {
        getPost()
        var updatedPost = ObjectOfResponse.updatedPost

        postData.value?.data?.addAll(updatedPost)
    }

    //get Post
    fun getPost() = viewModelScope.launch {
        fetchPost()
    }

    private suspend fun fetchPost() {
        postData.postValue(Resource.Loading())
        try {
            if (hasInternetConnection(getApplication<MyApplication>())) {
                //Network call
                val response = appRepository.getPosts()
//                val result = handlePostResponse(response)
                //Handling response
                postData.postValue(handlePostResponse(response as Response<ArrayList<PostModelItem>>))
            } else {
                postData.postValue(Resource.Error(getApplication<MyApplication>().getString(R.string.no_internet_connection)))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> postData.postValue(
                    Resource.Error(
                        getApplication<MyApplication>().getString(
                            R.string.network_failure
                        )
                    )
                )
                else -> postData.postValue(
                    Resource.Error(
                        getApplication<MyApplication>().getString(
                            R.string.conversion_error
                        )
                    )
                )
            }
        }
    }

    //Network handler
    private fun handlePostResponse(response: Response<ArrayList<PostModelItem>>): Resource<ArrayList<PostModelItem>> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                ObjectOfResponse.updatedPost = resultResponse
                return Resource.Success(ObjectOfResponse.updatedPost)
            }
        }
        return Resource.Error(response.message())
    }

    //Search post
    private var cachedPostList = MutableLiveData<Resource<ArrayList<PostModelItem>>>()
    private var isSearchStarting = true
    var isSearching = MutableStateFlow(false)
    //Query the existing data
    fun searchPostList(query: String) {
        if (isSearchStarting) {
            cachedPostList.value = _postData.value
            isSearchStarting = false
        }
        //Listen to searches
        val listToSearch = if (isSearchStarting) {

            postData.value
        } else {
            cachedPostList.value
        }
        // Querry the network
        viewModelScope.launch {
            if (query.isEmpty()) {
                _postData.value = cachedPostList.value
                isSearching.value = false
                isSearchStarting = true
                return@launch
            } else {
                val results = listToSearch?.data?.filter {
                    it.title!!.contains(query.trim(), ignoreCase = true) ||
                            it.id.toString().contains(query.trim())
                }
                results?.let {
                    _postData.value = Resource.Success(it as ArrayList<PostModelItem>)
                }
            }
            if (isSearchStarting) {
                cachedPostList.value = _postData.value
                isSearchStarting = false
            }
            isSearching.value = true
        }
    }

}