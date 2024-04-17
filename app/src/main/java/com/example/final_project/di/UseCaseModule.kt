package com.example.final_project.di

import com.example.final_project.domain.local.repository.datastore.DataStoreRepository
import com.example.final_project.domain.remote.repository.login.LoginRepository
import com.example.final_project.domain.remote.repository.registration.RegistrationRepository
import com.example.final_project.domain.local.usecase.datastore.clear.ClearDataStoreUseCase
import com.example.final_project.domain.local.usecase.datastore.authorization.ReadDataStoreUseCase
import com.example.final_project.domain.local.usecase.datastore.authorization.SaveDataStoreUseCase
import com.example.final_project.domain.local.usecase.datastore.profile_image.ReadUserUidUseCase
import com.example.final_project.domain.remote.usecase.login.LoginUseCase
import com.example.final_project.domain.remote.usecase.payment.PaymentUseCase
import com.example.final_project.domain.remote.usecase.registration.RegistrationUseCase
import com.example.final_project.domain.remote.usecase.validators.user.EmailValidatorUseCase
import com.example.final_project.domain.remote.usecase.validators.user.PasswordRepeatValidatorUseCase
import com.example.final_project.domain.remote.usecase.validators.user.PasswordValidatorUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideRegistrationUseCase(
        registrationRepository: RegistrationRepository
    ): RegistrationUseCase {
        return RegistrationUseCase(registrationRepository = registrationRepository)
    }

    @Singleton
    @Provides
    fun provideLoginUseCase(
        loginRepository: LoginRepository
    ): LoginUseCase {
        return LoginUseCase(loginRepository = loginRepository)
    }

    @Singleton
    @Provides
    fun provideEmailValidatorUseCase(): EmailValidatorUseCase {
        return EmailValidatorUseCase()
    }

    @Singleton
    @Provides
    fun providePasswordValidatorUseCase(): PasswordValidatorUseCase {
        return PasswordValidatorUseCase()
    }

    @Singleton
    @Provides
    fun providePasswordReenterValidatorUseCase(): PasswordRepeatValidatorUseCase {
        return PasswordRepeatValidatorUseCase()
    }

    @Singleton
    @Provides
    fun providePaymentUseCase(): PaymentUseCase {
        return PaymentUseCase()
    }

    @Singleton
    @Provides
    fun provideSaveDataStoreUseCase(
        dataStoreRepository: DataStoreRepository
    ): SaveDataStoreUseCase {
        return SaveDataStoreUseCase(dataStoreRepository = dataStoreRepository)
    }

    @Singleton
    @Provides
    fun provideReadDataStoreUseCase(
        dataStoreRepository: DataStoreRepository
    ): ReadDataStoreUseCase {
        return ReadDataStoreUseCase(dataStoreRepository = dataStoreRepository)
    }

    @Singleton
    @Provides
    fun provideClearDataStoreUseCase(
        dataStoreRepository: DataStoreRepository
    ): ClearDataStoreUseCase {
        return ClearDataStoreUseCase(dataStoreRepository = dataStoreRepository)
    }

    @Singleton
    @Provides
    fun provideReadUserUidUseCase(
        dataStoreRepository: DataStoreRepository
    ): ReadUserUidUseCase {
        return ReadUserUidUseCase(dataStoreRepository = dataStoreRepository)
    }

}
