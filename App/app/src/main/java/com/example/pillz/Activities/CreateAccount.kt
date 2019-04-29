package com.example.pillz.Activities

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.pillz.Models.User
import com.example.pillz.Network.Endpoints
import com.example.pillz.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateAccount : AppCompatActivity() {

    private val firstName : EditText by lazy { findViewById<EditText>(R.id.firstname) }
    private val lastName : EditText by lazy { findViewById<EditText>(R.id.lastname) }
    private val email : EditText by lazy { findViewById<EditText>(R.id.email) }
    private val password : EditText by lazy { findViewById<EditText>(R.id.password) }
    private val confirm : EditText by lazy { findViewById<EditText>(R.id.confirm) }
    private val loadingSpinner : ProgressBar by lazy { findViewById<ProgressBar>(R.id.loadingSpinner) }
    private val createAccount : Button by lazy { findViewById<Button>(R.id.createAccountButton) }

    private val restAPI: Endpoints by lazy { Endpoints.createRetrofitInstance() }

    private val users = ArrayList<User>()

    private val context : Context by lazy { this }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_account)

        supportActionBar?.hide()

        createAccount.setOnClickListener {
            submitForm()
        }
    }

    private fun isEmpty(text : EditText) : Boolean = (text.text.isBlank() || text.text.isEmpty() || text.text.isNullOrBlank())

    private fun submitForm() {
        var user: User?

        when {
            isEmpty(firstName).or(isEmpty(lastName).or(isEmpty(email).or(isEmpty(password).or(isEmpty(confirm))))) -> Toast.makeText(context, "All fields must be filled", Toast.LENGTH_LONG).show()
            password.text.toString() != confirm.text.toString() -> Toast.makeText(context, "Your passwords do not match", Toast.LENGTH_LONG).show()
            else -> {
                user = User(
                        email.text.toString(),
                        password.text.toString(),
                        firstName.text.toString(),
                        lastName.text.toString())
                getUsers(user)
            }
        }
    }

    private fun getUsers(user: User) {
        loadingSpinner.visibility = View.VISIBLE
        val call = restAPI.getUsers()

        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>?, response: Response<List<User>>?) {
                loadingSpinner.visibility = View.GONE
                response?.let {
                    it.body()?.let {
                        users.addAll(it)
                    }
                    users
                            .filter { it.email == user.email }
                            .forEach {
                                AlertDialog.Builder(context)
                                        .setTitle("Account already exists")
                                        .setMessage("An account already exists with the provided email address")
                                        .setNeutralButton("OK", null)
                                        .show()
                                return
                            }
                    apiPush(user)
                }
            }

            override fun onFailure(call: Call<List<User>>?, t: Throwable?) {
            }
        })

    }

    private fun apiPush(user: User) {
        loadingSpinner.visibility = View.VISIBLE
        val call = restAPI.addUser(user)

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>?, response: Response<User>?) {
                loadingSpinner.visibility = View.GONE
                AlertDialog.Builder(context)
                        .setTitle("Account created")
                        .setMessage("Your account has been created")
                        .setNeutralButton("OK", object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                finish()
                            }
                        })
                        .show()
            }

            override fun onFailure(call: Call<User>?, t: Throwable?) {
            }
        })
    }
}
