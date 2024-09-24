package com.lemieux.tasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.lemieux.tasks.ui.screen.TaskManagerScreen
import com.lemieux.tasks.ui.theme.TasksAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TasksAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TaskManagerScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
