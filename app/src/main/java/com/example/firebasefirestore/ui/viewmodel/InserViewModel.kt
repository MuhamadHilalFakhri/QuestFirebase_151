package com.example.firebasefirestore.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.firebasefirestore.model.Mahasiswa
import com.example.firebasefirestore.repository.MahasiswaRepository

class InserViewModel(
    private val mhs: MahasiswaRepository
): ViewModel(){



}

data class MahasiswaEvent(
    val nim: String = "",
    val nama: String = "",
    val jenis_kelamin: String = "",
    val alamat: String = "",
    val kelas: String = "",
    val angkatan: String = "",
)

//Menyimpan input form ke dalam entity
fun Mahasiswa.toMhsModel():Mahasiswa =Mahasiswa(
    nim = nim,
    nama = nama,
    jenis_kelamin = jenis_kelamin,
    alamat = alamat,
    kelas = kelas,
    angkatan = angkatan
)