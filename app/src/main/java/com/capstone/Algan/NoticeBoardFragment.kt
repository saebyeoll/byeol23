package com.capstone.Algan.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.Algan.R
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.ImageButton
import android.widget.ListView
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

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

    class DaeTaFragment : Fragment() {

        // SubstituteRequest 데이터 클래스 정의
        data class SubstituteRequest(
            val timeRange: String, // 대타 신청 시간
            val requesterName: String, // 대타 신청자 이름
            var acceptedBy: String? = null // 수락한 사람의 이름 (없으면 null)
        )

        private val substituteList = mutableListOf<SubstituteRequest>()
        private lateinit var adapter: ArrayAdapter<SubstituteRequest>

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.fragment_daeta, container, false)

            // ListView 초기화
            val listView = view.findViewById<ListView>(R.id.listViewSubstituteRequests)

            // ListView에 사용할 Adapter 커스터마이징
            adapter = object : ArrayAdapter<SubstituteRequest>(requireContext(), R.layout.item_substitute_request, substituteList) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val itemView = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_substitute_request, parent, false)

                    // 데이터 바인딩
                    val substituteTimeTextView = itemView.findViewById<TextView>(R.id.textViewSubstituteTime)
                    substituteTimeTextView.text = substituteList[position].timeRange

                    // 요청자 이름 설정
                    val requesterNameTextView = itemView.findViewById<TextView>(R.id.textViewRequesterName)
                    requesterNameTextView.text = substituteList[position].requesterName

                    // 수락한 사람의 이름 표시
                    val acceptedByTextView = itemView.findViewById<TextView>(R.id.textViewAcceptedBy)
                    val acceptedBy = substituteList[position].acceptedBy
                    acceptedByTextView.text = if (acceptedBy != null) "수락자: $acceptedBy" else ""

                    // 버튼 설정
                    val acceptButton = itemView.findViewById<Button>(R.id.buttonAccept)
                    val approveButton = itemView.findViewById<Button>(R.id.buttonApprove)

                    // 수락 버튼 클릭 시 처리
                    acceptButton.setOnClickListener {
                        handleAcceptRequest(position)
                    }

                    // 승인 버튼 클릭 시 처리
                    approveButton.setOnClickListener {
                        handleApproveRequest(position)
                    }

                    return itemView
                }
            }

            listView.adapter = adapter

            // 대타 신청 버튼 클릭 이벤트 설정
            val buttonRequestSubstitute = view.findViewById<ImageButton>(R.id.buttonRequestSubstitute)
            buttonRequestSubstitute.setOnClickListener {
                showDatePicker()
            }

            return view
        }

        private fun getRequesterName(): String {
            // TODO: DB 연동으로 변경.
            val sharedPreferences = requireContext().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
            return sharedPreferences.getString("사용자이름", "미확인된 사용자") ?: "미확인된 사용자"
        }

        private fun showDatePicker() {
            // MaterialDatePicker를 사용하여 날짜 선택
            val datePicker = com.google.android.material.datepicker.MaterialDatePicker.Builder.datePicker()
                .setTitleText("대타를 신청할 날짜를 선택하세요")
                .build()

            datePicker.addOnPositiveButtonClickListener { selection ->
                val selectedDate = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                    .format(java.util.Date(selection))
                showTimeRangePicker(selectedDate)
            }

            datePicker.show(childFragmentManager, "DATE_PICKER")
        }

        private fun showTimeRangePicker(selectedDate: String) {
            // 현재 시간
            val currentTime = java.util.Calendar.getInstance()
            val hour = currentTime.get(java.util.Calendar.HOUR_OF_DAY)
            val minute = currentTime.get(java.util.Calendar.MINUTE)

            // 시간 선택을 위한 AlertDialog
            val timePickerView = layoutInflater.inflate(R.layout.dialog_time_picker, null)

            val startHourPicker = timePickerView.findViewById<NumberPicker>(R.id.startHourPicker)
            val startMinutePicker = timePickerView.findViewById<NumberPicker>(R.id.startMinutePicker)
            val endHourPicker = timePickerView.findViewById<NumberPicker>(R.id.endHourPicker)
            val endMinutePicker = timePickerView.findViewById<NumberPicker>(R.id.endMinutePicker)

            // 기본 시간 설정
            startHourPicker.value = hour
            startMinutePicker.value = minute

            endHourPicker.value = hour + 1 // 기본적으로 종료시간은 1시간 뒤로 설정
            endMinutePicker.value = minute

            // NumberPicker 설정
            startHourPicker.minValue = 0
            startHourPicker.maxValue = 23
            startMinutePicker.minValue = 0
            startMinutePicker.maxValue = 59

            endHourPicker.minValue = 0
            endHourPicker.maxValue = 23
            endMinutePicker.minValue = 0
            endMinutePicker.maxValue = 59

            val dialog = AlertDialog.Builder(requireContext())
                .setTitle("대타를 요청할 시간을 설정하세요")
                .setView(timePickerView)
                .setPositiveButton("확인") { _, _ ->
                    val startHour = startHourPicker.value
                    val startMinute = startMinutePicker.value
                    val endHour = endHourPicker.value
                    val endMinute = endMinutePicker.value

                    val startTime = String.format("%02d:%02d", startHour, startMinute)
                    val endTime = String.format("%02d:%02d", endHour, endMinute)

                    // 선택된 시간 범위를 SubstituteRequest로 추가
                    val timeRange = "$selectedDate $startTime ~ $endTime"
                    val requesterName = getRequesterName() // 대타 요청자 이름
                    val newRequest = SubstituteRequest(timeRange, requesterName)
                    addToSubstituteList(newRequest)
                }
                .setNegativeButton("취소", null)
                .create()

            dialog.show()
        }

        private fun addToSubstituteList(request: SubstituteRequest) {
            substituteList.add(request)
            adapter.notifyDataSetChanged()
        }

        private fun handleAcceptRequest(position: Int) {
            // 수락 버튼 클릭 시 수락한 사람의 이름을 해당 대타 요청에 추가
            val requesterName = getRequesterName()
            substituteList[position].acceptedBy = requesterName
            adapter.notifyDataSetChanged()

            // 수락 처리
            Toast.makeText(requireContext(), "대타 신청 수락: ${substituteList[position].timeRange}", Toast.LENGTH_SHORT).show()
        }

        private fun handleApproveRequest(position: Int) {
            // 승인 버튼 클릭 시 처리
            Toast.makeText(requireContext(), "대타 신청 승인: ${substituteList[position].timeRange}", Toast.LENGTH_SHORT).show()
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
