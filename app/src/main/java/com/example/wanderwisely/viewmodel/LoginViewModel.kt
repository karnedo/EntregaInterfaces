package com.example.wanderwisely.viewmodel

import android.provider.Settings.Global.getString
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.wanderwisely.R
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

class LoginViewModel(private val navController: NavHostController) : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth

    private val _email = MutableLiveData<String>();
    val email: LiveData<String> = _email;

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun onLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _loginEnable.value = isValidEmail(email) && isValidPassword(password)
    }

    private fun isValidPassword(password: String): Boolean = password.length > 5

    //Patters de Android.util
    private fun isValidEmail(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    suspend fun onLoginSelected() {
        Log.i("MyApp", "Logging in...")

        //Go to the main screen
        navController.navigate("MainScreen")
    }

    suspend fun onSignupSelected() {
        Log.i("MyApp", "Singing up...")
    }

    fun signIn(email: String, password: String)
    = viewModelScope.launch {
        try{
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        navController.navigate("MainScreen")
                    }else{
                        _errorMessage.value = "Error al iniciar sesión: ${task.exception?.message}"
                    }
                }
        }catch(ex:Exception){
            _errorMessage.value = "Error al iniciar sesión: ${ex.message}"
        }
    }

    fun signUpWithEmail(email: String, password: String){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            task -> if(task.isSuccessful){
                navController.navigate("MainScreen")
            }else{
                _errorMessage.value = "Error al registrarse: ${task.exception?.message}"
            }
        }
    }

    fun clearError(){
        _errorMessage.value = ""
    }

}

