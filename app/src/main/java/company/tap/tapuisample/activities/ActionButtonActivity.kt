package company.tap.tapuisample.activities

import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import company.tap.taplocalizationkit.LocalizationManager
import company.tap.tapuilibrary.themekit.ThemeManager
import company.tap.tapuilibrary.uikit.datasource.ActionButtonDataSource
import company.tap.tapuilibrary.uikit.enums.ActionButtonState
import company.tap.tapuilibrary.uikit.views.TabAnimatedActionButton
import company.tap.tapuisample.R
import company.tap.tapuisample.adapters.context


class ActionButtonActivity : AppCompatActivity() {

    private lateinit var actionButton: TabAnimatedActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action_button)
        actionButton = findViewById(R.id.action_button)
//        actionButton.initActionButtonDataSource(false, context?.let { LocalizationManager.getLocale(it).language },
//            Color.parseColor(ThemeManager.getValue("actionButton.Valid.goLoginBackgroundColor")),
//            Color.parseColor(ThemeManager.getValue("actionButton.Valid.titleLabelColor")),
//            "Pay" )

        actionButton.setButtonDataSource(false, "en",
            "Pay",Color.parseColor("#00FF00"),Color.parseColor("#FFC0CB"))
        val metrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(metrics)
        val density = metrics.densityDpi
        println("density is"+density)
        actionButton.setDisplayMetrics(density)
        actionButton.setOnClickListener {
            pickStatus()
        }
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

    private fun getSuccessDataSource(): ActionButtonDataSource {
        return ActionButtonDataSource(
            text = "Pay!",
            textSize = 20f,
            textColor = Color.WHITE,
            cornerRadius = 100f,
            successImageResources = R.drawable.success,
            backgroundColor = resources.getColor(R.color.button_green)
        )
    }
}
