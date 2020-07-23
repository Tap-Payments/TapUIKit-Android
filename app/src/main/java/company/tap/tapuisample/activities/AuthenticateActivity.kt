package company.tap.tapuisample.activities

import android.Manifest
import android.annotation.TargetApi
import android.app.KeyguardManager
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyPermanentlyInvalidatedException
import android.security.keystore.KeyProperties
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import company.tap.tapuisample.R
import java.io.IOException
import java.security.*
import java.util.concurrent.Executor
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey
import javax.security.cert.CertificateException

/**
 * Created by AhlaamK on 7/22/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
class AuthenticateActivity: AppCompatActivity() {
    private var keyStore: KeyStore? = null

    // Variable used for storing the key in the Android Keystore container
    private val KEY_NAME = "android"
    lateinit var cipher: Cipher
    lateinit var textView: TextView
    lateinit var executor: Executor

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authenticate)
        //textView =  findViewById(R.id.errorText)
        initializeFingerPrint()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initializeFingerPrint() {
        // Initializing both Android Keyguard Manager and Fingerprint Manager
        // Initializing both Android Keyguard Manager and Fingerprint Manager
        val keyguardManager =
            getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        val fingerprintManager : FingerprintManager =  getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager
        // Check whether the device has a Fingerprint sensor.
        if(!fingerprintManager.isHardwareDetected()){
            /**
             * An error message will be displayed if the device does not contain the fingerprint hardware.
             * However if you plan to implement a default authentication method,
             * you can redirect the user to a default authentication activity from here.
             * Example:
             * Intent intent = new Intent(this, DefaultAuthenticationActivity.class);
             * startActivity(intent);
             */
            textView.setText("Your Device does not have a Fingerprint Sensor");
        }else{
            // Checks whether fingerprint permission is set on manifest
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                textView.setText("Fingerprint authentication permission not enabled");
            }else{
                // Check whether at least one fingerprint is registered
                if (!fingerprintManager.hasEnrolledFingerprints()) {
                    textView.setText("Register at least one fingerprint in Settings");
                }else{
                    // Checks whether lock screen security is enabled or not
                    if (!keyguardManager.isKeyguardSecure()) {
                        textView.setText("Lock screen security not enabled in Settings");
                    }else{
                        generateKey();


                        if (cipherInit()) {
                            if(cipher!=null) {
                                val cryptoObject =
                                    FingerprintManager.CryptoObject(cipher!!)
                                val helper = FingerprintHandler(this)
                                helper.startAuth(fingerprintManager, cryptoObject)
                            }
                        }
                    }
                }
            }
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    protected fun generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val keyGenerator: KeyGenerator
        keyGenerator = try {
            KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to get KeyGenerator instance", e)
        } catch (e: NoSuchProviderException) {
            throw RuntimeException("Failed to get KeyGenerator instance", e)
        }
        try {
            keyStore!!.load(null)
            keyGenerator.init(
                KeyGenParameterSpec.Builder(
                    KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT or
                            KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                        KeyProperties.ENCRYPTION_PADDING_PKCS7
                    )
                    .build()
            )
            keyGenerator.generateKey()
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        } catch (e: InvalidAlgorithmParameterException) {
            throw RuntimeException(e)
        } catch (e: CertificateException) {
            throw RuntimeException(e)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    open fun cipherInit(): Boolean {
        cipher = try {
            Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to get Cipher", e)
        } catch (e: NoSuchPaddingException) {
            throw RuntimeException("Failed to get Cipher", e)
        }
        return try {
            keyStore!!.load(null)
            val key: SecretKey = keyStore!!.getKey(
                KEY_NAME,
                null
            ) as SecretKey
            cipher.init(Cipher.ENCRYPT_MODE, key)
            true
        } catch (e: KeyPermanentlyInvalidatedException) {
            false
        } catch (e: KeyStoreException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: CertificateException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: UnrecoverableKeyException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: IOException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: InvalidKeyException) {
            throw RuntimeException("Failed to init Cipher", e)
        }
    }

    fun openBiometric(view: View) {
        val cancellationSignal = CancellationSignal()
        cancellationSignal.setOnCancelListener {
            showMessage("CancellationListener setOnCancelListener triggered")
        }
        /**
         * Currently BiometricPrompt API is supported only in Android P(version 28) devices.
         * For devices below Android version 28 Google is planning to provide Biometric Prompt Support Compat Library.
         * This will be released in subsequent versions. We can use CryptoObject variable as well in authenticate method for more stronger authentication
         * @link https://developer.android.com/reference/android/hardware/biometrics/BiometricPrompt.html#authenticate(android.hardware.biometrics.BiometricPrompt.CryptoObject,%20android.os.CancellationSignal,%20java.util.concurrent.Executor,%20android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            BiometricPrompt.Builder(this)
                .setDescription("Test Biometric Dialog")
                .setTitle("Biometric dialog")
                .setNegativeButton("Cancel", mainExecutor, DialogInterface.OnClickListener { dialogInterface, i ->
                    showMessage("Cancel button clicked")
                })
                .build()
                .authenticate(cancellationSignal, mainExecutor, object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                        super.onAuthenticationSucceeded(result)
                        showMessage("Authentication successful")

                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        /**
                         * Authentication failure takes place if we provide incorrect or unknown biometric credentials
                         */
                        showMessage("Authentication Failed")
                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                        super.onAuthenticationError(errorCode, errString)
                        /**
                         * Authentication errors can be of different types like lockout, hardware problems, issues in captured biometrics, timeouts,
                         * vendor error, etc.
                         */
                        showMessage("Authentication Error $errorCode $errString")
                        when (errorCode) {
                            BiometricPrompt.BIOMETRIC_ERROR_LOCKOUT_PERMANENT,
                            BiometricPrompt.BIOMETRIC_ERROR_LOCKOUT,
                            BiometricPrompt.BIOMETRIC_ERROR_CANCELED,
                            BiometricPrompt.BIOMETRIC_ERROR_HW_NOT_PRESENT,
                            BiometricPrompt.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                                showMessage("Biometric hardware and lockout issues")
                            }
                            BiometricPrompt.BIOMETRIC_ERROR_NO_SPACE,
                            BiometricPrompt.BIOMETRIC_ERROR_NO_BIOMETRICS -> {
                                showMessage("Biometric settings changes i.e either biometric is removed or error while adding biometric")
                            }
                            BiometricPrompt.BIOMETRIC_ERROR_USER_CANCELED -> {
                                showMessage("User presses back button when biometric dialog is displayed")
                            }
                            BiometricPrompt.BIOMETRIC_ERROR_TIMEOUT,
                            BiometricPrompt.BIOMETRIC_ERROR_VENDOR,
                            BiometricPrompt.BIOMETRIC_ERROR_UNABLE_TO_PROCESS -> {
                                showMessage("Biometric timeout, vendor or processing errors")
                            }
                            else -> {
                                showMessage("Generic biometric error")
                            }

                        }
                    }

                    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
                        super.onAuthenticationHelp(helpCode, helpString)
                        /**
                         * Help provided by biometric dialog during authentication
                         */
                        showMessage("Authentication Error $helpCode $helpString")
                        when (helpCode) {
                            BiometricPrompt.BIOMETRIC_ACQUIRED_IMAGER_DIRTY,
                            BiometricPrompt.BIOMETRIC_ACQUIRED_INSUFFICIENT,
                            BiometricPrompt.BIOMETRIC_ACQUIRED_PARTIAL,
                            BiometricPrompt.BIOMETRIC_ACQUIRED_TOO_FAST,
                            BiometricPrompt.BIOMETRIC_ACQUIRED_TOO_SLOW -> {
                                showMessage("Quality of image while IRIS and Face scan is blurry OR biometric moved fast or slow")
                            }
                            else -> {
                                showMessage("Generic biometric help")
                            }
                        }
                    }
                })
        }
    }
    fun showMessage(message: String) {
        Log.d("TAG", message)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}