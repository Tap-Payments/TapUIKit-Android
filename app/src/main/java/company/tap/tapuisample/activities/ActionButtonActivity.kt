package company.tap.tapuisample.activities

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import company.tap.tapuilibrary.utils.MorphingAnimation
import company.tap.tapuilibrary.views.TabAnimatedActionButton
import company.tap.tapuisample.R
import kotlinx.android.synthetic.main.activity_action_button.*

class ActionButtonActivity : AppCompatActivity() {

    private lateinit var actionButton: TabAnimatedActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action_button)
        actionButton = findViewById(R.id.action_button)
        actionButton.setOnClickListener {
            actionButton.startAnimation(getParams())
        }
    }

    private fun getParams(): MorphingAnimation.Params {
        return MorphingAnimation.Params.create(action_button)
            .height(actionButton.height, 150)
            .width(actionButton.width, 150)
            .cornerRadius(10, 100)
            .color(Color.BLUE, Color.RED)
            .duration(500)
    }
}
