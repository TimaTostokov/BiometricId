package com.face_biometric.biometricid.biometric

sealed interface BiometricResult {
    data object HardWareUnavailable : BiometricResult
    data object FeatureUnavailable : BiometricResult
    data class AuthenticationError(val error: String) : BiometricResult
    data object AuthenticationFailed : BiometricResult
    data object AuthenticationSuccess : BiometricResult
    data object AuthenticationNotSet : BiometricResult
}