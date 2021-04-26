# brother-label-printer-kt

[![GitHub license](https://img.shields.io/github/license/omarmiatello/brother-label-printer-kt)](LICENSE)
[![](https://img.shields.io/maven-central/v/com.github.omarmiatello.brother-label-printer-kt/core)](https://search.maven.org/search?q=g:com.github.omarmiatello.brother-label-printer-kt)
![Build](https://github.com/omarmiatello/brother-label-printer-kt/workflows/Pre%20Merge%20Checks/badge.svg)

Kotlin extensions for "[Brother Print SDK for Android](https://support.brother.com/g/s/es/dev/en/mobilesdk/android/index.html?c=eu_ot&lang=en&navi=offall&comple=on&redirect=on)" (supported models MPring, PocketJet, and RJ/TD/QL/PT series) - Tested on Brother QL-820NWB

## Setup

Add this in your `build.gradle.ktx` file:
```kotlin
// **Version: Brother Print SDK for Android™ ver.3.5.1**
// Could be downloaded here: https://support.brother.com/g/s/es/dev/en/mobilesdk/android/index.html?c=eu_ot&lang=en&navi=offall&comple=on&redirect=on
implementation(project(":BrotherPrintLibrary"))

implementation("com.github.omarmiatello.brother-label-printer-kt:core:1.0.1")
```

## How to use

Note:
- Brother Mobile SDK support only one device at a time.
- `com.brother.ptouch.sdk.Printer`, internally use a lot of static methods (and yes, it's a bad practice)

Step 1) Initialize onCreate() of your Application/Activity
```kotlin
BrotherManager.init(context)
```

Step 2) Listen for a printer
```kotlin
// OPTION 1: this search for the first printer available
val printer: StateFlow<PrinterState> = BrotherManager.setDeviceOrSearch()

// OPTION 2: this search for a specific printer of use search as fallback if the printer is not available at start
val printer: StateFlow<PrinterState> = BrotherManager.setDeviceOrSearch(
    SearchNetPrinter(
        model = PrinterInfo.Model.QL_820NWB,
        ip = "192.168.86.182",
    )
)
```

Step 3) Print using a template
```kotlin
fun printTemplate(easyPrinter: EasyPrinter) {
    viewModelScope.launch {
        easyPrinter.printTemplate(
            templateId = 4,
            data = mapOf(
                "title" to "This is a test",
                "qr" to "My QR Code",
            ),
        )
    }
}
```

## Contributing 🤝

Feel free to open a issue or submit a pull request for any bugs/improvements.

## License

```
MIT License

Copyright (c) 2021 Omar Miatello

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```