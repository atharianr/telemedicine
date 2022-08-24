package com.atharianr.telemedicine.ui.main.profile

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.data.source.remote.request.InputProfileRequest
import com.atharianr.telemedicine.data.source.remote.response.vo.StatusResponse
import com.atharianr.telemedicine.databinding.ActivityInputProfileBinding
import com.atharianr.telemedicine.ui.main.MainActivity
import com.atharianr.telemedicine.utils.Constant
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


class InputProfileActivity : AppCompatActivity() {

    private var _binding: ActivityInputProfileBinding? = null
    private val binding get() = _binding as ActivityInputProfileBinding

    private val inputProfileViewModel: InputProfileViewModel by viewModel()

    private lateinit var datePickerDialog: DatePickerDialog

    private var gender = 0
    private var bloodType = 0
    private val REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityInputProfileBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_Telemedicine)
        setContentView(binding.root)

        val fromAuth = intent.getBooleanExtra(Constant.FROM_AUTH, true)
        val token = intent.getStringExtra(Constant.TOKEN)
        var name = intent.getStringExtra(Constant.NAME)

        binding.apply {
            /* set gender dropdown data */
            val genderArray = arrayOf("Laki-laki", "Perempuan")
            val genderArrayAdapter =
                ArrayAdapter(this@InputProfileActivity, R.layout.spinner, genderArray)
            incGender.tv.setAdapter(genderArrayAdapter)

            /* set blood type dropdown data */
            val bloodArray = arrayOf("A", "B", "AB", "O")
            val bloodArrayAdapter =
                ArrayAdapter(this@InputProfileActivity, R.layout.spinner, bloodArray)
            incBlood.tv.setAdapter(bloodArrayAdapter)

            incName.et.setText(name)

            incBirthdate.et.setOnClickListener {
                datePickerDialog.show()
                disableKeyboard()
            }
            incBirthdate.et.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) datePickerDialog.show()
                disableKeyboard()
            }
            incBirthdate.et.showSoftInputOnFocus = false
            incGender.tv.showSoftInputOnFocus = false
            incGender.tv.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) incGender.tv.showDropDown()
                disableKeyboard()
            }
            incBlood.tv.showSoftInputOnFocus = false
            incBlood.tv.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) incBlood.tv.showDropDown()
                disableKeyboard()
            }

            btnSave.setOnClickListener {
                name = incName.et.text.toString()

                for (i in genderArray.indices) {
                    if (genderArray[i] == incGender.tv.text.toString()) {
                        gender = i
                        break
                    }
                }

                val birthdate = incBirthdate.et.text.toString()
                var bodyHeight = incHeight.et.text.toString()
                var bodyWeight = incWeight.et.text.toString()

                for (i in bloodArray.indices) {
                    if (bloodArray[i] == incBlood.tv.text.toString()) {
                        bloodType = i
                        break
                    }
                }

                val phoneNumber = incPhone.et.text.toString()
                val address = incAddress.et.text.toString()

                if (bodyHeight == "") {
                    bodyHeight = "0"
                }

                if (bodyWeight == "") {
                    bodyWeight = "0"
                }

                val inputProfileRequest = InputProfileRequest(
                    name!!,
                    gender,
                    birthdate,
                    bodyHeight.toInt(),
                    bodyWeight.toInt(),
                    bloodType,
                    phoneNumber,
                    address
                )

                isLoading(true)
                checkValidation(token, inputProfileRequest, fromAuth)
            }

            if (!fromAuth) {
                setupFormData(genderArray, bloodArray)
            }
        }

        setupForm()
        setupDatePicker()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            Toast.makeText(this, data?.data.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun disableKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(
            window.decorView.rootView.windowToken,
            InputMethodManager.RESULT_UNCHANGED_SHOWN
        )
    }

    private fun setupForm() {
        binding.apply {
            incName.apply {
                til.hint = getString(R.string.name)
                et.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            }
            incGender.til.hint = getString(R.string.gender)
            incBirthdate.til.hint = getString(R.string.birthday)
            incHeight.apply {
                til.hint = getString(R.string.body_height)
                et.inputType = InputType.TYPE_CLASS_NUMBER
            }
            incWeight.apply {
                til.hint = getString(R.string.body_weight)
                et.inputType = InputType.TYPE_CLASS_NUMBER
            }
            incBlood.til.hint = getString(R.string.blood_group)
            incPhone.apply {
                til.hint = getString(R.string.phone_number)
                et.inputType = InputType.TYPE_CLASS_PHONE
            }
            incAddress.apply {
                til.hint = getString(R.string.address)
                et.inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE
                et.isSingleLine = false
                et.maxLines = 4
            }
        }
    }

    private fun setupFormData(genderArray: Array<String>, bloodArray: Array<String>) {
        binding.apply {
            incName.et.setText(intent.getStringExtra(Constant.USER_NAME))
            incGender.tv.setText(
                genderArray[intent.getIntExtra(Constant.USER_GENDER, 0)],
                false
            )
            incBirthdate.et.setText(intent.getStringExtra(Constant.USER_BIRTHDATE))
            incHeight.et.setText(intent.getIntExtra(Constant.USER_HEIGHT, 0).toString())
            incWeight.et.setText(intent.getIntExtra(Constant.USER_WEIGHT, 0).toString())
            incBlood.tv.setText(bloodArray[intent.getIntExtra(Constant.USER_BLOOD, 0)], false)
            incPhone.et.setText(intent.getStringExtra(Constant.USER_PHONE))
            incAddress.et.setText(intent.getStringExtra(Constant.USER_ADDRESS))

            val photo = intent.getStringExtra(Constant.USER_PHOTO)
            if (photo != null || photo != "") {
                Glide.with(this@InputProfileActivity)
                    .load(Constant.USER_PHOTO_BASE_URL + photo)
                    .centerCrop()
                    .into(incPhoto.ivPhoto)

                incPhoto.tvUpload.visibility = View.GONE
                incPhoto.llEditPhoto.visibility = View.VISIBLE
            } else {
                incPhoto.tvUpload.visibility = View.VISIBLE
                incPhoto.llEditPhoto.visibility = View.GONE
            }

            incPhoto.btnUpload.setOnClickListener { openGalleryForImage() }
        }
    }

    private fun setupDatePicker() {
        val c = Calendar.getInstance()
        val dateStr = intent.getStringExtra(Constant.USER_BIRTHDATE)
        if (dateStr != null || dateStr == "") {
            val date = stringToDate(dateStr)
            if (date != null) {
                c.time = date
            }
        }

        val yearNow = c.get(Calendar.YEAR)
        val monthNow = c.get(Calendar.MONTH)
        val dayNow = c.get(Calendar.DAY_OF_MONTH)

        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            c.set(year, month, dayOfMonth)

            val df = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
//            birthday = c.time
            val birthdayString = df.format(c.time)
            binding.incBirthdate.et.setText(birthdayString)
        }

        datePickerDialog = DatePickerDialog(
            this,
            R.style.DatePickerDialogTheme,
            dateSetListener,
            yearNow,
            monthNow,
            dayNow
        )

        val dialogInterface = DialogInterface.OnClickListener { dialog, which ->
            if (which == DialogInterface.BUTTON_POSITIVE) {
                val datePicker = datePickerDialog.datePicker
                dateSetListener.onDateSet(
                    datePicker,
                    datePicker.year,
                    datePicker.month,
                    datePicker.dayOfMonth
                )
                dialog.dismiss()
                binding.apply {
                    incBirthdate.et.error = null
                    incHeight.et.requestFocus()
                }
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
            }
        }

        datePickerDialog.apply {
            datePicker.maxDate = System.currentTimeMillis()
            window?.setBackgroundDrawableResource(R.drawable.rounded_box_16)
            setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.next), dialogInterface)
        }
    }

    private fun checkValidation(
        token: String?,
        inputProfileRequest: InputProfileRequest,
        fromAuth: Boolean
    ) {
        val formattedDateMatcher = Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$")

        val gregorianDateMatcher = Pattern.compile(
            "^(29/02/(2000|2400|2800|(19|2[0-9])(0[48]|[2468][048]|[13579][26])))$"
                    + "|^((0[1-9]|1[0-9]|2[0-8])/02/((19|2[0-9])[0-9]{2}))$"
                    + "|^((0[1-9]|[12][0-9]|3[01])/(0[13578]|10|12)/((19|2[0-9])[0-9]{2}))$"
                    + "|^((0[1-9]|[12][0-9]|30)/(0[469]|11)/((19|2[0-9])[0-9]{2}))$"
        )

        binding.apply {
            // name
            if (incName.et.text.toString().isEmpty()) {
                incName.et.error = "Masukkan nama lengkap anda."
                isLoading(false)
                return
            } else {
                incName.et.error = null
            }

            // birthdate
            if (incBirthdate.et.text.toString().isEmpty()) {
                incBirthdate.et.error = "Masukkan tanggal lahir anda."
                isLoading(false)
                return
            }
            if (!formattedDateMatcher.matcher(incBirthdate.et.text.toString()).matches()) {
                incBirthdate.et.error = "Format tanggal tidak tepat."
                isLoading(false)
                return
            }
            if (!gregorianDateMatcher.matcher(incBirthdate.et.text.toString()).matches()) {
                incBirthdate.et.error = "Masukkan tanggal yang sesuai."
                isLoading(false)
                return
            } else {
                incBirthdate.et.error = null
            }

            // bodyHeight
            if (incHeight.et.text.toString().isEmpty()) {
                incHeight.et.error = "Masukkan tinggi badan anda."
                isLoading(false)
                return
            } else {
                incHeight.et.error = null
            }

            // bodyWeight
            if (incWeight.et.text.toString().isEmpty()) {
                incWeight.et.error = "Masukkan berat badan anda."
                isLoading(false)
                return
            } else {
                incWeight.et.error = null
            }

            // phoneNumber
            if (incPhone.et.text.toString().isEmpty()) {
                incPhone.et.error = "Masukkan nomor HP anda."
                isLoading(false)
                return
            } else {
                incPhone.et.error = null
            }

            // address
            if (incAddress.et.text.toString().isEmpty()) {
                incAddress.et.error = "Masukkan alamat anda."
                isLoading(false)
                return
            } else {
                incAddress.et.error = null
            }
        }

        inputProfile(token, inputProfileRequest, fromAuth)
    }

    private fun inputProfile(
        token: String?,
        inputProfileRequest: InputProfileRequest,
        fromAuth: Boolean
    ) {
        val bearerToken = "Bearer $token"
        inputProfileViewModel.inputProfile(bearerToken, inputProfileRequest).observe(this) {
            when (it.status) {
                StatusResponse.SUCCESS -> {
                    if (fromAuth) {
                        saveToken(token)
                        intentToMain()
                    } else {
                        onBackPressed()
                    }

                    isLoading(false)
                }

                StatusResponse.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    isLoading(false)

                    return@observe
                }

                else -> {
                    isLoading(false)
                }
            }
        }
    }

    private fun isLoading(loading: Boolean) {
        binding.apply {
            if (loading) {
                btnSave.text = ""
                btnSave.isEnabled = false
                progressBar.visibility = View.VISIBLE
            } else {
                btnSave.text = getString(R.string.save)
                btnSave.isEnabled = true
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun saveToken(token: String?) {
        val sharedPref = getSharedPreferences(Constant.USER_DATA, Context.MODE_PRIVATE) ?: return
        sharedPref.edit().putString(Constant.TOKEN, token).apply()
    }

    private fun intentToMain() {
        with(Intent(this, MainActivity::class.java)) {
            startActivity(this)
            finish()
        }
    }

    private fun stringToDate(dateStr: String, format: String = "dd/MM/yyyy"): Date? {
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())
        return dateFormat.parse(dateStr)
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }
}