package company.tap.tapuisample.activities

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import company.tap.tapcardvalidator_android.CardBrand
import company.tap.tapuilibrary.datasource.ActionButtonDataSource
import company.tap.tapuilibrary.enums.ActionButtonState
import company.tap.tapuilibrary.views.TabAnimatedActionButton
import company.tap.tapuisample.R

class ActionButtonActivity : AppCompatActivity() {

    private lateinit var actionButton: TabAnimatedActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action_button)
        actionButton = findViewById(R.id.action_button)
        actionButton.setButtonDataSource(getIdleDataSource())
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
                actionButton.setButtonDataSource(getSuccessDataSource())
                actionButton.changeButtonState(ActionButtonState.SUCCESS)
            } else {
                actionButton.setButtonDataSource(getErrorDataSource())
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
            text = "PAY!",
            textSize = 20f,
            textColor = Color.WHITE,
            cornerRadius = 100f,
            errorImageResources = R.drawable.error_mark,
            backgroundColor = resources.getColor(R.color.button_green),
            errorColor = resources.getColor(R.color.button_gray)
        )
    }

    private fun getSuccessDataSource(): ActionButtonDataSource {
        return ActionButtonDataSource(
            text = "PAY!",
            textSize = 20f,
            textColor = Color.WHITE,
            cornerRadius = 100f,
            successImageResources = R.drawable.checkmark,
            backgroundColor = resources.getColor(R.color.button_green)
        )
    }
}
