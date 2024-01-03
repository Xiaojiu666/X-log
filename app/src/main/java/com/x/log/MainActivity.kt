package com.x.log

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.x.log.output.FileConsoleAdapter
import com.x.log.service.PrinterConfig
import com.x.log.utils.decryptAndWriteToFile
import java.io.File
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

class MainActivity : AppCompatActivity() {
    // 使用 by viewModels() 代理属性获取 ViewModel 实例
    private lateinit var viewModel: MyViewModel


    @OptIn(ExperimentalTime::class)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        findViewById<TextView>(R.id.tv_click).setOnClickListener {
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