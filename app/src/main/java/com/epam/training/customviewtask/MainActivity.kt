package com.epam.training.customviewtask

import android.os.Bundle
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

/**
 * Main activity of application, that contains [Switch] to make a [PaintWidget] visible or invisible.
 *
 * @author Alexandra Makovskaya
 */
class MainActivity : AppCompatActivity(), PaintWidget.OnPaintWidgetChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val switchButton = findViewById<Switch>(R.id.switchButton)
        switchButton?.setOnCheckedChangeListener { _, isChecked ->
            val paintWidget = findViewById<PaintWidget>(R.id.paintWidget)
            paintWidget.isVisible = isChecked
        }
    }

    override fun onWidthChanged(newWidth: Int?) {
        Toast.makeText(this, getString(R.string.toast_width_changed, newWidth), Toast.LENGTH_SHORT)
            .show()
    }

    override fun onColorChanged() {
        Toast.makeText(this, R.string.toast_color_changed, Toast.LENGTH_SHORT).show()
    }
}
