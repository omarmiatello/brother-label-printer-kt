# BrotherPrintLibrary.aar

Could be downloaded here: https://support.brother.com/g/s/es/dev/en/mobilesdk/android/index.html?c=eu_ot&lang=en&navi=offall&comple=on&redirect=on

**Version: Brother Print SDK for Android™ ver.3.5.1**

The application development tool for Android™ (Brother Print SDK for Android™) introduced here is a development tool compatible with Android™.

The Brother Print SDK for Android™ is a tool to enable printing from your Android™ to our products of MPring, PocketJet, and RJ/TD/QL/PT series.
You can incorporate a print function into an application you already use that allows printing from Brother products.

## Changelog

https://support.brother.com/g/s/es/htmldoc/mobilesdk/about/release-notes-android.html

### 3.5.1
Added Supported Printer
- PT-P910BT

API Enhancements or Changes
- Compatible with Android 10 (Android Q)
  getExternalStorageDirectory is deprecated in API level 29. Brother Print SDK had internally used it until 3.4.0. See also workPath.
- Added an error code ERROR_WORKPATH_NOT_SET into ErrorCode
- Fixed a process to save a PRN file.
  - Automatic status notification mode is always turned off (Do not notify) when savePrnPath is set.