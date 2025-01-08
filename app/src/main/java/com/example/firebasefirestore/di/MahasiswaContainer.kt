package com.example.firebasefirestore.di

import com.example.firebasefirestore.repository.MahasiswaRepository
import com.example.firebasefirestore.repository.NetworkMahasiswaRepository
import com.google.firebase.firestore.FirebaseFirestore

interface AppContainer{
    val mahasiswaRepository: MahasiswaRepository
}
class MahasiswaContainer : AppContainer {
    private val firebase: FirebaseFirestore = FirebaseFirestore.getInstance()

    override val mahasiswaRepository: MahasiswaRepository by lazy {
        NetworkMahasiswaRepository(firebase)
    }

}