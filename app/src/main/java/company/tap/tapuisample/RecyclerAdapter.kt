package company.tap.tapuisample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import company.tap.tapuilibrary.TapImageView
import company.tap.tapuilibrary.TapTextView

/**
 * Created by AhlaamK on 5/12/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
// Note that we specify the custom ViewHolder which gives us access to our views
class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_custom_tapcard, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount()= 5

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}