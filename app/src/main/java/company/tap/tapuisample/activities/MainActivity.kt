package company.tap.tapuisample.activities


import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat

import company.tap.taplocalizationkit.LocalizationManager
import company.tap.tapuilibrary.fontskit.FontChanger
import company.tap.tapuilibrary.fontskit.enums.TapFont
import company.tap.tapuilibrary.themekit.ThemeManager
import company.tap.tapuilibrary.uikit.interfaces.TapAmountSectionInterface
import company.tap.tapuilibrary.uikit.interfaces.TapSwitchInterface
import company.tap.tapuilibrary.uikit.models.DialogConfigurations
import company.tap.tapuilibrary.uikit.utils.BaseActivity
import company.tap.tapuisample.BlurBuilder
import company.tap.tapuisample.R
import company.tap.tapuisample.fragments.*
import kotlinx.android.synthetic.main.activity_main.*
import java.security.KeyStore
import java.util.*
import java.util.concurrent.Executor
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

//////https://stackoverflow.com/questions/24149690/add-local-library-project-as-a-dependency-to-multiple-projects-in-android-studio

class MainActivity : BaseActivity(),
    TapAmountSectionInterface,
    TapSwitchInterface{
    private lateinit var fontChanger: FontChanger
    private lateinit var context: Context
    private val modalNFCBottomSheet = NFCSampleFragment()
    private val modalCardScannerBottomSheet = CardScannerSampleFragment()
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private val KEY_NAME = "android"



    @SuppressLint("ResourceAsColor", "SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
      //setLocale(this, LocalizationManager.getLocale(context).language)
      //  setLocale(this, "ar")

        LocalizationManager.loadTapLocale(resources, R.raw.lang)

       ThemeManager.loadTapTheme(resources, R.raw.defaultdarktheme2, "defaultdarktheme")
//        if (ThemeManager.currentTheme.isNotEmpty() && ThemeManager.currentTheme.contains("dark"))
          //  ThemeManager.loadTapTheme(this,"https://gist.githubusercontent.com/AhlaamK-tap/976650c60952b525d51b739254cd417c/raw/4ca48d48f3cd5dc8a0d4ab49b788bb3e2e9bdc0c/defaultlighttheme1.json","lighttheme")
         //  ThemeManager.loadTapTheme(this,"https://gist.githubusercontent.com/AhlaamK-tap/2ca0cbeaf430c6d40baa4d0700024848/raw/aba506185d267ee9d379b5c84d71c8cbed1cbbf2/darktheme.json","darktheme")
//        else   ThemeManager.loadTapTheme(this,"https://gist.githubusercontent.com/AhlaamK-tap/9862436dff3b3ca222243dad3705ec6a/raw/76f9bd9d4e12e7d9dccf5f4969baf09c15a1ea47/lighttheme","lighttheme")


        supportActionBar?.setBackgroundDrawable( ColorDrawable(Color.parseColor("#005959")))

        val originalBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.backgr)
        val blurredBitmap: Bitmap? = BlurBuilder.blur(this, originalBitmap)
        linearMain.background = BitmapDrawable(resources, originalBitmap)


        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    fun openBottomSheet(view: View) {
        val modalBottomSheet = BottomSheetDialog()
        modalBottomSheet.arguments = getArguments()
        modalBottomSheet.show(supportFragmentManager, BottomSheetDialog.TAG)
    }

    private fun getArguments(): Bundle {
        val arguments = Bundle()
        arguments.putFloatArray(DialogConfigurations.Corners, floatArrayOf(25f, 25f, 0f, 0f))
        arguments.putInt(DialogConfigurations.Color, Color.WHITE)
        arguments.putBoolean(DialogConfigurations.Cancelable, false)
        arguments.putFloat(DialogConfigurations.Dim, 0.75f)
        return arguments
    }

    fun openTapChip(view: View) {
        startActivity(Intent(this, TapChipsActivity::class.java))
    }

    override fun didClickItems() {
        println("Items button clicked !!!")
        Toast.makeText(this, "You have clicked Items", Toast.LENGTH_SHORT).show()
    }

    fun openAmountDiscountRow(view: View) {
        startActivity(Intent(this, AmountItemActivity::class.java))
    }

    fun openSectionsTabLayout(view: View) {
        startActivity(Intent(this, SectionsTabLayout::class.java))
    }

    fun openActionButton(view: View) {
        startActivity(Intent(this, ActionButtonActivity::class.java))
    }

    override fun enableSaveMobile(boolean: Boolean) {
        println("enableSaveMobile $boolean")
    }

    override fun enableSaveMerchantCheckout(boolean: Boolean) {
        println("enableSaveMerchantCheckout $boolean")
    }

    override fun enableSavegoPayCheckout(boolean: Boolean) {
        println("enableSavegoPayCheckout $boolean")
    }

    fun openSwitchView(view: View) {
        fontChanger = FontChanger(this.assets, TapFont.tapFontType(TapFont.RobotoLight))
        fontChanger.replaceFonts((findViewById(android.R.id.content)))
        val modalswitchBottomSheet =
            SwitchFragment()
        modalswitchBottomSheet.arguments = getArguments()
        modalswitchBottomSheet.show(
            supportFragmentManager,
            BottomSheetDialog.TAG
        )
    }

    fun openNFCView(view: View) {
        modalNFCBottomSheet.arguments = getArguments()
//        modalNFCBottomSheet.show(
//            supportFragmentManager,
//            BottomSheetDialog.TAG
//        )


    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        modalNFCBottomSheet.processNFC(intent)
    }

    fun openCardScannerView(view: View) {
        modalCardScannerBottomSheet.arguments = getArguments()
        modalCardScannerBottomSheet.show(
            supportFragmentManager,
            BottomSheetDialog.TAG
        )

    }

/*    override fun onScanCardFinished(card: Card?, cardImage: ByteArray?) {
      //  Toast.makeText(context,card.toString(), Toast.LENGTH_LONG).show()
        if (card != null) {
            showCardDialog(card)

        }
        Handler().postDelayed({
            supportFragmentManager
                .beginTransaction()
                .remove(modalCardScannerBottomSheet)
                .commit()
        }, 8000)


    }*/
   /* private fun showCardDialog(card: Card) {
        FrameManager.getInstance().frameColor = Color.GREEN
        val cardData = """
                Card number: ${card.cardNumberRedacted}
                Card holder: ${card.cardHolderName}
                Card expiration date: ${card.expirationDate}
                """.trimIndent()
        AlertDialog.Builder(this)
            .setTitle("Card Info")
            .setMessage(cardData)
            .setPositiveButton(android.R.string.yes, null)
            .setIcon(android.R.drawable.ic_dialog_info)
            .show()
    }*/


  /*  override fun onScanCardFailed(e: Exception?) {
        println("card scan failed $e")
    }
*/
    fun openFragment(view: View) {
        ExampleFragment()
            .show(supportFragmentManager, null)
    }
    fun swapTheme(view: View) {
//        startActivity(Intent(this, ThemeTestActivity::class.java))

        if (ThemeManager.currentTheme.isNotEmpty() && ThemeManager.currentTheme.contains("dark")) {
            ThemeManager.loadTapTheme(resources, R.raw.defaultlighttheme1, "defaultlighttheme")
        }else{
            ThemeManager.loadTapTheme(resources, R.raw.defaultdarktheme2, "defaultdarktheme")
        }

        }

    fun otpFragment(view: View) {
        OTPFragment().show(supportFragmentManager, null)
    }

    fun openBiometrics(view: View) {
        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS ->
                Log.d("MY_APP_TAG", "App can authenticate using biometrics.")
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                Log.e("MY_APP_TAG", "No biometric features available on this device.")
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                Log.e("MY_APP_TAG", "Biometric features are currently unavailable.")
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
                Log.e(
                    "MY_APP_TAG", "The user hasn't associated " +
                            "any biometric credentials with their account."
                )
        }
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        applicationContext,
                        "Authentication error: $errString", Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(
                        applicationContext,
                        "Authentication succeeded!", Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        applicationContext, "Authentication failed",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Touch ID for goPay")
            .setSubtitle("Authentication is needed to pay")
            .setNegativeButtonText(".")
            .build()
        biometricPrompt.authenticate(promptInfo)

      /*  val cipher = getCipher()
        val secretKey = getSecretKey()
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        biometricPrompt.authenticate(promptInfo,
            BiometricPrompt.CryptoObject(cipher))*/
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun generateSecretKey(keyGenParameterSpec: KeyGenParameterSpec) {
        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore"
        )
        keyGenerator.init(keyGenParameterSpec)
        keyGenerator.generateKey()
    }

    private fun getSecretKey(): SecretKey {
        val keyStore = KeyStore.getInstance("AndroidKeyStore")

        // Before the keystore can be accessed, it must be loaded.
        keyStore.load(null)
        return keyStore.getKey(KEY_NAME, null) as SecretKey
    }

    private fun getCipher(): Cipher {
        return Cipher.getInstance(
            KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/"
                    + KeyProperties.ENCRYPTION_PADDING_PKCS7
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menumain, menu)
        return true
    }
    // actions on click menu items
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_dark -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            true
        }
        R.id.action_light -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

            true
        }
        R.id.change_language -> {
            if (LocalizationManager.getLocale(context).language == "en") {
                LocalizationManager.setLocale(this, Locale("ar"))
                setLocale(this, "ar")
            } else if (LocalizationManager.getLocale(context).language == "ar") {
                LocalizationManager.setLocale(this, Locale("en"))
                setLocale(this, "en")
            }
            recreate()
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }


    fun setLocale(activity: Activity, languageCode: String?) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources: Resources = activity.resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    fun showLoyaltyView(view: View) {

        startActivity(Intent(this, LoyaltyViewActivity::class.java))
    }


}

