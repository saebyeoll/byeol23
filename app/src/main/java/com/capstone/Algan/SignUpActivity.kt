package com.capstone.Algan

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)  // 회원가입 화면의 XML 파일 이름

        val idField = findViewById<EditText>(R.id.id)  // 아이디 입력 필드
        val usernameField = findViewById<EditText>(R.id.username)  // 이름 입력 필드
        val passwordField = findViewById<EditText>(R.id.password)  // 비밀번호 입력 필드
        val roleGroup = findViewById<RadioGroup>(R.id.role_group)  // 역할 선택 필드
        val signupButton = findViewById<Button>(R.id.signup_button)  // 회원가입 버튼

        signupButton.setOnClickListener {
            val id = idField.text.toString()
            val username = usernameField.text.toString()
            val password = passwordField.text.toString()
            val selectedRoleId = roleGroup.checkedRadioButtonId
            val role = findViewById<RadioButton>(selectedRoleId).text.toString()

            if (id.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()) {
                // 회원가입 처리 로직 호출
                val success = signUp(id, username, password, role)
                if (success) {
                    Toast.makeText(this, "Sign Up successful!", Toast.LENGTH_SHORT).show()
                    // 회원가입 성공 후 로그인 화면으로 돌아가기
                    finish()  // 현재 화면을 종료하고 이전 화면으로 돌아감
                } else {
                    Toast.makeText(this, "Sign Up failed!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signUp(id: String, username: String, password: String, role: String): Boolean {
        // TODO: 회원가입 처리 로직 구현 (서버와 연동 + DB 구현)
        // 임시로 가입 성공을 반환
        return true
    }
}
