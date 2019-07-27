package ru.holofox.anicoubs.ui.extensions.behavior

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.content.Context
import androidx.core.view.ViewCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
import com.google.android.material.animation.AnimationUtils
import com.google.android.material.snackbar.Snackbar

/**
 * The [Behavior] for a View within a [CoordinatorLayout] to hide the view off the
 * bottom of the screen when scrolling down, and show it when scrolling up.
 */
class BottomNavigationBehavior<V : View>(context: Context, attrs: AttributeSet) :
    CoordinatorLayout.Behavior<V>(context, attrs) {

    private var height = 0
    private var currentState =
        STATE_SCROLLED_UP
    private var additionalHiddenOffsetY = 0
    private var currentAnimator: ViewPropertyAnimator? = null

    override fun onLayoutChild(parent: CoordinatorLayout, child: V, layoutDirection: Int): Boolean {
        val paramsCompat = child.layoutParams as ViewGroup.MarginLayoutParams
        height = child.measuredHeight + paramsCompat.bottomMargin
        return super.onLayoutChild(parent, child, layoutDirection)
    }

    override fun layoutDependsOn(parent: CoordinatorLayout, child: V, dependency: View): Boolean {
        if (dependency is Snackbar.SnackbarLayout)
            updateSnackbar(
                child,
                dependency
            )

        return super.layoutDependsOn(parent, child, dependency)
    }

    /** Sets an additional offset for the y position used to hide the view.  */
    private fun setAdditionalHiddenOffsetY(child: V, offset: Int) {
        additionalHiddenOffsetY = offset

        if (currentState == STATE_SCROLLED_DOWN)
            child.translationY = (height + additionalHiddenOffsetY).toFloat()
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout, child: V, directTargetChild: View,
        target: View, nestedScrollAxes: Int
    ): Boolean = nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout, child: V, target: View,
        dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int
    ) {
        if (dyConsumed > 0) slideDown(child) else slideUp(child)
    }

    /**
     * Perform an animation that will slide the child from it's current position to be totally on the
     * screen.
     */
    private fun slideUp(child: V) {
        if (currentState == STATE_SCROLLED_UP)
            return

        currentAnimator?.let {
            it.cancel()
            child.clearAnimation()
        }

        currentState =
            STATE_SCROLLED_UP
        animateChildTo(
            child, 0, ENTER_ANIMATION_DURATION.toLong(),
            AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR
        )
    }

    /**
     * Perform an animation that will slide the child from it's current position to be totally off the
     * screen.
     */
    private fun slideDown(child: V) {
        if (currentState == STATE_SCROLLED_DOWN)
            return

        currentAnimator?.let {
            it.cancel()
            child.clearAnimation()
        }

        currentState =
            STATE_SCROLLED_DOWN
        animateChildTo(
            child, height + additionalHiddenOffsetY,
            EXIT_ANIMATION_DURATION.toLong(),
            AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR
        )
    }

    private fun animateChildTo(child: V, targetY: Int, duration: Long, interpolator: TimeInterpolator) {
        currentAnimator = child.animate()
            .translationY(targetY.toFloat())
            .setInterpolator(interpolator)
            .setDuration(duration)
            .setListener(
                object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        currentAnimator = null
                    }
                })
    }

    companion object {

        private const val ENTER_ANIMATION_DURATION = 225
        private const val EXIT_ANIMATION_DURATION = 175

        private const val STATE_SCROLLED_DOWN = 1
        private const val STATE_SCROLLED_UP = 2

        private fun updateSnackbar(child: View, snackbarLayout: Snackbar.SnackbarLayout) {
            if (snackbarLayout.layoutParams is CoordinatorLayout.LayoutParams) {
                val params = snackbarLayout.layoutParams as CoordinatorLayout.LayoutParams
                    params.anchorId = child.id
                    params.anchorGravity = Gravity.TOP
                    params.gravity = Gravity.TOP

                snackbarLayout.layoutParams = params
            }
        }

    }
}