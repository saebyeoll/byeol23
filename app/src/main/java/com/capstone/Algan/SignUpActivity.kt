package com.capstone.Algan

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup) // XML 파일 연결

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        // UI 요소 연결
        val idField = findViewById<EditText>(R.id.id)
        val usernameField = findViewById<EditText>(R.id.username)
        val passwordField = findViewById<EditText>(R.id.password)
        val emailField = findViewById<EditText>(R.id.email)
        val phoneField = findViewById<EditText>(R.id.phone)
        val roleGroup = findViewById<RadioGroup>(R.id.role_group)
        val signupButton = findViewById<Button>(R.id.signup_button)

        signupButton.setOnClickListener {
            val id = idField.text.toString()
            val username = usernameField.text.toString()
            val password = passwordField.text.toString()
            val email = emailField.text.toString()
            val phone = phoneField.text.toString()

            // 라디오 버튼에서 선택된 역할(role) 가져오기
            val selectedRoleId = roleGroup.checkedRadioButtonId
            val role = if (selectedRoleId != -1) {
                findViewById<RadioButton>(selectedRoleId).text.toString()
            } else {
                "일반 사용자"
            }

            signUp(id, username, password, email, phone, role)
        }
    }

    private fun signUp(id: String, username: String, password: String, email: String, phone: String, role: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser!!.uid
                    val user = User(id, username, email, password, phone, role)

                    database.reference.child("users").child(userId).setValue(user)
                        .addOnSuccessListener {
                            Toast.makeText(this, "회원가입 성공!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MyPageActivity::class.java)) // 마이페이지로 이동
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "데이터 저장 실패: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(this, "회원가입 실패: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
