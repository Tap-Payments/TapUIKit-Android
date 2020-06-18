package company.tap.tapuisample


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
import company.tap.tapuilibrary.atoms.TapButton
import company.tap.tapuilibrary.atoms.TapChipGroup
import company.tap.tapuilibrary.atoms.TapImageView
import company.tap.tapuilibrary.atoms.TapTextView
import company.tap.tapuilibrary.interfaces.TapAmountSectionInterface
import company.tap.tapuilibrary.views.TapBottomSheetDialog
import java.io.InputStream
import java.net.URL


/**
 * Created by AhlaamK on 6/10/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
open class BottomSheetDialog : TapBottomSheetDialog() {
    lateinit var businessName: TapTextView
    lateinit var businessFor: TapTextView
    lateinit var businessIcon: TapImageView
    lateinit var selectedCurrency: TapTextView
    lateinit var currentCurrency: TapTextView
    lateinit var placeHolderText: TapTextView
    lateinit var itemTitleText: TapTextView
    lateinit var itemAmountText: TapTextView
    lateinit var showDescrpText: TapTextView
    lateinit var totalAmountText: TapTextView
    lateinit var totalQuantityText: TapTextView
    lateinit var discountText: TapTextView
    lateinit var placeholderString: String
    lateinit var itemCount: TapButton
    private var tapAmountSectionInterface: TapAmountSectionInterface? = null
    private lateinit var chipRecycler: RecyclerView
    val arrayList:ArrayList<Int> = arrayListOf(1, 2, 3, 4, 5,6)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.custom_bottom_sheet, container, false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            tapAmountSectionInterface = context as TapAmountSectionInterface
        } catch (ex: ClassCastException) {
            try {
                tapAmountSectionInterface = parentFragment as TapAmountSectionInterface
            } catch (ignore: Exception) {}
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
    private fun initializeViews(view: View){
        businessName = view.findViewById(R.id.business_name)
        businessFor = view.findViewById(R.id.payment_for)
        businessIcon = view.findViewById(R.id.business_icon)
        currentCurrency = view.findViewById(R.id.textView_currentcurrency)
        selectedCurrency = view.findViewById(R.id.textview_selectedcurrency)
        itemCount = view.findViewById(R.id.textView_itemcount)
        itemTitleText = view.findViewById(R.id.item_title)
        itemAmountText = view.findViewById(R.id.item_amount)
        showDescrpText = view.findViewById(R.id.show_description)
        totalAmountText = view.findViewById(R.id.total_amount)
        totalQuantityText = view.findViewById(R.id.total_quantity)
        discountText = view.findViewById(R.id.discount_text)
        selectedCurrency.text = "SR1000,000.000"
        currentCurrency.text = "KD1000,000.000"
        businessName.text = "Tap Payments"
        businessFor.text = "PAYMENT FOR"
        itemCount.text = "10 ITEMS"
        itemTitleText.text = "ITEM TITLE"
        itemAmountText.text = "KD000,000.000"
        showDescrpText.text = "Show Description"
        totalAmountText.text = "KD000,000.000"
        totalQuantityText.text="1"
        discountText.text = "10% Discount"
        totalAmountText.paintFlags = totalAmountText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        itemCount.setOnClickListener {
            tapAmountSectionInterface?.didClickItems()
        }

        placeHolderText = view.findViewById(R.id.placeholder_text)
        placeholderString = businessName.text[0].toString()
        placeHolderText.text = placeholderString
        placeHolderText.visibility = View.VISIBLE
        DownLoadImageTask(
            businessIcon,
            placeHolderText
        ).execute("https://avatars3.githubusercontent.com/u/19837565?s=200&v=4")
        setupChip(view)
    }

    private fun setupChip(view: View) {
        val mainChipgroup = view.findViewById<TapChipGroup>(R.id.mainChipgroup)
        mainChipgroup.orientation = LinearLayout.HORIZONTAL
        val groupName = view.findViewById<TapTextView>(R.id.group_name)
        groupName.text = getString(R.string.recent)
        val groupAction = view.findViewById<TapTextView>(R.id.group_action)
        groupAction.text = getString(R.string.edit)
        chipRecycler = view.findViewById(R.id.chip_recycler)
        chipRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        chipRecycler.adapter = MultipleTypeAdapter(arrayList)
        groupAction.setOnClickListener{
            Toast.makeText(context,"You clicked Edit",Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}