package com.example.final_project.data.remote.mapper.registration

import com.example.final_project.data.remote.model.registration.RegistrationDto
import com.example.final_project.domain.remote.model.registration.GetRegistration

fun RegistrationDto.toDomain(): GetRegistration {
    return GetRegistration(
        user = user
    )
}