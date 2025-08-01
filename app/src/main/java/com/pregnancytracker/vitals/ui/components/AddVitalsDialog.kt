package com.pregnancytracker.vitals.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddVitalsDialog(
    onDismiss: () -> Unit,
    onSave: (Int, Int, Int, Double, Int, String) -> Unit
) {
    var systolicBP by remember { mutableStateOf("") }
    var diastolicBP by remember { mutableStateOf("") }
    var heartRate by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var babyKicks by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    
    var systolicError by remember { mutableStateOf(false) }
    var diastolicError by remember { mutableStateOf(false) }
    var heartRateError by remember { mutableStateOf(false) }
    var weightError by remember { mutableStateOf(false) }
    var babyKicksError by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Add Vitals Entry",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                // Blood Pressure Section
                Text(
                    text = "Blood Pressure",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = systolicBP,
                        onValueChange = { 
                            systolicBP = it
                            systolicError = false
                        },
                        label = { Text("Systolic") },
                        placeholder = { Text("120") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = systolicError,
                        modifier = Modifier.weight(1f)
                    )
                    
                    OutlinedTextField(
                        value = diastolicBP,
                        onValueChange = { 
                            diastolicBP = it
                            diastolicError = false
                        },
                        label = { Text("Diastolic") },
                        placeholder = { Text("80") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = diastolicError,
                        modifier = Modifier.weight(1f)
                    )
                }

                OutlinedTextField(
                    value = heartRate,
                    onValueChange = { 
                        heartRate = it
                        heartRateError = false
                    },
                    label = { Text("Heart Rate (BPM)") },
                    placeholder = { Text("72") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = heartRateError,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = weight,
                    onValueChange = { 
                        weight = it
                        weightError = false
                    },
                    label = { Text("Weight (kg)") },
                    placeholder = { Text("65.5") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    isError = weightError,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = babyKicks,
                    onValueChange = { 
                        babyKicks = it
                        babyKicksError = false
                    },
                    label = { Text("Baby Kicks Count") },
                    placeholder = { Text("10") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = babyKicksError,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes (Optional)") },
                    placeholder = { Text("Any additional notes...") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2,
                    maxLines = 4
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Button(
                        onClick = {
                            // Validate inputs
                            val systolicInt = systolicBP.toIntOrNull()
                            val diastolicInt = diastolicBP.toIntOrNull()
                            val heartRateInt = heartRate.toIntOrNull()
                            val weightDouble = weight.toDoubleOrNull()
                            val babyKicksInt = babyKicks.toIntOrNull()
                            
                            systolicError = systolicInt == null || systolicInt <= 0
                            diastolicError = diastolicInt == null || diastolicInt <= 0
                            heartRateError = heartRateInt == null || heartRateInt <= 0
                            weightError = weightDouble == null || weightDouble <= 0
                            babyKicksError = babyKicksInt == null || babyKicksInt < 0
                            
                            if (!systolicError && !diastolicError && !heartRateError && 
                                !weightError && !babyKicksError) {
                                onSave(
                                    systolicInt!!,
                                    diastolicInt!!,
                                    heartRateInt!!,
                                    weightDouble!!,
                                    babyKicksInt!!,
                                    notes
                                )
                            }
                        }
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}
