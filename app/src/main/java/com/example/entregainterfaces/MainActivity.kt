package com.example.entregainterfaces

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore.Audio.Radio
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.entregainterfaces.ui.theme.EntregaInterfacesTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EntregaInterfacesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    /*
                    Un botón con el texto 'Presionar' que, al hacer clic, actualizará el mensaje en el campo de texto
                    con 'Botón presionado' y mostrará un `CircularProgressIndicator` durante 5 segundos
                     */
                    //Ejercicio1()
                    //2. Un campo de texto que mostrará un mensaje, inicialmente no visible.
                    /*
                    Una casilla de verificación (checkbox) con el texto 'Activar' que, al marcarla, mostrará el Text
                    anterior
                     */
                    //Ejercicio3()
                    //4. Un icono de tu elección que se mostrará siempre en la interfaz.
                    //Ejercicio4()

                    //5. Un interruptor (switch) que mostrará en grupo de botones siguiente(punto 6).
                    //Ejercicio5()
                    //6.Un grupo de botones de radio (radiobutton) con al menos tres opciones distintas que permitirá al
                    //usuario seleccionar una opción y actualizar el mensaje del campo de texto en consecuencia
                    //(Ejercicio 6 dentro de Ejercicio())

                    //7. Una imagen que se actualizará al hacer clic en el botón. La imagen puede cambiar entre al menos
                    //tres imágenes diferentes.
                    //Ejercicio7()
                    Column(Modifier.fillMaxSize()){
                        Row(Modifier.fillMaxWidth().weight(.5f)){
                            //Un botón con el texto 'Presionar' que, al hacer clic, actualizará el mensaje en el campo de texto
                            //con 'Botón presionado' y mostrará un `CircularProgressIndicator` durante 5 segundos
                            Ejercicio1()
                        }
                        Row(Modifier.fillMaxWidth().weight(.25f)){
                            //2. Un campo de texto que mostrará un mensaje, inicialmente no visible.
                            //Una casilla de verificación (checkbox) con el texto 'Activar' que,
                            // al marcarla, mostrará el Text anterior
                            Ejercicio3()
                        }
                        Row(Modifier.fillMaxWidth().weight(1f)){
                            //Un icono de tu elección que se mostrará siempre en la interfaz
                            Ejercicio4()
                        }
                        Row(Modifier.fillMaxWidth().weight(2f)){
                            //6. Un grupo de botones de radio (radiobutton) con al menos tres opciones distintas que permitirá al
                            //usuario seleccionar una opción y actualizar el mensaje del campo de texto en consecuencia
                            //7. Una imagen que se actualizará al hacer clic en el botón. La imagen puede cambiar entre al menos
                            //tres imágenes diferentes.
                            Ejercicio7()
                        }
                        Row(Modifier.fillMaxWidth().weight(2f)){
                            //5. Un interruptor (switch) que mostrará en grupo de botones siguiente
                            //6. Un grupo de botones de radio (radiobutton) con al menos tres opciones
                            // distintas que permitirá al usuario seleccionar una opción y
                            // actualizar el mensaje del campo de texto en consecuencia
                            Ejercicio5()
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun Ejercicio1() {
    var buttonText by rememberSaveable { mutableStateOf("Presionar") }
    var showProgress by rememberSaveable { mutableStateOf(false) }

    Button(
        onClick = {
            buttonText = "Botón presionado"
            showProgress = true
            Handler(Looper.getMainLooper()).postDelayed({
                showProgress = false
            }, 5000)
        }
    ) {
        if (showProgress) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = Color.Red,
                strokeWidth = 2.dp
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = buttonText)
    }
}

@Composable
fun Ejercicio3(){
    var showButton by rememberSaveable { mutableStateOf(false) }
    Checkbox(checked = showButton, onCheckedChange = {showButton=!showButton})
    if(showButton){
        Text(text = "Mensaje")
    }
}

@Composable
fun Ejercicio4(){
    Image(painter = painterResource(id = R.drawable.nosfe),
        contentDescription = "Imagen")
}

@Composable
fun Ejercicio5() {
    var isSwitchOn by rememberSaveable { mutableStateOf(false) }
    var selectedOption by rememberSaveable { mutableStateOf(0) }
    var textFieldValue by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Mostrar opciones:")
            Switch(
                checked = isSwitchOn,
                onCheckedChange = { isChecked ->
                    isSwitchOn = isChecked
                    textFieldValue = ""
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isSwitchOn) {
            val options = listOf("Opción 1", "Opción 2", "Opción 3")

            Ejercicio6(
                selected = selectedOption,
                onClick = { index ->
                    selectedOption = index
                    textFieldValue = options[index]
                },
                options = options
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = textFieldValue)
        }
    }
}

@Composable
fun Ejercicio6(
    selected: Int,
    onClick: (Int) -> Unit,
    options: List<String>
) {
    Column {
        options.forEachIndexed { index, option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                RadioButton(
                    selected = selected == index,
                    onClick = { onClick(index) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = option)
            }
        }
    }
}

@Composable
fun Ejercicio7() {
    val images = listOf(
        painterResource(id = R.drawable.img1),
        painterResource(id = R.drawable.img2),
        painterResource(id = R.drawable.img3)
    )

    var currentImage by rememberSaveable { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = images[currentImage],
            contentDescription = "Imagen",
            modifier = Modifier
                .size(200.dp)
                .background(Color.Gray)
                .clickable {
                    currentImage = (currentImage + 1) % images.size
                }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            currentImage = (currentImage + 1) % images.size
        }) {
            Text(text = "Cambiar Imagen")
        }
    }
}