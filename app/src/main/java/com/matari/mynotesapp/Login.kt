package com.matari.mynotesapp

import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_notes.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.create_acount.*
import kotlinx.android.synthetic.main.create_acount.view.*

class Login : AppCompatActivity() {

    var SavedChecked = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var sharedPerfernce = getSharedPreferences("DataUsers", Context.MODE_PRIVATE)
        var SavedUname = sharedPerfernce.getString("username", "None")
        edUsername.setText(SavedUname.toString())
        var isChecked = sharedPerfernce.getString("checked1", "None")

        if (isChecked == "1") {
            bxChecked.isChecked
            txt_register.visibility = View.INVISIBLE
            txt_pwdforget.visibility = View.VISIBLE
            var SavedPsw = sharedPerfernce.getString("psw", "None")

            edPsw.setText(SavedPsw.toString())
        }


        if (bxChecked.isChecked == true) {
            SavedChecked = true
        }


        btnAdd.setOnClickListener {
            var username: String = edUsername.text.toString()
            var psw: String = edPsw.text.toString()

            val sharedPerfernce = getSharedPreferences("DataUsers", Context.MODE_PRIVATE)
            var SavedUname = sharedPerfernce.getString("username", "None")
            var SavedPsw = sharedPerfernce.getString("psw", "None")

            if (username == SavedUname && psw == SavedPsw) {
                if (bxChecked.isChecked == true) {
                    val editer = sharedPerfernce.edit()
                    editer.putString("checked1", "1")
                    editer.commit()
                }
                Toast.makeText(this, "Login success", Toast.LENGTH_LONG).show()
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Password or username is wrong", Toast.LENGTH_LONG).show()
            }
        }

        txt_register.setOnClickListener {
            showDialog()
        }

        txt_pwdforget.setOnClickListener {
            var alertBulder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.create_acount, null)
            view.edtpsw1.visibility = View.INVISIBLE
            view.edtpsw2.visibility = View.INVISIBLE

            val sharedPerfernce = getSharedPreferences("DataUsers", Context.MODE_PRIVATE)
            var getUsername = sharedPerfernce.getString("username", "None")
            var getBirthday = sharedPerfernce.getString("B_date", "None")
            Toast.makeText(this, "Please enter your username and your bithdary", Toast.LENGTH_LONG).show()
            alertBulder.setNeutralButton("Cancel") { _, _ ->
                // Toast.makeText(applicationContext,"You cancelled the dialog.",Toast.LENGTH_SHORT).show()
            }
            view.btnRegest.setText("enter")
            alertBulder.setView(view)
            val alertDialog = alertBulder.create()
            alertDialog.show()

            view.btnRegest.setOnClickListener {

                if (view.edtUname.text.toString() == getUsername && view.edtBirthday.text.toString() == getBirthday) {

                    view.edtpsw1.visibility = View.VISIBLE
                    view.edtpsw2.visibility = View.VISIBLE
                    view.btnRegest.setText("regist")
                    //     alertBulder.setCancelable(true)
                    showDialog()
                    alertDialog.hide()
                } else {
                    Toast.makeText(this, "Username or birthday is wrong", Toast.LENGTH_LONG).show()
                }
            }


        }
    }

    private fun showDialog() {
        var alertBulder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.create_acount, null)
        alertBulder.setNeutralButton("Cancel") { _, _ ->
            // Toast.makeText(applicationContext,"You cancelled the dialog.",Toast.LENGTH_SHORT).show()
        }
        alertBulder.setView(view)
        val alertDialog = alertBulder.create()
        alertDialog.show()
      view.btnRegest.setOnClickListener {
            val username = view.edtUname.text.toString()
            val password1 = view.edtpsw1.text.toString()
            val password2 = view.edtpsw1.text.toString()
            val bday = view.edtBirthday.text.toString()



            if (username.isEmpty() || password1.isEmpty() || password2.isEmpty() || bday.isEmpty()) {
                Toast.makeText(this, "Empty feld ", Toast.LENGTH_LONG).show()
            } else {
                if (password1 == password2) {
                    val sharedPerfernce = getSharedPreferences("DataUsers", Context.MODE_PRIVATE)
                    val editer = sharedPerfernce.edit()
                    editer.putString("username", username)
                    editer.putString("psw", password1)
                    editer.putString("B_date", bday)

                    Toast.makeText(this, "your a count is success created", Toast.LENGTH_LONG).show()
                    editer.commit()


                    view.edtUname.text.clear()
                    view.edtpsw1.text.clear()
                    view.edtpsw2.text.clear()
                    view.edtBirthday.text.clear()

                    txt_register.visibility = View.INVISIBLE
                    txt_pwdforget.visibility = View.VISIBLE

                    alertDialog.hide() //hide the dialog
                } else {
                    Toast.makeText(this, "Passwords is note same please check that", Toast.LENGTH_LONG).show()
                }
            }
        }

    }


}
