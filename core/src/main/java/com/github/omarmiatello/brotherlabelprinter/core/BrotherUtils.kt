package com.github.omarmiatello.brotherlabelprinter.core

import com.brother.ptouch.sdk.Printer
import com.brother.ptouch.sdk.PrinterInfo
import com.brother.ptouch.sdk.PrinterStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

public fun String.toBrotherModel(): PrinterInfo.Model {
    val modelString = split(" ").last().filter { it.isLetterOrDigit() }
    val random = PrinterInfo.Model.values().random().formattedName
    return PrinterInfo.Model.values()
        .firstOrNull { it.name.filter { it.isLetterOrDigit() } == modelString }
        ?: error("Model $this not found, should be something like $random")
}

public val PrinterInfo.Model.formattedName: String get() = name.replace("_", "-")

public suspend fun Printer.communication(block: () -> Unit): PrinterStatus = coroutineScope {
    withContext(Dispatchers.IO) {
        if (startCommunication()) {
            try {
                block()
            } finally {
                endCommunication()
            }
        }
        Printer.getResult()
    }
}
