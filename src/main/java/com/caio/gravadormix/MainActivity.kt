package com.caio.gravadormix

import android.media.MediaRecorder
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.caio.gravadormix.ui.theme.GravadorMixTheme
import com.caio.gravadormix.ui.MainScreen
import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import java.io.File
import android.media.MediaPlayer


class MainActivity : ComponentActivity() {

    private var recorder: MediaRecorder? = null
    private var fileName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GravadorMixTheme {
                MainScreen(
                    onStart = { startRecording() },
                    onStop = { stopRecording() },
                    onShowSaved = { showSavedAudios(this) }
                )
            }
        }
    }

    private fun playAudio(file: File) {
        try {
            val mediaPlayer = MediaPlayer().apply {
                setDataSource(file.absolutePath)
                prepare()
                start()
            }

            mediaPlayer.setOnCompletionListener {
                it.release()
                Toast.makeText(this, "Reprodução finalizada", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Erro ao reproduzir áudio", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showSavedAudios(context: Context) {
        val dir = getExternalFilesDir(null)
        val files = dir?.listFiles() ?: emptyArray()

        AlertDialog.Builder(context)
            .setTitle("Áudios Salvos")
            .setItems(files.map { it.name }.toTypedArray()) { _, which ->
                // Ao clicar, reproduz o áudio selecionado
                playAudio(files[which])
            }
            .setPositiveButton("Fechar", null)
            .show()
    }

    private fun startRecording() {
        fileName = getExternalFilesDir(null)?.absolutePath +
                "/record_" + System.currentTimeMillis() + ".m4a"
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setOutputFile(fileName)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            prepare()
            start()
        }
    }

    private fun stopRecording() {
        try {
            recorder?.apply {
                stop()
                release()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            recorder = null
        }
    }



@Composable
fun RecorderUI(
    onStart: () -> Unit,
    onStop: () -> Unit,
    onShowSaved: () -> Unit,   // <-- novo callback
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFBBDEFB))
    ) {
        // Título no topo
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Gravador Mix",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        // Botões no rodapé
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Botão Áudios Salvos
            Button(
                onClick = { onShowSaved() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2196F3), // azul
                    contentColor = Color.White
                )
            ) {
                Text("📂 Áudios Salvos", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botão Iniciar Gravação
            Button(
                onClick = { onStart() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50), // verde
                    contentColor = Color.White
                )
            ) {
                Text("🎙️ Iniciar Gravação", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botão Parar Gravação
            Button(
                onClick = { onStop() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF44336), // vermelho
                    contentColor = Color.White
                )
            ) {
                Text("⏹️ Parar Gravação", fontSize = 18.sp)
            }
        }
    }
} }