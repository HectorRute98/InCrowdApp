package com.gprosoft.incrowdapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.gprosoft.incrowdapp.R
import com.gprosoft.incrowdapp.databinding.ActivityMainBinding
import com.gprosoft.incrowdapp.ui.view.login.LoginFragment
import com.gprosoft.incrowdapp.ui.view.register.RegisterFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(2000)
        setTheme(R.style.Theme_InCrowdApp)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        changeFragment(LoginFragment())
        binding.buttonLogin.setOnClickListener { changeFragment(LoginFragment()) }
        binding.buttonSignUp.setOnClickListener { changeFragment(RegisterFragment()) }
    }

    fun changeFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }

}