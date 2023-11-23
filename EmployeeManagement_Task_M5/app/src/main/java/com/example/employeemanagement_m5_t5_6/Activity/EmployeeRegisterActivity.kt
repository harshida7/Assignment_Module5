package com.example.employeemanagement_m5_t5_6.Activity

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Color
import android.opengl.GLES30
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.UnderlineSpan
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.employeemanagement_m5_t5_6.Model.DashboardModel
import com.example.employeemanagement_m5_t5_6.R
import com.example.employeemanagement_m5_t5_6.databinding.ActivityEmployeeRegisterBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import org.json.JSONException
import java.util.Calendar
import java.util.concurrent.TimeUnit

class EmployeeRegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmployeeRegisterBinding
    lateinit var sharedPreferences: SharedPreferences
    lateinit var sharedPreferencesUser: SharedPreferences
    lateinit var layout:LinearLayout

    lateinit var edtOTP: EditText
    lateinit var imgVerify:ImageView
    lateinit var toolbar: Toolbar


    lateinit var verificationid: String
    private lateinit var auth: FirebaseAuth
    lateinit var mcallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks

   companion object {
       lateinit var edtdob: EditText
   }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        toolbar = findViewById(R.id.tool1)
        setSupportActionBar(toolbar)

        val DarkModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK// Retrieve the Mode of the App.
        val isDarkModeOn = DarkModeFlags == Configuration.UI_MODE_NIGHT_YES//Check if the Dark Mode is On

        if(isDarkModeOn){
            binding.cardRegister.setCardBackgroundColor(Color.parseColor("#282828"))
            binding.linearRegister1.setBackgroundColor(Color.parseColor("#121212"))
            toolbar.setBackgroundColor(Color.parseColor("#282828"))
            toolbar.setTitleTextColor(Color.WHITE)
        }
        else{
           // binding.linearRegister1.setBackgroundColor(Color.parseColor("#ffe5ec"))
            binding.cardRegister.setCardBackgroundColor(Color.parseColor("#ffe5ec"))
            binding.imgVerify.setImageResource(R.drawable.verify)
            toolbar.setBackgroundColor(Color.parseColor("#ffe5ec"))
            toolbar.setTitleTextColor(Color.BLACK)
        }

        imgVerify = findViewById(R.id.imgVerify)
        edtOTP = findViewById(R.id.edtOTP)

        auth = FirebaseAuth.getInstance()

        val str = "Login"
        val spannstr = SpannableString(str)

        spannstr.setSpan(UnderlineSpan(), 0, spannstr.length, 0)
        binding.userLoginTxt.text = spannstr

        sharedPreferences = getSharedPreferences("SESSION", Context.MODE_PRIVATE)
        sharedPreferencesUser = getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE)
        edtdob = findViewById(R.id.edtDob)

        if (sharedPreferences.getBoolean("SESSION", false) && !sharedPreferences.getString("email", "")!!.isEmpty()) {
            startActivity(Intent(this, AddProjectActivity::class.java))
            finish()
        }
        if (sharedPreferencesUser.getBoolean("USER_SESSION", false) && !sharedPreferencesUser.getString("email", "")!!.isEmpty()) {
            startActivity(Intent(this, AddProjectActivity::class.java))
            finish()
        }
        binding.userLoginTxt.setOnClickListener {
            startActivity(Intent(applicationContext, EmployeeLoginActivity::class.java))
        }
        edtdob.setOnClickListener {
            var d1 = MyDatePicker()
            d1.show(supportFragmentManager, "Select Date")

        }

        imgVerify.setOnClickListener {
            if (TextUtils.isEmpty(binding.edtPhone.text.toString())) {
                Toast.makeText(this@EmployeeRegisterActivity, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show()
            } else {

                var mob = binding.edtPhone.text.toString()
                //var mob2 = num.toString()+mob.toString()
                sendverificationcode(mob)
                edtOTP.visibility =  View.VISIBLE
            }
        }
        binding.btnRegister1.setOnClickListener {

                if (TextUtils.isEmpty(edtOTP.text.toString())) {
                    Toast.makeText(this@EmployeeRegisterActivity, "Please enter a valid OTP.", Toast.LENGTH_SHORT).show()
                } else {
                    val otp: String = edtOTP.text.toString()
                    verifycode(otp)

                }
        }

        mcallback= object :PhoneAuthProvider.OnVerificationStateChangedCallbacks()
        {
            override fun onVerificationCompleted(p0: PhoneAuthCredential)
            {

                var code =p0.smsCode

                if(code!=null)
                {
                    edtOTP.setText(code)
                }
                else
                {
                    Toast.makeText(applicationContext,"Error ", Toast.LENGTH_LONG).show();
                }
            }
            override fun onVerificationFailed(p0: FirebaseException)
            {
                p0.printStackTrace()
                Toast.makeText(applicationContext,"Failed "  + p0.message,Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken)
            {
                verificationid=p0
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.option, menu)

            return super.onCreateOptionsMenu(menu)
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {

            when (item.itemId) {
                R.id.logout -> {
                    sharedPreferences.edit().clear().commit()
                    finish()
                    startActivity(Intent(applicationContext, EmployeeRegisterActivity::class.java))
                }
            }

            return super.onOptionsItemSelected(item)
        }

        override fun onBackPressed() {
            super.onBackPressed()
            finishAffinity()
        }

        class MyDatePicker : DialogFragment(), DatePickerDialog.OnDateSetListener {

            override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

                var c = Calendar.getInstance()
                var date = c.get(Calendar.DAY_OF_MONTH)
                var month = c.get(Calendar.MONTH)
                var year = c.get(Calendar.YEAR)

                return DatePickerDialog(requireActivity(), this, year, month, date)
            }

            override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                EmployeeRegisterActivity.edtdob.setText(
                    StringBuilder()
                        .append(p3).append("/").append(p2 + 1).append("/")
                        .append(p1).append("")
                )
            }
        }

    private fun verifycode(otp: String)
    {
        val credential = PhoneAuthProvider.getCredential(verificationid, otp)
        signinwithcredential(credential)
    }

    private fun signinwithcredential(credential: PhoneAuthCredential)
    {
        auth.signInWithCredential(credential)
            .addOnCompleteListener {

                if(it.isSuccessful)
                {
                    var user_fname = binding.edtFName.text.toString()
                    var user_lname = binding.edtLName.text.toString()
                    var user_dob = edtdob.text.toString()
                    var user_email = binding.edtEmail.text.toString()
                    var user_genderF = binding.radioFemale.text.toString()
                    var user_genderM = binding.radioMale.text.toString()
                    var user_pass = binding.edtPassword.text.toString()
                    var user_mob= binding.edtPhone.text.toString()

                    if (user_fname.length == 0 && user_lname.length == 0 && user_email.length == 0
                        && user_pass.length == 0 && user_genderF.length == 0 && user_genderM.length == 0
                    ) {
                        binding.edtFName.setError("Please enter first name")
                        binding.edtLName.setError("Please enter last name")
                        binding.edtEmail.setError("Please enter email")
                        binding.edtDob.setError("Please enter Date of birth")
                        binding.txtGender.setError("Please select Gender")
                        binding.edtPhone.setError("Please enter phone number")
                        binding.edtPassword.setError("Please enter password")
                    } else if (user_fname.length == 0) {
                        binding.edtFName.setError("Please enter first name")
                    } else if (user_lname.length == 0) {
                        binding.edtLName.setError("Please enter last name")
                    } else if (user_email.length == 0) {
                        binding.edtEmail.setError("Please enter email")
                    } else if (user_dob.length == 0) {
                        edtdob.setError("Please enter date of birth")
                    } else if (user_genderF.length == 0 || user_genderM.length == 0) {
                        binding.txtGender.setError("Please select gender")
                    } else if (TextUtils.isEmpty(binding.edtPhone.text.toString())) {
                        binding.edtPhone.setError("Please enter mobile number")
                    } else if (user_pass.length == 0) {
                        binding.edtPassword.setError("Please enter password")
                    }
                    var stringRequest: StringRequest = object : StringRequest(
                        Request.Method.POST,
                        "https://compressible-approa.000webhostapp.com/Emp_API/empregister.php",
                        Response.Listener {

                            Toast.makeText(applicationContext, "User Added", Toast.LENGTH_LONG)
                                .show()
                            binding.edtFName.setText("")
                            binding.edtLName.setText("")
                            binding.edtEmail.setText("")
                            binding.edtDob.setText("")
                            binding.radioFemale.isChecked = false
                            binding.radioMale.isChecked = false
                            binding.edtPhone.setText("")
                            binding.edtPassword.setText("")

                            var editor: SharedPreferences.Editor = sharedPreferences.edit()
                            editor.putBoolean("SESSION", true)
                            editor.putString("email", user_email)
                            editor.putString("pass", user_pass)
                            editor.commit()

                        },
                        {
                            Toast.makeText(applicationContext, "No Internet", Toast.LENGTH_LONG)
                                .show()
                        }) {
                        override fun getParams(): MutableMap<String, String>? {
                            var map = HashMap<String, String>()
                            map["first_name"] = user_fname
                            map["last_name"] = user_lname
                            map["date_of_birth"] = user_dob
                            map["email"] = user_email

                            if (binding.radioFemale.isChecked) {
                                map["gender"] = user_genderF
                            } else if (binding.radioMale.isChecked) {
                                map["gender"] = user_genderM
                            }
                            map["phone"] = user_mob

                            map["password"] = user_pass

                            return map
                        }
                    }
                    var queue: RequestQueue = Volley.newRequestQueue(this)
                    queue.add(stringRequest)
                    startActivity(Intent(applicationContext, AddProjectActivity::class.java))
                    imgVerify.setImageResource(R.drawable.baseline_check_circle_outline_24)
                    Toast.makeText(applicationContext,"success",Toast.LENGTH_LONG).show()
                }
            }
            .addOnFailureListener {

                imgVerify.setImageResource(R.drawable.baseline_cancel_24)
                Toast.makeText(applicationContext,"Error",Toast.LENGTH_LONG).show()

            }

    }

    private fun sendverificationcode(mob: String)
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(mob,60, TimeUnit.SECONDS,this,mcallback)
    }
}


