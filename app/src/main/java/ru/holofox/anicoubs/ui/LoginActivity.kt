package ru.holofox.anicoubs.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil

import com.afollestad.materialdialogs.MaterialDialog

import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope

import ru.holofox.anicoubs.R
import ru.holofox.anicoubs.databinding.ActivityLoginBinding
import ru.holofox.anicoubs.ui.base.LocaleAppCombatActivity

class LoginActivity : LocaleAppCombatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.apply {
            loginButton.setOnClickListener {
                VK.login(this@LoginActivity,
                    arrayListOf(VKScope.WALL, VKScope.VIDEO, VKScope.OFFLINE))
            }
        }

        isLoggedIn()
    }

    private fun isLoggedIn() {
        if (VK.isLoggedIn()) {
            MainActivity.startFrom(this@LoginActivity)
            overridePendingTransition(0, 0)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object: VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                MainActivity.startFrom(this@LoginActivity)
                finish()
            }
            override fun onLoginFailed(errorCode: Int) {
                MaterialDialog(this@LoginActivity)
                    .title(R.string.vk_unknown_error)
                    .message(R.string.vk_try_again)
                    .positiveButton(R.string.vk_ok).show()
            }
        }
        if (!VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    companion object {
        fun startFrom(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)
        }
    }

}