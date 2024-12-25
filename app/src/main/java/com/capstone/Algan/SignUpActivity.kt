package com.capstone.Algan

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        // ActionBar 숨기기
        supportActionBar?.hide()

        val idField = findViewById<EditText>(R.id.id)
        val usernameField = findViewById<EditText>(R.id.username)
        val passwordField = findViewById<EditText>(R.id.password)
        val emailField = findViewById<EditText>(R.id.email)
        val phoneField = findViewById<EditText>(R.id.phone)
        val roleGroup = findViewById<RadioGroup>(R.id.role_group)
        val signupButton = findViewById<Button>(R.id.signup_button)

        // 툴바의 back_button 클릭 리스너 추가
        val backButton = findViewById<ImageButton>(R.id.back_button)
        backButton.setOnClickListener {
            // 이전 화면으로 돌아가기
            onBackPressed()
        }

        signupButton.setOnClickListener {
            val id = idField.text.toString()
            val username = usernameField.text.toString()
            val password = passwordField.text.toString()
            val email = emailField.text.toString()
            val phone = phoneField.text.toString()
            val selectedRoleId = roleGroup.checkedRadioButtonId
            val role = findViewById<RadioButton>(selectedRoleId)?.text.toString()

            // 필드 체크 (빈 값이면 에러 메시지)
            if (id.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty() || role.isEmpty()) {
                Toast.makeText(this, "모든 필드를 입력하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                // 회원가입 처리
                val success = signUp(id, username, password, role, email, phone)
                if (success) {
                    Toast.makeText(this, "회원가입 성공!", Toast.LENGTH_SHORT).show()
                    finish()  // 회원가입 후 현재 화면 종료
                } else {
                    Toast.makeText(this, "회원가입 실패!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this, "오류: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signUp(id: String, username: String, password: String, role: String, email: String, phone: String): Boolean {
        // 실제 회원가입 처리 로직 구현 (예: 서버와 연동)
        // 임시로 성공 반환
        return true
    }
}
