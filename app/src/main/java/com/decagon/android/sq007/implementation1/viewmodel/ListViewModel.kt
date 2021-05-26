package com.decagon.android.sq007.implementation1.viewmodel

import android.app.Application
import android.util.Log
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

class ListViewModel(app: Application, private val appRepository: Repository) : AndroidViewModel(app)  {
    var postData: MutableLiveData<Resource<PostModel>> = MutableLiveData()
     var postList = arrayListOf<PostModelItem>()

    init {
        getPictures()
        var updatedPost = ObjectOfResponse.updatedPost
        postData.value?.data?.addAll(updatedPost)
    }

    fun getPictures() = viewModelScope.launch {
        fetchPost()
    }

    private suspend fun fetchPost() {
        postData.postValue(Resource.Loading())
        try {
            if (hasInternetConnection(getApplication<MyApplication>())) {
                val response = appRepository.getPosts()
                val result = handlePostResponse(response)
                postData.postValue(handlePostResponse(response))
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

    private fun handlePostResponse(response: Response<PostModel>): Resource<PostModel> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                ObjectOfResponse.updatedPost = resultResponse
                return Resource.Success(ObjectOfResponse.updatedPost as PostModel)
            }
        }
        return Resource.Error(response.message())
    }

}