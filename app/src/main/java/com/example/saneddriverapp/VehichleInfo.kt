package com.example.saneddriverapp


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import android.app.DatePickerDialog
import com.example.saneddriverapp.network.VehicleInfoDto
import androidx.compose.ui.graphics.Color
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.clickable
import androidx.compose.material3.LocalTextStyle
import com.example.saneddriverapp.network.RegistrationViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehichleInfo(
    navController: NavHostController,
    requestId: Long,
    registrationViewModel: RegistrationViewModel
) {


    val viewModel: CountryViewModel = viewModel()
    val context = LocalContext.current

    var vehicleModelExpanded by remember { mutableStateOf(false) }





    val englishPlateRegex =
        Regex("^\\d{4}[A-Z]{3}$")
    val dateRegex = Regex("^\\d{4}-\\d{2}-\\d{2}$")

    val isRegistrationDateValid =
        registrationViewModel.vehicleForm.vehicleRegistrationExpiryDate.isBlank() ||
                dateRegex.matches(registrationViewModel.vehicleForm.vehicleRegistrationExpiryDate)

    val isLicenseDateValid =
        registrationViewModel.vehicleForm.driverLicenseExpiryDate.isBlank() ||
                dateRegex.matches(registrationViewModel.vehicleForm.driverLicenseExpiryDate)

    val isRegistrationExpired = try {
        if (dateRegex.matches(registrationViewModel.vehicleForm.vehicleRegistrationExpiryDate)) {
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            formatter.isLenient = false
            val enteredDate = formatter.parse(registrationViewModel.vehicleForm.vehicleRegistrationExpiryDate)
            enteredDate?.before(Date()) ?: false
        } else false
    } catch (e: Exception) {
        false
    }

    val isLicenseExpired = try {
        if (dateRegex.matches(registrationViewModel.vehicleForm.driverLicenseExpiryDate)) {
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            formatter.isLenient = false
            val enteredDate = formatter.parse(registrationViewModel.vehicleForm.driverLicenseExpiryDate)
            enteredDate?.before(Date()) ?: false
        } else false
    } catch (e: Exception) {
        false
    }


    LaunchedEffect(Unit) {
        viewModel.loadVehicleModels()
        viewModel.loadVehicleTypes()
    }
    val vehicleTypes by viewModel.vehicleTypes.collectAsState()
    val isFormValid =
        registrationViewModel.vehicleForm.vehicleType.isNotBlank() &&
                registrationViewModel.vehicleForm.vehicleModel.isNotBlank() &&
                registrationViewModel.vehicleForm.vehicleName.isNotBlank() &&
                registrationViewModel.vehicleForm.vehicleSequenceNumber.length == 9 &&
                englishPlateRegex.matches(registrationViewModel.vehicleForm.vehicleNumberPlateEn) &&
                registrationViewModel.vehicleForm.vehicleRegistrationExpiryDate.isNotBlank() &&
                registrationViewModel.vehicleForm.driverLicenseExpiryDate.isNotBlank() &&
                isRegistrationDateValid &&
                isLicenseDateValid &&
                !isRegistrationExpired &&
                !isLicenseExpired


    val scrollState = rememberScrollState()

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

        SignUpStepper(currentStep = 2)

        Spacer(Modifier.height(32.dp))

        var vehicleTypeExpanded by remember { mutableStateOf(false) }
        var selectedVehicleType by remember { mutableStateOf("") }
        var selectedVehicleTypeId by remember { mutableStateOf<Long?>(null) }
        Text(
            text = "Vehicle Type",
            modifier = Modifier.fillMaxWidth()
        )
        ExposedDropdownMenuBox(
            expanded = vehicleTypeExpanded,
            onExpandedChange = { vehicleTypeExpanded = !vehicleTypeExpanded }
        ) {

            OutlinedTextField(
                value = registrationViewModel.vehicleForm.vehicleType,
                onValueChange = {},
                readOnly = true,
                textStyle = LocalTextStyle.current.copy(color = Color.Black),
                label = { Text("Choose Vehicle Type") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(vehicleTypeExpanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = vehicleTypeExpanded,
                onDismissRequest = { vehicleTypeExpanded = false }
            ) {

                vehicleTypes.forEach { type ->

                    DropdownMenuItem(
                        text = {
                            Text((type.name ?: "").trim())
                        },
                        onClick = {

                            selectedVehicleType = (type.name ?: "").trim()
                            selectedVehicleTypeId = type.id

                            registrationViewModel.vehicleForm.vehicleType = selectedVehicleType

                            vehicleTypeExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        // ---------------- Vehicle model ----------------
        val vehicleModels by viewModel.vehicleModels.collectAsState()

        Text(
            text = "Vehicle Model",
            modifier = Modifier.fillMaxWidth()
        )

        ExposedDropdownMenuBox(
            expanded = vehicleModelExpanded,
            onExpandedChange = { vehicleModelExpanded = !vehicleModelExpanded }
        ) {
            OutlinedTextField(
                value = registrationViewModel.vehicleForm.vehicleModel,
                onValueChange = {},
                readOnly = true,
                textStyle = LocalTextStyle.current.copy(color = Color.Black),
                label = { Text("Choose Vehicle Model") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = vehicleModelExpanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = vehicleModelExpanded,
                onDismissRequest = { vehicleModelExpanded = false }
            ) {
                vehicleModels.forEach { model ->
                    DropdownMenuItem(
                        text = {
                            Text(model.modelName.orEmpty())
                        },
                        onClick = {
                            registrationViewModel.vehicleForm.vehicleModel = model.modelName.orEmpty()
                            vehicleModelExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))


        // ---------------- EXPIRY DATE ----------------
        val calendar = Calendar.getInstance()

        val registrationDatePicker = DatePickerDialog(
            context,
            { _, year, month, day ->
                registrationViewModel.vehicleForm.vehicleRegistrationExpiryDate =
                    String.format("%04d-%02d-%02d", year, month + 1, day)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        val licenseDatePicker = DatePickerDialog(
            context,
            { _, year, month, day ->
                registrationViewModel.vehicleForm.driverLicenseExpiryDate =
                    String.format("%04d-%02d-%02d", year, month + 1, day)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )












        // ---------------- vehicle NAME ----------------
        Text(
            text = "Vehicle name",
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value =  registrationViewModel.vehicleForm.vehicleName,
            onValueChange = {
                registrationViewModel.vehicleForm.vehicleName = it
            },
            label = { Text("Ex;Camrey") },
            singleLine = true,

            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = "Sequence Number",
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = registrationViewModel.vehicleForm.vehicleSequenceNumber,
            onValueChange = {
                registrationViewModel.vehicleForm.vehicleSequenceNumber =
                    it.filter { c -> c.isDigit() }
                        .take(9)
            },
            label = { Text("Ex:123456789") },
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,

            modifier = Modifier.fillMaxWidth()
        )

        if (
            registrationViewModel.vehicleForm.vehicleSequenceNumber.isNotEmpty() &&
            registrationViewModel.vehicleForm.vehicleSequenceNumber.length != 9
        ) {
            Text(
                "Sequence number must be 9 digits",
                color = MaterialTheme.colorScheme.error
            )
        }
        Spacer(Modifier.height(12.dp))

        // ---------------- plate Number EN ----------------

        Text(
            text = "Vehicle numberPlate (EN)",
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = registrationViewModel.vehicleForm.vehicleNumberPlateEn,
            onValueChange = {
                registrationViewModel.vehicleForm.vehicleNumberPlateEn =
                    it.uppercase().take(7)
            },
            label = {
                Text("Ex: 1234ABC")

            }, textStyle = LocalTextStyle.current.copy(color = Color.Black),
            singleLine = true,


            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))

        // ---------------- Plate Number AR (placeholder) ----------------
        Text(
            text = "Vehicle numberPlate (AR)",
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = registrationViewModel.vehicleForm.vehicleNumberPlateAr,
            onValueChange = {
                registrationViewModel.vehicleForm.vehicleNumberPlateAr = it.take(7)
            },
            label = {
                Text("Ex: 1234أبج")
            }, textStyle = LocalTextStyle.current.copy(color = Color.Black),
            singleLine = true,


            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))


        // ---------------- Registration Expiry Date ----------------

        Text(
            text = "Vehicle registration expire date",
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = registrationViewModel.vehicleForm.vehicleRegistrationExpiryDate,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(color = Color.Black),
                trailingIcon = {
                    androidx.compose.material3.IconButton(
                        onClick = { registrationDatePicker.show() }
                    ) {
                        Text("📅")
                    }
                }
            )
        }
            if (isRegistrationExpired) {
                Text(
                    "Vehicle registration is expired",
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(Modifier.height(12.dp))

            Text(
                text = "Driving license expire date",
                modifier = Modifier.fillMaxWidth()
            )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
                value = registrationViewModel.vehicleForm.driverLicenseExpiryDate,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(color = Color.Black),
                trailingIcon = {
                    androidx.compose.material3.IconButton(
                        onClick = { licenseDatePicker.show() }
                    ) {
                        Text("📅")
                    }
                }
            )
            if (isLicenseExpired) {
                Text(
                    "Driving license is expired",
                    color = MaterialTheme.colorScheme.error
                )
            }
        Spacer(Modifier.height(200.dp))

        Button(
                onClick = {

                    registrationViewModel.vehicleInfo =
                        VehicleInfoDto(
                            vehicleType = registrationViewModel.vehicleForm.vehicleType,
                            vehicleModel = registrationViewModel.vehicleForm.vehicleModel,
                            vehicleName =  registrationViewModel.vehicleForm.vehicleName,
                            vehicleSequenceNumber = registrationViewModel.vehicleForm.vehicleSequenceNumber,
                            vehicleNumberPlateEn = registrationViewModel.vehicleForm.vehicleNumberPlateEn,
                            vehicleNumberPlateAr = registrationViewModel.vehicleForm.vehicleNumberPlateAr,
                            vehicleRegistrationExpiryDate = registrationViewModel.vehicleForm.vehicleRegistrationExpiryDate,
                            driverLicenseExpiryDate = registrationViewModel.vehicleForm.driverLicenseExpiryDate
                        )

                    navController.navigate("uploadDocs/$requestId")
                },
                enabled = isFormValid,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Continue")
            }
            Spacer(Modifier.height(40.dp))


        }
    }
