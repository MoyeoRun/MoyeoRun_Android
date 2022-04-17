package com.moyerun.moyeorun_android.login

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import java.lang.IllegalStateException
import kotlin.coroutines.resume

class LoginRepository(
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    // Todo: 모여런 서버 붙일 때 반환 타입 조정
    suspend fun signIn(idToken: String): ApiResponse<Unit> {
        val firebaseSignInResult = withContext(coroutineDispatcher) { signInWithFirebaseCredential(idToken) }
        if (firebaseSignInResult is ApiResponse.Failure) {
            return firebaseSignInResult
        }
        val moyeorunSignInResult = withContext(coroutineDispatcher) { signInWithMoyeoRun(idToken) }
        if (firebaseSignInResult is ApiResponse.Failure) {
            Firebase.auth.signOut()
        }
        return withContext(coroutineDispatcher) { signInWithMoyeoRun(idToken) }
    }

    private suspend fun signInWithFirebaseCredential(idToken: String): ApiResponse<FirebaseUser> {
        val firebaseAuth = Firebase.auth
        return suspendCancellableCoroutine {
            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
            firebaseAuth.signInWithCredential(firebaseCredential)
                .addOnCompleteListener { task ->
                    val firebaseUser = firebaseAuth.currentUser
                    if (firebaseUser != null) {
                        it.resume(ApiResponse.Success(firebaseUser))
                    } else {
                        it.resume(
                            ApiResponse.Failure(
                                task.exception
                                    ?: IllegalStateException("FirebaseAuth Failure: FirebaseUser and Task.exception is null")
                            )
                        )
                    }
                }
        }
    }

    private suspend fun signInWithMoyeoRun(idToken: String): ApiResponse<Unit> {
        //Todo: 모여런 서버 signIn
        return ApiResponse.Success(Unit)
    }

    // Todo: 임시. 네트워크 베이스 코드가 Rebase 되면 대체할 것
    sealed class ApiResponse<out T> {
        data class Success<T>(val data: T) : ApiResponse<T>()
        data class Failure(val error: Throwable) : ApiResponse<Nothing>()
    }
}