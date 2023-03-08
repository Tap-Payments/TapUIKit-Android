package company.tap.tapuisample.activities

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import company.tap.tapuilibrary.uikit.atoms.IndeterminateProgressButton
import company.tap.tapuilibrary.uikit.datasource.ActionButtonDataSource
import company.tap.tapuilibrary.uikit.enums.ActionButtonState
import company.tap.tapuilibrary.uikit.utils.dp
import company.tap.tapuilibrary.uikit.utils.getColorX
import company.tap.tapuilibrary.uikit.utils.getDrawableX
import company.tap.tapuilibrary.uikit.utils.sp
import company.tap.tapuilibrary.uikit.views.TabAnimatedActionButton
import company.tap.tapuilibrary.uikit.views.TapActionButton
import company.tap.tapuilibrary.uikit.views.TapMorphingButton
import company.tap.tapuisample.R
import kotlinx.coroutines.delay


class ActionButtonActivity : AppCompatActivity() {

    private lateinit var actionButton: TabAnimatedActionButton
    private lateinit var morphbutton: TapActionButton
    private lateinit var tapMorphingButton: TapMorphingButton
    private lateinit var tapButton: IndeterminateProgressButton
    private var mMorphCounter1 = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action_button)
        actionButton = findViewById(R.id.action_button)
        morphbutton = findViewById(R.id.morphbutton)
        tapMorphingButton = findViewById(R.id.tapMorphingButton)
        tapButton = findViewById(R.id.tapButton)
//        actionButton.initActionButtonDataSource(false, context?.let { LocalizationManager.getLocale(it).language },
//            Color.parseColor(ThemeManager.getValue("actionButton.Valid.goLoginBackgroundColor")),
//            Color.parseColor(ThemeManager.getValue("actionButton.Valid.titleLabelColor")),
//            "Pay" )

        actionButton.setButtonDataSource(
            false, "en",
            "Pay", Color.parseColor("#00FF00"), Color.parseColor("#FFC0CB")
        )
        val metrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(metrics)
        val density = metrics.densityDpi
        println("density is" + density)
        actionButton.setDisplayMetrics(density)
        actionButton.setOnClickListener {

            pickStatus()
        }

        morphbutton.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                morphbutton.apply {
                    fromBgColor = getColorX(R.color.gray)
                    toBgColor = getColorX(R.color.button_green)
                    fromTextColor = getColorX(R.color.white)
                    toTextColor = getColorX(R.color.black_color)
                    text = "Login"
                    textSize = 24 * sp()
                    setPadding(
                        (32 * dp()).toInt(),
                        (16 * dp()).toInt(),
                        (32 * dp()).toInt(),
                        (16 * dp()).toInt()
                    )
                    iconDrawable = getDrawableX(R.drawable.tap_logo).apply {
                        setTint(getColorX(R.color.white))
                    }
                    while (true) {
                        delay(1000)
                        setUIState(TapActionButton.UIState.Loading)
                        delay(1500)
                        setUIState(TapActionButton.UIState.Button)
                    }
                }
            }
        }
        tapButton.setOnClickListener {
            if (mMorphCounter1 == 0) {
                mMorphCounter1++;
                morphToSuccess(tapButton)
            } else if (mMorphCounter1 == 1) {
                mMorphCounter1 = 0;
                simulateProgress1(tapButton);
            }
        }

// inside on click event
        tapMorphingButton.setOnClickListener {
            tapMorphingButton.morph(getCircle())
        }

    }
    private fun morphToSuccess(btnMorph: IndeterminateProgressButton) {
        val circle: TapMorphingButton.Params = TapMorphingButton.Params.create()
            .duration(1000)
            .cornerRadius(56)
            .width(56)
            .height(56)
            .color(R.color.button_green)
            .colorPressed(R.color.gray)
            .icon(R.drawable.checkmark)
        btnMorph.morph(circle)
    }
    private fun simulateProgress1(@NonNull button: IndeterminateProgressButton) {
        val progressColor1: Int = (R.color.blue)
        val progressColor2: Int = (R.color.button_green)
        val progressColor3: Int = (R.color.colorAccent)
        val progressColor4: Int = R.color.red_error
        val color: Int = (R.color.mb_gray)
        val progressCornerRadius: Int = 4
        val width: Int = (200)
        val height: Int = 8
        val duration: Int = 3000
        val handler = Handler()
        handler.postDelayed(Runnable {
            //morphToSuccess(button)
            button.unblockTouch()
        }, 4000)
        button.blockTouch() // prevent user from clicking while button is in progress
        button.morphToProgress(
            color, progressCornerRadius, width, height, duration, progressColor1, progressColor2,
            progressColor3, progressColor4
        )
    }
    fun getCircle():TapMorphingButton.Params{
    return  TapMorphingButton.Params.create()
            .duration(500)
                .text("PAY")
            .cornerRadius(100) // 48 dp
            .width(R.dimen.edit_text_height_basic) // 48 dp
            .height(R.dimen.edit_text_height_basic) // 48 dp
            .color((R.color.gray)) // normal state color
            .colorPressed(R.color.button_green) // pressed state color
            .icon(R.drawable.checkmark); // icon
    }

        private fun pickStatus() {
            var alert: AlertDialog? = null
            val items = arrayOf("SUCCESS", "ERROR")
            val builder = AlertDialog.Builder(this)
            builder.setCancelable(true)
            builder.setTitle("Pick Status")
            builder.setItems(items) { _, position ->
                if (position == 0) {
//                actionButton.setButtonDataSource(getSuccessDataSource())
                    actionButton.changeButtonState(ActionButtonState.SUCCESS)
                    Handler(Looper.getMainLooper()).postDelayed(Runnable {
                    actionButton.changeButtonState(ActionButtonState.RESET)
                    }, 5000)
                } else {
//                actionButton.setButtonDataSource(getErrorDataSource())
                    actionButton.changeButtonState(ActionButtonState.ERROR)

                }
                alert?.hide()
            }
            alert = builder.create()
            alert.show()
        }

        private fun getIdleDataSource(): ActionButtonDataSource {
            return ActionButtonDataSource(
                text = "Pay",
                textSize = 16f,
                textColor = Color.WHITE,
                cornerRadius = 100f,
                backgroundColor = resources.getColor(R.color.button_green)
            )
        }

        private fun getErrorDataSource(): ActionButtonDataSource {
            return ActionButtonDataSource(
                text = "Pay",
                textSize = 20f,
                textColor = Color.WHITE,
                cornerRadius = 100f,
                errorImageResources = R.drawable.error_gif,
                backgroundColor = resources.getColor(R.color.button_gray),
                errorColor = resources.getColor(R.color.button_gray)
            )
        }

        @SuppressLint("ResourceAsColor")
        private fun getSuccessDataSource(): ActionButtonDataSource {
            return ActionButtonDataSource(
                text = "Pay!",
                textSize = 20f,
                textColor = Color.WHITE,
                cornerRadius = 100f,
                successImageResources = R.drawable.success,
                backgroundColor = R.color.button_green
            )
        }
    }

