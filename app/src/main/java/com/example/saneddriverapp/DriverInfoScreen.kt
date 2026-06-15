package com.example.saneddriverapp

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.material3.MaterialTheme
import android.app.DatePickerDialog
import java.util.Calendar
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import android.util.Patterns
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField

@Composable
fun DriverInfoScreen(
    navController: NavHostController,
    idOrIqama: String
) {
    var gender by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var nationalId by remember { mutableStateOf("") }
    var licenseNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var dateBirth by remember { mutableStateOf("") }

    val dateRegex =
        Regex("^\\d{2}/\\d{2}/\\d{4}$")
    val isUnder18 = try {
        if (dateRegex.matches(dateBirth)) {

            val formatter =
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

            formatter.isLenient = false

            val birthDate = formatter.parse(dateBirth)

            val birthCalendar = Calendar.getInstance()
            birthCalendar.time = birthDate!!

            val today = Calendar.getInstance()

            var age =
                today.get(Calendar.YEAR) -
                        birthCalendar.get(Calendar.YEAR)

            if (
                today.get(Calendar.DAY_OF_YEAR) <
                birthCalendar.get(Calendar.DAY_OF_YEAR)
            ) {
                age--
            }

            age < 18

        } else {
            false
        }
    } catch (e: Exception) {
        false
    }

    val isDateFormatValid =
        expiryDate.isBlank() || dateRegex.matches(expiryDate)

    val isExpired = try {
        if (dateRegex.matches(expiryDate)) {
            val formatter =
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

            formatter.isLenient = false

            val enteredDate =
                formatter.parse(expiryDate)

            enteredDate?.before(Date()) ?: false
        } else {
            false
        }
    } catch (e: Exception) {
        false
    }
     val isFormValid =
        fullName.isNotBlank() &&
                gender.isNotBlank() &&
                !isUnder18 &&
                nationalId.length >= 10 &&
                licenseNumber.isNotBlank() &&
                isDateFormatValid &&
                !isExpired &&
                expiryDate.isNotBlank()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Received ID: $idOrIqama")
        Text("Driver Information", fontSize = 28.sp)

        Spacer(modifier = Modifier.height(24.dp))

        Text("ID or Iqama", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = idOrIqama,
            onValueChange = {},
            readOnly = true,
            label = { Text("ID / Iqama") },
            modifier = Modifier.fillMaxWidth(),
            )

        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            navController.context,
            { _, year, month, dayOfMonth ->
                expiryDate =
                    String.format(
                        "%02d/%02d/%04d",
                        dayOfMonth,
                        month + 1,
                        year
                    )            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        val birthDatePickerDialog = DatePickerDialog(
            navController.context,
            { _, year, month, dayOfMonth ->
                dateBirth =
                    String.format(
                        "%02d/%02d/%04d",
                        dayOfMonth,
                        month + 1,
                        year
                    )
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = expiryDate,
                onValueChange = {
                    expiryDate = it
                },
                label = { Text("DD/MM/YYYY") },
                modifier = Modifier.weight(1f),
                isError = expiryDate.isNotEmpty() && !isDateFormatValid,
                singleLine = true
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    datePickerDialog.show()
                }
            ) {
                Text("📅")
            }
        }

        if (!isDateFormatValid) {
            Text(
                text = "Date must be in DD/MM/YYYY format",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            )
        }

        if (isExpired) {
            Text(
                text = "ID / Iqama is expired",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = fullName,
            onValueChange = {
                fullName = it.filter { c ->
                    c.isLetter() || c.isWhitespace()
                }
            },
            label = { Text("Ex: Mohammed Abdullah") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Gender",
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Button(
                onClick = { gender = "Male" },
                modifier = Modifier.weight(1f),
                colors =
                    if (gender == "Male")
                        ButtonDefaults.buttonColors()
                    else
                        ButtonDefaults.outlinedButtonColors()
            ) {
                Text("Male")
            }

            Button(
                onClick = { gender = "Female" },
                modifier = Modifier.weight(1f),
                colors =
                    if (gender == "Female")
                        ButtonDefaults.buttonColors()
                    else
                        ButtonDefaults.outlinedButtonColors()
            ) {
                Text("Female")
            }
        }
        Row(
                verticalAlignment = Alignment.CenterVertically
                ) {
            TextField(
                value = dateBirth,
                onValueChange = {
                    dateBirth = it
                },
                label = { Text("DD/MM/YYYY") },
                modifier = Modifier.weight(1f),
                isError = dateBirth.isNotEmpty() && !isDateFormatValid,
                singleLine = true
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    birthDatePickerDialog.show()
                }
            ) {
                Text("📅")
            }
        }
        if (isUnder18) {
            Text(
                text = "Driver must be at least 18 years old",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate("nextStep") // later we define
            },
            enabled = isFormValid
        ) {
            Text("Continue")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Text("Back")
        }
    }
}