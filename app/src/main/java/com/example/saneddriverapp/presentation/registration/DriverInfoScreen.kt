package com.example.saneddriverapp.presentation.registration
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.saneddriverapp.presentation.country.CountryViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import com.example.saneddriverapp.presentation.city.CityViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.saneddriverapp.domain.model.PersonalInfo
import android.provider.Settings
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
    val uiState by registrationViewModel
        .uiState
        .collectAsState()

    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val countryViewModel: CountryViewModel = hiltViewModel()
    val cityViewModel: CityViewModel = hiltViewModel()
    val countries by countryViewModel
        .countries
        .collectAsStateWithLifecycle()
    var cityExpanded by remember { mutableStateOf(false) }
    var nationalityExpanded by remember { mutableStateOf(false) }





    val deviceId =
        Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        ) ?: "unknown"

    val passwordRegex =
        Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@!#$%&]).{8,}$")

    val isPasswordValid = uiState.password.matches(passwordRegex)

    val dateRegex = Regex("^\\d{4}-\\d{2}-\\d{2}$")
    val isFullNameValid =
        uiState.fullName.trim().matches(
            Regex("^[A-Za-z]+\\s+[A-Za-z]+.*$")
        )
    val isUnder18 = try {
        if (dateRegex.matches(uiState.dateBirth)) {
            val formatter =
                SimpleDateFormat(
                    "yyyy-MM-dd",
                    Locale.getDefault()
                )

            formatter.isLenient = false

            val birthDate = formatter.parse(uiState.dateBirth)
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

    val isDateFormatValid = uiState.expiryDate.isBlank() || dateRegex.matches(uiState.expiryDate)

    val isExpired = try {
        if (dateRegex.matches(uiState.expiryDate)) {
            val formatter =
                SimpleDateFormat(
                    "yyyy-MM-dd",
                    Locale.getDefault()
                )
            formatter.isLenient = false

            val enteredDate = formatter.parse(uiState.expiryDate)
            enteredDate?.before(Date()) ?: false
        } else false
    } catch (e: Exception) {
        false
    }

    val isFormValid = uiState.fullName.isNotBlank() &&
            uiState.gender.isNotBlank() &&
                isFullNameValid &&
                uiState.nationality.isNotBlank() &&
                uiState.city.isNotBlank() &&
                !isUnder18 &&
                isDateFormatValid &&
                !isExpired &&
            uiState.expiryDate.isNotBlank() &&
                isPasswordValid &&
            uiState.confirmPassword.isNotBlank() &&
            uiState.password == uiState.confirmPassword


    val scrollState = rememberScrollState()
    val cities by cityViewModel
        .cities
        .collectAsStateWithLifecycle()
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
                registrationViewModel.updateExpiryDate(
                    String.format(
                        "%04d-%02d-%02d",
                        year,
                        month + 1,
                        day
                    )
                )    },
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
                value = uiState.expiryDate,
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
            value = uiState.fullName,
            onValueChange = {
                registrationViewModel.updateFullName(
                    it.filter { c ->
                        c.isLetter() || c.isWhitespace()
                    }
                )            },
            label = { Text("Ex: Mohammed Abdullah") },
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            singleLine = true,

            modifier = Modifier.fillMaxWidth()
        )
        if (
            uiState.fullName.isNotEmpty() &&
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
                onClick =  { registrationViewModel.updateGender("Male") },
                modifier = Modifier.weight(1f),
                colors =
                    if (uiState.gender == "Male")
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
                onClick = { registrationViewModel.updateGender("Female") },
                modifier = Modifier.weight(1f),
                colors =
                    if (uiState.gender == "Female")
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
                registrationViewModel.updateDateBirth(
                    String.format(
                        "%04d-%02d-%02d",
                        year,
                        month + 1,
                        day
                    )
                )            },
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
                value = uiState.dateBirth,
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
                value = uiState.nationality,
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
                        text = { Text(country.name) },
                        onClick = {
                            registrationViewModel.updateNationality(
                                country.name
                            )

                            cityViewModel.loadCities(
                                country.code
                            )
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
                if (uiState.nationality.isNotBlank()) {
                    cityExpanded = !cityExpanded
                }
            }
        ) {
            OutlinedTextField(
                value = uiState.city,
                onValueChange = {},
                readOnly = true,
                textStyle = LocalTextStyle.current.copy(color = Color.Black),
                label = { Text("City") },
                placeholder = {
                    if (uiState.nationality.isBlank()) {
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
                            Text(city.name)                        },
                        onClick = {
                            registrationViewModel.updateCity(
                                city.name
                            )
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
            value = uiState.password,
            onValueChange = {
                registrationViewModel.updatePassword(it) },
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

        if (uiState.password.isNotEmpty() && !isPasswordValid) {
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
            value = uiState.confirmPassword,
            onValueChange = {
                registrationViewModel.updateConfirmPassword(it) },
            label = { Text("Confirm Password") },
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation =
                if (passwordVisible) VisualTransformation.None
                else PasswordVisualTransformation()
        )

        if (uiState.confirmPassword.isNotEmpty() && uiState.password != uiState.confirmPassword) {
            Text("Passwords do not match", color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(300.dp))

        Button(
            onClick = {

                registrationViewModel.personalInfo =
                    PersonalInfo(
                        idNumber = idOrIqama,
                        idExpiryDate = uiState.expiryDate,
                        fullName = uiState.fullName.trim(),
                        gender = uiState.gender,
                        dob = uiState.dateBirth,
                        nationality = uiState.nationality,
                        city = uiState.city,
                        password = uiState.password.trim(),
                        imei = "356938035643809",
                        mobile = phoneNumber,
                        country = uiState.nationality

                    )
                navController.navigate(
                    "vehicle_info/$requestId"
                )
            },
            enabled = isFormValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continue")
        }
        Spacer(Modifier.height(40.dp))

    }


}