package company.tap.tapuilibrary.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.adapters.CurrencyAdapter
import company.tap.tapuilibrary.adapters.ItemAdapter
import company.tap.tapuilibrary.atoms.TapChipGroup
import company.tap.tapuilibrary.atoms.TapTextView
import company.tap.tapuilibrary.model.CurrencyModel


/**
 * Created by AhlaamK on 6/21/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
open class CurrencyViewFragment : Fragment() {
    private lateinit var chipRecycler: RecyclerView
    lateinit var currencyList: ArrayList<CurrencyModel>

    private lateinit var itemsRecycler: RecyclerView
    private val itemList: ArrayList<Int> = arrayListOf(1, 2, 3, 4, 5, 6,7,8,9,10,11,12,13,14,15,16,17,18,20,21,22)
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.item_frame_currency, container, false)
        fillData()

        val currencyGroup = view.findViewById<TapChipGroup>(R.id.currencyLayout1)
        currencyGroup.orientation = LinearLayout.HORIZONTAL
        val groupName = currencyGroup.findViewById<TapTextView>(R.id.group_name)
        groupName.visibility=View.GONE
        val groupAction = currencyGroup.findViewById<TapTextView>(R.id.group_action)
        groupAction.visibility=View.GONE
        chipRecycler = currencyGroup.findViewById<View>(R.id.chip_recycler) as RecyclerView
       // chipRecycler.setHasFixedSize(true)
        chipRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        chipRecycler.adapter =
            CurrencyAdapter(currencyList)
        itemsRecycler = view.findViewById<View>(R.id.items_recylerview) as RecyclerView
        itemsRecycler.setHasFixedSize(false)
        itemsRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        itemsRecycler.adapter =
            ItemAdapter(itemList)
        itemsRecycler.setOnTouchListener { v, event ->
            val action = event.action
            when (action) {
                MotionEvent.ACTION_DOWN ->                         // Disallow NestedScrollView to intercept touch events.
                    v.parent.requestDisallowInterceptTouchEvent(true)
                MotionEvent.ACTION_UP ->                         // Allow NestedScrollView to intercept touch events.
                    v.parent.requestDisallowInterceptTouchEvent(false)
            }

            // Handle RecyclerView touch events.
            v.onTouchEvent(event)
            true
        }

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

}