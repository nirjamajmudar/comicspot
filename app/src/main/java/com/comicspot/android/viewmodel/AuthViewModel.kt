package com.comicspot.android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comicspot.android.database.AuthorizationInfo
import com.comicspot.android.repository.AuthRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel@Inject constructor(private val repository: AuthRepository) : ViewModel() {

    val authInfo: LiveData<AuthorizationInfo> = repository.authInfo
    var authInfoValue : AuthorizationInfo? = null

    fun update(authInfo: AuthorizationInfo) = viewModelScope.launch {
        authInfoValue = authInfo
        repository.update(authInfo)

    }
}