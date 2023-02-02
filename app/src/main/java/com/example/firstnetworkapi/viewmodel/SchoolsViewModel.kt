package com.example.firstnetworkapi.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstnetworkapi.service.Network
import com.example.firstnetworkapi.service.ServiceApi
import com.example.firstnetworkapi.view.UIState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "SchoolsViewModel"

class SchoolsViewModel(
    private val serviceApi: ServiceApi,
    private val ioDispatcher :CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _schools: MutableLiveData<UIState> = MutableLiveData(UIState.LOADING)
    val schools: LiveData<UIState> get() = _schools

    fun getAllSchools() {
        viewModelScope.launch(ioDispatcher) {
            _schools.postValue(UIState.LOADING)

            try {
                val response = serviceApi.getAllSchools()
                if (response.isSuccessful) {
                    response.body()?.let {
                        // this post value works in main thread and worker thread
                        _schools.postValue(UIState.SUCCESS(it))
                        withContext(Dispatchers.Main) {
                            // this set value only works in the main thread
                            // _schools.value = UIState.SUCCESS(it)
                            Log.d(TAG, "onCreate: $it")
                        }
                    } ?: throw Exception("Error null schools response")
                } else {
                    throw Exception(response.errorBody()?.string())
                }
            } catch (e: Exception) {
                _schools.postValue(UIState.ERROR(e))
                Log.e(TAG, "onCreate: ${e.localizedMessage}", e)
            }
        }
    }
}