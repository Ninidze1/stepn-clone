package btu.ninidze.stepcounter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import btu.ninidze.stepcounter.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}