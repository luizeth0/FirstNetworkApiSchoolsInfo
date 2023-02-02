package com.example.firstnetworkapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.firstnetworkapi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val navHost = supportFragmentManager.findFragmentById(R.id.frag_container) as NavHostFragment
        setupActionBarWithNavController(navHost.navController)

    }

    /**
     * Set up the function onBackPressed to return with the arrow on the navController
     */
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}