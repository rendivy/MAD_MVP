package ru.yangel.mad_mvp.ui.presenter

import ru.yangel.domain.usecase.ConvertPhoneNumberUseCase
import ru.yangel.domain.usecase.ValidatePhoneNumberUseCase
import ru.yangel.mad_mvp.ui.view.PhoneNumberView
import javax.inject.Inject


class PhoneNumberPresenter @Inject constructor(
    private val validatePhoneNumberUseCase: ValidatePhoneNumberUseCase,
    private val convertPhoneNumberUseCase: ConvertPhoneNumberUseCase,
) {
    var view: PhoneNumberView? = null

    fun onConvertedPhoneNumberChanged(phoneNumber: String) {
        val validationResult = validatePhoneNumberUseCase(phoneNumber)
        if (validationResult) {
            val convertedPhoneNumber = convertPhoneNumberUseCase(phoneNumber)
            view?.setConvertedPhoneNumber(convertedPhoneNumber)
            view?.showConvertedPhoneNumber(convertedPhoneNumber)
        } else {
            view?.showValidationError()
        }
    }
}