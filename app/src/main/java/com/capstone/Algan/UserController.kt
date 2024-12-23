package com.capstone.Algan

class UserController(private val userService: UserService) {

    fun handleUserRegistration(username: String, password: String, role: String, invitationCode: String) {
        try {
            userService.registerUser(username, password, role, invitationCode)
            println("User registered successfully!")
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }

    fun handleLogin(username: String, password: String): Boolean {
        // TODO: 로그인 검증 로직 추가
        println("Login attempted with username: $username")
        return true // 임시로 로그인 성공 반환
    }
}
