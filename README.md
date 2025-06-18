Belote Note project
I started this project to help people who play the card game "Belote".
My project is a utility app where Belote players save their points.
The idea comes in summer in Moldova, where my friends and I wanted to play Belote, but we didn't have paper and a pencil to store the points.
So I decided to create an app to substitute paper and pencil, in this way we can play Belote every time, everywhere, until there is an Android phone that runs my app.

Belote can be played in different countries:

•	Quebec: Bœuf

•	Bulgaria: Бридж-белот, Bridge-Belote

•	Greece: Βίδα, Vida; Μπουρλότ, Bourlot

•	Cyprus: Πιλόττα, Pilotta

•	Croatia: Bela

•	Macedonia: Бељот, Beljot

•	Armenia: Բազար բլոտ, Bazaar Belote

•	Saudi Arabia: بلوت, Baloot

•	Russia: Белот, Belot

•	Tunisia: Belote

•	Moldavia: Belote

•	Madagascar: Tsiroanomandidy or Beloty

My app will support many languages:

•	French

•	Italian

•	Romanian

•	Bulgarian

•	Greek

•	Turkish

•	Albanian

•	Serbian

•	Armenian

•	Arabic

•	Russian

•	English (default language)

This is a Kotlin Multiplatform project targeting Android, iOS.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…# BeloteNote
# BeloteNote
# BeloteNote
