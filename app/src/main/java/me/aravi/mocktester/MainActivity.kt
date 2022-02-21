package me.aravi.mocktester

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import me.aravi.mockcheck.MockCheck

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MockCheck(this).observerMockChanges().observe(this) {
            Toast.makeText(this, "Mocked location : $it", Toast.LENGTH_SHORT).show()
        }
    }
}