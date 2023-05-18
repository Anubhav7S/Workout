package com.example.workout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.workout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.pow

class BMIActivity : AppCompatActivity() {
    companion object{
        private const val METRIC_UNITS_VIEW="METRIC_UNITS_VIEW"
        private const val US_UNITS_VIEW="US_UNITS_VIEW"
    }
    private var currentVisibleView:String= METRIC_UNITS_VIEW
    private var binding:ActivityBmiBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarBmiActivity)
        if (supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title="Calculate BMI"
        }
        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        makeVisibleMetricsView()
        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId:Int ->
            if (checkedId==R.id.rbMetricUnits){
                makeVisibleMetricsView()
            }
            else{
                makeVisibleUSUnitsView()
            }
        }
        binding?.btnCalculateUnits?.setOnClickListener {
            calculateUnits()
        }
    }

    private fun makeVisibleMetricsView(){
        currentVisibleView= METRIC_UNITS_VIEW
        binding?.tilMetricUnitWeight?.visibility=View.VISIBLE
        binding?.tilMetricUnitHeight?.visibility=View.VISIBLE
        binding?.tilMetricUSUnitWeight?.visibility=View.INVISIBLE
        binding?.tilMetricUSUnitHeightFeet?.visibility=View.INVISIBLE
        binding?.tilMetricUSUnitHeightInch?.visibility=View.INVISIBLE

        binding?.etMetricUnitHeight?.text!!.clear()
        binding?.etMetricUnitWeight?.text!!.clear()
        binding?.llDisplayBMIResults?.visibility=View.INVISIBLE
    }
    private fun makeVisibleUSUnitsView(){
        currentVisibleView= US_UNITS_VIEW
        binding?.tilMetricUnitWeight?.visibility=View.INVISIBLE
        binding?.tilMetricUnitHeight?.visibility=View.INVISIBLE
        binding?.tilMetricUSUnitWeight?.visibility=View.VISIBLE
        binding?.tilMetricUSUnitHeightFeet?.visibility=View.VISIBLE
        binding?.tilMetricUSUnitHeightInch?.visibility=View.VISIBLE

        binding?.etMetricUSUnitHeightFeet?.text!!.clear()
        binding?.etMetricUSUnitHeightInch?.text!!.clear()
        binding?.etMetricUSUnitWeight?.text!!.clear()
        binding?.llDisplayBMIResults?.visibility=View.INVISIBLE
    }

    private fun displayBMIResults(bmi:Float){
        val bmiLabel:String
        val bmiDescription:String
        if (bmi.compareTo(15f)<=0){
            bmiLabel="Severely Underweight"
            bmiDescription="OOPS,you really need to take care of yourself, eat more!"
        }
        else if (bmi.compareTo(15f)>0 && bmi.compareTo(18.5)<=0){
            bmiLabel="Underweight"
            bmiDescription="Take care of yourself, eat more!"
        }
        else if (bmi.compareTo(18.5)>0 && bmi.compareTo(25f)<=0){
            bmiLabel="Normal"
            bmiDescription="Congratulations! You are fit."
        }
        else if (bmi.compareTo(25f)>0 && bmi.compareTo(30f)<=0){
            bmiLabel="Overweight"
            bmiDescription="Take care of yourself, eat cautiously and exercise more!"
        }
        else{
            bmiLabel="Severely Overweight"
            bmiDescription="OOPS,you really need to take care of yourself , eat cautiously and exercise more!"
        }
        val bmiValue=BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()
        binding?.llDisplayBMIResults?.visibility= View.VISIBLE
        binding?.tvBMIValue?.text=bmiValue
        binding?.tvBMIType?.text=bmiLabel
        binding?.tvBMIDescription?.text=bmiDescription
    }

    private fun validateMetricUnits():Boolean{
        var isValid=true
        if (binding?.etMetricUnitWeight?.text.toString().isEmpty()){
            isValid=false
        }
        else if(binding?.etMetricUnitHeight?.text.toString().isEmpty()){
            isValid=false
        }
        return isValid
    }

    private fun calculateUnits(){
        if (currentVisibleView== METRIC_UNITS_VIEW){
            if (validateMetricUnits()){
                val heightValue:Float=binding?.etMetricUnitHeight?.text.toString().toFloat()/100
                val weightValue:Float=binding?.etMetricUnitWeight?.text.toString().toFloat()
                val bmi=weightValue/(heightValue*heightValue)
                displayBMIResults(bmi)
            }
            else{
                Toast.makeText(this@BMIActivity,"Please enter valid values!",Toast.LENGTH_SHORT).show()
            }
        }
        else{
            if (validateUSUnits()){
                val usUnitHeightValueFeet:String=binding?.etMetricUSUnitHeightFeet?.text.toString()
                val usUnitHeightValueInch:String=binding?.etMetricUSUnitHeightInch?.text.toString()
                val usUnitWeightValue:Float=binding?.etMetricUSUnitWeight?.text.toString().toFloat()
                val usheightValue=usUnitHeightValueFeet.toFloat()*12 + usUnitHeightValueInch.toFloat()
                val bmi=703*(usUnitWeightValue/(usheightValue*usheightValue))
                displayBMIResults(bmi)
            }
            else{
                Toast.makeText(this@BMIActivity,"Please enter valid values!",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateUSUnits():Boolean{
        var isValid=true
        when{
            binding?.etMetricUSUnitHeightFeet?.text.toString().isEmpty()->{
                isValid=false
            }
            binding?.etMetricUSUnitHeightInch?.text.toString().isEmpty()->{
                isValid=false
            }
            binding?.etMetricUSUnitWeight?.text.toString().isEmpty()->{
                isValid=false
            }
        }
        return isValid
    }
}