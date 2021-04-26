package com.github.omarmiatello.brotherlabelprinter.sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brother.ptouch.sdk.PrinterInfo
import com.github.omarmiatello.brotherlabelprinter.core.BrotherManager
import com.github.omarmiatello.brotherlabelprinter.core.EasyPrinter
import com.github.omarmiatello.brotherlabelprinter.core.PrinterState
import com.github.omarmiatello.brotherlabelprinter.core.SearchNetPrinter
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val printer: StateFlow<PrinterState> = BrotherManager.setDeviceOrSearch()

    fun printTemplate(easyPrinter: EasyPrinter) {
        viewModelScope.launch {
            easyPrinter.printTemplate(
                templateId = 5,
                data = mapOf(
                    "qr" to "ABC",
                    "title" to "My title",
                ),
            )
        }
    }
}
