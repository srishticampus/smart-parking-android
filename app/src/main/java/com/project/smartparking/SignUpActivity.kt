package com.project.smartparking

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.project.smartparking.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.signUpBtn.setOnClickListener {

            if (binding.nameEt.text.isNotEmpty() || binding.emailEt.text.isNotEmpty() || binding.phoneNumberEt.text.isNotEmpty()
                || binding.passwordEt.text.isNotEmpty() || binding.retypePasswordEt.text.isNotEmpty()
            ) {
                if (binding.passwordEt.text.toString()
                        .equals(binding.retypePasswordEt.text.toString())
                ) {

                    signUp()


                } else {
                    Toast.makeText(applicationContext, "Password not match", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(applicationContext, "Fill the details", Toast.LENGTH_SHORT).show()
            }


        }


    }

    private fun signUp() {

        val sharedPreference = getSharedPreferences("shares_pref", MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putString("phone", binding.phoneNumberEt.text.toString())
        editor.putString("password", binding.retypePasswordEt.text.toString())
        editor.commit()
        startActivity(Intent(this@SignUpActivity, MainActivity::class.java))

    }
}