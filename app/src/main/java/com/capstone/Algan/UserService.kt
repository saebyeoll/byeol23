package com.capstone.Algan

// 사용자 기능 처리
class UserService {
    // 등록된 사용자 목록
    private val users = mutableListOf<User>()
    // 초대 코드 목록
    private val invitationCodes = mutableListOf<InvitationCode>()
    // 초대 코드 유효성 검사
    fun validateInvitationCode(code: String): Boolean {
        // 주어진 초대 코드와 일치하는 초대 코드 찾기
        val invitation = invitationCodes.find { it.code == code }
        // 초대 코드가 존재하고, 사용되지 않았으며, 만료되지 않았을 경우 true 반환
        return if (invitation != null && !invitation.isUsed && System.currentTimeMillis() <= invitation.expiryDate) {
            true
        } else {
            // 유효하지 않거나 만료된 초대 코드인 경우 예외 발생
            throw IllegalArgumentException("Invalid or expired invitation code")
        }
    }
    // 사용자 등록 함수
    fun registerUser(username: String, password: String, role: String, invitationCode: String) {
        // 초대 코드 유효성 검사
        if (validateInvitationCode(invitationCode)) {
            // 새로운 사용자 객체 생성 (ID는 사용자 목록 크기 + 1)
            val newUser = User(users.size + 1, username, password, role)

            // 새로운 사용자 목록에 추가
            users.add(newUser)

            // 해당 초대 코드를 사용된 상태로 업데이트
            invitationCodes.find { it.code == invitationCode }?.isUsed = true
        }
    }
}
