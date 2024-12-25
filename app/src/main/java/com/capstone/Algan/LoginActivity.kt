package com.capstone.Algan

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // ActionBar 숨기기
        supportActionBar?.hide()

        val usernameField = findViewById<EditText>(R.id.username)
        val passwordField = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.login_button)
        val signupButton = findViewById<Button>(R.id.signup_button)

        loginButton.setOnClickListener {
            val username = usernameField.text.toString()
            val password = passwordField.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                // 로그인 처리 로직 호출
                val success = login(username, password)
                if (success) {
                    Toast.makeText(this, "로그인 성공~~!", Toast.LENGTH_SHORT).show()
                    // 로그인 성공 후 MainActivity로 이동
                    startActivity(Intent(this, MainActivity::class.java))
                    finish() // 로그인 화면 종료
                } else {
                    Toast.makeText(this, "로그인 실패~~!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "모든 필드를 입력하세요", Toast.LENGTH_SHORT).show()
            }
        }

        signupButton.setOnClickListener {
            // 회원가입 화면으로 이동
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun login(username: String, password: String): Boolean {
        // TODO: 로그인 로직 구현
        return username == "test" && password == "1234" // 임시로 설정한 아이디 비번
    }
}
