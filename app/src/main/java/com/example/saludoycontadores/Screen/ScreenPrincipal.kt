package com.example.saludoycontadores.Screen


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun Principal(){
    var aceptar by rememberSaveable { mutableStateOf(0) }
    var cancelar by rememberSaveable { mutableStateOf(0) }
    var textoSaludo by rememberSaveable { mutableStateOf("") }
    var show by rememberSaveable { mutableStateOf(false) }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { show = true},
            Modifier.padding(bottom = 10.dp))
        {
            Text(text = "Saludar")
        }
        Text(text = textoSaludo,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .border(2.dp, Color.Black)
                .height(50.dp)
                .width(250.dp)
                .wrapContentHeight(Alignment.CenterVertically))
    }

    if (show){
        Dialogo(show = show,
            aceptar = aceptar,
            cancelar = cancelar,
            sumar = {
                if (aceptar == it ){
                    aceptar++
                }else if(cancelar == it){
                    cancelar++
                }
            },
            onDismiss = {show = true},
            onDismissOn = {show = false},
            onChange = { textoSaludo = it },
            onChangeName = { if (textoSaludo!=""){
                textoSaludo = "Hola $textoSaludo"
            }
            },
            onChangeClear = { textoSaludo = "" },
            textoSaludo = textoSaludo)
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dialogo(show : Boolean,
            aceptar : Int,
            cancelar : Int,
            sumar : (Int) -> Unit,
            onDismiss : (Boolean) -> Unit,
            onDismissOn : (Boolean) -> Unit,
            onChange: (String) -> Unit,
            onChangeName : (String) -> Unit,
            onChangeClear : (String) -> Unit,
            textoSaludo: String){
    Dialog(onDismissRequest = { onDismiss(show)},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false)) {
        Column(modifier = Modifier
            .background(Color.White)
            .padding(top = 15.dp, bottom = 20.dp)
            .fillMaxWidth()
            .height(200.dp),
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Text(text = "Configuración",
                modifier = Modifier
                    .padding(bottom = 15.dp, start = 185.dp),
                color = Color.Black,
                fontSize = 20.sp)

            OutlinedTextField(
                value = textoSaludo,
                onValueChange = { onChange(it)},
                label = { Text(text = "Introduce tu nombre")},
                )

            Row(Modifier.padding(top = 30.dp))
            {
                Button(onClick = { onDismissOn(show)
                    onChangeName(textoSaludo)
                    sumar(aceptar)},
                    Modifier.padding(end = 15.dp)) {
                    Text(text = "A$aceptar")
                }
                Button(onClick = { onChangeClear(textoSaludo) },
                    Modifier.padding(end = 15.dp)) {
                    Text(text = "Limpiar")
                }
                Button(onClick = { onDismissOn(show)
                    sumar(cancelar)}) {
                    Text(text = "C$cancelar")
                }
            }
        }
    }
}