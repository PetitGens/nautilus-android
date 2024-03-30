package fr.iut.gon.info.td3.projetAndroid

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class EditDive {
    @Composable
    fun DiveForm(onSubmit: (DiveData) -> Unit) {
        var api =  ""

        var date by remember { mutableStateOf("") }
        var time by remember { mutableStateOf("") }
        var location by remember { mutableStateOf("") }
        var diveLevel by remember { mutableStateOf("") }
        var minPeople by remember { mutableStateOf("") }
        var maxPeople by remember { mutableStateOf("") }
        var boat by remember { mutableStateOf("") }
        var requiredLevel by remember { mutableStateOf("") }
        var surfaceSecurity by remember { mutableStateOf("") }
        var director by remember { mutableStateOf("") }
        var pilot by remember { mutableStateOf("") }
        var surfaceSecurityName by remember { mutableStateOf("") }
        var directorName by remember { mutableStateOf("") }
        var pilotName by remember { mutableStateOf("") }

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    OutlinedTextField(
                        value = date,
                        onValueChange = { date = it },
                        label = { Text("Date") },
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    OutlinedTextField(
                        value = location,
                        onValueChange = { location = it },
                        label = { Text("Lieu") },
                        modifier = Modifier.weight(1f)
                    )


                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ){
                    OutlinedTextField(
                        value = time,
                        onValueChange = { time = it },
                        label = { Text("Heure") },
                    )
                }




                OutlinedTextField(
                    value = diveLevel,
                    onValueChange = { diveLevel = it },
                    label = { Text("Niveau de plongée") }
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = minPeople,
                        onValueChange = { minPeople = it },
                        label = { Text("Nombre min. de personnes") },
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    OutlinedTextField(
                        value = maxPeople,
                        onValueChange = { maxPeople = it },
                        label = { Text("Nombre max. de personnes") },
                        modifier = Modifier.weight(1f)
                    )
                }

                OutlinedTextField(
                    value = boat,
                    onValueChange = { boat = it },
                    label = { Text("Nom du bateau") }
                )

                OutlinedTextField(
                    value = requiredLevel,
                    onValueChange = { requiredLevel = it },
                    label = { Text("Niveau requis") }
                )

                OutlinedTextField(
                    value = surfaceSecurity,
                    onValueChange = { surfaceSecurity = it },
                    label = { Text("Sécurité de surface") }
                )

                OutlinedTextField(
                    value = director,
                    onValueChange = { director = it },
                    label = { Text("Directeur") }
                )

                OutlinedTextField(
                    value = pilot,
                    onValueChange = { pilot = it },
                    label = { Text("Pilote") }
                )

                OutlinedTextField(
                    value = surfaceSecurityName,
                    onValueChange = { surfaceSecurityName = it },
                    label = { Text("Sécurité de surface") }
                )

                OutlinedTextField(
                    value = directorName,
                    onValueChange = { directorName = it },
                    label = { Text("Nom du directeur") }
                )

                OutlinedTextField(
                    value = pilotName,
                    onValueChange = { pilotName = it },
                    label = { Text("Nom du pilote") }
                )

                Button(
                    onClick = {
                        val diveData = DiveData(
                            date = date,
                            time = time,
                            location = location,
                            diveLevel = diveLevel,
                            minPeople = minPeople.toIntOrNull() ?: 0,
                            maxPeople = maxPeople.toIntOrNull() ?: 0,
                            boat = boat,
                            requiredLevel = requiredLevel,
                            surfaceSecurity = surfaceSecurity,
                            director = director,
                            pilot = pilot,
                            surfaceSecurityName = surfaceSecurityName,
                            directorName = directorName,
                            pilotName = pilotName
                        )
                        onSubmit(diveData)
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Soumettre")
                }
            }
        }
    }

    data class DiveData(
        val date: String,
        val time: String,
        val location: String,
        val diveLevel: String,
        val minPeople: Int,
        val maxPeople: Int,
        val boat: String,
        val requiredLevel: String,
        val surfaceSecurity: String,
        val director: String,
        val pilot: String,
        val surfaceSecurityName: String,
        val directorName: String,
        val pilotName: String
    )

    @Preview
    @Composable
    fun PreviewDiveForm() {
        DiveForm(onSubmit = { /* Handle submit action */ })
    }
}

