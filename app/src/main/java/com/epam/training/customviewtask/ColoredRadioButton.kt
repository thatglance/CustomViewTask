package com.epam.training.customviewtask

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatRadioButton

/**
 * Custom radio button, that has its own color.
 *
 * @author Alexandra Makovskaya
 */
class ColoredRadioButton(context: Context, attrs: AttributeSet?) : AppCompatRadioButton(context, attrs) {

    private var checkCirclePaint = Paint()
    private var colorCirclePaint = Paint()

    var color: Int = Color.BLACK
        set(value) {
            field = value
            colorCirclePaint.color = value
        }

    init {
        attrs?.let {
            val attributes = context.obtainStyledAttributes(it, R.styleable.ColoredRadioButton)
            color = attributes.getColor(R.styleable.ColoredRadioButton_color, Color.BLACK)
            attributes?.recycle()
        }

        checkCirclePaint.color = Color.GRAY
        checkCirclePaint.style = Paint.Style.FILL
        colorCirclePaint.color = color
        colorCirclePaint.style = Paint.Style.FILL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = 20 + 3 + 3
        val desiredHeight = 20 + 3 + 3
        setMeasuredDimension(
            measureDimension(desiredWidth, widthMeasureSpec),
            measureDimension(desiredHeight, heightMeasureSpec)
        )
    }

    private fun measureDimension(desiredSize: Int, measureSpec: Int): Int {
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)

        return when (specMode) {
            MeasureSpec.EXACTLY -> specSize
            MeasureSpec.AT_MOST -> Math.min(desiredSize, specSize)
            else -> desiredSize
        }
    }

    override fun onDraw(canvas: Canvas?) {
        if (isChecked) {
            canvas?.drawCircle(width / 2f, height / 2f, height * 3 / 8f, checkCirclePaint)
        }
        canvas?.drawCircle(width / 2f, height / 2f, height / 3.5f, colorCirclePaint)
    }
}