package com.example.threads.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class AuthViewModel : ViewModel() {

    val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance()
    val userRef = db.getReference("users")

    private val _firebaseUser = MutableLiveData<FirebaseUser>()
    val firebaseUser : LiveData<FirebaseUser> = _firebaseUser

    private val _error = MutableLiveData<String>()
    val error : LiveData<String> = _error

    init{
        _firebaseUser.value = auth.currentUser
    }

    fun login(email : String, password : String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    _firebaseUser.postValue(auth.currentUser)
                }else{
                    _error.postValue("Something went wrong")
                }
            }
    }

    fun register(email : String, password : String,name : String,bio : String,username : String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    _firebaseUser.postValue(auth.currentUser)
                }else{
                    _error.postValue("Something went wrong")
                }
            }
    }

    // 1:00:50
    //https://console.firebase.google.com/project/thread-40271/overview
}
