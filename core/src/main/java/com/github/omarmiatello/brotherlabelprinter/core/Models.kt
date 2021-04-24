package com.github.omarmiatello.brotherlabelprinter.core

import com.brother.ptouch.sdk.Printer
import com.brother.ptouch.sdk.PrinterInfo
import com.brother.ptouch.sdk.PrinterStatus

public sealed class PrinterState

public object Searching : PrinterState()

public object NotAvailable : PrinterState()

public data class SearchNetPrinter(
    val model: PrinterInfo.Model,
    val ip: String,
) : PrinterState()

public data class EasyPrinter(val printer: Printer) : PrinterState() {

    /**
     * https://support.brother.com/g/s/es/htmldoc/mobilesdk/guide/print-template.html#print-template
     */
    public suspend fun printTemplate(
        templateId: Int,
        data: Map<String, String>,
        encode: String? = null,
    ): PrinterStatus = printTemplateBy(templateId, encode) {
        data.forEach { (k, v) -> printer.replaceTextName(v, k) }
    }

    /**
     * https://support.brother.com/g/s/es/htmldoc/mobilesdk/guide/print-template.html#print-template
     */
    public suspend fun printTemplate(
        templateId: Int,
        texts: List<String>,
        encode: String? = null,
    ): PrinterStatus = printTemplateBy(templateId, encode) {
        texts.forEach { printer.replaceText(it) }
    }

    /**
     * https://support.brother.com/g/s/es/htmldoc/mobilesdk/guide/print-template.html#print-template
     */
    public suspend fun printTemplateByIndex(
        templateId: Int,
        map: Map<Int, String>,
        encode: String? = null,
    ): PrinterStatus = printTemplateBy(templateId, encode) {
        map.forEach { (k, v) -> printer.replaceTextIndex(v, k) }
    }

    public suspend fun printTemplateBy(
        templateId: Int,
        encode: String? = null,
        params: (Printer) -> Unit,
    ): PrinterStatus = printer.communication {
        if (printer.startPTTPrint(templateId, encode)) {
            params(printer)
            val result: PrinterStatus = printer.flushPTTPrint()
            if (result.errorCode != PrinterInfo.ErrorCode.ERROR_NONE) {
                error(result.errorCode)
            }
        }
    }
}
