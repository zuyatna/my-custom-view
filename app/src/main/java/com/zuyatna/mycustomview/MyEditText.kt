package com.zuyatna.mycustomview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat

class MyEditText : AppCompatEditText, View.OnTouchListener {

    private lateinit var clearButtonImage: Drawable

    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        clearButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_close_black_24) as Drawable
        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do Nothing
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().isNotEmpty()) {
                    showClearButton()
                } else {
                    hideClearButton()
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                // Do Nothing
            }
        })
    }

    private fun showClearButton() {
        setButtonDrawables(endOfTheText =  clearButtonImage)
    }

    private fun hideClearButton() {
        setButtonDrawables()
    }

    private fun setButtonDrawables(
        startOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        if (compoundDrawables[2] != null) {
            val clearButtonStart: Float
            val clearButtonEnd: Float
            var isClearButtonClicked = false

            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                clearButtonEnd = (clearButtonImage.intrinsicWidth + paddingStart).toFloat()

                if (p1 != null) {
                    when {
                        p1.x < clearButtonEnd -> isClearButtonClicked = true
                    }
                }
            } else {
                clearButtonStart = (width - paddingEnd - clearButtonImage.intrinsicWidth).toFloat()

                if (p1 != null) {
                    when {
                        p1.x > clearButtonStart -> isClearButtonClicked = true
                    }
                }
            }

            if (isClearButtonClicked) {
                if (p1 != null) {
                    when (p1.action) {
                        MotionEvent.ACTION_DOWN -> {
                            clearButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_close_black_24) as Drawable
                            showClearButton()
                            return true
                        }
                        MotionEvent.ACTION_UP -> {
                            clearButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_close_black_24) as Drawable

                            when {
                                text != null -> text?.clear()
                            }
                            hideClearButton()
                            return true
                        }
                        else -> return false
                    }
                }
            } else return false
        }
        return false
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        hint = "Input Your Name"
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }
}