package com.example.firebasefirestore.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.firebasefirestore.repository.MahasiswaRepository

class InserViewModel(
    private val mhs: MahasiswaRepository
): ViewModel(){

    data class MahasiswaEvent(
        val nim: String = "",
        val nama: String = "",
        val jenisKelamin: String = "",
        val alamat: String = "",
        val kelas: String = "",
        val angkatan: String = "",
    )
}