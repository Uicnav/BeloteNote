package com.ionvaranita.belotenote

import platform.UIKit.UIDevice
import platform.Foundation.*

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun Long.getFormattedDate(): String {
    val date = NSDate.dateWithTimeIntervalSince1970(this.toDouble())
    val formatter = NSDateFormatter()
    formatter.locale = NSLocale.autoupdatingCurrentLocale
    formatter.timeZone = NSTimeZone.systemTimeZone
    formatter.setLocalizedDateFormatFromTemplate("d MMMM yyyy HH:mm")
    return formatter.stringFromDate(date)
}