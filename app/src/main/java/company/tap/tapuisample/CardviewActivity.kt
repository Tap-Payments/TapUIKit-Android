package company.tap.tapuisample

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import company.tap.tapuilibrary.atoms.TapChipGroup
import company.tap.tapuilibrary.atoms.TapTextView
import kotlinx.android.synthetic.main.activity_cardview.*

class CardviewActivity : AppCompatActivity() {
    private lateinit var chipRecycler: RecyclerView
    val arrayList: ArrayList<Int> = arrayListOf(1, 2, 3, 4, 5, 6)

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cardview)
        setUpSwitch()
        setupTapCardChip()
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

    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("RestrictedApi", "ResourceAsColor")
    private fun setupTapCardChip() {
        val mainLayout = findViewById<TapChipGroup>(R.id.mainLayout)
        mainLayout.orientation = LinearLayout.HORIZONTAL
        val groupName = findViewById<TapTextView>(R.id.group_name)
        groupName.text = getString(R.string.select)
        val groupAction = findViewById<TapTextView>(R.id.group_action)
        groupAction.text = getString(R.string.edit)
        chipRecycler = findViewById(R.id.chip_recycler)
        chipRecycler.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        //  chipRecycler.adapter = RecyclerAdapter(arrayList)
        chipRecycler.adapter = MultipleTypeAdapter(arrayList)

    }

}
