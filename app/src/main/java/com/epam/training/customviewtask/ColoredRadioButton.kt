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

    private val paint = Paint()

    var color: Int = Color.BLACK

    init {
        attrs?.let {
            val attributes = context.obtainStyledAttributes(it, R.styleable.ColoredRadioButton)
            color = attributes.getColor(R.styleable.ColoredRadioButton_color, Color.BLACK)
            attributes?.recycle()
        }

        paint.style = Paint.Style.FILL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
            measureDimension(DESIRED_WIDTH, widthMeasureSpec),
            measureDimension(DESIRED_HEIGHT, heightMeasureSpec)
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
            paint.color = Color.GRAY
            canvas?.drawCircle(width / 2f, height / 2f, height * 3 / 8f, paint)
        }
        paint.color = color
        canvas?.drawCircle(width / 2f, height / 2f, height / 3.5f, paint)
    }

    companion object {
        private const val DESIRED_WIDTH = 26
        private const val DESIRED_HEIGHT = 26
    }
}