package com.dhruvisha.darji.ui.view


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhruvisha.darji.R
import com.dhruvisha.darji.room.AppDatabase
import com.dhruvisha.darji.room.UserData
import com.dhruvisha.darji.utils.SharedPrefsHelp.clearSharedPrefs
import com.dhruvisha.darji.ui.viewmodel.ImageViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ImageViewModel

    var toolbar: ActionBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = supportActionBar
        supportActionBar?.title = "Home"
        viewModel = ViewModelProvider(this).get(ImageViewModel::class.java)
        viewModel.executeNetworkCall()
        viewModel.getNetworkCallSuccess()
        viewModel.getNetworkCallResults().observe(this, Observer { userData -> initRecyclerView(userData) })
    }
    private fun initRecyclerView(userData: List<UserData>) {
        recyclerview.apply {
            setHasFixedSize(true)
            val layoutManager: LinearLayoutManager = GridLayoutManager(this@MainActivity, 2)
            this.layoutManager = layoutManager
           adapter = ImageAdapter(
               userData,
               this@MainActivity
           )
        }

        AppDatabase.getDatabase(applicationContext)?.userDao()?.insertAll(userData)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_logout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                clearSharedPrefs(this@MainActivity)
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item as MenuItem)
        }


    }


}