package com.example.dmr3a_feedback1.ui.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.dmr3a_feedback1.DataBase.Novel
import com.example.dmr3a_feedback1.DataBase.NovelDatabase


@Composable
fun AddReviewScreen(novel: Novel, novelDatabase: NovelDatabase, onReviewAdded: () -> Unit, onBackToDetails: () -> Unit) {
    var reviewText by remember { mutableStateOf("") }
    var usuario by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    var showDialog by remember { mutableStateOf(false) }






    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {


        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text("Tu nombre") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            )

        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = reviewText,
            onValueChange = { reviewText = it },
            label = { Text("Escribe tu reseña") },
            modifier = Modifier
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (reviewText.isBlank() || usuario.isBlank()) { // Validar ambos campos
                    showDialog = true
                } else {
                    novelDatabase.addReview(novel, reviewText, usuario)
                    onReviewAdded()
                }
            }
        )  {
            Text("Guardar reseña")
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Error") },
                text = { Text("Ningún campo puede estar vacío") },
                confirmButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("OK")
                    }
                }
            )
        }

    }
}