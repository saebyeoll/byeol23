package com.capstone.Algan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // ActionBar 숨기기
        supportActionBar?.hide()
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)

        // 기본 화면으로 급여 화면을 설정
        replaceFragment(SalaryFragment())

        // 하단 내비게이션 아이템 클릭 리스너 설정
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragment_salary -> {
                    replaceFragment(SalaryFragment()) // 급여 화면으로 이동
                    true
                }
                R.id.fragment_noticeboard -> {
                    replaceFragment(NoticeBoardFragment()) // 게시판 화면으로 이동
                    true
                }
                R.id.fragment_checklist -> {
                    replaceFragment(ChecklistFragment()) // 체크리스트 화면으로 이동
                    true
                }
                R.id.fragment_workrecord -> {
                    replaceFragment(WorkRecordFragment()) // 출퇴근 기록 화면으로 이동
                    true
                }
                else -> false
            }
        }
    }

    // Fragment 교체 함수
    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, fragment)
        transaction.addToBackStack(null) // 뒤로가기 기능
        transaction.commit()
    }
}
