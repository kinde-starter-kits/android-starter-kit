# Kinde Android SDK starter kit.
## Requirements
Minimum supported Android SDK is Android SDK 21 (Android 5.0 Lollipop)

## How to build
```
./gradlew build
```


## How to integrate with Kinde
1. Set up Kinde environment
   - Open Kinde settings in the [Kinde Admin area](https://app.kinde.com).
   - Navigate to `App keys` section
   - Add urls for callback and logout:

         au.kinde://<your kinde url>//kinde_callback
      For example:

         au.kinde://coolapp.kinde.com/kinde_callback

2. Setup Android project

   - Add meta-data to `<application>` section of your `AndroidManifest.xml`, for example:
      * Kinde domain (Token host):

            <meta-data
            android:name="au.kinde.domain"
            android:value="coolapp.kinde.com" />

      * Client ID:

            <meta-data
            android:name="au.kinde.clientId"
            android:value="12usan9gwef901bsdf09basd" />

3. Build the application by calling `./gradlew build` from the root directory of the starter kit

##How to authorize
1. With PKCE
   - call `login` function of SDK with `grantType` parameter set to `GrantType.PKCE` ([See GrantType class for available options](sdk/src/main/java/au/kinde/sdk/GrantType.kt))

2. Without PKCE
   -  call `login` function of SDK without parameters or with `grantType` parameter set to `GrantType.NONE` ([See GrantType class for available options](sdk/src/main/java/au/kinde/sdk/GrantType.kt))
 