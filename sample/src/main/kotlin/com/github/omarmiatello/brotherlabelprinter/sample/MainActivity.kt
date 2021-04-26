package com.github.omarmiatello.brotherlabelprinter.sample

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.omarmiatello.brotherlabelprinter.core.BrotherManager
import com.github.omarmiatello.brotherlabelprinter.core.EasyPrinter
import com.github.omarmiatello.brotherlabelprinter.core.NotAvailable
import com.github.omarmiatello.brotherlabelprinter.sample.theme.SampleTheme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    val vm by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        BrotherManager.init(this)

        setContent {
            SampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    SampleLayout()
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SampleLayout() {
    val vm = viewModel<MainViewModel>()
    Column(
            modifier = Modifier.padding(16.dp)
    ) {
        val printer by vm.printer.collectAsState()
//        println("asd: $printer")
        Text(text = "$printer")
        AnimatedVisibility(printer is EasyPrinter) {
            Button(onClick = { vm.printTemplate(printer as EasyPrinter) }) {
                Text(text = "Print")
            }
        }
    }
}
