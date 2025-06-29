package com.example.ch10_dialog

import android.app.DatePickerDialog
import android.app.ProgressDialog.show
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.ch10_dialog.databinding.ActivityMainBinding
import com.example.ch10_dialog.databinding.DialogCustomBinding
import java.lang.Boolean

class MainActivity : AppCompatActivity() {
    val TAG = "25androidprograming"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDate.setOnClickListener {
            DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    //TODO("Not yet implemented")
                    Log.d(TAG, "날짜 정하기 : $year 년 ${month + 1} 월 $dayOfMonth 일")
                    binding.btnDate.text = "날짜 정하기 : $year 년 ${month + 1} 월 $dayOfMonth 일"
                    binding.btnDate.textSize = 24f
                    binding.btnDate.setTextColor(Color.rgb(255, 0, 0))

                    Toast.makeText(
                        applicationContext,
                        "날짜 정하기 : $year 년 ${month + 1} 월 $dayOfMonth 일",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }, 2025, 3, 7).show() //2025-04-07

        }

        binding.btnTime.setOnClickListener {
            TimePickerDialog(this, object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    //TODO("Not yet implemented")
                    binding.btnTime.text = "시간 정하기 : $hourOfDay 시 $minute 분"
                    binding.btnTime.textSize = 24f
                    binding.btnTime.setTextColor(Color.rgb(255, 0, 0))
                }
            }, 15, 19, true).show()
        }


        val eventhandler = object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                //TODO("Not yet implemented")
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    Log.d(TAG, "POSITIVE BUTTON")
                } else if (which == DialogInterface.BUTTON_NEGATIVE) {
                    Log.d(TAG, "NEGATIVE BUTTON")
                } else if (which == DialogInterface.BUTTON_NEUTRAL) {
                    Log.d(TAG, "NEUTRAL BUTTON")
                }
            }
        }


        binding.btnAlert.setOnClickListener {
            /*
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("알림")
            dialog.setIcon(android.R.drawable.ic_dialog_alert)
            dialog.setMessage("정말로 종료할 까요?")
            dialog.setPositiveButton("예", null)
            dialog.setNegativeButton("아니오", null)
            dialog.setNeutralButton("상세정보", null)
            dialog.show()

            */
            AlertDialog.Builder(this).run {
                setTitle("알림")
                setIcon(android.R.drawable.ic_dialog_alert)
                setMessage("정말로 종료할까요?")
                setPositiveButton("예", eventhandler)
                setNegativeButton("아니오", eventhandler)
                setNeutralButton("상세정보", eventhandler)
                show()
            }

            /*
            AlertDialog.Builder(this).run{
                setTitle("알림")
                setIcon(android.R.drawable.ic_dialog_alert)
                setMessage("정말로 종료할까요?")
                setPositiveButton("예", DialogInterface.OnClickListener{ dialog, which ->
                    Log.d(TAG, "POSITIVE BUTTON")
                })
                setNegativeButton("아니오", DialogInterface.OnClickListener{ dialog, which ->
                    Log.d(TAG, "NEGATIVE BUTTON")
                })
                setNeutralButton("상세정보", DialogInterface.OnClickListener{ dialog, which ->
                    Log.d(TAG, "NEUTRAL BUTTON")
                })
                show()
            }
             */
        }


        val items = arrayOf<String>("빨간", "초록", "파란", "노란")

        var selected = 1

        val eventhandler2 = object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                //TODO("Not yet implemented")
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    Log.d(TAG, "POSITIVE BUTTON")
                    binding.btnSingle.text = "${items[selected]} 선택"
                    binding.btnSingle.textSize = 24f
                    binding.btnSingle.setTextColor(Color.rgb(255, 0, 0))
                } else if (which == DialogInterface.BUTTON_NEGATIVE) {
                    Log.d(TAG, "NEGATIVE BUTTON")
                } else if (which == DialogInterface.BUTTON_NEUTRAL) {
                    Log.d(TAG, "NEUTRAL BUTTON")
                }
            }
        }

        binding.btnItem.setOnClickListener {
            AlertDialog.Builder(this).run {
                setTitle("알림")
                setIcon(android.R.drawable.ic_dialog_alert)
                setItems(items, object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        binding.btnItem.text = "${items[which]} 선택"
                        binding.btnItem.textSize = 24f
                        binding.btnItem.setTextColor(Color.rgb(255, 0, 0))
                    }
                })
                setPositiveButton("예", eventhandler)
                setNegativeButton("아니오", eventhandler)
                setNeutralButton("상세정보", eventhandler)
                show()
            }
        }

        binding.btnSingle.setOnClickListener {
            AlertDialog.Builder(this).run {
                setTitle("알림")
                setIcon(android.R.drawable.ic_dialog_alert)
                setSingleChoiceItems(items, 1, object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        selected = which //사용자가 선택한 값을 저장해 두겠다.
                    }
                })
                setPositiveButton("예", eventhandler2)
                setNegativeButton("아니오", eventhandler2)
                setNeutralButton("상세정보", eventhandler2)
                show()
            }
        }

        //val mul_selected = arrayOf<Boolean>(true, true, false, false)
        val mul_selected = booleanArrayOf(true, true, false, false)


        val eventhandler3 = object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    Log.d(TAG, "POSITIVE BUTTON")
                    var str = ""
                    for (i in 0..3) {
                        if (mul_selected[i]) {  // only add the item if it's selected
                            str += items[i] + " "
                        }
                    }
                    binding.btnMulti.text = str
                    binding.btnMulti.textSize = 24f
                    binding.btnMulti.setTextColor(Color.rgb(255, 0, 0))
                } else if (which == DialogInterface.BUTTON_NEGATIVE) {
                    Log.d(TAG, "NEGATIVE BUTTON")
                } else if (which == DialogInterface.BUTTON_NEUTRAL) {
                    Log.d(TAG, "NEUTRAL BUTTON")
                }
            }
        }

        binding.btnMulti.setOnClickListener {
            AlertDialog.Builder(this).run {
                setTitle("알림")
                setIcon(android.R.drawable.ic_dialog_alert)
                setMultiChoiceItems(items, mul_selected, object : DialogInterface.OnMultiChoiceClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int, isChecked: Boolean) {
                        Log.d(TAG, "$which 항목 $isChecked")
                        mul_selected[which] = isChecked
                    }
                })
                setPositiveButton("예", eventhandler3)
                setNegativeButton("아니오", eventhandler3)
                setNeutralButton("상세정보", eventhandler3)
                show()
            }
        }


        val dia_binding = DialogCustomBinding.inflate(layoutInflater)
        val eventhandler4 = object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    Log.d(TAG, "POSITIVE BUTTON")
                    if(dia_binding.rbtn1.isChecked == true){
                        binding.btnCustom.text = dia_binding.rbtn1.text.toString()
                        //binding.btnCustom.text = "1학년"
                    }
                    binding.btnCustom.textSize = 24f
                    binding.btnCustom.setTextColor(Color.rgb(255, 0, 0))
                }
            }
        }
        val cus_dia = AlertDialog.Builder(this).run{ //매번 반복해서 생성하는 것이 아니라. 만들어서 show만 하기
            setTitle("알림")
            setView(dia_binding.root)
            setPositiveButton("yes", eventhandler4)
            create()
        }
        binding.btnCustom.setOnClickListener {
            //AlertDialog.Builder(this).run{
            //    setTitle("알림")
            //    setView(dia_binding.root)
            //    setPositiveButton("yes", null)
            //    show()
            //}
            cus_dia.show()
        }

    }
}















