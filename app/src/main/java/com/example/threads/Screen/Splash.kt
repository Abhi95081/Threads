package com.example.threads.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.threads.Navigation.Routes
import com.example.threads.R
import kotlinx.coroutines.delay

@Composable
fun Splash(navController: NavHostController) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val image = createRef()
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.constrainAs(image) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }

    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigate(Routes.BottomNav.routes)
    }

    //Now the Fire Base is Begin.
}
