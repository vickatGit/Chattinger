package com.example.chattinger.ui.Activities.SplashActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.chattinger.R
import com.example.chattinger.Utils.Constants
import com.example.chattinger.ui.Activities.LoginActivity.LoginActivity
import com.example.chattinger.ui.Activities.RoomsActivity.RoomsActivity
import com.example.chattinger.ui.ParentApplication
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory:SplashViewModelFactory
    lateinit var viewModel:SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        (application as ParentApplication).applicationComponent.inject(this)

        viewModel= ViewModelProvider(this,viewModelFactory).get(SplashViewModel::class.java)


                viewModel.getUsers()?.observe(this, Observer {
                    Log.d("TAG", "onCreate: $it")
                    Toast.makeText(this, "and sizee $it", Toast.LENGTH_LONG).show()

                        if (it.size <= 0) {
                            var intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        } else {
                            var intent = Intent(this, RoomsActivity::class.java)
                            intent.putExtra(
                                Constants.GET_USER_NAME_TAG,
                                it.get(0).userName.toString()
                            )
                            startActivity(intent)
                        }

                })



    }
}