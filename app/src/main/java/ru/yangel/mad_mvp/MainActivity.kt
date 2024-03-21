package ru.yangel.mad_mvp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint
import ru.yangel.domain.usecase.ConvertPhoneNumberUseCase
import ru.yangel.domain.usecase.ValidatePhoneNumberUseCase
import ru.yangel.mad_mvp.ui.presenter.PhoneNumberPresenter
import ru.yangel.mad_mvp.ui.theme.MAD_MVPTheme

import ru.yangel.mad_mvp.ui.view.PhoneNumberView
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var presenter: PhoneNumberPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ContentScreen(presenter)
        }
    }

}

@Composable
fun ContentScreen(presenter: PhoneNumberPresenter) {

    val focusManager = LocalFocusManager.current
    var phoneNumber by remember { mutableStateOf("") }
    var isPhoneNumberValid by remember { mutableStateOf(false) }
    var convertedPhoneNumber by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = phoneNumber,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Black,
                focusedBorderColor = Color.Black,
                errorBorderColor = Color.Red,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
            ),

            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            singleLine = true,
            maxLines = 1,
            onValueChange = {
                phoneNumber = it
            },
            label = { Text(text = "PhoneNumber") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        AnimatedVisibility(visible = showDialog) {
            val message = when (isPhoneNumberValid) {
                true -> convertedPhoneNumber
                false -> stringResource(id = R.string.invalid_phone_format)
            }
            val messageColor = when (isPhoneNumberValid) {
                true -> Color.Black
                false -> Color.Red
            }
            Text(
                text = message,
                color = messageColor,
                fontSize = 16.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
        Button(
            onClick = {
                presenter.onConvertedPhoneNumberChanged(phoneNumber)
                showDialog = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
            )
        ) {
            Text(text = "Convert PhoneNumber", modifier = Modifier.padding(8.dp), fontSize = 14.sp)
        }

        DisposableEffect(presenter) {
            presenter.view = object : PhoneNumberView {

                override fun showValidationError() {
                    isPhoneNumberValid = false
                }

                override fun showConvertedPhoneNumber(phoneNumber: String) {
                    convertedPhoneNumber = phoneNumber
                    isPhoneNumberValid = true
                }


                override fun setConvertedPhoneNumber(phoneNumber: String) {
                    convertedPhoneNumber = phoneNumber
                }
            }

            onDispose {
                presenter.view = null
            }
        }
    }
}

