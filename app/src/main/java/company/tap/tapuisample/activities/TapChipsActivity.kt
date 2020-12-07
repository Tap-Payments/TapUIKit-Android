package company.tap.tapuisample.activities

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import company.tap.tapcardvalidator_android.CardBrand
import company.tap.taplocalizationkit.LocalizationManager
import company.tap.tapuilibrary.uikit.model.CurrencyModel
import company.tap.tapuisample.R
import company.tap.tapuisample.adapters.CardTypeAdapter
import company.tap.tapuilibrary.uikit.adapters.CurrencyAdapter
import company.tap.tapuilibrary.uikit.atoms.TapChipGroup
import company.tap.tapuilibrary.uikit.atoms.TapTextView
import company.tap.tapuilibrary.uikit.datasource.GoPayLoginDataSource
import company.tap.tapuilibrary.uikit.interfaces.GoPayLoginInterface
import company.tap.tapuilibrary.uikit.interfaces.TapSelectionTabLayoutInterface
import company.tap.tapuilibrary.uikit.models.SectionTabItem
import company.tap.tapuilibrary.uikit.organisms.GoPayLoginInput
import company.tap.tapuilibrary.uikit.organisms.GoPayPasswordInput
import company.tap.tapuilibrary.uikit.utils.BaseActivity
import company.tap.tapuilibrary.uikit.views.TapMobilePaymentView
import company.tap.tapuilibrary.uikit.views.TapSelectionTabLayout
import kotlinx.android.synthetic.main.activity_cardview.*

/***
 * A sample Activity to show Chips .
 * */

class TapChipsActivity : BaseActivity(),
    TapSelectionTabLayoutInterface,
    GoPayLoginInterface {
    private lateinit var chipRecycler: RecyclerView
    private val paymentsList: ArrayList<Int> = arrayListOf(1, 2, 3, 4, 5, 6)
    private lateinit var currencyList: ArrayList<CurrencyModel>
    private lateinit var gopaySelectTab: TapSelectionTabLayout
    private lateinit var tapMobileInputView: TapMobilePaymentView
    private lateinit var ll: LinearLayout
    private lateinit var  goPayLoginInput: GoPayLoginInput
    private lateinit var goPayPasswordInput: GoPayPasswordInput
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cardview)
        setupTabLayout()

        setUpSwitch()
        setupCardChip()
        setupCurrencyChips()
    }

    private fun setupTabLayout() {
        gopaySelectTab = findViewById(R.id.tab_select_gopay)
        gopaySelectTab.setTabLayoutInterface(this)
        goPayLoginInput = findViewById(R.id.gopay_login_input)
        goPayPasswordInput = findViewById(R.id.goPay_password)
//        tapMobileInputView = TapMobilePaymentView(context, null)
//        ll.addView(tapMobileInputView)
//        addCard()
//        addMobile()
        goPayLoginInput.changeDataSource(GoPayLoginDataSource())
        goPayLoginInput.setLoginInterface(this)

    }

    //Setup for currency chips
    private fun setupCurrencyChips() {
        fillData()
        val currencyLayout = findViewById<TapChipGroup>(R.id.currencyLayout)
        val header_view = currencyLayout.findViewById<ConstraintLayout>(R.id.header_view)
        header_view.visibility = View.GONE
        chipRecycler = currencyLayout.findViewById(R.id.chip_recycler)
        chipRecycler.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
//        chipRecycler.adapter = CurrencyAdapter(currencyList)
    }

    private fun setUpSwitch() {
        hideKnetSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                paymentsList.add(1)
                paymentsList.add(3)
                paymentsList.add(5)
                chipRecycler.adapter?.notifyDataSetChanged()
            } else {
                paymentsList.remove(1)
                paymentsList.remove(3)
                paymentsList.remove(5)
                chipRecycler.adapter?.notifyDataSetChanged()
            }
        }
        hideSavedcardSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                paymentsList.add(4)
                paymentsList.add(6)
                chipRecycler.adapter?.notifyDataSetChanged()
            } else {
                paymentsList.remove(4)
                paymentsList.remove(6)
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
        groupName.text = LocalizationManager.getValue("GatewayHeader","HorizontalHeaders","leftTitle")

        val groupAction = findViewById<TapTextView>(R.id.group_action)
        groupAction.text = LocalizationManager.getValue("edit","Common")
        chipRecycler = findViewById(R.id.chip_recycler)
        chipRecycler.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        //  chipRecycler.adapter = RecyclerAdapter(arrayList)
//        chipRecycler.adapter = CardTypeAdapter(paymentsList,)

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

    override fun onTabSelected(position: Int?) {

    }

    private fun addMobile() {
        val items = ArrayList<SectionTabItem>()
        items.add(
            SectionTabItem(
                resources.getDrawable(
                    R.drawable.zain_gray
                ), resources.getDrawable(R.drawable.zain_dark), CardBrand.zain
            )
        )
        gopaySelectTab.addSection(items)
    }


    private  fun addCard(){
        val items = ArrayList<SectionTabItem>()
        items.add(
            SectionTabItem(
                resources.getDrawable(
                    R.drawable.zain_gray
                ), resources.getDrawable(R.drawable.zain_dark), CardBrand.zain
            )
        )
        gopaySelectTab.addSection(items)
    }

    override fun onChangeClicked() {
      //  AnimationEngine.applyTransition(bottomSheet, SLIDE)
        goPayLoginInput.visibility = View.VISIBLE
        goPayPasswordInput.visibility = View.VISIBLE
    }

    override fun onEmailValidated() {
        //AnimationEngine.applyTransition(bottomSheet, SLIDE)
        goPayLoginInput.visibility = View.GONE
        goPayPasswordInput.visibility = View.VISIBLE
    }

    override fun onPhoneValidated() {

    }
}
