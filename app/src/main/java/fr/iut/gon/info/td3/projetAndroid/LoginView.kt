package fr.iut.gon.info.td3.projetAndroid

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun LoginComponent(modifier: Modifier){
    val (email, emailSetter) = remember {
        mutableStateOf("")
    }
    val (password, passwordSetter) = remember {
        mutableStateOf("")
    }

    Form(
        modifier = modifier,
        email = email,
        password = password,
        onEmailChange = emailSetter,
        onPasswordChange = passwordSetter,
        onSubmit = {}
    )
}

@Composable
fun Form(
    modifier: Modifier,
    email: String ,
    password: String, 
    onEmailChange: (String) -> Unit, 
    onPasswordChange: (String) -> Unit, 
    onSubmit: () -> Unit)
{
    Column (modifier = modifier.wrapContentWidth()){
        Text(text = "Adresse email")
        TextField(value = email, onValueChange = onEmailChange)
        Text(text = "Mot de passe")
        TextField(
            value = password,
            onValueChange = onPasswordChange,
            visualTransformation = PasswordVisualTransformation()
        )
        OutlinedButton(onClick = onSubmit) {
            Text(text = "Log in")
        }
    }    
}