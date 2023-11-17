import android.content.Intent
import android.net.InetAddresses.isNumericAddress
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ieseljust.pmdm.whatsdam.databinding.ActivityMainBinding
import org.json.JSONObject
import com.ieseljust.pmdm.whatsdam.ViewModels.LoginViewModel
import com.ieseljust.pmdm.whatsdam.view.Activity_messages_window
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var loginViewModel: LoginViewModel
    private var nickname = ""
    private var ipserver = ""

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        loginViewModel.loginStatus.observe(this) {
            handleLoginStatus(it)
        }

        if (savedInstanceState != null) {
            nickname = savedInstanceState.getString("nickname", "")
            ipserver = savedInstanceState.getString("ipserver", "")
        }

        binding.buttonConnect.setOnClickListener {
            nickname = binding.nickNameText.text.toString()
            ipserver = binding.serverAddressText.text.toString()

            if (nickname.isNotEmpty() && isNumericAddress(ipserver)) {
                binding.progressBar.visibility = View.VISIBLE
                binding.buttonConnect.isEnabled = false
                lifecycleScope.launch {
                    loginViewModel.login(nickname, ipserver)
                }
            }
        }
    }

    private fun handleLoginStatus(statusJson: JSONObject) {
        binding.progressBar.visibility = View.GONE

        if (statusJson.has("status")) {
            when (statusJson.getString("status")) {
                "error" -> {
                    binding.statusTextView.text = statusJson.getString("message")
                    binding.statusTextView.visibility = View.VISIBLE
                    binding.buttonConnect.isEnabled = true
                }
                "ok" -> {
                    val intent = Intent(this, Activity_messages_window::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("nickname", nickname)
        outState.putString("ipserver", ipserver)
    }
}
