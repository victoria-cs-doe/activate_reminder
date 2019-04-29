package com.example.pillz.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Patterns
import com.example.pillz.R
import android.view.View
import android.widget.*
import com.example.pillz.Models.User
import com.example.pillz.Network.Endpoints
import com.example.pillz.Utils.Globals
import com.mobapphome.simpleencryptorlib.SimpleEncryptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var buttonConnect: Button
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loadingSpinner: ProgressBar
    private lateinit var createAccount : RelativeLayout

    private val context: Context by lazy { this }
    private val users = ArrayList<User>()
    private val restAPI: Endpoints by lazy { Endpoints.createRetrofitInstance() }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        setupViews()
    }

    private fun setupViews() {
        createAccount = findViewById(R.id.createAccountContainer)
        createAccount.setOnClickListener {
            startActivity(Intent(context, CreateAccount::class.java))
        }
        loadingSpinner = findViewById(R.id.loadingSpinner)
        email = findViewById(R.id.userNameTextEdit)
        password = findViewById(R.id.passwordTextEdit)
        buttonConnect = findViewById(R.id.connect)
        buttonConnect.setOnClickListener {
            if (!checkUsernamePassword()) {
                Toast.makeText(this.context,
                        context.resources.getString(R.string.usernamePasswordBlack),
                        Toast.LENGTH_LONG).show()
            } else {
                getUsers()
            }
        }
    }

    private fun getUsers() {
        loadingSpinner.visibility = View.VISIBLE
        val call = restAPI.getUsers()

        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>?, response: Response<List<User>>?) {
                response?.let {
                    it.body()?.let {
                        users.addAll(it)
                    }
                }
                loadingSpinner.visibility = View.GONE
                if (connectUser(email.text.toString(), password.text.toString())) {
                    startActivity(Intent(this@MainActivity, Menu::class.java))
                    finish()
                } else {
                    AlertDialog.Builder(context)
                            .setTitle(context.resources.getString(R.string.invalidLogin))
                            .setMessage(context.resources.getString(R.string.invalidLoginLong))
                            .setNeutralButton("OK", null)
                            .show()
                }
            }

            override fun onFailure(call: Call<List<User>>?, t: Throwable?) {
            }
        })

    }

    private fun connectUser(email: String, password: String): Boolean {
        return users
                .firstOrNull { email == it.email }
                ?.let { password == (it.password) } == true
    }

    private fun checkUsernamePassword(): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches())
            return false
        return !(password.text.isEmpty() || password.text.isBlank() ||
                email.text.isEmpty() || email.text.isBlank())
    }
}
