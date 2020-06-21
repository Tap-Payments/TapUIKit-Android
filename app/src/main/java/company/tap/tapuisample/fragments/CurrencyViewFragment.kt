package company.tap.tapuisample.fragments

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import company.tap.tapuilibrary.atoms.TapChipGroup
import company.tap.tapuilibrary.atoms.TapTextView
import company.tap.tapuilibrary.views.TapItemsView
import company.tap.tapuisample.CurrencyModel
import company.tap.tapuisample.R
import company.tap.tapuisample.adapters.CurrencyAdapter


/**
 * Created by AhlaamK on 6/21/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
class CurrencyViewFragment : Fragment() {
    private lateinit var chipRecycler: RecyclerView
    lateinit var currencyList: ArrayList<CurrencyModel>
    private lateinit var itemName: TapTextView
    private lateinit var itemAmount: TapTextView
    private lateinit var descText: TapTextView
    private lateinit var totalAmount: TapTextView
    private lateinit var totalQuantity: TapTextView
    private lateinit var discount: TapTextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.item_frame_currency, container, false)
        fillData()
        // Add the following lines to create RecyclerView
        val currencyGroup = view.findViewById<TapChipGroup>(R.id.currencyLayout1)
        val tapItemsGroup = view.findViewById<TapItemsView>(R.id.amount_item_view)
        currencyGroup.orientation = LinearLayout.HORIZONTAL
        val groupName = currencyGroup.findViewById<TapTextView>(R.id.group_name)
        groupName.visibility=View.GONE
        val groupAction = currencyGroup.findViewById<TapTextView>(R.id.group_action)
        groupAction.visibility=View.GONE
        chipRecycler = currencyGroup.findViewById<View>(R.id.chip_recycler) as RecyclerView
        chipRecycler.setHasFixedSize(true)
        chipRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        chipRecycler.adapter =
            CurrencyAdapter(currencyList)
        amountDescRowInit(tapItemsGroup)
        return view
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

    @SuppressLint("SetTextI18n")
    private fun amountDescRowInit(view: View){
        itemName = view.findViewById(R.id.item_title)
        itemAmount = view.findViewById(R.id.item_amount)
        descText = view.findViewById(R.id.show_description)
        totalAmount = view.findViewById(R.id.total_amount)
        totalQuantity = view.findViewById(R.id.total_quantity)
        discount = view.findViewById(R.id.discount_text)
        itemName.text = "ITEM TITLE"
        itemAmount.text = "KD000,000.000"
        descText.text = "Show Description"
        totalAmount.text = "KD000,000.000"
        totalQuantity.text = "1"
        discount.text = "10% Discount"
        totalAmount.paintFlags = totalAmount.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG


    }

}