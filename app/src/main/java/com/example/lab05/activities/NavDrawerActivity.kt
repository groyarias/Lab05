package com.example.lab05.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.lab05.R
import com.example.lab05.databinding.ActivityNavDrawerBinding
import com.google.android.material.navigation.NavigationView

class NavDrawerActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityNavDrawerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarNavDrawer.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_nav_drawer)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.menu.findItem(R.id.nav_gestion_estudiante).setCheckable(true)
        navView.menu.findItem(R.id.nav_gestion_estudiante).setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.nav_gestion_estudiante -> {
                    drawerLayout.close()
                    val intent = Intent(this, EstudianteActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        navView.menu.findItem(R.id.nav_gestion_cursos).setCheckable(true)
        navView.menu.findItem(R.id.nav_gestion_cursos).setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.nav_gestion_cursos -> {
                    drawerLayout.close()
                    val intent = Intent(this, CursoActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        navView.menu.findItem(R.id.nav_matricula).setCheckable(true)
        navView.menu.findItem(R.id.nav_matricula).setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.nav_matricula -> {
                    drawerLayout.close()
                    val intent = Intent(this, MatriculaActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.nav_drawer, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_nav_drawer)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}