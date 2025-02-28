package com.example.threads.Screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.threads.Navigation.Routes
import com.example.threads.R

@Composable
fun Register(navHostController: NavHostController) {

    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

 // image
    var imageuri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {

        uri: Uri? ->
        imageuri = uri
        //1:09:50
    }
    val permissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()){

        isGrenter : Boolean ->
            if(isGrenter){


            }else{

            }

    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Register Here",
            style = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
        )

        Spacer(modifier = Modifier.height(30.dp))
        
        Image(painter = painterResource(id = R.drawable.person) , contentDescription = "person",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.Gray)
                .clickable {

                    //Write code


                }, contentScale = ContentScale.Crop)

        Spacer(modifier = Modifier.height(50.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text = "Name") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(text = "User Name") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = bio,
            onValueChange = { bio = it },
            label = { Text(text = "Bio..") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        ElevatedButton(
            onClick = { /* Handle registration */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Register here",
                style = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 20.sp),
                modifier = Modifier.padding(vertical = 6.dp)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
        TextButton(
            onClick = {
                navHostController.navigate(Routes.Login.routes) {
                    popUpTo(navHostController.graph.startDestinationId) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        ) {
            Text(
                text = "Already Registered? Login here",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
            )
        }
    }
}
