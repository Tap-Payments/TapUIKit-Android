package company.tap.tapuisample.interfaces

import android.animation.Animator
import androidx.recyclerview.widget.RecyclerView


interface AnimateViewHolder {
    fun preAnimateAddImpl()
    fun preAnimateRemoveImpl()
    fun animateAddImpl(listener: Animator.AnimatorListener)
    fun animateRemoveImpl(
        listener: Animator.AnimatorListener
    )
}