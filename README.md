# clean-MVVM
Android application using TMDB Api with clean architecture principles, MVVM, Hilt DI, Kotlin, Coroutines, Kotlin Flow, LiveData, Room and Retrofit.

# Setup
You need to setup your own TMDB API key. Register for one [here](https://www.themoviedb.org/settings/api). You can read their terms and conditions [here](https://www.themoviedb.org/documentation/api/terms-of-use)

Next you should copy `apikey.properties.example` to `apikey.properties` and add your key into `THEMOVIEDB_API_KEY`

Finally if you want to run the release variant you can create a new keystore via `Build` -> `Generate Signed Bundle / APK...` and store your keystore in a safe location. Then copy `apikey.properties.example` to `apikey.properties` and fill in the sensitive keystore information from the keystore you created earlier.

# Warning
You might get a warning since the project uses java 11. You can change java version the project uses in `Preferences` -> `Build,Execution, Deployment` -> `Build Tools` -> `Gradle`. The latest Android studio version should have java 11 bundled.
