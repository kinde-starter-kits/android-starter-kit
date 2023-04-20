package au.kinde

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import au.kinde.sdk.GrantType
import au.kinde.sdk.KindeSDK
import au.kinde.sdk.SDKListener


/**
 * @author roman
 * @since 1.0
 */
class KindeActivity : AppCompatActivity() {

    private lateinit var loggedInState: View
    private lateinit var loggedOutState: View
    private lateinit var loadingGroup: View
    private lateinit var progress: View
    private lateinit var userName: TextView
    private lateinit var userNameInitials: TextView

    private lateinit var sdk: KindeSDK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        loggedOutState = findViewById(R.id.gr_logged_out)
        loggedInState = findViewById(R.id.gr_logged_in)
        loadingGroup = findViewById(R.id.gr_loading)
        progress = findViewById(R.id.progress)

        userName = findViewById(R.id.tv_username)
        userNameInitials = findViewById(R.id.tv_initials)

        findViewById<View>(R.id.b_go_to_docs).setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(DOCS_URL)))
        }
        findViewById<View>(R.id.tv_help).setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(HELP_URL)))
        }

        findViewById<View>(R.id.b_sign_in).setOnClickListener {
//            sdk.login()
            sdk.login(GrantType.PKCE)
        }
        findViewById<View>(R.id.b_sign_up).setOnClickListener {
//            sdk.register()
            sdk.register(GrantType.PKCE)
        }
        findViewById<View>(R.id.b_sign_out).setOnClickListener {
            sdk.logout()
        }

        sdk = KindeSDK(
            activity = this,
            loginRedirect = "kinde.sdk://<your_kinde_host>/kinde_callback",
            logoutRedirect = "kinde.sdk://<your_kinde_host>/kinde_logoutcallback",
            sdkListener = object : SDKListener {
                override fun onNewToken(token: String) {
                    loggedInState.visibility = View.VISIBLE
                    loggedOutState.visibility = View.GONE
                    loadingGroup.visibility = View.INVISIBLE
                    progress.visibility = View.VISIBLE
                    execute {
                        sdk.getUserProfileV2()?.let {
                            Handler(Looper.getMainLooper()).post {
                                userName.text = it.name
                                userNameInitials.text = getString(
                                    R.string.username_initials,
                                    it.givenName?.first(),
                                    it.familyName?.first()
                                )
                                progress.visibility = View.GONE
                                loadingGroup.visibility = View.VISIBLE
                            }
                        }
                    }
                }

                override fun onLogout() {
                    loggedInState.visibility = View.GONE
                    loggedOutState.visibility = View.VISIBLE
                }

                override fun onException(exception: Exception) {
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(this@KindeActivity, exception.message, Toast.LENGTH_LONG)
                            .show()
                    }
                }
            })
    }

    private fun execute(function: () -> Unit) {
        Thread {
            function.invoke()
        }.start()
    }

    companion object {
        private const val TAG = "Kinde"
        private const val DOCS_URL = "https://kinde.com/docs"
        private const val HELP_URL = "https://kinde.com/docs"
    }
}