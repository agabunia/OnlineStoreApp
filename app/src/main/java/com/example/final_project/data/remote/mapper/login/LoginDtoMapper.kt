package com.example.final_project.data.remote.mapper.login

import com.example.final_project.data.remote.model.login.LoginDto
import com.example.final_project.domain.remote.model.login.GetLogin

fun LoginDto.toDomain(): GetLogin {
    return GetLogin(
        user = user
    )
}