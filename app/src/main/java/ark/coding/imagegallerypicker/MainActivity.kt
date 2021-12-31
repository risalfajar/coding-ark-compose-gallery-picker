package ark.coding.imagegallerypicker

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ark.coding.imagegallerypicker.ui.theme.ImageGalleryPickerTheme
import coil.compose.rememberImagePainter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageGalleryPickerTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    var selectedImage by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
        selectedImage = uri
    }

    MainContent(selectedImage) {
        launcher.launch("image/jpeg")
    }
}

@Composable
private fun MainContent(
    selectedImage: Uri? = null,
    onImageClick: () -> Unit
) {
    Scaffold() {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (selectedImage != null)
                Image(
                    painter = rememberImagePainter(selectedImage),
                    contentDescription = "Selected image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            onImageClick()
                        })
            else
                OutlinedButton(onClick = onImageClick) {
                    Text(text = "Choose Image")
                }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ImageGalleryPickerTheme() {
        MainContent() {
        }
    }
}

@Preview
@Composable
private fun PreviewSelected() {
    ImageGalleryPickerTheme() {
        MainContent(Uri.EMPTY) {

        }
    }
}