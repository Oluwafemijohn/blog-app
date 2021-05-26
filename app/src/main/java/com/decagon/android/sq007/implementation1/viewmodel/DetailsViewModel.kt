package com.decagon.android.sq007.implementation1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.decagon.android.sq007.R
import com.decagon.android.sq007.implementation1.app.MyApplication
import com.decagon.android.sq007.implementation1.model.commenstmodel.CommentModel
import com.decagon.android.sq007.implementation1.model.commenstmodel.CommentModelItem
import com.decagon.android.sq007.implementation1.repository.Repository
import com.decagon.android.sq007.implementation1.utils.Resource
import com.decagon.android.sq007.implementation1.utils.Util.hasInternetConnection
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class DetailsViewModel(app: Application, private val appRepository: Repository) : AndroidViewModel(app)  {
    private val _commentData: MutableLiveData<Resource<CommentModel>> = MutableLiveData()
    val  commentData get()= _commentData

    private val _comment: MutableLiveData<Resource<CommentModelItem>> = MutableLiveData()
    val  comment : LiveData<Resource<CommentModelItem>>
    get()= _comment
     fun getCommentsId(position: Int){
        viewModelScope.launch {
            appRepository.getComments(position)
            commentData.postValue(Resource.Loading())

            try {
                if (hasInternetConnection(getApplication<MyApplication>())) {
                    val response = appRepository.getComments(position)
                    commentData.postValue(handlePostResponse(response))
                } else {
                    commentData.postValue(Resource.Error(getApplication<MyApplication>().getString(R.string.no_internet_connection)))
                }
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> commentData.postValue(
                        Resource.Error(
                            getApplication<MyApplication>().getString(
                                R.string.network_failure
                            )
                        )
                    )
                    else -> commentData.postValue(
                        Resource.Error(
                            getApplication<MyApplication>().getString(
                                R.string.conversion_error
                            )
                        )
                    )
                }
            }
        }

    }

    private fun handlePostResponse(response: Response<CommentModel>): Resource<CommentModel> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handlePostResponse2(response: Response<CommentModelItem>): Resource<CommentModelItem> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


    fun addComment(body:String, email: String?, id: Int?, name: String?, postId:Int){
        viewModelScope.launch {
            val commentModelPost = CommentModelItem(body, email!!, id!!, name!!, postId)
            val response = appRepository.addComment(commentModelPost)
            _comment.value = handlePostResponse2(response)

        }
    }
}