package com.example.chattinger.ui.Activities.LoginActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.chattinger.R
import com.example.chattinger.Utils.Constants
import com.example.chattinger.ui.Activities.Chat.ChatActivity
import com.example.chattinger.ui.Activities.RoomsActivity.RoomsActivity
import com.example.chattinger.ui.ParentApplication
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    private lateinit var userName:EditText
    private lateinit var startChat:Button
    private lateinit var viewModel: LoginViewModel

    @Inject
    lateinit var viewModelFactory: LoginViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        (application as ParentApplication).applicationComponent.inject(this)

        initialise()
        viewModel=ViewModelProvider(this,viewModelFactory).get(LoginViewModel::class.java)

        startChat.setOnClickListener {
            if(!userName.text.isNullOrEmpty()){

                viewModel.loginUser(userName.text.toString())
                var intent=Intent(this,RoomsActivity::class.java)
                intent.putExtra(Constants.GET_USER_NAME_TAG,userName.text.toString())
                startActivity(intent)
            }else{
                Toast.makeText(this,"All fields should be filled ",Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun initialise(){
        userName=findViewById(R.id.userName)
        startChat=findViewById(R.id.start_chat)
    }
}