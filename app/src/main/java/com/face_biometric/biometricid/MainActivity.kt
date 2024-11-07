package com.face_biometric.biometricid

import android.content.Intent
import android.hardware.biometrics.BiometricManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.face_biometric.biometricid.biometric.BiometricPromptManager
import com.face_biometric.biometricid.biometric.BiometricResult
import com.face_biometric.biometricid.ui.theme.BiometricIdTheme

class MainActivity : AppCompatActivity() {

    private val promptManager by lazy {
        BiometricPromptManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BiometricIdTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val biometricResult by promptManager.promptResult.collectAsState(initial = null)
                    LaunchedEffect(biometricResult) {
                        if (biometricResult is BiometricResult.AuthenticationNotSet) {
                            val enrollIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                                    putExtra(
                                        Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                                        BiometricManager.Authenticators.BIOMETRIC_STRONG
                                    )
                                }
                            } else {
                                null
                            }

                            enrollIntent?.let {
                            }
                        }
                    }
                    Column(
                        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = {
                            promptManager.showBiometricPrompt(
                                title = "Temirlan",
                                description = "jolchubekov"
                            )
                        }) {
                            Text(text = "Authenticated")
                        }
                        biometricResult.let { result ->
                            Text(
                                text = when (result) {
                                    is BiometricResult.AuthenticationError -> {
                                        result.error
                                    }

                                    BiometricResult.AuthenticationFailed -> {
                                        "Failed"
                                    }

                                    BiometricResult.AuthenticationNotSet -> {
                                        "not set"
                                    }

                                    BiometricResult.AuthenticationSuccess -> {
                                        "success"
                                    }

                                    BiometricResult.FeatureUnavailable -> {
                                        "unavailable"
                                    }

                                    BiometricResult.HardWareUnavailable -> {
                                        "hardware unavailable"
                                    }

                                    null -> ""
                                }
                            )
                        }
                    }
                }
            }
        }
    }

}