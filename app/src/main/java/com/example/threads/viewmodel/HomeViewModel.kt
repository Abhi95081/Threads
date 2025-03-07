package com.example.threads.viewmodel

import android.content.Context
import android.media.Image
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.threads.model.Threadmodel
import com.example.threads.model.UserModel
import com.example.threads.utils.Sharedpref
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.storage
import java.util.UUID

class HomeViewModel : ViewModel() {

    private val db = FirebaseDatabase.getInstance()
     val thread = db.getReference("threads")

    private val _threadsAndUsers = MutableLiveData<List<Pair<Threadmodel, UserModel>>>()
    val threadsAndUsers: LiveData<List<Pair<Threadmodel, UserModel>>> = _threadsAndUsers

    private fun fetchThreadsAndUsers(onResult: (List<Pair<Threadmodel, UserModel>>) -> Unit) {
        thread.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val result = mutableListOf<Pair<Threadmodel, UserModel>>()

                for(threadSnapshot in snapshot.children) {
                      val thread = threadSnapshot.getValue(Threadmodel::class.java)
                    thread.let {
                        fetchUserFromThread(it!!) {
                            user ->
                            result.add(0,it to user)

                            if(result.size == snapshot.childrenCount.toInt()) {
                                onResult(result)
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun fetchUserFromThread(thread: Threadmodel, onResult: (UserModel) -> Unit) {
        db.getReference("threads").child(thread.userId)
            .addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(UserModel::class.java)
                    user?.let { onResult }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

    }
}

