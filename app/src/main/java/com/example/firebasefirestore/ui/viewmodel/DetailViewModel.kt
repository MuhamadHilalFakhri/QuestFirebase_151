package com.example.firebasefirestore.ui.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasefirestore.model.Mahasiswa
import com.example.firebasefirestore.repository.MahasiswaRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch


sealed class DetailUiState {
    data class Success(val mahasiswa: Mahasiswa) : DetailUiState()
    data class Error(val exception: Throwable) : DetailUiState()
    object Loading : DetailUiState()
}

class DetailViewModel(
    private val mhs: MahasiswaRepository
) : ViewModel() {

    var detailUIState: DetailUiState by mutableStateOf(DetailUiState.Loading)
        private set

    fun getMahasiswaByNim(nim: String) {
        viewModelScope.launch {
            mhs.getMahasiswaByNim(nim)
                .onStart {
                    detailUIState = DetailUiState.Loading
                }
                .catch { exception ->
                    detailUIState = DetailUiState.Error(exception)
                }
                .collect { mahasiswa ->
                    detailUIState = DetailUiState.Success(mahasiswa)
                }
        }
    }
}

