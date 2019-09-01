package com.simplychat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class FullscreenActivity : AppCompatActivity() {


    private val SIGN_IN_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Handler().postDelayed({
            if (FirebaseAuth.getInstance().currentUser == null) {
                // Start sign in/sign up activity
                // Choose authentication providers
                val providers = arrayListOf(
                    AuthUI.IdpConfig.EmailBuilder().build(),
                    AuthUI.IdpConfig.PhoneBuilder().build(),
                    AuthUI.IdpConfig.GoogleBuilder().build()
                )

                startActivityForResult(
                    AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                    SIGN_IN_REQUEST_CODE
                )
            } else {
                // User is already signed in. Therefore, display
                // a welcome Toast
                Toast.makeText(
                    this,
                    "Welcome " + FirebaseAuth.getInstance()
                        .currentUser?.displayName,
                    Toast.LENGTH_LONG
                )
                    .show()

                //Goto Main Activity
                finish()
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }
        }, 5000)

    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SIGN_IN_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(
                    this,
                    "Successfully signed in. Welcome!",
                    Toast.LENGTH_LONG
                )
                    .show()
                //Goto Main Activity
                finish()
                startActivity(Intent(applicationContext, MainActivity::class.java))
            } else {
                Toast.makeText(
                    this,
                    "We couldn't sign you in. Please try again later.",
                    Toast.LENGTH_LONG
                )
                    .show()

                // Close the app
                finish()
            }
        }

    }
}
