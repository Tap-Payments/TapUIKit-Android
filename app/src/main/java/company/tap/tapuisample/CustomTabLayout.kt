package company.tap.tapuisample

import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import com.google.android.material.tabs.TabLayout
import java.lang.reflect.Field


class CustomTabLayout : TabLayout {
    private var DIVIDER_FACTOR = 10

    constructor(context: Context?) : super(context!!) {
        initTabMinWidth()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
        initTabMinWidth()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!, attrs, defStyleAttr
    ) {
        initTabMinWidth()
    }

    fun setTabNumbers(num: Int) {
        DIVIDER_FACTOR = num
        initTabMinWidth()
    }

    private fun initTabMinWidth() {
        val wh = getScreenSize(context)
        val tabMinWidth = wh[WIDTH_INDEX] / DIVIDER_FACTOR
        Log.v(
            "CUSTOM TAB LAYOUT",
            "SCREEN WIDTH = " + wh[WIDTH_INDEX] + " && tabTotalWidth = " + tabMinWidth * DIVIDER_FACTOR + " && TotalTabs = " + DIVIDER_FACTOR
        )
        val field: Field
        try {
            field = TabLayout::class.java.getDeclaredField(SCROLLABLE_TAB_MIN_WIDTH)
            field.isAccessible = true
            field.set(this, tabMinWidth)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val WIDTH_INDEX = 0
        private const val SCROLLABLE_TAB_MIN_WIDTH = "mScrollableTabMinWidth"
        private const val HEIGHT_INDEX = 1
        fun getScreenSize(context: Context): IntArray {
            val widthHeight = IntArray(2)
            widthHeight[WIDTH_INDEX] = 0
            widthHeight[HEIGHT_INDEX] = 0
            try {
                val windowManager =
                    context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                val display = windowManager.defaultDisplay
                val size = Point()
                display.getSize(size)
                widthHeight[WIDTH_INDEX] = size.x
                widthHeight[HEIGHT_INDEX] = size.y
                if (!isScreenSizeRetrieved(widthHeight)) {
                    val metrics = DisplayMetrics()
                    display.getMetrics(metrics)
                    widthHeight[0] = metrics.widthPixels
                    widthHeight[1] = metrics.heightPixels
                }

                // Last defense. Use deprecated API that was introduced in lower than API 13
                if (!isScreenSizeRetrieved(widthHeight)) {
                    widthHeight[0] = display.width // deprecated
                    widthHeight[1] = display.height // deprecated
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return widthHeight
        }

        private fun isScreenSizeRetrieved(widthHeight: IntArray): Boolean {
            return widthHeight[WIDTH_INDEX] != 0 && widthHeight[HEIGHT_INDEX] != 0
        }
    }
}