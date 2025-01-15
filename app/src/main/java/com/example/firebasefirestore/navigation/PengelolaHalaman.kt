package com.example.firebasefirestore.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
//import com.example.firebasefirestore.ui.view.DetailMhsView
import com.example.firebasefirestore.ui.view.HomeScreen
import com.example.firebasefirestore.ui.view.InsertMhsView

@Composable
fun PengelolaHalaman(
    modifier: Modifier,
    navController: NavHostController = rememberNavController()
){
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier
    ){
        composable(DestinasiHome.route){
            HomeScreen(
                navigateToItemEntry = {
                    navController.navigate(DestinasiInsert.route)
                }
            )
        }

        composable(DestinasiInsert.route){
            InsertMhsView(
                onBack = {navController.popBackStack()},
                onNavigate = {
                    navController.navigate(DestinasiHome.route)
                }
            )
        }
//        composable(DestinasiDetail.route) { backStackEntry ->
//            // Mendapatkan NIM dari argument route
//            val nim = backStackEntry.arguments?.getString(DestinasiDetail.route)
//
//            nim?.let {
//                DetailMhsView(
//                    nim = it, // Mengirimkan NIM ke DetailMhsView
//                    navigateBack = {
//                        // Aksi ketika tombol "Kembali" ditekan
//                        navController.navigate(DestinasiHome.route) {
//                            popUpTo(DestinasiHome.route) {
//                                inclusive = true // Pop sampai ke DestinasiHome
//                            }
//                        }
//                    },
//
//                )
//            }
        }
    }