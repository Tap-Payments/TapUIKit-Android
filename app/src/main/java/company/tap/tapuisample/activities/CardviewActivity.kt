package company.tap.tapuisample.activities

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import company.tap.tapuilibrary.atoms.TapChipGroup
import company.tap.tapuilibrary.atoms.TapTextView
import company.tap.tapuisample.CurrencyModel
import company.tap.tapuisample.R
import company.tap.tapuisample.adapters.CurrencyAdapter
import company.tap.tapuisample.adapters.MultipleTypeAdapter
import kotlinx.android.synthetic.main.activity_cardview.*

/***
 * Sample Activity to show how CardView will be accept different view .
 * and how it will glow on selecting the cardview.
 * */

class CardviewActivity : AppCompatActivity() {
    private lateinit var chipRecycler: RecyclerView
    private val arrayList: ArrayList<Int> = arrayListOf(1, 2, 3, 4, 5, 6)
    lateinit var currencyList: ArrayList<CurrencyModel>

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cardview)
        setUpSwitch()
        setupCardChip()
        setupCurrencyChips()
    }

    //Setup for currency chips
    private fun setupCurrencyChips() {
        fillData()
        val currencyLayout = findViewById<TapChipGroup>(R.id.currencyLayout)
        val header_view = currencyLayout.findViewById<LinearLayout>(R.id.header_view)
        header_view.visibility = View.GONE
        chipRecycler = currencyLayout.findViewById(R.id.chip_recycler)
        chipRecycler.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        chipRecycler.adapter =
            CurrencyAdapter(currencyList)
    }

    private fun setUpSwitch() {
        hideKnet_Switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                arrayList.add(1)
                arrayList.add(3)
                arrayList.add(5)
                chipRecycler.adapter?.notifyDataSetChanged()
            } else {
                arrayList.remove(1)
                arrayList.remove(3)
                arrayList.remove(5)
                chipRecycler.adapter?.notifyDataSetChanged()
            }
        }
        hideSavedCard_Switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                arrayList.add(4)
                arrayList.add(6)
                chipRecycler.adapter?.notifyDataSetChanged()
            } else {
                arrayList.remove(4)
                arrayList.remove(6)
                chipRecycler.adapter?.notifyDataSetChanged()
            }
        }
    }

    //Set up for card chips
    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("RestrictedApi", "ResourceAsColor")
    private fun setupCardChip() {
        val mainLayout = findViewById<TapChipGroup>(R.id.mainLayout)
        mainLayout.orientation = LinearLayout.HORIZONTAL
        val groupName = findViewById<TapTextView>(R.id.group_name)
        groupName.text = getString(R.string.select)
        val groupAction = findViewById<TapTextView>(R.id.group_action)
        groupAction.text = getString(R.string.edit)
        chipRecycler = findViewById(R.id.chip_recycler)
        chipRecycler.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        //  chipRecycler.adapter = RecyclerAdapter(arrayList)
        chipRecycler.adapter =
            MultipleTypeAdapter(arrayList)

    }

    //Filling dummy data for currency chips
    private fun fillData() {
        currencyList = ArrayList()

        //adding some dummy data to the list
        currencyList.add(
            CurrencyModel(
                "KWD",
                "https://www.countryflags.io/kw/flat/24.png"
            )
        )
        currencyList.add(
            CurrencyModel(
                "SAR",
                "https://www.countryflags.io/sa/flat/24.png"
            )
        )
        currencyList.add(
            CurrencyModel(
                "BHD",
                "https://www.countryflags.io/bh/flat/24.png"
            )
        )
        currencyList.add(
            CurrencyModel(
                "QAR",
                "https://www.countryflags.io/qa/flat/24.png"
            )
        )
        currencyList.add(
            CurrencyModel(
                "KWD",
                "https://www.countryflags.io/kw/flat/24.png"
            )
        )
        currencyList.add(
            CurrencyModel(
                "SAR",
                "https://www.countryflags.io/sa/flat/24.png"
            )
        )

    }
}
