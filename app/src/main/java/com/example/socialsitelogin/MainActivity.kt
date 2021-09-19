package com.example.socialsitelogin


import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class MainActivity : AppCompatActivity() {



    var firebaseAuth: FirebaseAuth?=null
    var callbackManager:CallbackManager?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firebaseAuth=FirebaseAuth.getInstance()
        callbackManager= CallbackManager.Factory.create()


        // Configure Google Sign In


        login_button.setReadPermissions("email")
        login_button.setOnClickListener {
            signin()

        }
    }
    fun google(view: View) {

        val intent = Intent(this, GloadActivity::class.java)

        startActivity(intent)

    }



    private fun signin(){
        login_button.registerCallback(callbackManager,object : FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult?) {
                handelFacebookAccessToken(result!!.accessToken)
            }

            override fun onCancel() {

            }

            override fun onError(error: FacebookException?) {

            }
        })
    }

    private fun handelFacebookAccessToken(accessToken: AccessToken?) {
        val credentials=FacebookAuthProvider.getCredential(accessToken!!.token)
        firebaseAuth!!.signInWithCredential(credentials)
            .addOnSuccessListener {result->
                val email = result.user?.email
                Toast.makeText(this, "You logged in with "+email, Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {e->
                Toast.makeText(this, "e.message ", Toast.LENGTH_SHORT).show()

            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }
}


    /*private fun printKeyHash() {
        try {
            val info=packageManager.getPackageInfo("com.example.socialsitelogin", PackageManager.GET_SIGNATURES)
            for (signature in info.signatures){
                val md= MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e("KEYHASH", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        }
        catch (e: PackageManager.NameNotFoundException)
        {
            Log.d(TAG, "printHashKey()", e)
        }
        catch (e: NoSuchAlgorithmException)
        {
            Log.d(TAG, "printHashKey()", e)
        }
    }*/

