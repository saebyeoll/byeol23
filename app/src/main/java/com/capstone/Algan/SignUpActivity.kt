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
        setContentView(R.layout.activity_signup)

        val idField = findViewById<EditText>(R.id.id)  // 아이디 입력 필드
        val usernameField = findViewById<EditText>(R.id.username)  // 이름 입력 필드
        val passwordField = findViewById<EditText>(R.id.password)  // 비밀번호 입력 필드
        val phoneField = findViewById<EditText>(R.id.phone)  // 전화번호 입력 필드
        val emailField = findViewById<EditText>(R.id.email)  // 이메일 입력 필드
        val companyNameField = findViewById<EditText>(R.id.company_name)  // 회사 이름 입력 필드
        val roleGroup = findViewById<RadioGroup>(R.id.role_group)  // 역할 선택 필드
        val signupButton = findViewById<Button>(R.id.signup_button)  // 회원가입 버튼

        signupButton.setOnClickListener {
            val id = idField.text.toString()
            val username = usernameField.text.toString()
            val password = passwordField.text.toString()
            val phone = phoneField.text.toString()
            val email = emailField.text.toString()
            val companyName = companyNameField.text.toString()
            val selectedRoleId = roleGroup.checkedRadioButtonId
            val role = findViewById<RadioButton>(selectedRoleId).text.toString()

            if (id.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() &&
                phone.isNotEmpty() && email.isNotEmpty()) {

                // 회사 이름은 사업주만 필요하므로 체크
                if (role == "사업주" && companyName.isEmpty()) {
                    Toast.makeText(this, "회사명을 입력해주세요", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // User 객체 생성
                val user = User(
                    id = id.toInt(),
                    username = username,
                    password = password,
                    role = role,
                    phone = phone,
                    email = email,
                    companyName = if (role == "사업주") companyName else null
                )

                // 회원가입 처리 로직 호출
                val success = signUp(user)
                if (success) {
                    Toast.makeText(this, "회원가입 성공~!!", Toast.LENGTH_SHORT).show()
                    // 회원가입 성공 후 로그인 화면으로
                    finish()  // 현재 화면을 종료하고 이전 화면으로
                } else {
                    Toast.makeText(this, "회원가입 실패~!!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "모든 항목을 채워주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signUp(user: User): Boolean {
        // TODO: 회원 가입 로직( 서버 DB 연동) 구현
        return true
    }
}
