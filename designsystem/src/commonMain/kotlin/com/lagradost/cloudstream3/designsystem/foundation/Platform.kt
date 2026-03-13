package com.lagradost.cloudstream3.designsystem.foundation

enum class PlatformType {
    Android, IOS, Desktop
}

enum class FormFactor {
    Phone, Tablet, TV
}

expect val currentPlatform: PlatformType

expect fun isTelevision(): Boolean
