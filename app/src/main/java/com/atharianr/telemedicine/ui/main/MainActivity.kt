package com.atharianr.telemedicine.ui.main

import android.content.*
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.data.source.remote.request.InputProfileRequest
import com.atharianr.telemedicine.data.source.remote.response.vo.StatusResponse
import com.atharianr.telemedicine.databinding.ActivityMainBinding
import com.atharianr.telemedicine.ui.main.article.ArticleFragment
import com.atharianr.telemedicine.ui.main.consultation.ConsultationFragment
import com.atharianr.telemedicine.ui.main.home.HomeFragment
import com.atharianr.telemedicine.ui.main.profile.ProfileFragment
import com.atharianr.telemedicine.utils.Constant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding as ActivityMainBinding
    private var integerDeque: Deque<Int> = ArrayDeque(3)
    private var flag = true

    private val mainViewModel: MainViewModel by viewModel()

    private var name: String? = null
    private var email: String? = null
    private var gender: Int = 0
    private var birthdate: String? = null
    private var bodyHeight: Int = 0
    private var bodyWeight: Int = 0
    private var bloodType: Int = 0
    private var phoneNumber: String? = null
    private var address: String? = null
    private var photo: String? = null
    private var fcmTokenBroadcast: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_Telemedicine)
        setContentView(binding.root)

        integerDeque.push(R.id.home)

        getUserDetail(getBearerToken())
        setupBottomNav()
        receiveBroadcast()
        sendTokenToServer(getFCMToken(), getBearerToken())
    }

    override fun onResume() {
        super.onResume()
        if (integerDeque.peek() == R.id.home || integerDeque.peek() == R.id.profile) {
            getUserDetail(getBearerToken())
        }
    }

    override fun onBackPressed() {
        integerDeque.pop()
        if (!integerDeque.isEmpty()) {
            loadFragment(getFragment(integerDeque.peek()))
        } else {
            finish()
        }

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(
            binding.root.windowToken,
            InputMethodManager.RESULT_UNCHANGED_SHOWN
        )
    }

    private fun setupBottomNav() {
        binding.apply {

            bottomNav.selectedItemId = R.id.home

            bottomNav.setOnItemSelectedListener {

                val id = it.itemId
                if (integerDeque.contains(id)) {
                    if (id == R.id.home) {
                        if (integerDeque.size != 1) {
                            if (flag) {
                                integerDeque.addFirst(R.id.home)
                                flag = !flag
                            }
                        }
                    }
                    integerDeque.remove(id)
                }
                integerDeque.push(id)
                loadFragment(getFragment(id))

                return@setOnItemSelectedListener true
            }

            bottomNav.setOnItemReselectedListener {
                return@setOnItemReselectedListener
            }
        }
    }

    private fun getFragment(itemId: Int?): Fragment {
        binding.apply {
            when (itemId) {
                R.id.home -> {
                    bottomNav.menu.getItem(0).isChecked = true
                    return HomeFragment()
                }
                R.id.consultation -> {
                    bottomNav.menu.getItem(1).isChecked = true
                    return ConsultationFragment()
                }

                R.id.article -> {
                    bottomNav.menu.getItem(2).isChecked = true
                    return ArticleFragment()
                }
                R.id.profile -> {
                    bottomNav.menu.getItem(3).isChecked = true
                    return ProfileFragment()
                }
            }
            bottomNav.menu.getItem(0).isChecked = true
            return HomeFragment()
        }
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            val bundle = Bundle()
            bundle.putString(Constant.USER_NAME, name)
            bundle.putString(Constant.USER_EMAIL, email)
            bundle.putInt(Constant.USER_GENDER, gender)
            bundle.putString(Constant.USER_BIRTHDATE, birthdate)
            bundle.putInt(Constant.USER_HEIGHT, bodyHeight)
            bundle.putInt(Constant.USER_WEIGHT, bodyWeight)
            bundle.putInt(Constant.USER_BLOOD, bloodType)
            bundle.putString(Constant.USER_PHONE, phoneNumber)
            bundle.putString(Constant.USER_ADDRESS, address)
            bundle.putString(Constant.USER_PHOTO, photo)
            fragment.arguments = bundle

            val tag = fragment::class.java.simpleName
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.fragment, fragment, tag)
                .commit()
            return true
        }
        return false
    }

    private fun getUserDetail(token: String?) {
        if (token != null) {
            mainViewModel.getUserDetail(token).observe(this) {
                when (it.status) {
                    StatusResponse.SUCCESS -> {
                        name = it.body?.data?.name
                        email = it.body?.data?.email
                        if (it.body?.data?.gender != null) {
                            gender = it.body.data.gender.toInt()
                        }
                        birthdate = it.body?.data?.birthdate
                        if (it.body?.data?.bodyHeight != null) {
                            bodyHeight = it.body.data.bodyHeight.toInt()
                        }
                        if (it.body?.data?.bodyWeight != null) {
                            bodyWeight = it.body.data.bodyWeight.toInt()
                        }
                        if (it.body?.data?.bloodType != null) {
                            bloodType = it.body.data.bloodType.toInt()
                        }
                        phoneNumber = it.body?.data?.phoneNumber
                        address = it.body?.data?.address
                        photo = it.body?.data?.photo

                        saveUserId(it.body?.data?.id.toString())
                        loadFragment(getFragment(integerDeque.peek()))
                    }

                    StatusResponse.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                        return@observe
                    }

                    else -> return@observe
                }
            }
        }
    }

    private fun receiveBroadcast() {
        fcmTokenBroadcast = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val fcmToken = intent.extras?.getString(Constant.FCM_TOKEN, "")
                sendTokenToServer(fcmToken, getBearerToken())
            }
        }
        registerReceiver(fcmTokenBroadcast, IntentFilter(Constant.ON_NEW_TOKEN))
    }

    private fun sendTokenToServer(fcmToken: String?, bearerToken: String?) {
        val inputProfileRequest = InputProfileRequest(fcmToken = fcmToken)
        if (bearerToken != null && bearerToken != "") {
            if (fcmToken != null && fcmToken != "") {
                CoroutineScope(Dispatchers.IO).launch {
                    mainViewModel.putTokenFCM(bearerToken, inputProfileRequest)
                }
            }
        }
    }

    private fun getBearerToken(): String? {
        val sharedPref = getSharedPreferences(Constant.USER_DATA, Context.MODE_PRIVATE)
        return sharedPref.getString(Constant.TOKEN, "")
    }

    private fun getFCMToken(): String? {
        val sharedPref = getSharedPreferences(Constant.DEVICE_DATA, Context.MODE_PRIVATE)
        return sharedPref.getString(Constant.FCM_TOKEN, "")
    }

    private fun saveUserId(userId: String?) {
        val sharedPref = getSharedPreferences(Constant.USER_DATA, Context.MODE_PRIVATE)
        sharedPref.edit().putString(Constant.USER_ID, userId).apply()
    }
}