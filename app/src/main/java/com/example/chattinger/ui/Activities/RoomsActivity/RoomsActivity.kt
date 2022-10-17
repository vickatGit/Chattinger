package com.example.chattinger.ui.Activities.RoomsActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chattinger.R
import com.example.chattinger.Utils.Constants
import com.example.chattinger.data.Models.RoomModel
import com.example.chattinger.ui.Activities.Adapters.RoomsAdapter
import com.example.chattinger.ui.Activities.RoomCreationActivity.RoomCreationActivity
import com.example.chattinger.ui.ParentApplication
import com.google.android.material.floatingactionbutton.FloatingActionButton
import javax.inject.Inject

class RoomsActivity : AppCompatActivity() {

    lateinit var viewModel:RoomsVideModel
    @Inject
    lateinit var viewModelFactory: RoomsViewModelFactory
    private lateinit var createRoom:FloatingActionButton
    private var userCreatedRooms=ArrayList<RoomModel>(1)
    private lateinit var userCreatedRoomsAdapter:RoomsAdapter
    private lateinit var userCreatedRoomsRecycler:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rooms)


        val userName = intent.getStringExtra(Constants.GET_USER_NAME_TAG)
        Toast.makeText(this, "username is $userName", Toast.LENGTH_SHORT)
        (application as ParentApplication).applicationComponent.inject(this)

        initialise()
        viewModel= ViewModelProvider(this,viewModelFactory).get(RoomsVideModel::class.java)
        userCreatedRoomsAdapter=RoomsAdapter(this,userCreatedRooms,userName)
        userCreatedRoomsRecycler.layoutManager=LinearLayoutManager(this)
        userCreatedRoomsRecycler.adapter=userCreatedRoomsAdapter
        fetchUserCreatedRooms()
        createRoom.setOnClickListener {
            startActivity(Intent(this,RoomCreationActivity::class.java))
        }

    }

    override fun onRestart() {
        super.onRestart()
        fetchUserCreatedRooms()
    }
    private fun initialise() {
        createRoom=findViewById(R.id.create_room)
        userCreatedRoomsRecycler=findViewById(R.id.user_created_rooms)
    }
    private fun fetchUserCreatedRooms(){
        viewModel.getUserCreatedRooms().observe(this, Observer {
            if(it!=null){
                userCreatedRooms.clear()
                userCreatedRooms.addAll(it)
                userCreatedRoomsAdapter.notifyDataSetChanged()
            }
        })
    }
}
