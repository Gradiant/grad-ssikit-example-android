package com.example.androidssikitexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import id.walt.servicematrix.ServiceMatrix
import id.walt.servicematrix.utils.AndroidUtils
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.io.File
import java.security.Security

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeSSIKit()
        //adjustSecurityProviders()
        val editTextBearerToken = findViewById<EditText>(R.id.editTextBearerToken)

        findViewById<Button>(R.id.buttonUpdateBearerToken).setOnClickListener {
            saveBearerToken(editTextBearerToken.text.toString())
        }

        findViewById<Button>(R.id.buttonDidEbsi).setOnClickListener {
            DidEbsi.run()
        }

        findViewById<Button>(R.id.buttonKeyManagement).setOnClickListener {
            KeyManagement.run()
        }

        findViewById<Button>(R.id.buttonVerifiableCredentials).setOnClickListener {
            VerifiableCredentials.run()
        }
    }

    private fun initializeSSIKit() {
        val dataDirPath = dataDir.absolutePath
        val dataRootPath = "$dataDirPath/data"
        val serviceMatrixProps = resources.openRawResource(R.raw.servicematrix)
        AndroidUtils.setAndroidDataDir(dataDirPath)
        AndroidUtils.setDataRoot(dataRootPath)
        ServiceMatrix(serviceMatrixProps)
    }

    private fun adjustSecurityProviders() {
        Security.removeProvider("BC")
        Security.insertProviderAt(BouncyCastleProvider(), 1)
    }

    private fun saveBearerToken(bearerToken: String) {
        val bearerTokenFile = File("${AndroidUtils.getDataRoot()}/ebsi/bearer-token.txt")
        bearerTokenFile.writeText(bearerToken)
    }
}