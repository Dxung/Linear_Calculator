package com.example.linearlayout_calculator

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import android.widget.Button

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var textResult: TextView

    var state: Int = 1    //chuyển qua lại giữa số hạng thứ nhất và số hạng thứ 2
    var op: Int = 0       //1 là +; 2 là - ; 3 là *; 4 là /
    var op1: Int = 0
    var op2: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textResult = findViewById(R.id.resultNum)

        // Thiết lập sự kiện click cho các nút
        findViewById<Button>(R.id.btn0).setOnClickListener(this)
        findViewById<Button>(R.id.btn1).setOnClickListener(this)
        findViewById<Button>(R.id.btn2).setOnClickListener(this)
        findViewById<Button>(R.id.btn3).setOnClickListener(this)
        findViewById<Button>(R.id.btn4).setOnClickListener(this)
        findViewById<Button>(R.id.btn5).setOnClickListener(this)
        findViewById<Button>(R.id.btn6).setOnClickListener(this)
        findViewById<Button>(R.id.btn7).setOnClickListener(this)
        findViewById<Button>(R.id.btn8).setOnClickListener(this)
        findViewById<Button>(R.id.btn9).setOnClickListener(this)
        findViewById<Button>(R.id.btnCE).setOnClickListener(this)
        findViewById<Button>(R.id.btnC).setOnClickListener(this)
        findViewById<Button>(R.id.btnBS).setOnClickListener(this)
        findViewById<Button>(R.id.btnDivision).setOnClickListener(this)
        findViewById<Button>(R.id.btnMultiplication).setOnClickListener(this)
        findViewById<Button>(R.id.btnMinus).setOnClickListener(this)
        findViewById<Button>(R.id.btnPlus).setOnClickListener(this)
        findViewById<Button>(R.id.btnPosOrNeg).setOnClickListener(this)
        findViewById<Button>(R.id.btnDot).setOnClickListener(this)
        findViewById<Button>(R.id.btnEquals).setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        val id = p0?.id
        when (id) {
            R.id.btn0 -> addDigit(0)
            R.id.btn1 -> addDigit(1)
            R.id.btn2 -> addDigit(2)
            R.id.btn3 -> addDigit(3)
            R.id.btn4 -> addDigit(4)
            R.id.btn5 -> addDigit(5)
            R.id.btn6 -> addDigit(6)
            R.id.btn7 -> addDigit(7)
            R.id.btn8 -> addDigit(8)
            R.id.btn9 -> addDigit(9)
            R.id.btnPlus -> {
                checkDuplicateOp()
                op = 1
                state = 2
            }
            R.id.btnMinus -> {
                checkDuplicateOp()
                op = 2
                state = 2
            }
            R.id.btnMultiplication -> {
                checkDuplicateOp()
                op = 3
                state = 2
            }
            R.id.btnDivision -> {
                checkDuplicateOp()
                op = 4
                state = 2
            }
            R.id.btnEquals -> {
                val result = calculateResult()
                textResult.text = "$result"
                //resetCalculator()
            }
            R.id.btnCE -> {
                // Xóa số hiện tại
                if (state == 1) {
                    op1 = 0
                    textResult.text = "$op1"
                } else {
                    op2 = 0
                    textResult.text = "$op2"
                }
            }
            R.id.btnC -> {
                // Xóa toàn bộ
                resetCalculator()
                textResult.text = "0"
            }
            R.id.btnBS -> {
                // backspace
                if (state == 1) {
                    op1 /= 10
                    textResult.text = "$op1"
                } else {
                    op2 /= 10
                    textResult.text = "$op2"
                }
            }
            R.id.btnPosOrNeg -> {
                // Đảo dấu
                if (state == 1) {
                    op1 = -op1
                    textResult.text = "$op1"
                } else {
                    op2 = -op2
                    textResult.text = "$op2"
                }
            }
        }
    }

    private fun calculateResult(): Int {
        return if (state == 1) {
            // Nếu state là 1, trả về op1
            op1
        } else if (state == 2) {
            // Nếu state là 2, thực hiện phép toán dựa trên op
            when (op) {
                1 -> op1 + op2       // Phép cộng
                2 -> op1 - op2       // Phép trừ
                3 -> op1 * op2       // Phép nhân
                4 -> if (op2 != 0) op1 / op2 else 0  // Phép chia, tránh chia cho 0
                else -> 0            // Trả về 0 nếu không có phép toán hợp lệ
            }
        } else {
            // Nếu không thuộc state 1 hay state 2, có thể trả về giá trị mặc định
            0
        }
    }

    private fun resetCalculator() {
        state = 1
        op1 = 0
        op2 = 0
        op = 0
    }

    fun addDigit(c: Int) {
        if (state == 1) {
            // Giới hạn độ dài tối đa cho op1 là 8 chữ số
            if (op1 < 10000000) {
                op1 = op1 * 10 + c
                textResult.text = "$op1"
            }
        } else {
            // Giới hạn độ dài tối đa cho op2 là 8 chữ số
            if (op2 < 10000000) {
                op2 = op2 * 10 + c
                textResult.text = "$op2"
            }
        }
    }

    //trong trường hợp ấn operation thay vì dấu =
    private fun checkDuplicateOp(){
        if(state==2) {
            op1 = calculateResult()
            textResult.text = "$op1"
            op2=0
            op=0
            state=1
        }
    }
}
