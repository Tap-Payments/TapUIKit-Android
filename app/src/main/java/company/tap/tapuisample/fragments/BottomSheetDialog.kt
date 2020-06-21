package company.tap.tapuisample.fragments


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionManager
import com.tap.tapfontskit.FontChanger
import com.tap.tapfontskit.enums.TapFont
import com.tap.tapfontskit.enums.TapFont.Companion.tapFontType
import company.tap.tapuilibrary.atoms.TapButton
import company.tap.tapuilibrary.atoms.TapChipGroup
import company.tap.tapuilibrary.atoms.TapImageView
import company.tap.tapuilibrary.atoms.TapTextView
import company.tap.tapuilibrary.interfaces.TapAmountSectionInterface
import company.tap.tapuilibrary.views.TapBottomSheetDialog
import company.tap.tapuisample.R
import company.tap.tapuisample.adapters.MultipleTypeAdapter
import java.io.InputStream
import java.net.URL


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
    private lateinit var itemName: TapTextView
    private lateinit var itemAmount: TapTextView
    private lateinit var descText: TapTextView
    private lateinit var totalAmount: TapTextView
    private lateinit var totalQuantity: TapTextView
    private lateinit var discount: TapTextView
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

    open class DownLoadImageTask(imageView: ImageView, textView: TapTextView) :
        AsyncTask<String, Void, Bitmap>() {
        private var imageView: ImageView
        private var textView: TapTextView


        override fun onPreExecute() {
            super.onPreExecute()
            textView.visibility = View.VISIBLE
            imageView.visibility = View.GONE
        }

        override fun onPostExecute(result: Bitmap) {
            if (result != null) {
                imageView.setImageBitmap(result)
                textView.visibility = View.GONE
                imageView.visibility = View.VISIBLE
            }

        }

        init {
            this.imageView = imageView
            this.textView = textView

        }

        override fun doInBackground(vararg urls: String): Bitmap? {
            val urlOfImage = urls[0]
            var logo: Bitmap? = null
            try {
                val `is`: InputStream = URL(urlOfImage).openStream()
                logo = BitmapFactory.decodeStream(`is`)
            } catch (e: Exception) { // Catch the download exception
                e.printStackTrace()
            }
            return logo
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
       // amountDesrpRowInit(view)
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
    private fun headerViewInit(view: View){
        businessName = view.findViewById(R.id.business_name)
        businessFor = view.findViewById(R.id.payment_for)
        businessIcon = view.findViewById(R.id.business_icon)
        businessName.text = "Tap Payments"
        businessFor.text = "PAYMENT FOR"
        businessPlaceholder = view.findViewById(R.id.placeholder_text)
        businessInitial = businessName.text[0].toString()
        businessPlaceholder.text = businessInitial
        businessPlaceholder.visibility = View.VISIBLE
        DownLoadImageTask(
            businessIcon,
            businessPlaceholder
        ).execute("https://avatars3.githubusercontent.com/u/19837565?s=200&v=4")
    }
    @SuppressLint("SetTextI18n")
    private fun amountViewInit(view: View){
        currentCurrency = view.findViewById(R.id.textView_currentcurrency)
        selectedCurrency = view.findViewById(R.id.textview_selectedcurrency)
        itemCount = view.findViewById(R.id.textView_itemcount)
        itemCount.text = "10 ITEMS"
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

                itemCount.text = "10 ITEMS"

            } else {
                bottomSheetLayout?.let { layout ->
                    layout.post {
                        val addTransition: Transition =
                            TransitionInflater.from(context)
                                .inflateTransition(R.transition.add_fragment)
                        TransitionManager.beginDelayedTransition(layout, addTransition)
                    }
                }
                childFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, currencyViewFragment)
                    .commit()
                itemCount.text = "CLOSE"
            }
            isFragmentAdded = !isFragmentAdded
        }

    }
    @SuppressLint("SetTextI18n")
    private fun amountDesrpRowInit(view: View){
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


    companion object {
        const val TAG = "ModalBottomSheet"
    }
}