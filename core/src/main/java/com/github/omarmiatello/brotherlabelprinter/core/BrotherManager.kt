package com.github.omarmiatello.brotherlabelprinter.core

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.brother.ptouch.sdk.Printer
import com.brother.ptouch.sdk.PrinterInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds

/**
 * Brother Mobile SDK support only one device at a time.
 * [com.brother.ptouch.sdk.Printer], internally use a lot of static methods (and yes, it's a bad practice)
 */
@OptIn(ExperimentalTime::class)
public object BrotherManager {
    private var pref: SharedPreferences? = null
    private var workPath: String = ""

    private val netSearch = MutableStateFlow(false)
    private val current = MutableStateFlow<PrinterState>(NotAvailable)

    public fun init(context: Context) {
        workPath = context.filesDir.absolutePath
        pref = PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Suppress("MagicNumber")
    public val netPrinters: StateFlow<Set<SearchNetPrinter>> = flow<Set<SearchNetPrinter>> {
        val set = mutableSetOf<SearchNetPrinter>()
        while (true) {
            netSearch.value = true
            set += Printer().getNetPrinters("").map {
                SearchNetPrinter(
                    model = it.modelName.toBrotherModel(),
                    ip = it.ipAddress,
                )
            }

            emit(set)

            netSearch.value = false
            delay((if (set.size > 0) 5000 else 100).milliseconds)
        }
    }.flowOn(Dispatchers.IO).stateIn(GlobalScope, SharingStarted.WhileSubscribed(), emptySet())

    public val printer: StateFlow<PrinterState> =
        combine(current, netSearch) { current, netSearch ->
            when (current) {
                is EasyPrinter,
                is SearchNetPrinter -> current
                NotAvailable -> if (netSearch) Searching else current
                Searching -> TODO()
            }
        }.stateIn(GlobalScope, SharingStarted.WhileSubscribed(), NotAvailable)

    public suspend fun netPrinterTakeFirst(): Set<SearchNetPrinter> = netPrinters.first {
        it.isNotEmpty() && setDevice(it.random()) != NotAvailable
    }

    public suspend fun setDevice(model: String, ip: String): PrinterState =
        setDevice(model.toBrotherModel(), ip)

    public suspend fun setDevice(model: PrinterInfo.Model, ip: String): PrinterState =
        setDevice(SearchNetPrinter(model, ip))

    public suspend fun setDevice(searchNetPrinter: SearchNetPrinter): PrinterState =
        withContext(Dispatchers.IO) {
            current.value = searchNetPrinter
            Printer().apply {
                printerInfo = printerInfo.also {
                    it.printerModel = searchNetPrinter.model
                    it.port = PrinterInfo.Port.NET
                    it.ipAddress = searchNetPrinter.ip
                    it.workPath = workPath
                    it.printQuality = PrinterInfo.PrintQuality.HIGH_RESOLUTION
                }
            }.takeIf { !it.firmVersion.isNullOrEmpty() }
                .let { if (it != null) EasyPrinter(it) else NotAvailable }
                .also {
                    current.value = it
                    pref?.putLast(searchNetPrinter)
                }
        }

    public fun setDeviceOrSearch(netPrinter: SearchNetPrinter? = null): StateFlow<PrinterState> =
        printer.onSubscription {
            coroutineScope {
                launch {
                    val printer = netPrinter ?: pref?.getLastSearchNetPrinter()
                    println(printer)
                    if (printer == null || setDevice(printer) == NotAvailable) {
                        netPrinterTakeFirst()
                    }
                }
            }
        }.stateIn(GlobalScope, SharingStarted.WhileSubscribed(), NotAvailable)

    private fun SharedPreferences.putLast(searchNetPrinter: SearchNetPrinter) {
        edit()
            .putString("brother_model", searchNetPrinter.model.formattedName)
            .putString("brother_ip", searchNetPrinter.ip)
            .apply()
    }

    @Suppress("TooGenericExceptionCaught", "SwallowedException")
    private fun SharedPreferences.getLastSearchNetPrinter(): SearchNetPrinter? {
        val ip = getString("brother_ip", null)
        val model = try {
            getString("brother_model", null)?.toBrotherModel()
        } catch (e: Exception) {
            null
        }
        return if (ip != null && model != null) {
            SearchNetPrinter(model, ip)
        } else null
    }
}
