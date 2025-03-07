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
    private val threadRef = db.getReference("threads")

    private var _threadsAndUsers = MutableLiveData<List<Pair<Threadmodel, UserModel>>>()
    val threadsAndUsers: LiveData<List<Pair<Threadmodel, UserModel>>> = _threadsAndUsers

    init {
        fetchThreadsAndUsers()
    }

    private fun fetchThreadsAndUsers() {
        threadRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val result = mutableListOf<Pair<Threadmodel, UserModel>>()
                val totalThreads = snapshot.childrenCount.toInt()

                if (totalThreads == 0) {
                    _threadsAndUsers.value = result
                    return
                }

                var processedThreads = 0

                for (threadSnapshot in snapshot.children) {
                    val thread = threadSnapshot.getValue(Threadmodel::class.java)
                    thread?.let { threadModel ->
                        fetchUserFromThread(threadModel) { user ->
                            result.add(threadModel to user)
                            processedThreads++

                            // Update LiveData once all threads are processed
                            if (processedThreads == totalThreads) {
                                _threadsAndUsers.value = result
                            }
                        }
                    } ?: run {
                        processedThreads++
                        if (processedThreads == totalThreads) {
                            _threadsAndUsers.value = result
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error properly (optional logging)
            }
        })
    }

    private fun fetchUserFromThread(thread: Threadmodel, onResult: (UserModel) -> Unit) {
        db.getReference("users").child(thread.userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(UserModel::class.java)
                    onResult(user ?: UserModel("Unknown", "")) // Provide a default user if null
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            })
    }
}
