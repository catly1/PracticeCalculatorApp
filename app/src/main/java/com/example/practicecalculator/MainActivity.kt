package com.example.practicecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    var lastNumber = false
    var lastDot = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit(view: View){
        tvInput.append((view as Button).text)
        lastNumber = true
    }

    fun onClear(view: View){
        tvInput.text = ""
        lastNumber = false
        lastDot = false
    }

    fun onDecimalPoint(view: View){
        if (lastNumber && !lastDot){
            tvInput.append(".")
            lastNumber = false
            lastDot = true
        }
    }

    fun onOperator(view: View){
        if(lastNumber && !isOperatorAdded(tvInput.text.toString())){
            tvInput.append((view as Button).text)
            lastNumber = false
            lastDot = false
        }
    }

    private fun isOperatorAdded(value: String) : Boolean{
        return if (value.startsWith("-")){
            false
        } else {
            value.contains("/") || value.contains("*")
                    || value.contains("+") || value.contains("-")
        }
    }

    private fun removeZeroAfterDot(result: String) : String{
        var value = result
        if(result.contains(".0"))
            value = result.substring(0, result.length - 2)
        return value
    }

    fun onEqual(view: View){
        if(lastNumber){
            var tvValue = tvInput.text.toString()
            var prefix = ""
            try{

                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                if(tvValue.contains("-")){
                    val splitValue = tvValue.split("-")
                    var left = splitValue[0]
                    var right = splitValue[1]

                    if (prefix.isNotEmpty()){
                        left = prefix + left
                    }

                    tvInput.text = removeZeroAfterDot((left.toDouble() - right.toDouble()).toString())
                }

                if(tvValue.contains("+")){
                    val splitValue = tvValue.split("+")
                    var left = splitValue[0]
                    var right = splitValue[1]

                    if (prefix.isNotEmpty()){
                        left = prefix + left
                    }

                    tvInput.text = removeZeroAfterDot((left.toDouble() + right.toDouble()).toString())
                }

                if(tvValue.contains("*")){
                    val splitValue = tvValue.split("*")
                    var left = splitValue[0]
                    var right = splitValue[1]

                    if (prefix.isNotEmpty()){
                        left = prefix + left
                    }

                    tvInput.text = removeZeroAfterDot((left.toDouble() * right.toDouble()).toString())
                }

                if(tvValue.contains("/")){
                    val splitValue = tvValue.split("/")
                    var left = splitValue[0]
                    var right = splitValue[1]

                    if (prefix.isNotEmpty()){
                        left = prefix + left
                    }

                    tvInput.text = removeZeroAfterDot((left.toDouble() / right.toDouble()).toString())
                }
            } catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }

    }
}
