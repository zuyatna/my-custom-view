package com.zuyatna.mycustomview

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var myButton: MyButton
    private lateinit var myEditText: MyEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myButton = findViewById(R.id.my_button)
        myEditText = findViewById(R.id.my_edit_text)

        setMyButtonEnable()

        myEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do Nothing
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(p0: Editable?) {
                // Do Nothing
            }
        })

        myButton.setOnClickListener {
            Toast.makeText(this@MainActivity, myEditText.text, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setMyButtonEnable() {
        val result = myEditText.text
        if (result != null && result.toString().isNotEmpty()) {
            myButton.isEnabled = true
        }
    }
}