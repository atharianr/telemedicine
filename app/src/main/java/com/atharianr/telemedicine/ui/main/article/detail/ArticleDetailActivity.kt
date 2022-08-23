package com.atharianr.telemedicine.ui.main.article.detail

import android.os.Bundle
import android.widget.RadioButton
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.databinding.ActivityArticleDetailBinding
import com.atharianr.telemedicine.utils.Constant
import com.bumptech.glide.Glide

class ArticleDetailActivity : AppCompatActivity() {

    private var _binding: ActivityArticleDetailBinding? = null
    private val binding get() = _binding as ActivityArticleDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityArticleDetailBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_Telemedicine)
        setContentView(binding.root)

        val title = intent.getStringExtra(Constant.ARTICLE_TITLE)
        val image = intent.getStringExtra(Constant.ARTICLE_IMAGE)
        val definition = intent.getStringExtra(Constant.ARTICLE_DEFINITION)
        val symptom = intent.getStringExtra(Constant.ARTICLE_SYMPTOM)
        val complication = intent.getStringExtra(Constant.ARTICLE_COMPLICATION)
        val diagnosis = intent.getStringExtra(Constant.ARTICLE_DIAGNOSIS)
        val treatment = intent.getStringExtra(Constant.ARTICLE_TREATMENT)

        binding.apply {
            Glide.with(this@ArticleDetailActivity)
                .load(image)
                .centerCrop()
                .into(ivDisease)

            tvTitle.text = title
        }

        setupRadioGroup(definition, symptom, complication, diagnosis, diagnosis, treatment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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
}