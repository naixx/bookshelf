package com.github.naixx.bookshelf.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.naixx.bookshelf.app.AuthManager
import com.github.naixx.bookshelf.databinding.SplashActivityBinding
import common.startActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var authManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (authManager.isLogged) {
            startActivity<MainActivity>()
            finish()
            return
        }

        val binding = SplashActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginBtn.setOnClickListener {
            AuthDialogFragment.newInstance(true).show(supportFragmentManager, null)
        }
        binding.registerBtn.setOnClickListener {
            AuthDialogFragment.newInstance(false).show(supportFragmentManager, null)
        }
    }
}
