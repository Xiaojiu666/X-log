package com.x.log

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Environment
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.x.log.utils.CipherManager
import com.x.log.utils.decryptAndWriteToFile
import java.io.File
import kotlin.time.ExperimentalTime

class MainActivity : AppCompatActivity() {
    // 使用 by viewModels() 代理属性获取 ViewModel 实例
    private lateinit var viewModel: MyViewModel


    @OptIn(ExperimentalTime::class)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val byteARRAY = CipherManager.getInstance("guoxu").encrypt(
//            HeaderInfo().toString().toByteArray()
//        )
//        val result = CipherManager.getInstance("guoxu").decrypted(byteARRAY)
//        println("result ${String(result)}")
        XLog.e("onCreate")
        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        findViewById<TextView>(R.id.tv_click).setOnClickListener {
            XLog.e("setOnClickListener")
            viewModel.clickLog()
        }
        findViewById<TextView>(R.id.tv_decode).setOnClickListener {
            val dir = baseContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            val file = File(dir, "text.txt")
            val file1 = File(dir, "text1.txt")
            decryptAndWriteToFile(file.path, file1.path, "ABC")
        }
    }
}