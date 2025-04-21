package com.myapp.chickifier.presentation

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapp.chickifier.R
import com.myapp.chickifier.data.messageList
import com.myapp.chickifier.ui.theme.Pink40
import com.myapp.chickifier.ui.theme.darkBlue
import com.myapp.chickifier.ui.theme.orange

@Composable
fun HomeScreen(modifier: Modifier) {

    var text by remember { mutableStateOf("") }
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val chickIcon = "🐣"
    val bunnyIcon = "🐰"

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(darkBlue)
            .padding(horizontal = 10.dp)
            .padding(top = 40.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
    ) {

        Text(
            text = "Easter Messages Hub",
            fontSize = 24.sp,
            color = orange,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(Font(R.font.nunitoextrab))
        )
        Spacer(modifier = Modifier.height(30.dp))

        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
            },
            placeholder = {
                Text(
                    text = "Type something to Chickify!",
                    fontSize = 14.sp,
                    color = Color.White.copy(0.7f),
                    fontFamily = FontFamily(Font(R.font.nunitoitalic))
                )
            },
            modifier = Modifier.fillMaxWidth()
                .border(1.dp, Color.Transparent),
            colors = TextFieldDefaults.colors(
                focusedLabelColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedLabelColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = Color.White,
            ),
            shape = RoundedCornerShape(24.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        if(text.isNotEmpty()) {
            Button(
                onClick = {
                    if (text.isNotEmpty()) {
                        val copiedText = "$chickIcon$bunnyIcon $text $bunnyIcon$chickIcon "
                        val clip = ClipData.newPlainText("EasterMessage", copiedText)
                        clipboard.setPrimaryClip(clip)
                    }
                    Toast.makeText(context, "$text : Copied!", Toast.LENGTH_SHORT).show()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = orange
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = "Copy", fontSize = 14.sp,
                    modifier = Modifier
                        .padding(4.dp),
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        //message list
        Text(
            text = "Try using sample messages",
            fontSize = 14.sp,
            modifier = Modifier
                .padding(10.dp),
            fontWeight = FontWeight.Bold,
            color = Color.White.copy(0.7f)
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(10.dp)
        ) {
            items(messageList){message->
                ListItem(message){
                    val copiedMessage = "$chickIcon$bunnyIcon $message $bunnyIcon$chickIcon "
                    val clipMessage = ClipData.newPlainText("CopiedMessage", copiedMessage)
                    clipboard.setPrimaryClip(clipMessage)

                    Toast.makeText(context, "$message : Copied!", Toast.LENGTH_SHORT).show()
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

    }
}

@Composable
fun ListItem(message: String, onItemClicked: ()-> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Pink40.copy(0.2f), RoundedCornerShape(12.dp))
            .padding(10.dp)
            .clickable { onItemClicked() },
        horizontalArrangement = Arrangement.Start

    ) {
        Icon(
            painter = painterResource(R.drawable.chics),
            contentDescription = "easter bunny",
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )

        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = message,
            fontSize = 14.sp,
            color = orange.copy(0.4f),
            fontFamily = FontFamily(Font(R.font.nunitoextrab))
        )
        Spacer(modifier = Modifier.width(10.dp))
        Icon(
            painter = painterResource(R.drawable.bunny),
            contentDescription = "easter bunny",
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )
    }
}



@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(modifier = Modifier)
}

@Preview(showBackground = true)
@Composable
fun ListItemPreview() {
    ListItem("hi there"){}
}