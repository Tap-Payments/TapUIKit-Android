package company.tap.tapuisample.activities

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
        actionButton.setButtonDataSource(getSuccessDataSource())
        actionButton.setOnClickListener {
            actionButton.changeButtonState(ActionButtonState.SUCCESS)
        }
    }

    private fun getIdleDataSource(): ActionButtonDataSource {
        return ActionButtonDataSource(
            text = "PAY!",
            textSize = 20f,
            textColor = Color.WHITE,
            cornerRadius = 10f,
            backgroundColor = resources.getColor(R.color.button_green)
        )
    }

    private fun getErrorDataSource(): ActionButtonDataSource {
        return ActionButtonDataSource(
            text = "PAY!",
            textSize = 20f,
            textColor = Color.WHITE,
            cornerRadius = 10f,
            errorImageResources = R.drawable.ic_error,
            backgroundColor = resources.getColor(R.color.button_green),
            errorColor = resources.getColor(R.color.button_gray)
        )
    }

    private fun getSuccessDataSource(): ActionButtonDataSource {
        return ActionButtonDataSource(
            text = "PAY!",
            textSize = 20f,
            textColor = Color.WHITE,
            cornerRadius = 10f,
            successImageResources = R.drawable.ic_check,
            backgroundColor = resources.getColor(R.color.button_green)
        )
    }
}
