package com.epam.training.customviewtask

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get

/**
 * Custom paint widget, that contains [ColoredRadioButton] for choosing color and [SeekBar] for choosing width.
 *
 * @author Alexandra Makovskaya
 */
class PaintWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var seekBarMaxWidth = 0

    private var defaultColorPosition = 0

    private var firstItemColor = 0

    private val paintWidgetListener = context as OnPaintWidgetChangeListener

    private val seekBar: SeekBar by lazy {
        findViewById<SeekBar>(R.id.seekBar)
    }
    private val widthValue: TextView by lazy {
        findViewById<TextView>(R.id.widthValue)
    }
    private val colorsGroup: RadioGroup by lazy {
        findViewById<RadioGroup>(R.id.colorsGroup)
    }

    init {
        inflate(context, R.layout.paint_widget, this)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.PaintWidget)
        seekBarMaxWidth = attributes.getInteger(R.styleable.PaintWidget_maxWidth, 10)
        updateSeekBar()

        defaultColorPosition = attributes.getInteger(R.styleable.PaintWidget_defaultColorPosition, 0)
        updateDefaultColorPosition()

        firstItemColor = attributes.getColor(R.styleable.PaintWidget_firstItemColor, Color.BLACK)
        updateFirstItemColor()

        attributes.recycle()

        colorsGroup.setOnCheckedChangeListener { _, checkedId ->
            val checkedRadioButton = findViewById<ColoredRadioButton>(checkedId)
            val checkedColor = checkedRadioButton?.color
            checkedColor?.let {
                seekBar.progressDrawable?.setColorFilter(it, PorterDuff.Mode.SRC_ATOP)
                paintWidgetListener.onColorChanged()
            }
        }

        seekBar.thumb?.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
        seekBar.setOnSeekBarChangeListener(SeekBarChangeListener())
        seekBar.progress = 1

        widthValue.text = seekBar.progress.toString()
    }

    private fun updateSeekBar() {
        seekBar.max = seekBarMaxWidth
    }

    private fun updateDefaultColorPosition() {
        val defaultRadioButton = colorsGroup[defaultColorPosition] as ColoredRadioButton
        defaultRadioButton.isChecked = true
        seekBar.progressDrawable?.setColorFilter(defaultRadioButton.color, PorterDuff.Mode.SRC_ATOP)
    }

    private fun updateFirstItemColor() {
        val firstRadioButton = findViewById<ColoredRadioButton>(R.id.radioButton1)
        firstRadioButton?.color = firstItemColor
    }

    inner class SeekBarChangeListener : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            val newWidth = seekBar?.progress
            widthValue.text = newWidth.toString()
            paintWidgetListener.onWidthChanged(newWidth)
        }

        override fun onStartTrackingTouch(p0: SeekBar?) {
        }

        override fun onStopTrackingTouch(p0: SeekBar?) {
        }
    }

    interface OnPaintWidgetChangeListener {
        fun onWidthChanged(newWidth: Int?)
        fun onColorChanged()
    }
}

