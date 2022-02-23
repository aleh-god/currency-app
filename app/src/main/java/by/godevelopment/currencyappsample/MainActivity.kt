package by.godevelopment.currencyappsample

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.currencyappsample.commons.TAG
import by.godevelopment.currencyappsample.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private var _navController: NavController? = null
    private val navController get() = _navController!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment
        = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        _navController = navHostFragment.navController

        setupNavUI()
    }

    private fun setupNavUI() {
        navController.let {
            binding.bottomNavMenu.setupWithNavController(it)
            binding.toolbar.setupWithNavController(it, AppBarConfiguration(it.graph))
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val icon = findViewById<View>(R.id.save_settings)
            if (destination.id == R.id.settings_dest) {
                icon.visibility = View.VISIBLE
            } else {
                icon.visibility = View.GONE
            }
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.save_settings)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        _navController = null
    }
}