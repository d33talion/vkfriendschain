package com.d33t.vkfriends

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.view.View
import android.widget.EditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

private const val SHARED_PREFERENCES_FILE: String = "VK_FRIENDS_PREFERENCES"
private const val REQUEST_CODE_AUTHORIZATION: Int = 1

class MainActivity : AppCompatActivity() {

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onDestroy() {
        super.onDestroy()

        job.cancel()
    }

    fun onSearchButtonClick(view: View) {
        if (!isAuthorized()) {
            goToAuthorization()
            return
        }

        startSearch()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_AUTHORIZATION && resultCode == RESULT_OK) {
            val sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            if (data!!.hasExtra("ACCESS_TOKEN")) {
                editor.putString("ACCESS_TOKEN", data.getStringExtra("ACCESS_TOKEN"))
                editor.putLong("ACCESS_TOKEN_ISSUE_TIME", Date().time)
            }

            if (data.hasExtra("EXPIRATION_TIME")) {
                val accessTokenLifetimeSeconds = Integer.parseInt(data.getStringExtra("EXPIRATION_TIME"))
                editor.putLong("ACCESS_TOKEN_EXPIRATION_TIME", Date().time + accessTokenLifetimeSeconds)
            }

            if (data.hasExtra("USER_ID")) {
                editor.putString("USER_ID", data.getStringExtra("USER_ID"))
            }

            editor.apply()

            startSearch()
        }
    }

    private fun validateUserIdInput(inputId: Int ): Boolean {
        val userIdTextBox = findViewById<EditText>(inputId)
        val userId = userIdTextBox.text.toString()
        if (userId.isBlank()) {
            userIdTextBox.error = getString(R.string.user_id_missed)
            return false
        } else {
            val isUserIdValid = validateUserId(userId)
            if (!isUserIdValid) {
                userIdTextBox.error = getString(R.string.user_id_invalid)
                return false
            }
        }

        return true
    }

    private suspend fun validateUserId(userId: String): Boolean {
        val accessToken = getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE).getString("ACCESS_TOKEN", "")
        if (accessToken.isNullOrBlank()) {
            // do something to handle such kind of error
        }


        uiScope.launch {
            var userInfo = VkAdapter(accessToken).getUserInfo(userId)
            return userInfo != null
        }
    }

    private fun isAuthorized(): Boolean {
        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE)
        if (!sharedPreferences.contains("ACCESS_TOKEN") || !sharedPreferences.contains("ACCESS_TOKEN_EXPIRATION_TIME"))
            return false

        val accessTokenExpirationTime = sharedPreferences.getLong("ACCESS_TOKEN_EXPIRATION_TIME", 0)
        if (Date().time > accessTokenExpirationTime) return false

        return true
    }

    private fun goToAuthorization() {
        startActivityForResult(
            Intent(this, AuthorizationActivity::class.java),
            REQUEST_CODE_AUTHORIZATION)
    }

    private fun startSearch() {
        val uidsValidationPassed = validateUserIdInput(R.id.firstUserTextBox) && validateUserIdInput(R.id.secondUserTextBox)
        if (!uidsValidationPassed)
            return


    }
}
