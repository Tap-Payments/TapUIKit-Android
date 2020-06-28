package company.tap.tapuisample.fragments


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionManager
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import company.tap.taplocalizationkit.LocalizationManager
import company.tap.tapuilibrary.atoms.TapButton
import company.tap.tapuilibrary.atoms.TapChipGroup
import company.tap.tapuilibrary.atoms.TapImageView
import company.tap.tapuilibrary.atoms.TapTextView
import company.tap.tapuilibrary.datasource.HeaderDataSource
import company.tap.tapuilibrary.interfaces.TapAmountSectionInterface
import company.tap.tapuilibrary.views.TapBottomSheetDialog
import company.tap.tapuilibrary.views.TapHeader
import company.tap.tapuisample.R
import company.tap.tapuisample.adapters.MultipleTypeAdapter
import kotlinx.android.synthetic.main.custom_bottom_sheet.*


/**
 * Created by AhlaamK on 6/10/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
open class BottomSheetDialog : TapBottomSheetDialog() {

    private lateinit var businessIcon: TapImageView
    private lateinit var selectedCurrency: TapTextView
    private lateinit var currentCurrency: TapTextView
    private lateinit var businessPlaceholder: TapTextView
    private lateinit var itemCount: TapButton
    private var tapAmountSectionInterface: TapAmountSectionInterface? = null
    private lateinit var chipRecycler: RecyclerView
    private val paymentsList: ArrayList<Int> = arrayListOf(1, 2, 3, 4, 5, 6)
    private var isFragmentAdded = false
    private var businessName: String? = null

    private lateinit var tapHeader: TapHeader

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.custom_bottom_sheet, container, false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            tapAmountSectionInterface = context as TapAmountSectionInterface
        } catch (ex: ClassCastException) {
            try {
                tapAmountSectionInterface = parentFragment as TapAmountSectionInterface
            } catch (ignore: Exception) {
            }
        }

    }

    override fun onViewCreated(
        view: View,
        @Nullable savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews(view)
    }

    @SuppressLint("SetTextI18n")
    private fun initializeViews(view: View) {
        headerViewInit(view)
        amountViewInit(view)
        setupChip(view)

    }

    @SuppressLint("ResourceAsColor")
    private fun setupChip(view: View) {
        val mainChipgroup = view.findViewById<TapChipGroup>(R.id.mainChipgroup)
        mainChipgroup.orientation = LinearLayout.HORIZONTAL
        val groupName = view.findViewById<TapTextView>(R.id.group_name)
        groupName.text = LocalizationManager.getValue("select", "Common")
        groupName.setTextColor(R.color.text_color)
        val groupAction = view.findViewById<TapTextView>(R.id.group_action)
        groupAction.text = LocalizationManager.getValue("edit", "Common")
        groupName.setTextColor(R.color.text_color)
        chipRecycler = view.findViewById(R.id.chip_recycler)
        chipRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        chipRecycler.adapter =
            MultipleTypeAdapter(paymentsList)
        groupAction.setOnClickListener {
            Toast.makeText(context, "You clicked Edit", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun headerViewInit(view: View) {
        tapHeader = view.findViewById(R.id.headerView)
        businessName = "Tap Payments"
        tapHeader.setHeaderDataSource(getHeaderDataSource())
    }

    private fun getHeaderDataSource(): HeaderDataSource {
        return HeaderDataSource(
            textBusinessName = businessName,
            textBusinessFor = LocalizationManager.getValue("paymentFor", "TapMerchantSection"),
            businessImageResources = "https://avatars3.githubusercontent.com/u/19837565?s=200&v=4",
            businessInitial = businessName?.get(0).toString()
        )
    }

    @SuppressLint("SetTextI18n")
    private fun amountViewInit(view: View) {
        currentCurrency = view.findViewById(R.id.textView_currentcurrency)
        selectedCurrency = view.findViewById(R.id.textview_selectedcurrency)
        itemCount = view.findViewById(R.id.textView_itemcount)
        itemCount.text = "22 ITEMS"
        selectedCurrency.text = "SR1000,000.000"
        currentCurrency.text = "KD1000,000.000"
        if (isFragmentAdded) {
            currentCurrency.visibility = View.VISIBLE
        }
        val currencyViewFragment = CurrencyViewFragment()
        itemCount.setOnClickListener {
            tapAmountSectionInterface?.didClickItems()
            if (isFragmentAdded) {
                childFragmentManager
                    .beginTransaction()
                    .remove(currencyViewFragment)
                    .commit()
                fragment_container.visibility = View.VISIBLE

                bottomSheetLayout?.let { layout ->
                    val removeTransition: Transition =
                        TransitionInflater.from(context)
                            .inflateTransition(R.transition.remove_fragment)
                    TransitionManager.beginDelayedTransition(layout, removeTransition)
                }
                selectedCurrency.text = "SR1000,000.000"
                itemCount.text = "22 ITEMS"
            } else {
                childFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container1, currencyViewFragment)
                    .commit()
                bottomSheetLayout?.let { layout ->
                    layout.post {
                        val addTransition: Transition =
                            TransitionInflater.from(context)
                                .inflateTransition(R.transition.add_fragment)
                        TransitionManager.beginDelayedTransition(layout, addTransition)
                    }
                }
                selectedCurrency.text = "KD1000,000.000"
                currentCurrency.visibility = View.GONE
                fragment_container.visibility = View.GONE
                itemCount.text = LocalizationManager.getValue("close", "Common")
                Handler().postDelayed({
                    bottomSheetDialog.behavior.state = STATE_EXPANDED

                }, 1000)
            }
            isFragmentAdded = !isFragmentAdded

        }

        println("bottom state ${bottomSheetDialog.behavior.state}")

    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}