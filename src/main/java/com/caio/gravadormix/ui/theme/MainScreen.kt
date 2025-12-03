package com.caio.gravadormix.ui

import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.platform.LocalContext
import com.caio.gravadormix.ui.RecorderUI
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun MainScreen(
    onStart: () -> Unit,
    onStop: () -> Unit,
    onShowSaved: () -> Unit)   {
    val context = LocalContext.current

    var drawerOpen by remember { mutableStateOf(false) }

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "Configurações",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    style = MaterialTheme.typography.titleMedium
                )
                Divider()

                // Opções de Configuração
                Text(
                    text = "Qualidade do áudio",
                    modifier = Modifier.padding(16.dp)
                )
                Text("Alta", modifier = Modifier.padding(start = 32.dp))
                Text("Padrão", modifier = Modifier.padding(start = 32.dp))
                Text("Baixa", modifier = Modifier.padding(start = 32.dp))

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Formato de gravação",
                    modifier = Modifier.padding(16.dp)
                )
                Text("M4A", modifier = Modifier.padding(start = 32.dp))
                Text("WAV", modifier = Modifier.padding(start = 32.dp))
                Text("AAC", modifier = Modifier.padding(start = 32.dp))

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Manter tela ligada",
                    modifier = Modifier.padding(16.dp)
                )
            }
        },
        drawerState = rememberDrawerState(
            if (drawerOpen) DrawerValue.Open else DrawerValue.Closed
        )
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Gravador Mix") },
                    navigationIcon = {
                        IconButton(onClick = { drawerOpen = !drawerOpen }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { padding ->
            RecorderUI(
                onStart = onStart,
                onStop = onStop,
                onShowSaved = onShowSaved,
                modifier = Modifier.padding(padding)
            )
        }
    }
}