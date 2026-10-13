package com.example.financemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.financemanager.ui.navigation.AppNavigation
import com.example.financemanager.ui.theme.FinanceManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinanceManagerTheme {
                AppNavigation()
            }
        }
    }
}