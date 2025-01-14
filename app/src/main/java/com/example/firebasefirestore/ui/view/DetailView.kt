//package com.example.firebasefirestore.ui.view
//
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Edit
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.FloatingActionButton
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.firebasefirestore.model.Mahasiswa
//import com.example.firebasefirestore.ui.viewmodel.DetailUiState
//import com.example.firebasefirestore.ui.viewmodel.DetailViewModel
//import com.example.firebasefirestore.ui.viewmodel.PenyediaViewModel
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DetailMhsView(
//    nim: String,
//    modifier: Modifier = Modifier,
//    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory),
//    onEditClick: (String) -> Unit = {},
//    navigateBack:()->Unit,
//){
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {Text("Detail")}
//            )
//        },
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = {onEditClick(nim)},
//                shape = MaterialTheme.shapes.medium,
//                modifier = Modifier.padding(16.dp)
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Edit,
//                    contentDescription = "Edit Mahasiswa"
//                )
//            }
//        }
//    ) { innerPadding->
//        val detailUiState by viewModel.detailUIState.()
//
//        BodyDetailMhs(
//            modifier = Modifier.padding(innerPadding),
//            detailUiState = detailUiState,
//            retryAction = {viewModel.getMahasiswaByNim(nim)}
//        )
//    }
//}
//
//
//@Composable
//fun BodyDetailMhs(
//    modifier: Modifier = Modifier,
//    detailUiState: DetailUiState,
//    retryAction: ()-> Unit = {}
//){
//    when (detailUiState){
//        is DetailUiState.Loading ->{
//            OnLoading(modifier = modifier.fillMaxSize())
//        }
//
//        is DetailUiState.Success->{
//            Column(
//                modifier = modifier
//                    .fillMaxSize()
//                    .padding(16.dp)
//            ) {
//                ItemDetailMhs(mahasiswa = detailUiState.mahasiswa)
//            }
//        }
//
//        is DetailUiState.Error->{
//            OnError(
//                retryAction= retryAction,
//                modifier = modifier.fillMaxSize()
//            )
//        }
//
//        else -> {
//            Text("Unexpected")
//        }
//    }
//}
//
//@Composable
//fun ItemDetailMhs(
//    mahasiswa : Mahasiswa
//){
//    Card (
//        modifier = Modifier.fillMaxWidth(),
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.primaryContainer,
//            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
//        )
//    ){
//        Column(modifier = Modifier.padding(16.dp)) {
//            ComponentDetailMhs(judul = "NIM", isinya = mahasiswa.nim)
//            Spacer(modifier = Modifier.padding(4.dp))
//            ComponentDetailMhs(judul = "Nama", isinya = mahasiswa.nama)
//            Spacer(modifier = Modifier.padding(4.dp))
//            ComponentDetailMhs(judul = "Alamat", isinya = mahasiswa.alamat)
//            Spacer(modifier = Modifier.padding(4.dp))
//            ComponentDetailMhs(judul = "Dosen pertama", isinya = mahasiswa.dosen1)
//            Spacer(modifier = Modifier.padding(4.dp))
//            ComponentDetailMhs(judul = "Dosen kedua", isinya = mahasiswa.dosen2)
//            Spacer(modifier = Modifier.padding(4.dp))
//            ComponentDetailMhs(judul = "Judul Skripsi", isinya = mahasiswa.judul_skripsi)
//            Spacer(modifier = Modifier.padding(4.dp))
//            ComponentDetailMhs(judul = "Jenis Kelamin", isinya = mahasiswa.jenis_kelamin)
//            Spacer(modifier = Modifier.padding(4.dp))
//            ComponentDetailMhs(judul = "Kelas", isinya = mahasiswa.kelas)
//            Spacer(modifier = Modifier.padding(4.dp))
//            ComponentDetailMhs(judul = "Angkatan", isinya = mahasiswa.angkatan)
//        }
//    }
//}
//
//@Composable
//fun ComponentDetailMhs(
//    judul: String,
//    isinya: String
//){
//    Column(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalAlignment = Alignment.Start
//    ) {
//        Text(
//            text = "$judul :",
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.Gray
//        )
//
//        Text(
//            text =  isinya,
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold
//        )
//    }
//}