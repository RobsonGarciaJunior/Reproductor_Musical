package com.example.aplicacionmusical_actividad1_robson_1eva

import android.os.Bundle
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var favouriteSong: Boolean = false
    private var musicStatePlay: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonPlayPause: ImageButton = findViewById(R.id.buttonPlayPause)
        val buttonFav: ImageButton = findViewById(R.id.buttonFav)
        val musicBar: SeekBar = findViewById(R.id.idMusicBar)

        //Calculates the value of current song minute based on the porcentage of the seekbar
        findViewById<TextView>(R.id.currentSongMinute).text = transformFromSecondsToString(
            transformFromStringToSeconds(findViewById<TextView>(R.id.totalSongMinute).text.toString()) * musicBar.progress / 100
        )

        buttonPlayPause.setOnClickListener {
            changeFromPlayToPause()
        }

        buttonFav.setOnClickListener {
            addToFav()
        }
        //Changes the current song minute based on the progress clicked on the seekbar
        musicBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val min: String = findViewById<TextView>(R.id.totalSongMinute).text.toString()
                val totalSongDuration: Int = transformFromStringToSeconds(min)
                val currentSongSec: Int = totalSongDuration * musicBar.progress / 100
                findViewById<TextView>(R.id.currentSongMinute).text =
                    transformFromSecondsToString(currentSongSec)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

    }

    private fun transformFromSecondsToString(currentSongSec: Int): CharSequence {
        val min = (currentSongSec % 3600) / 60
        val sec = currentSongSec % 60
        return String.format("%02d:%02d", min, sec)
    }

    private fun transformFromStringToSeconds(min: String): Int {
        val separation: List<String> = min.split(":") //Separa hasta los :
        val minMusic = separation[0]
        val secMusic = separation[1]
        val minInt: Int = minMusic.toInt()
        val secInt: Int = secMusic.toInt()
        return (minInt * 60) + secInt
    }

    private fun addToFav() {
        val buttonFav: ImageButton = findViewById(R.id.buttonFav)
        if (favouriteSong) {
            buttonFav.setImageResource(android.R.drawable.btn_star_big_off)
            favouriteSong = false
        } else {
            favouriteSong = true
            buttonFav.setImageResource(android.R.drawable.btn_star_big_on)
        }
    }

    private fun changeFromPlayToPause() {
        val buttonPlayPause: ImageButton = findViewById(R.id.buttonPlayPause)
        if (musicStatePlay) {
            buttonPlayPause.setImageResource(android.R.drawable.ic_media_play)
            musicStatePlay = false
        } else {
            buttonPlayPause.setImageResource(android.R.drawable.ic_media_pause)
            musicStatePlay = true
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("musicState", musicStatePlay)
        outState.putBoolean("favState", favouriteSong)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        favouriteSong = savedInstanceState.getBoolean("favState")
        musicStatePlay = savedInstanceState.getBoolean("musicState")
        restoreValues()
    }

    private fun restoreValues() {
        val buttonPlayPause: ImageButton = findViewById(R.id.buttonPlayPause)
        val buttonFav: ImageButton = findViewById(R.id.buttonFav)
        if (favouriteSong) {
            buttonFav.setImageResource(android.R.drawable.btn_star_big_on)
        }
        if (musicStatePlay) {
            buttonPlayPause.setImageResource(android.R.drawable.ic_media_pause)
        }
    }
}