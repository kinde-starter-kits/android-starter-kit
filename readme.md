# Kinde Android SDK starter kit.
## Requirements
Minimum supported Android SDK is Android SDK 21 (Android 5.0 Lollipop)

## How to build
```
./gradlew build
```


## How to integrate
1. Set up Kinde environment
   - Open Kinde settings in the webapp.
   - Navigate to `App keys` section
   - Add urls for callback and logout:

         au.kinde://<your kinde url>//kinde_callback
      For example:

         au.kinde://coolapp.kinde.com/kinde_callback

2. Setup Android project
   - Add `kinde-sdk.arr` to your Android project:
      * Put it in `libs` folder. Make sure to include libs folder in your gradle file.   
         To do so, add the following line to the `dependencies` section
               
            implementation fileTree(include: ['*.aar'],dir: 'libs')
      * Alternatively you can add `kinde-sdk.aar` as separate module. Don't forget to add dependecies for this module to build.gradle

   - Add dependencies to Retrofit and Gson converter:
   
         implementation "com.squareup.retrofit2:retrofit:$retrofit_version"
         implementation "com.squareup.retrofit2:converter-gson:$retrofit_version"
   - Add meta-data to `<application>` section of your `AndroidManifest.xml`:
      * Kinde domain (Token host):

            <meta-data
            android:name="au.kinde.domain"
            android:value="coolapp.kinde.com" />
      * Client ID:

            <meta-data
            android:name="au.kinde.clientId"
            android:value="example@example" />

##How to authorize
1. With PKCE
   - call `login` function of SDK with `grantType` parameter set to `GrantType.PKCE` ([See GrantType class for available options](sdk/src/main/java/au/kinde/sdk/GrantType.kt))

2. Without PKCE
    -  call `login` function of SDK without parameters or with `grantType` parameter set to `GrantType.NONE` ([See GrantType class for available options](sdk/src/main/java/au/kinde/sdk/GrantType.kt))
    