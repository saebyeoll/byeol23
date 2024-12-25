package com.capstone.Algan

//사용자
data class User(
    val id: Int, //아이디
    val username: String, // 이름
    val password: String, // 비번
    val role: String, // "business_owner" or "employee"
    var company : String // 회사 이름
)
