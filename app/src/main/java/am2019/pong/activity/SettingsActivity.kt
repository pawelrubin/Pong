package am2019.pong.activity

import am2019.pong.R
import am2019.pong.pong.Difficulty
import am2019.pong.pong.Mode
import am2019.pong.pong.Settings
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.dialog_settings.*

class SettingsActivity : AppCompatActivity() {

    private val settings = Settings()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        ArrayAdapter.createFromResource(this, R.array.modeTypes, R.layout.custom_spinner).also {
                adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(R.layout.custom_row)
            // Apply the adapter to the spinner
            pvpSpinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(this, R.array.difficulty_types, R.layout.custom_spinner).also {
                adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(R.layout.custom_row)
            // Apply the adapter to the spinner
            difficultySpinner.adapter = adapter
        }

        pvpSpinner.onItemSelectedListener = this.ModeSpinnerHandler()
        difficultySpinner.onItemSelectedListener = this.DifficultySpinnerHandler()
    }

    fun play(view: View) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("settings", settings)
        startActivity(intent)
    }

    private inner class ModeSpinnerHandler : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            settings.pvp = Mode.valueOf(parent.getItemAtPosition(position).toString().toUpperCase().replace(
                ' ',
                '_'
            ))
        }
    }

    private inner class DifficultySpinnerHandler : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            settings.difficulty = Difficulty.valueOf(parent.getItemAtPosition(position).toString().toUpperCase())
        }
    }
}
