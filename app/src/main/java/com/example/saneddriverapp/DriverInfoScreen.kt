package com.example.saneddriverapp
import androidx.compose.foundation.clickable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.OutlinedButton
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.saneddriverapp.network.CountryViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.saneddriverapp.network.PersonalInfoDto
import com.example.saneddriverapp.network.RegistrationViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size

@Composable
fun SignUpStepper(
    currentStep: Int
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            StepCircle(
                selected = currentStep >= 1
            )

            Box(
                Modifier
                    .weight(1f)
                    .height(2.dp)
                    .background(Color.LightGray)
            )

            StepCircle(
                selected = currentStep >= 2
            )

            Box(
                Modifier
                    .weight(1f)
                    .height(2.dp)
                    .background(Color.LightGray)
            )

            StepCircle(
                selected = currentStep >= 3
            )
        }

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text("Account\nInformation")

            Text("Vehicle\nInformation")

            Text("Upload\nDocuments")
        }
    }
}

@Composable
private fun StepCircle(
    selected: Boolean
) {

    Box(
        modifier = Modifier
            .size(36.dp)
            .border(
                2.dp,
                if (selected) Color(0xFF00A86B)
                else Color.LightGray,
                CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {

        if (selected) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(
                        Color(0xFF00A86B),
                        CircleShape
                    )
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriverInfoScreen(
    navController: NavHostController,
    requestId: Long,
    idOrIqama: String,
    phoneNumber: String,
    registrationViewModel: RegistrationViewModel
){

    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val viewModel: CountryViewModel = viewModel()
    val countries by viewModel.countries.collectAsState()

    var countryExpanded by remember { mutableStateOf(false) }
    var cityExpanded by remember { mutableStateOf(false) }
    var nationalityExpanded by remember { mutableStateOf(false) }

    var selectedCountry by remember { mutableStateOf("") }






    val passwordRegex =
        Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@!#$%&]).{8,}$")

    val isPasswordValid = registrationViewModel.password.matches(passwordRegex)

    val dateRegex = Regex("^\\d{4}-\\d{2}-\\d{2}$")
    val isFullNameValid =
        registrationViewModel.fullName.trim().matches(
            Regex("^[A-Za-z]+\\s+[A-Za-z]+.*$")
        )
    val isUnder18 = try {
        if (dateRegex.matches(registrationViewModel.dateBirth)) {
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            formatter.isLenient = false

            val birthDate = formatter.parse(registrationViewModel.gender)
            val birthCalendar = Calendar.getInstance().apply {
                time = birthDate!!
            }

            val today = Calendar.getInstance()

            var age = today.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR)

            if (today.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
                age--
            }

            age < 18
        } else false
    } catch (e: Exception) {
        false
    }

    val isDateFormatValid = registrationViewModel.expiryDate.isBlank() || dateRegex.matches(registrationViewModel.expiryDate)

    val isExpired = try {
        if (dateRegex.matches(registrationViewModel.expiryDate)) {
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            formatter.isLenient = false

            val enteredDate = formatter.parse(registrationViewModel.expiryDate)
            enteredDate?.before(Date()) ?: false
        } else false
    } catch (e: Exception) {
        false
    }

    val isFormValid =
        registrationViewModel.fullName.isNotBlank() &&
                registrationViewModel.gender.isNotBlank() &&
                isFullNameValid &&
                registrationViewModel.nationality.isNotBlank() &&
                registrationViewModel.city.isNotBlank() &&
                !isUnder18 &&
                isDateFormatValid &&
                !isExpired &&
                registrationViewModel.expiryDate.isNotBlank() &&
                isPasswordValid &&
                registrationViewModel.confirmPassword.isNotBlank() &&
                registrationViewModel.password == registrationViewModel.confirmPassword


    val scrollState = rememberScrollState()
    val cities by viewModel.cities.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "←",
                fontSize = 40.sp,
                modifier = Modifier.clickable {
                    navController.popBackStack()
                }
            )

            Spacer(Modifier.weight(1f))

            Text(
                text = "Sign Up",
                fontSize = 24.sp
            )

            Spacer(Modifier.weight(1f))
        }

        Spacer(Modifier.height(24.dp))

        SignUpStepper(currentStep = 1)

        Spacer(Modifier.height(32.dp))
        Text(
            text = "ID/Iqama",
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = idOrIqama,
            onValueChange = {},
            readOnly = true,
            label = { Text("ID / Iqama") },
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        // ---------------- EXPIRY DATE ----------------
        val calendar = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, day ->
                registrationViewModel.expiryDate =
                    String.format("%04d-%02d-%02d", year, month + 1, day)            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        Text(
            text = "ID Expiry Date",
            modifier = Modifier.fillMaxWidth()
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = registrationViewModel.expiryDate,
                onValueChange = {},
                readOnly = true,
                textStyle = LocalTextStyle.current.copy(color = Color.Black),
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(
                        onClick = { datePickerDialog.show() }
                    ) {
                        Text("📅")
                    }
                }
            )
        }

        if (isExpired) {
            Text("ID is expired", color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(12.dp))

        // ---------------- FULL NAME ----------------
        Text(
            text = "Full Name",
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = registrationViewModel.fullName,
            onValueChange = {
                registrationViewModel.fullName =
                    it.filter { c -> c.isLetter() || c.isWhitespace() }            },
            label = { Text("Ex: Mohammed Abdullah") },
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            singleLine = true,

            modifier = Modifier.fillMaxWidth()
        )
        if (
            registrationViewModel.fullName.isNotEmpty() &&
            !isFullNameValid
        ) {
            Text(
                text = "Enter first name and last name",
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(Modifier.height(12.dp))
        // ---------------- GENDER ----------------
        Text(
            text = "Gender",
            modifier = Modifier.fillMaxWidth()

        )

        Row(Modifier.fillMaxWidth()) {

            Button(
                onClick = { registrationViewModel.gender = "Male" },
                modifier = Modifier.weight(1f),
                colors =
                    if (registrationViewModel.gender == "Male")
                        ButtonDefaults.buttonColors()
                    else
                        ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        )
            ) {
                Text("Male")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { registrationViewModel.gender = "Female" },
                modifier = Modifier.weight(1f),
                colors =
                    if (registrationViewModel.gender == "Female")
                        ButtonDefaults.buttonColors()
                    else
                        ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        )
            ) {
                Text("Female")
            }
        }

        Spacer(Modifier.height(12.dp))

        // ---------------- BIRTH DATE ----------------
        val birthPicker = DatePickerDialog(
            context,
            { _, year, month, day ->
                registrationViewModel.dateBirth = String.format("%04d-%02d-%02d", year, month + 1, day)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        Text(
            text = "Date of Birth",
            modifier = Modifier.fillMaxWidth()
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = registrationViewModel.dateBirth,
                onValueChange = {},
                readOnly = true,
                textStyle = LocalTextStyle.current.copy(color = Color.Black),
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(
                        onClick = { birthPicker.show() }

                    ) {
                        Text("📅")
                    }
                }
            )
        }

        if (isUnder18) {
            Text("Must be at least 18", color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(12.dp))

        // ---------------- NATIONALITY ----------------
        Text(
            text = "Nationality",
            modifier = Modifier.fillMaxWidth()
        )
        ExposedDropdownMenuBox(
            expanded = nationalityExpanded,
            onExpandedChange = { nationalityExpanded = !nationalityExpanded }
        ) {
            OutlinedTextField(
                value = registrationViewModel.nationality,
                onValueChange = {},
                readOnly = true,
                textStyle = LocalTextStyle.current.copy(color = Color.Black),
                label = { Text("Choose Nationality") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(nationalityExpanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = nationalityExpanded,
                onDismissRequest = { nationalityExpanded = false }
            ) {
                countries.forEach { country ->
                    DropdownMenuItem(
                        text = { Text(country.countryName ?: "") },
                        onClick = {
                            registrationViewModel.nationality = country.countryName ?: ""
                            viewModel.loadCities(country.countryCode ?: "")
                            nationalityExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        // ---------------- CITY (placeholder) ----------------
        Text(
            text = "City",
            modifier = Modifier.fillMaxWidth()
        )

        ExposedDropdownMenuBox(
            expanded = cityExpanded,
            onExpandedChange = {
                if (registrationViewModel.nationality.isNotBlank()) {
                    cityExpanded = !cityExpanded
                }
            }
        ) {
            OutlinedTextField(
                value = registrationViewModel.city,
                onValueChange = {},
                readOnly = true,
                textStyle = LocalTextStyle.current.copy(color = Color.Black),
                label = { Text("City") },
                placeholder = {
                    if (registrationViewModel.nationality.isBlank()) {
                        Text("Select nationality first")
                    }
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(cityExpanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = cityExpanded,
                onDismissRequest = {
                    cityExpanded = false
                }
            ) {
                cities.forEach { city ->
                    DropdownMenuItem(
                        text = {
                            Text(city.cityName ?: "")
                        },
                        onClick = {
                            registrationViewModel.city = city.cityName ?: ""
                            cityExpanded = false
                        }
                    )
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        Text(
            text = "Password",
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = registrationViewModel.password,
            onValueChange = { registrationViewModel.password = it },
            label = { Text("Password") },
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),

            visualTransformation =
                if (passwordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector =
                            if (passwordVisible) Icons.Default.Visibility
                            else Icons.Default.VisibilityOff,
                        contentDescription = null
                    )
                }
            }
        )

        if (registrationViewModel.password.isNotEmpty() && !isPasswordValid) {
            Text(
                "Password must contain:\n•At least 8 charachters\n• At least 1 uppercase letter [A-Z]\n• At least 1 lowercase letter [a-z]\n• At least 1 number [0-9]\n• At least 1 Symbol [@,!,#,$,%,&]",
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // CONFIRM PASSWORD
        Text(
            text = "Confirm Password",
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = registrationViewModel.confirmPassword,
            onValueChange = { registrationViewModel.confirmPassword = it },
            label = { Text("Confirm Password") },
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation =
                if (passwordVisible) VisualTransformation.None
                else PasswordVisualTransformation()
        )

        if (registrationViewModel.confirmPassword.isNotEmpty() && registrationViewModel.password != registrationViewModel.confirmPassword) {
            Text("Passwords do not match", color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(300.dp))

        Button(
            onClick = {

                registrationViewModel.personalInfo =
                    PersonalInfoDto(
                        idNumber = idOrIqama,
                        idExpiryDate = registrationViewModel.expiryDate,
                        fullName = registrationViewModel.fullName,
                        gender = registrationViewModel.gender,
                        dob = registrationViewModel.dateBirth,
                        nationality = registrationViewModel.nationality,
                        city = registrationViewModel.city,
                        password = registrationViewModel.password,
                        mobile = phoneNumber,
                        country = registrationViewModel.nationality
                    )

                navController.navigate("vehicle_info/$requestId")
            },
            enabled = isFormValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continue")
        }
        Spacer(Modifier.height(40.dp))

    }


}