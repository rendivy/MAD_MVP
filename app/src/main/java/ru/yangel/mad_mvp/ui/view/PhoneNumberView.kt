package ru.yangel.mad_mvp.ui.view



interface PhoneNumberView {
    fun showValidationError()
    fun showConvertedPhoneNumber(phoneNumber: String)
    fun setConvertedPhoneNumber(phoneNumber: String)
}


