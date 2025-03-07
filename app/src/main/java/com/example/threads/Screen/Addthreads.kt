package com.example.threads.Screen

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.threads.Navigation.Routes
import com.example.threads.R.drawable
import com.example.threads.utils.Sharedpref
import com.example.threads.viewmodel.AddthreadViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.ui.res.painterResource as painterResource1

@Composable
fun AddThreads(navHostController: NavHostController) {
    val context = LocalContext.current
    var thread by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val threadViewModel: AddthreadViewModel = viewModel()
    val isPosted by threadViewModel.isPosted.observeAsState(false)

    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
    val userName = remember { Sharedpref.getUserName(context) }
    val userImage = remember { Sharedpref.getImage(context) }

    val permissionToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        imageUri = uri
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {
                Toast.makeText(context, "Permission required to select image", Toast.LENGTH_SHORT).show()
            }
        }

    fun requestStoragePermission() {
        val isGranted = ContextCompat.checkSelfPermission(context, permissionToRequest) == PackageManager.PERMISSION_GRANTED
        if (isGranted) {
            launcher.launch("image/*")
        } else {
            permissionLauncher.launch(permissionToRequest)
        }
    }

    LaunchedEffect(isPosted) {
        if (isPosted == true) {
            thread = ""
            imageUri = null
            Toast.makeText(context, "Thread Added", Toast.LENGTH_SHORT).show()

            navHostController.navigate(Routes.Home.routes) {
                popUpTo(Routes.AddThreads.routes) { inclusive = true }
            }
        }
    }

    ConstraintLayout(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        val (crossPic, text, logo, userNameText, editText, attachMedia, replyText, button, imageBox) = createRefs()

        Image(
            painter = painterResource1(id = drawable.baseline_close_24),
            contentDescription = "Close",
            modifier = Modifier.constrainAs(crossPic) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }.clickable {
                navHostController.navigate(Routes.Home.routes) {
                    popUpTo(Routes.AddThreads.routes) { inclusive = true }
                }
            }
        )

        Text(
            text = "Add Thread",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.ExtraBold),
            modifier = Modifier.constrainAs(text) {
                top.linkTo(crossPic.top)
                start.linkTo(crossPic.end, margin = 12.dp)
                bottom.linkTo(crossPic.bottom)
            }
        )

        Image(
            painter = rememberAsyncImagePainter(model = userImage),
            contentDescription = "Profile Image",
            modifier = Modifier.constrainAs(logo) {
                top.linkTo(text.bottom)
                start.linkTo(parent.start)
            }.size(36.dp).clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Text(
            text = userName,
            style = TextStyle(fontSize = 20.sp),
            modifier = Modifier.constrainAs(userNameText) {
                top.linkTo(logo.top)
                start.linkTo(logo.end, margin = 12.dp)
                bottom.linkTo(logo.bottom)
            }
        )

        BasicTextFieldWithHint(
            hint = "Start a thread ...",
            value = thread,
            onValueChange = { thread = it },
            modifier = Modifier.constrainAs(editText) {
                top.linkTo(userNameText.bottom)
                start.linkTo(userNameText.start)
                end.linkTo(parent.end)
            }.padding(8.dp).fillMaxWidth()
        )

        if (imageUri == null) {
            Image(
                painter = painterResource1(id = drawable.attachmedia),
                contentDescription = "Attach Image",
                modifier = Modifier.constrainAs(attachMedia) {
                    top.linkTo(editText.bottom)
                    start.linkTo(editText.start)
                }.clickable { requestStoragePermission() }
            )
        } else {
            Box(
                modifier = Modifier
                    .background(Color.Gray)
                    .padding(1.dp)
                    .constrainAs(imageBox) {
                        top.linkTo(editText.bottom)
                        start.linkTo(editText.start)
                        end.linkTo(parent.end)
                    }
                    .height(250.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = imageUri),
                    contentDescription = "Selected Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remove Image",
                    modifier = Modifier.align(Alignment.TopEnd).clickable {
                        imageUri = null
                    }
                )
            }
        }

        Text(
            text = "Anyone Can Reply",
            style = TextStyle(fontSize = 20.sp),
            modifier = Modifier.constrainAs(replyText) {
                start.linkTo(parent.start, margin = 12.dp)
                bottom.linkTo(parent.bottom, margin = 12.dp)
            }
        )

        TextButton(
            onClick = {
                if (thread.isNotBlank()) {
                    if (imageUri == null) {
                        threadViewModel.saveData(thread, userId, "")
                    } else {
                        threadViewModel.saveImage(thread, userId, imageUri!!)
                    }
                } else {
                    Toast.makeText(context, "Thread cannot be empty", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.constrainAs(button) {
                end.linkTo(parent.end, margin = 12.dp)
                bottom.linkTo(parent.bottom, margin = 12.dp)
            }
        ) {
            Text(text = "Post", style = TextStyle(fontSize = 20.sp))
        }
    }
}

@Composable
fun BasicTextFieldWithHint(hint: String, value: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        if (value.isEmpty()) {
            Text(text = hint, color = Color.Gray)
        }
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle.Default.copy(color = Color.Black),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
