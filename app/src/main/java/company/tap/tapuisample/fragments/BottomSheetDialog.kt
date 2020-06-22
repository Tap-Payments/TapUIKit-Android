package company.tap.tapuisample.fragments


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
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
import company.tap.tapuilibrary.atoms.TapButton
import company.tap.tapuilibrary.atoms.TapChipGroup
import company.tap.tapuilibrary.atoms.TapImageView
import company.tap.tapuilibrary.atoms.TapTextView
import company.tap.tapuilibrary.interfaces.TapAmountSectionInterface
import company.tap.tapuilibrary.views.TapBottomSheetDialog
import company.tap.tapuisample.R
import company.tap.tapuisample.TapAsyncUtil
import company.tap.tapuisample.adapters.MultipleTypeAdapter
import kotlinx.android.synthetic.main.custom_bottom_sheet.*


/**
 * Created by AhlaamK on 6/10/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
open class BottomSheetDialog : TapBottomSheetDialog() {
    private lateinit var businessName: TapTextView
    private lateinit var businessFor: TapTextView
    private lateinit var businessIcon: TapImageView
    private lateinit var selectedCurrency: TapTextView
    private lateinit var currentCurrency: TapTextView
    private lateinit var businessPlaceholder: TapTextView
    private lateinit var businessInitial: String
    private lateinit var itemCount: TapButton
    private var tapAmountSectionInterface: TapAmountSectionInterface? = null
    private lateinit var chipRecycler: RecyclerView
    private val paymentsList: ArrayList<Int> = arrayListOf(1, 2, 3, 4, 5, 6)
    private var isFragmentAdded = false
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

    private fun setupChip(view: View) {
        val mainChipgroup = view.findViewById<TapChipGroup>(R.id.mainChipgroup)
        mainChipgroup.orientation = LinearLayout.HORIZONTAL
        val groupName = view.findViewById<TapTextView>(R.id.group_name)
        groupName.text = getString(R.string.select)
        val groupAction = view.findViewById<TapTextView>(R.id.group_action)
        groupAction.text = getString(R.string.edit)
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
        businessName = view.findViewById(R.id.business_name)
        businessFor = view.findViewById(R.id.payment_for)
        businessIcon = view.findViewById(R.id.business_icon)
        businessName.text = "Tap Payments"
        businessFor.text = "PAYMENT FOR"
        businessPlaceholder = view.findViewById(R.id.placeholder_text)
        businessInitial = businessName.text[0].toString()
        businessPlaceholder.text = businessInitial
        businessPlaceholder.visibility = View.VISIBLE
        TapAsyncUtil.DownLoadImageTask(
            businessIcon,
            businessPlaceholder
        ).execute("https://avatars3.githubusercontent.com/u/19837565?s=200&v=4")
    }

    @SuppressLint("SetTextI18n")
    private fun amountViewInit(view: View) {
        currentCurrency = view.findViewById(R.id.textView_currentcurrency)
        selectedCurrency = view.findViewById(R.id.textview_selectedcurrency)
        itemCount = view.findViewById(R.id.textView_itemcount)
        itemCount.text = "22 ITEMS"
        selectedCurrency.text = "SR1000,000.000"
        currentCurrency.text = "KD1000,000.000"
        val currencyViewFragment = CurrencyViewFragment()
        itemCount.setOnClickListener {
            tapAmountSectionInterface?.didClickItems()
            if (isFragmentAdded) {
                bottomSheetLayout?.let { layout ->
                    val removeTransition: Transition =
                        TransitionInflater.from(context)
                            .inflateTransition(R.transition.remove_fragment)
                    TransitionManager.beginDelayedTransition(layout, removeTransition)
                }
                childFragmentManager
                    .beginTransaction()
                    .remove(currencyViewFragment)
                    .commit()
                fragment_container.visibility = View.VISIBLE

                itemCount.text = "22 ITEMS"
                currentCurrency.visibility= View.VISIBLE

            } else {
                bottomSheetLayout?.let { layout ->
                    layout.post {
                        val addTransition: Transition =
                            TransitionInflater.from(context)
                                .inflateTransition(R.transition.add_fragment)
                        TransitionManager.beginDelayedTransition(layout, addTransition)
                    }
                }
                fragment_container.visibility = View.GONE
                childFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container1, currencyViewFragment)
                    .commit()
                itemCount.text = "CLOSE"
                currentCurrency.visibility= View.INVISIBLE
            }
            isFragmentAdded = !isFragmentAdded
        }

    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}