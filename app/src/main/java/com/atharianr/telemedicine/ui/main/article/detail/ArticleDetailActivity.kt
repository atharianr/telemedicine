package com.atharianr.telemedicine.ui.main.article.detail

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.data.source.remote.response.vo.StatusResponse
import com.atharianr.telemedicine.databinding.ActivityArticleDetailBinding
import com.atharianr.telemedicine.ui.main.article.ArticleViewModel
import com.atharianr.telemedicine.utils.Constant
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticleDetailActivity : AppCompatActivity() {

    private var _binding: ActivityArticleDetailBinding? = null
    private val binding get() = _binding as ActivityArticleDetailBinding

    private val articleViewModel: ArticleViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityArticleDetailBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_Telemedicine)
        setContentView(binding.root)

        val articleId = intent.getIntExtra(Constant.ARTICLE_ID, 0).toString()
        val title = intent.getStringExtra(Constant.ARTICLE_TITLE)
        binding.tvTitle.text = title

        getArticleDetail(getBearerToken(), articleId)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getArticleDetail(token: String?, articleId: String) {
        isLoading(loading = true)
        if (token != null) {
            articleViewModel.getArticleDetail(token, articleId).observe(this) {
                when (it.status) {
                    StatusResponse.SUCCESS -> {
                        val data = it.body?.data
                        if (data != null) {
                            binding.apply {
                                if (data.imageUrl != null || data.imageUrl != "") {
                                    Glide.with(this@ArticleDetailActivity)
                                        .load(data.imageUrl)
                                        .placeholder(R.drawable.image_placeholder)
                                        .centerCrop()
                                        .into(ivDisease)
                                }

                                setupRadioGroup(
                                    data.definition,
                                    data.symptom,
                                    data.complication,
                                    data.cause,
                                    data.diagnosis,
                                    data.treatment
                                )
                            }
                            isLoading(loading = false)
                        }
                    }

                    StatusResponse.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                        isLoading(loading = false)
                        return@observe
                    }

                    else -> {
                        isLoading(loading = false)
                        return@observe
                    }
                }
            }
        }
    }

    private fun setupRadioGroup(
        definition: String?,
        symptom: String?,
        complication: String?,
        cause: String?,
        diagnosis: String?,
        treatment: String?
    ) {
        binding.apply {
            /* init */
            rbDefinition.isChecked = true
            setRadioButtonTextColor(rbDefinition, R.color.white)
            setRadioButtonTextColor(rbSymptom, R.color.black)
            setRadioButtonTextColor(rbComplication, R.color.black)
            setRadioButtonTextColor(rbCause, R.color.black)
            setRadioButtonTextColor(rbDiagnosis, R.color.black)
            setRadioButtonTextColor(rbTreatment, R.color.black)
            tvContent.text = definition

            /* onCheckedChange */
            rgContent.setOnCheckedChangeListener { _, i ->
                when (i) {
                    rbDefinition.id -> {
                        setRadioButtonTextColor(rbDefinition, R.color.white)
                        setRadioButtonTextColor(rbSymptom, R.color.black)
                        setRadioButtonTextColor(rbComplication, R.color.black)
                        setRadioButtonTextColor(rbCause, R.color.black)
                        setRadioButtonTextColor(rbDiagnosis, R.color.black)
                        setRadioButtonTextColor(rbTreatment, R.color.black)
                        tvContent.text = definition
                    }

                    rbSymptom.id -> {
                        setRadioButtonTextColor(rbDefinition, R.color.black)
                        setRadioButtonTextColor(rbSymptom, R.color.white)
                        setRadioButtonTextColor(rbComplication, R.color.black)
                        setRadioButtonTextColor(rbCause, R.color.black)
                        setRadioButtonTextColor(rbDiagnosis, R.color.black)
                        setRadioButtonTextColor(rbTreatment, R.color.black)
                        tvContent.text = symptom
                    }

                    rbComplication.id -> {
                        setRadioButtonTextColor(rbDefinition, R.color.black)
                        setRadioButtonTextColor(rbSymptom, R.color.black)
                        setRadioButtonTextColor(rbComplication, R.color.white)
                        setRadioButtonTextColor(rbCause, R.color.black)
                        setRadioButtonTextColor(rbDiagnosis, R.color.black)
                        setRadioButtonTextColor(rbTreatment, R.color.black)
                        tvContent.text = complication
                    }

                    rbCause.id -> {
                        setRadioButtonTextColor(rbDefinition, R.color.black)
                        setRadioButtonTextColor(rbSymptom, R.color.black)
                        setRadioButtonTextColor(rbComplication, R.color.black)
                        setRadioButtonTextColor(rbCause, R.color.white)
                        setRadioButtonTextColor(rbDiagnosis, R.color.black)
                        setRadioButtonTextColor(rbTreatment, R.color.black)
                        tvContent.text = cause
                    }

                    rbDiagnosis.id -> {
                        setRadioButtonTextColor(rbDefinition, R.color.black)
                        setRadioButtonTextColor(rbSymptom, R.color.black)
                        setRadioButtonTextColor(rbComplication, R.color.black)
                        setRadioButtonTextColor(rbCause, R.color.black)
                        setRadioButtonTextColor(rbDiagnosis, R.color.white)
                        setRadioButtonTextColor(rbTreatment, R.color.black)
                        tvContent.text = diagnosis
                    }

                    rbTreatment.id -> {
                        setRadioButtonTextColor(rbDefinition, R.color.black)
                        setRadioButtonTextColor(rbSymptom, R.color.black)
                        setRadioButtonTextColor(rbComplication, R.color.black)
                        setRadioButtonTextColor(rbCause, R.color.black)
                        setRadioButtonTextColor(rbDiagnosis, R.color.black)
                        setRadioButtonTextColor(rbTreatment, R.color.white)
                        tvContent.text = treatment
                    }
                }
            }
        }
    }

    private fun setRadioButtonTextColor(radioButton: RadioButton, @ColorRes color: Int) {
        radioButton.setTextColor(ContextCompat.getColor(this, color))
    }

    private fun isLoading(loading: Boolean) {
        binding.apply {
            if (loading) {
                layoutData.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            } else {
                layoutData.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun getBearerToken(): String? {
        val sharedPref = getSharedPreferences(Constant.USER_DATA, Context.MODE_PRIVATE)
        return sharedPref.getString(Constant.TOKEN, "")
    }
}