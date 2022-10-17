package com.example.chattinger.ui.Activities.RoomCreationActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.chattinger.R
import com.example.chattinger.ui.Activities.LoginActivity.LoginViewModel
import com.example.chattinger.ui.ParentApplication
import javax.inject.Inject

class RoomCreationActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: RoomCreationViewModelFactory
    lateinit var viewModel: RoomCreationViewModel
    private lateinit var roomNameTaker:EditText
    private lateinit var createRoom:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_creation)
        (application as ParentApplication).applicationComponent.inject(this)

        initialise()
        viewModel= ViewModelProvider(this,viewModelFactory).get(RoomCreationViewModel::class.java)
        createRoom.setOnClickListener {
            if(!roomNameTaker.text.isNullOrEmpty()){
                viewModel.createRoom(roomNameTaker.text.toString()).observe(this, Observer {
                    if(it){
                        Toast.makeText(this,"room Successfully inserted",Toast.LENGTH_LONG).show()
                        finish()
                    }else{
                        Toast.makeText(this,"Cant insert room into database",Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }

    private fun initialise() {
        roomNameTaker=findViewById(R.id.room_name)
        createRoom=findViewById(R.id.create_room)
    }
}