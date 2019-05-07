package com.epam.training.customviewtask

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.View
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
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

    var seekBarMaxWidth = 0
        set(value) {
            field = value
            updateSeekBar()
        }

    var defaultColorPosition = 0
        set(value) {
            field = value
            updateDefaultColorPosition()
        }

    var firstItemColor = 0
        set(value) {
            field = value
            updateFirstItemColor()
        }

    var paintWidgetListener: OnPaintWidgetChangeListener? = null

    private var view: View? = null
    private var seekBar: SeekBar? = null
    private var widthValue: TextView? = null
    private var colorsGroup: RadioGroup? = null

    init {
        view = View.inflate(context, R.layout.paint_widget, this)

        seekBar = view?.findViewById(R.id.seekBar)
        widthValue = view?.findViewById(R.id.widthValue)
        colorsGroup = view?.findViewById(R.id.colorsGroup)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.PaintWidget)
        seekBarMaxWidth = attributes.getInteger(R.styleable.PaintWidget_maxWidth, 10)
        defaultColorPosition = attributes.getInteger(R.styleable.PaintWidget_defaultColorPosition, 0)
        firstItemColor = attributes.getColor(R.styleable.PaintWidget_firstItemColor, Color.BLACK)
        attributes.recycle()

        colorsGroup?.setOnCheckedChangeListener { _, checkedId ->
            val checkedRadioButton = view?.findViewById<ColoredRadioButton>(checkedId)
            val checkedColor = checkedRadioButton?.color
            checkedColor?.let {
                seekBar?.progressDrawable?.setColorFilter(it, PorterDuff.Mode.SRC_ATOP)
                paintWidgetListener?.onColorChanged()
            }
        }

        seekBar?.thumb?.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
        seekBar?.setOnSeekBarChangeListener(SeekBarChangeListener())
        seekBar?.progress = 1

        widthValue?.text = seekBar?.progress.toString()
    }

    private fun updateSeekBar() {
        seekBar?.max = seekBarMaxWidth
    }

    private fun updateDefaultColorPosition() {
        val defaultRadioButton = colorsGroup?.get(defaultColorPosition) as ColoredRadioButton
        defaultRadioButton.isChecked = true
        seekBar?.progressDrawable?.setColorFilter(defaultRadioButton.color, PorterDuff.Mode.SRC_ATOP)
    }

    private fun updateFirstItemColor() {
        val firstRadioButton = view?.findViewById<ColoredRadioButton>(R.id.radioButton1)
        firstRadioButton?.color = firstItemColor
    }

    inner class SeekBarChangeListener : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            val newWidth = seekBar?.progress
            widthValue?.text = newWidth.toString()
            paintWidgetListener?.onWidthChanged(newWidth)
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

