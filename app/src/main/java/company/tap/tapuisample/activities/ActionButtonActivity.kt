package company.tap.tapuisample.activities

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import company.tap.tapuilibrary.animation.MorphingAnimation
import company.tap.tapuilibrary.views.TabAnimatedActionButton
import company.tap.tapuisample.R
import kotlinx.android.synthetic.main.activity_action_button.*

class ActionButtonActivity : AppCompatActivity(), TabAnimatedActionButton.AnimationListener{

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
            .color(resources.getColor(R.color.button_green), resources.getColor(R.color.button_gray))
            .duration(500)
            .listener(this)
    }

    override fun onAnimationEnd() {
        val errorIcon = ImageView(this)
        errorIcon.setImageResource(R.drawable.ic_error)
        actionButton.addChildView(errorIcon)
    }
}
