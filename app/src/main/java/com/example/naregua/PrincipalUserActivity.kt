package com.example.naregua

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.naregua.databinding.ActivityPrincipalUserBinding
import com.google.firebase.auth.FirebaseAuth

class PrincipalUserActivity : AppCompatActivity() {
    private val binding by lazy{ActivityPrincipalUserBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val navController = findNavController(R.id.nav_host_fragment)
        setupWithNavController(binding.bottomNavigation, navController)
    }

    override fun onDestroy() {
        super.onDestroy()

        FirebaseAuth.getInstance().signOut()
    }
}