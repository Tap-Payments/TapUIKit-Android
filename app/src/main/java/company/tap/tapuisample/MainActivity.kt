package company.tap.tapuisample

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.google.android.material.internal.ViewUtils.dpToPx
import company.tap.tapuilibrary.TapChip
import company.tap.tapuilibrary.TapHeader
import company.tap.tapuilibrary.TapImageView
import company.tap.tapuilibrary.TapTextView
import kotlinx.android.synthetic.main.activity_main.*

import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {
    private lateinit var imageView_master: TapImageView
    private lateinit var imageView_visa: TapImageView
    private lateinit var imageView_amex: TapImageView
    private lateinit var textView_card: TapTextView
    private lateinit var tap_card_chip: TapChip
    private lateinit var  cardLinearLayout:LinearLayout
    var idVal: Int = 0

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupHeader()
        setUpSwitch()
        setupTapCardChip()
    }

    private fun setupHeader() {
        val businessName = findViewById<TapTextView>(R.id.businessName)
        businessName.text = "Business Name"
        val businessIcon = findViewById<TapImageView>(R.id.businessIcon)
        businessIcon.setImageResource(company.tap.tapuilibrary.R.drawable.tap_logo)
        val cancelIcon = findViewById<TapImageView>(R.id.cancel_icon)
        cancelIcon.setImageResource(company.tap.tapuilibrary.R.drawable.cancel)

        cancelIcon.setOnClickListener {
            Toast.makeText(this, "You clicked on Closed.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpSwitch() {
        leftAccessory_Switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                imageView_visa.visibility = View.VISIBLE
            } else {
               imageView_visa.visibility= View.GONE
            }
        }
        rightAccessory_Switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                imageView_master.visibility = View.VISIBLE
            } else {
                imageView_master.visibility = View.GONE
            }
        }
    }

    fun changeText(view: View) {
        textView_card.visibility = View.VISIBLE
        imageView_amex.visibility = View.GONE
        changeTextDialog()
    }

    private fun changeTextDialog() {
        var inputText = ""
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Chip Content")
        builder.setMessage("Enter a string value")
        // Set up the input
        val input = EditText(this)
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)
        // Set up the buttons
        builder.setPositiveButton(
            "Submit"
        ) { dialog, _ ->
            inputText = input.text.toString()
            textView_card.text = inputText

        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, _ -> dialog.cancel() }
        builder.show()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("RestrictedApi", "ResourceAsColor")
    private fun setupTapCardChip() {
        val mainLayout = findViewById<LinearLayout>(R.id.mainLayout)
        mainLayout.orientation = LinearLayout.HORIZONTAL
        for (i in 0 until 2) {
            //Creating views to add to layout and pass that to the Chip
            cardLinearLayout = LinearLayout(this)
            cardLinearLayout.orientation = LinearLayout.HORIZONTAL
            val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            cardLinearLayout.layoutParams = params
            cardLinearLayout.id = i

            val width = dpToPx(this, 30)
            val height = dpToPx(this, 30)
            val parms = LinearLayout.LayoutParams(width.toInt(), height.toInt())
            parms.leftMargin = dpToPx(this, 5).toInt()
            parms.rightMargin = dpToPx(this, 5).toInt()
            parms.gravity = Gravity.CENTER

            imageView_master = TapImageView(this, null)
            imageView_master.setImageResource(R.drawable.mastercard)
            imageView_master.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView_master.layoutParams = parms
            imageView_master.id = i
            cardLinearLayout.addView(imageView_master)

            imageView_amex = TapImageView(this, null)
            imageView_amex.setImageResource(R.drawable.amex)
            imageView_amex.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView_amex.layoutParams = parms
            imageView_amex.visibility = View.GONE
            imageView_amex.id = i
            cardLinearLayout.addView(imageView_amex)

            textView_card = TapTextView(this, null)
            textView_card.text = "∙∙∙∙ 123"
            textView_card.textSize = dpToPx(this, 6.5.toInt())
            textView_card.setTextColor(Color.parseColor("#474747"))
            textView_card.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL)
            textView_card.id = i
            textView_card.gravity = Gravity.END
            val params1 = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            params1.leftMargin = dpToPx(this, 5).toInt()
            params1.rightMargin = dpToPx(this, 5).toInt()
            params1.topMargin = dpToPx(this, 5).toInt()
            textView_card.setLineSpacing(dpToPx(this, 4), 1f)
            textView_card.layoutParams = params1
            cardLinearLayout.addView(textView_card)

            imageView_visa = TapImageView(this, null)
            imageView_visa.setImageResource(R.drawable.visa)
            imageView_visa.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView_visa.layoutParams = parms
            imageView_visa.id = i
            cardLinearLayout.addView(imageView_visa)
            //Calling the TapChip and passing views to add in chip
            tap_card_chip = TapChip(this, null,cardLinearLayout)
            tap_card_chip.setContentPadding(
                dpToPx(this, 5).toInt(),
                dpToPx(this, 5).toInt(),
                dpToPx(this, 5).toInt(),
                dpToPx(this, 5).toInt()
            )
            tap_card_chip.outlineSpotShadowColor = R.color.shadowcolor
            tap_card_chip.radius = dpToPx(this, 8)
            tap_card_chip.preventCornerOverlap = true
            tap_card_chip.elevation = dpToPx(this, 5)
            val cardViewParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).also {
                it.setMargins(
                    dpToPx(this, 5).roundToInt(), dpToPx(this, 5).roundToInt(),
                    dpToPx(this, 5).roundToInt(),
                    dpToPx(this, 5).roundToInt()
                )
            }
            tap_card_chip.id = i
            tap_card_chip.layoutParams = cardViewParams
            tap_card_chip.setOnClickListener {
                idVal = it.id
                Toast.makeText(this, "value click ${it.id}", Toast.LENGTH_LONG).show()
            }
            mainLayout.addView(tap_card_chip)
        }


    }


    fun changeToImage(view: View) {
        imageView_amex.visibility = View.VISIBLE
        textView_card.visibility = View.GONE
    }

    // extension method to convert pixels to dp
    fun Int.toDp(context: Context): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
    ).toInt()

}


