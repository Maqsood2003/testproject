package com.pregnancytracker.vitals

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pregnancytracker.vitals.ui.screen.VitalsScreen
import com.pregnancytracker.vitals.ui.theme.PregnancyVitalsTrackerTheme
import com.pregnancytracker.vitals.ui.viewmodel.VitalsViewModel

class MainActivity : ComponentActivity() {
    
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission granted, notifications will work
        } else {
            // Permission denied, handle accordingly
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Request notification permission for Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
        
        setContent {
            PregnancyVitalsTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val application = application as PregnancyTrackerApplication
                    val viewModel: VitalsViewModel = viewModel {
                        VitalsViewModel(
                            repository = application.repository,
                            notificationManager = application.notificationManager
                        )
                    }
                    
                    VitalsScreen(
                        viewModel = viewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    
                    // Check if opened from notification
                    intent?.getBooleanExtra("open_vitals_dialog", false)?.let { shouldOpen ->
                        if (shouldOpen) {
                            viewModel.showAddDialog()
                        }
                    }
                }
            }
        }
    }
}
