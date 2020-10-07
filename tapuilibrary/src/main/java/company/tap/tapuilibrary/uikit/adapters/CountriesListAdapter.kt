package company.tap.tapuilibrary.uikit.adapters


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import company.tap.tapuilibrary.R
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by AhlaamK on 10/6/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/



class CountriesListAdapter(context: Context, resource: Int, values: Array<String>) : ArrayAdapter<String>(context, resource, values) {


    var resource: Int
    var values: Array<String>
    var vi: LayoutInflater

    init {
        this.resource = resource
        this.values = values
        this.vi = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView: View = inflater.inflate(R.layout.country_list_item, parent, false)
        val textView = rowView.findViewById(R.id.txtViewCountryName) as TextView
        val imageView: ImageView = rowView.findViewById(R.id.imgViewFlag) as ImageView
        val g: List<String> = values[position].split(",")
        textView.setText(GetCountryZipCode(g[1])?.trim())
        val pngName = g[1].trim { it <= ' ' }.toLowerCase()
        imageView.setImageResource(
            context.resources.getIdentifier(
                "drawable/$pngName",
                null,
                context.packageName
            )
        )
        return rowView
    }

    private fun GetCountryZipCode(ssid: String): String? {
        val loc = Locale("", ssid)
        return loc.getDisplayCountry().trim()
    }
}