package company.tap.tapuisample.activities

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import company.tap.tapuilibrary.animation.MorphingAnimation
import company.tap.tapuilibrary.enums.ActionButtonState
import company.tap.tapuilibrary.views.TabAnimatedActionButton
import company.tap.tapuisample.R
import kotlinx.android.synthetic.main.activity_action_button.*

class ActionButtonActivity : AppCompatActivity(){

    private lateinit var actionButton: TabAnimatedActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action_button)
        actionButton = findViewById(R.id.action_button)
        actionButton.setOnClickListener {
            actionButton.changeButtonState(ActionButtonState.LOADING)
        }
    }


}
