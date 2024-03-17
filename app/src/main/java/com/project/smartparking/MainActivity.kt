package com.project.smartparking

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.project.smartparking.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.signUpBt.setOnClickListener {
            startActivity(Intent(this@MainActivity, SignUpActivity::class.java))
        }

        binding.loginBtn.setOnClickListener {

            val sharedPreference = getSharedPreferences("shares_pref", MODE_PRIVATE)
            val phone = sharedPreference.getString("phone", "nul").toString()
            val password = sharedPreference.getString("password", "null").toString()

            if (binding.etPhoneNumber.text.isNotEmpty() && binding.etPassword.text.isNotEmpty()) {
                if (phone.equals(binding.etPhoneNumber.text.toString()) && password.equals(
                        binding.etPassword.text.toString()
                    )
                ) {
                    // Toast.makeText(applicationContext, phone, Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                } else {
                    Toast.makeText(applicationContext, password, Toast.LENGTH_SHORT).show()
                    // Toast.makeText(applicationContext, "Wrong Credentials", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(applicationContext, "Fill the details", Toast.LENGTH_SHORT).show()
            }


        }
        
    }
}