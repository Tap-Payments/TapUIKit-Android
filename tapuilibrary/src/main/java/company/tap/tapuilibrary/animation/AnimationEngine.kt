package company.tap.tapuilibrary.animation

import android.view.ViewGroup
import androidx.transition.Transition
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet

/**
 *
 * Created by Mario Gamal on 6/25/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
object AnimationEngine {

    fun applyTransition(sceneRoot: ViewGroup, transition: Transition? = null) {
        if (transition != null)
            TransitionManager.beginDelayedTransition(sceneRoot, transition)
        else
            TransitionManager.beginDelayedTransition(sceneRoot)
    }
}