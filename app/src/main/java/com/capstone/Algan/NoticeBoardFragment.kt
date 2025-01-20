package com.capstone.Algan.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.Algan.R
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast

class NoticeBoardFragment : Fragment() {

    // 액티비티에서 프래그먼트 교체하는 함수
    private fun replaceFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.frame_container, fragment)
        transaction?.commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_noticeboard, container, false)

        // 기본 화면을 대타 화면으로 설정
        replaceFragment(DaeTaFragment())

        // 대타 버튼 클릭
        val buttonDaeta = view.findViewById<View>(R.id.button_daeta)
        buttonDaeta.setOnClickListener {
            replaceFragment(DaeTaFragment())
        }

        // 공지 버튼 클릭
        val buttonNotice = view.findViewById<View>(R.id.button_notice)
        buttonNotice.setOnClickListener {
            replaceFragment(NoticeFragment())
        }

        // 소통 버튼 클릭
        val buttonCommunication = view.findViewById<View>(R.id.button_communication)
        buttonCommunication.setOnClickListener {
            replaceFragment(CommunicationFragment())
        }

        return view
    }

    // 대타 화면 프래그먼트
    class DaeTaFragment : Fragment() {

        private val substituteList = mutableListOf<String>()
        private lateinit var adapter: ArrayAdapter<String>

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.fragment_daeta, container, false)

            // ListView 초기화
            val listView = view.findViewById<android.widget.ListView>(R.id.listViewSubstituteRequests)
            adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, substituteList)
            listView.adapter = adapter

            // 대타 신청 버튼 클릭 이벤트 설정
            val buttonRequestSubstitute = view.findViewById<View>(R.id.buttonRequestSubstitute)
            buttonRequestSubstitute.setOnClickListener {
                showDatePicker()
            }

            return view
        }

        private fun showDatePicker() {
            // MaterialDatePicker를 사용하여 날짜 선택
            val datePicker = com.google.android.material.datepicker.MaterialDatePicker.Builder.datePicker()
                .setTitleText("대타를 신청할 날짜를 선택하세요")
                .build()

            datePicker.addOnPositiveButtonClickListener { selection ->
                val selectedDate = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date(selection))
                showTimePicker(selectedDate)
            }

            datePicker.show(childFragmentManager, "DATE_PICKER")
        }

        private fun showTimePicker(selectedDate: String) {
            // 현재 시간을 가져옵니다.
            val currentTime = java.util.Calendar.getInstance()
            val hour = currentTime.get(java.util.Calendar.HOUR_OF_DAY)
            val minute = currentTime.get(java.util.Calendar.MINUTE)

            // 시작 시간 선택
            val startTimePicker = android.app.TimePickerDialog(
                requireContext(),
                { _, startHour, startMinute ->
                    val startTime = String.format("%02d:%02d", startHour, startMinute)

                    // 종료 시간 선택
                    val endTimePicker = android.app.TimePickerDialog(
                        requireContext(),
                        { _, endHour, endMinute ->
                            val endTime = String.format("%02d:%02d", endHour, endMinute)

                            // 선택된 시간 범위를 리스트에 추가
                            val timeRange = "$selectedDate $startTime ~ $endTime"
                            addToSubstituteList(timeRange)
                        },
                        hour,
                        minute,
                        true
                    )
                    endTimePicker.setTitle("종료 시간을 선택하세요")
                    endTimePicker.show()
                },
                hour,
                minute,
                true
            )
            startTimePicker.setTitle("시작 시간을 선택하세요")
            startTimePicker.show()
        }


        private fun addToSubstituteList(request: String) {
            // 선택된 날짜 및 시간을 리스트에 추가
            substituteList.add(request)
            adapter.notifyDataSetChanged()
            android.widget.Toast.makeText(requireContext(), "대타 신청이 추가되었습니다.", android.widget.Toast.LENGTH_SHORT).show()
        }
    }


    // 공지 화면 프래그먼트
    class NoticeFragment : Fragment() {

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.fragment_notice, container, false)
        }
    }

    // 소통 화면 프래그먼트
    class CommunicationFragment : Fragment() {

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.fragment_communication, container, false)
        }
    }
}
