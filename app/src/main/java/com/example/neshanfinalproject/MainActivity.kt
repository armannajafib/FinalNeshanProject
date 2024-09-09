package com.example.neshanfinalproject
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val originButton = findViewById<Button>(R.id.origin)
        val destinationButton = findViewById<Button>(R.id.destination)

        val searchFragment = SearchFragment()
        val searchFragmentLayout = R.id.search_fragment
        originButton.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(searchFragmentLayout,searchFragment)
                addToBackStack(null)
                commit()
            }
        }
        destinationButton.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(searchFragmentLayout,searchFragment)
                addToBackStack(null)
                commit()
            }
        }
        var userLocationActivity =UserLocationActivity()
    }
}
