package jp.techacademy.takayuki.ochiai.calcapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.view.View
import android.content.Intent
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity(), View.OnClickListener {

    val _PLUS = 1
    val _MINUS = 2
    val _MULTIPLICATION = 3
    val _DIVISION = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonPlus.setOnClickListener(this)
        buttonMinus.setOnClickListener(this)
        buttonMultiplication.setOnClickListener(this)
        buttonDivision.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        // 入力数値1
        val num1 = editNum1.text.toString()
        // 入力数値2
        val num2 = editNum2.text.toString()
        // 計算種別(初期値に「＋」を設定)
        var calculation = _PLUS

        // 押されたボタンから計算種別を特定
        when (v.id) {
            R.id.buttonPlus -> calculation = _PLUS
            R.id.buttonMinus -> calculation = _MINUS
            R.id.buttonMultiplication -> calculation = _MULTIPLICATION
            R.id.buttonDivision -> calculation = _DIVISION
        }

        // 計算可能かチェック
        val errorMessage = getErrorMessage(num1, num2, calculation)
        // エラー通知ダイアログ表示(処理中断)
        if (!errorMessage.isNullOrEmpty()) {
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("入力エラー")
            alertDialogBuilder.setMessage(errorMessage)
            alertDialogBuilder.setPositiveButton("OK", null)
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()

            return
        }

        // 計算結果を表示(次画面)
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("RESULT", doCalculation(num1.toDouble(), num2.toDouble(), calculation))
        startActivity(intent)
    }

    // 計算エラー文言返却
    private fun getErrorMessage(num1: String, num2: String, calculation: Int): String? {
        // 数値のNULLチェック
        if (num1.isNullOrEmpty() || num2.isNullOrEmpty()) return "数値が入力されていません。数値を入力してください。"

        // 0除算チェック
        if (calculation == _DIVISION && num2.toDouble() == 0.0) return "0除算となっています。2つ目の数値には0以外を入力してください。"

        return null
    }

    // 計算実行
    private fun doCalculation(num1: Double, num2: Double, calculation: Int): Double {
        var result: Double = 0.0
        when (calculation) {
            _PLUS -> result = num1 + num2
            _MINUS -> result = num1 - num2
            _MULTIPLICATION -> result = num1 * num2
            _DIVISION -> result = num1 / num2
        }

        return result
    }
}
