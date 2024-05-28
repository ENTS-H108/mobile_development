package com.ents_h108.petwell.view.auth

import android.content.Context
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText
import com.ents_h108.petwell.R

class PasswordEditText : AppCompatEditText {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var isPasswordVisible = false

    init {
        updateIcon()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val drawableEndIndex = 2
        if (event?.action == MotionEvent.ACTION_UP) {
            if (event.rawX >= (right - compoundDrawables[drawableEndIndex].bounds.width())) {
                togglePasswordVisibility()
                performClick()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    private fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
        transformationMethod = if (isPasswordVisible) {
            HideReturnsTransformationMethod.getInstance()
        } else {
            PasswordTransformationMethod.getInstance()
        }
        updateIcon()
        setSelection(text?.length ?: 0)
    }

    private fun updateIcon() {
        val drawable = if (isPasswordVisible) {
            R.drawable.visibility_on
        } else {
            R.drawable.visibility_off
        }
        setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable, 0)
    }
}
