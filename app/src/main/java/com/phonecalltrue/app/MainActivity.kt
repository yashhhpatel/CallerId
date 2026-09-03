package com.phonecalltrue.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.phonecalltrue.app.navigation.PhoneCallTrueNavHost
import com.phonecalltrue.app.ui.theme.PhoneCallTrueTheme
import androidx.lifecycle.compose.collectAsStateWithLifecycle

class MainActivity : ComponentActivity() {

    private val viewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()
            PhoneCallTrueTheme(themeMode = themeMode) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    PhoneCallTrueNavHost(viewModel = viewModel)
                }
            }
        }
    }
}
