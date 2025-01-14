package com.mahriqz.mywebapp

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.addCallback
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.mahriqz.mywebapp.ui.theme.MyWebAppTheme

class MainActivity : ComponentActivity() {
    private var webView: WebView? = null // Referencia al WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Manejar el botón "Regresar"
        onBackPressedDispatcher.addCallback(this) {
            if (webView?.canGoBack() == true) {
                webView?.goBack()
            } else {
                finish() // Cierra la aplicación si no hay historial
            }
        }

        setContent {
            MyWebAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WebViewScreen(
                        url = "http://52.191.130.170:8000/", // Reemplaza con tu dirección IP
                        modifier = Modifier.padding(innerPadding),
                        onWebViewCreated = { view ->
                            webView = view // Guarda la referencia al WebView
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun WebViewScreen(
    url: String,
    modifier: Modifier = Modifier,
    onWebViewCreated: (WebView) -> Unit
) {
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    useWideViewPort = true
                    loadWithOverviewMode = true
                    // Deshabilitar zoom
                    setSupportZoom(false)
                    builtInZoomControls = false
                    displayZoomControls = false
                    cacheMode = android.webkit.WebSettings.LOAD_DEFAULT
                    layoutAlgorithm = android.webkit.WebSettings.LayoutAlgorithm.NORMAL
                }
                loadUrl(url)
                onWebViewCreated(this) // Pasa la referencia al WebView
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun WebViewPreview() {
    MyWebAppTheme {
        WebViewScreen(
            url = "http://example.com",
            onWebViewCreated = {}
        )
    }
}
