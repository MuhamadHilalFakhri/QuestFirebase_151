package com.example.firebasefirestore.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebasefirestore.model.Mahasiswa
import com.example.firebasefirestore.ui.viewmodel.FormErrorState
import com.example.firebasefirestore.ui.viewmodel.FormState
import com.example.firebasefirestore.ui.viewmodel.HomeUiState
import com.example.firebasefirestore.ui.viewmodel.InsertUiState
import com.example.firebasefirestore.ui.viewmodel.InsertViewModel
import com.example.firebasefirestore.ui.viewmodel.MahasiswaEvent
import com.example.firebasefirestore.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertMhsView(
    onBack: () -> Unit,
    onNavigate: ()-> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val uiState = viewModel.uiState
    val uiEvent = viewModel.uiEvent
    val snackbarHostState = remember{ SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    LaunchedEffect(uiState) {
        when (uiState){
            is FormState.Success ->{
                println(
                    "InsertMhsView: uiState is FormState.Success, navigate to home " + uiState.message
                )

                coroutineScope.launch {
                    snackbarHostState.showSnackbar(uiState.message)
                }
                delay(700)
                onNavigate()

                viewModel.resetSnackBarMessage()
            }

            is FormState.Error -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(uiState.message)
                }
            }
            else -> Unit
        }
    }

    Scaffold (
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Tambah Mahasiswa") },
                navigationIcon = {
                    Button(onClick = onBack) {
                        Text("Back")
                    }
                }
            )
        }
    ){ padding ->
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp)
            .verticalScroll(scrollState)
        )
        {
            InsertBodyMhs(
                uiState = uiEvent,
                homeUiState = uiState,
                onValueChange = {updatedEvent ->
                    viewModel.updateState(updatedEvent)
                },
                onClick = {
                    if (viewModel.validateFields()){
                        viewModel.insertMhs()
                    }
                }
            )
        }
    }
}
@Composable
fun InsertBodyMhs(
    modifier: Modifier = Modifier,
    onValueChange: (MahasiswaEvent) -> Unit,
    uiState: InsertUiState,
    onClick: () -> Unit,
    homeUiState: FormState
){
    Column (
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        FormMahasiswa(
            mahasiswaEvent = uiState.insertUiEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = homeUiState !is FormState.Loading,
        ) {
            if (homeUiState is FormState.Loading){
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier
                        .size(20.dp)
                        .padding(end = 8.dp)
                )
                Text("Loading...")
            }else{
                Text("Add")
            }
        }
    }
}
@Composable
fun FormMahasiswa(
    mahasiswaEvent: MahasiswaEvent = MahasiswaEvent(),
    onValueChange: (MahasiswaEvent) -> Unit,
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
) {
    val jenis_kelamin = listOf("Laki-laki", "Perempuan")
    val kelas = listOf("A", "B", "C", "D", "E")
    val dosen1 = listOf("Akbar","Kevin","Arif")
    val dosen2 = listOf("Tejo","Astra","Omen")

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.nama,
            onValueChange = {
                onValueChange(mahasiswaEvent.copy(nama = it))
            },
            label = { Text("Nama") },
            isError = errorState.nama != null,
            placeholder = { Text("Masukkan nama") },
        )
        Text(
            text = errorState.nama ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.nim,
            onValueChange = {
                onValueChange(mahasiswaEvent.copy(nim = it))
            },
            label = { Text("NIM") },
            isError = errorState.nim != null,
            placeholder = { Text("Masukkan NIM") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = errorState.nim ?: "",
            color = Color.Red
        )

        Text(text = "Jenis Kelamin")
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            jenis_kelamin.forEach { jk ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = mahasiswaEvent.jenis_kelamin == jk,
                        onClick = {
                            onValueChange(mahasiswaEvent.copy(jenis_kelamin = jk))
                        },
                    )
                    Text(
                        text = jk,
                    )
                }
            }
        }

        Text(text = "Dosen Pertama")
        Row {
            dosen1.forEach { dosen1 ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = mahasiswaEvent.dosen1 == dosen1,
                        onClick = {
                            onValueChange(mahasiswaEvent.copy(dosen1 = dosen1))
                        },
                    )
                    Text(text = dosen1)
                }
            }
            Text(
                text = errorState.dosen1 ?: "",
                color = Color.Red
            )
        }

        Text(text = "Dosen Kedua")
        Row {
            dosen2.forEach { dosen2 ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = mahasiswaEvent.dosen2 == dosen2,
                        onClick = {
                            onValueChange(mahasiswaEvent.copy(dosen2 = dosen2))
                        },
                    )
                    Text(text = dosen2)
                }
            }
            Text(
                text = errorState.dosen2 ?: "",
                color = Color.Red
            )
        }
        Text(
            text = errorState.dosen2 ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.alamat,
            onValueChange = {
                onValueChange(mahasiswaEvent.copy(alamat = it))
            },
            label = { Text("Alamat") },
            isError = errorState.alamat != null,
            placeholder = { Text("Masukkan Alamat") },
        )
        Text(
            text = errorState.alamat ?: "",
            color = Color.Red
        )

        Text(text = "Kelas")
        Row {
            kelas.forEach { kelas ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = mahasiswaEvent.kelas == kelas,
                        onClick = {
                            onValueChange(mahasiswaEvent.copy(kelas = kelas))
                        },
                    )
                    Text(text = kelas)
                }
            }
            Text(
                text = errorState.kelas ?: "",
                color = Color.Red
            )
        }
    }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = mahasiswaEvent.angkatan,
        onValueChange = {
            onValueChange(mahasiswaEvent.copy(angkatan = it))
        },
        label = { Text("Angkatan") },
        isError = errorState.angkatan != null,
        placeholder = { Text("Masukkan angkatan") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
    Text(
        text = errorState.angkatan ?: "",
        color = Color.Red
    )

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = mahasiswaEvent.judul_skripsi,
        onValueChange = {
            onValueChange(mahasiswaEvent.copy(judul_skripsi = it))
        },
        label = { Text("Judul skrispi") },
        isError = errorState.judul_skripsi != null,
        placeholder = { Text("Masukkan judul skripsi") },
    )
    Text(
        text = errorState.judul_skripsi ?: "",
        color = Color.Red
    )
///



//    Spacer(modifier = Modifier.height(16.dp))
//
//    OutlinedTextField(
//        modifier = Modifier.fillMaxWidth(),
//        value = mahasiswaEvent.dosen2,
//        onValueChange = {
//            onValueChange(mahasiswaEvent.copy(dosen2 = it))
//        },
//        label = { Text("Dosen") },
//        isError = errorState.dosen2 != null,
//        placeholder = { Text("Masukkan dosen pertama") },
//    )
//    Text(
//        text = errorState.dosen2 ?: "",
//        color = Color.Red
//    )
}