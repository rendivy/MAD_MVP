package ru.yangel.mad_mvp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.yangel.domain.usecase.ConvertPhoneNumberUseCase
import ru.yangel.domain.usecase.ValidatePhoneNumberUseCase
import ru.yangel.mad_mvp.ui.presenter.PhoneNumberPresenter

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideValidatePhoneNumberUseCase(): ValidatePhoneNumberUseCase =
        ValidatePhoneNumberUseCase()

    @Provides
    fun provideConvertPhoneNumberUseCase(): ConvertPhoneNumberUseCase =
        ConvertPhoneNumberUseCase()


    @Provides
    fun providePresenter(
        validatePhoneNumberUseCase: ValidatePhoneNumberUseCase,
        convertPhoneNumberUseCase: ConvertPhoneNumberUseCase
    ): PhoneNumberPresenter = PhoneNumberPresenter(validatePhoneNumberUseCase, convertPhoneNumberUseCase)


}