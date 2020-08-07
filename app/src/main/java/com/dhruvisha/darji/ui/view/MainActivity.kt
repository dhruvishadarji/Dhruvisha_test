package com.dhruvisha.darji.ui.view


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhruvisha.darji.R
import com.dhruvisha.darji.network.ApiService
import com.dhruvisha.darji.network.RetrofitClient.instance
import com.dhruvisha.darji.room.AppDatabase
import com.dhruvisha.darji.room.UserData
import com.dhruvisha.darji.ui.viewmodel.ImageViewModel
import com.dhruvisha.darji.utils.SharedPrefsHelp.clearSharedPrefs
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.function.Consumer


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ImageViewModel
    var compositeDisposable = CompositeDisposable()
    var toolbar: ActionBar? = null
    var myAPI:ApiService ? = null


    private var mDisposable: CompositeDisposable? = null
    private var mPaginator: PublishProcessor<Int>? = null
    private var mAdapter: ImageAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = supportActionBar
        supportActionBar?.title = "Home"
        mDisposable = CompositeDisposable()
        val retrofit = instance
        myAPI = retrofit!!.create(ApiService::class.java)
        mPaginator = PublishProcessor.create()

        val layoutManager: LinearLayoutManager = GridLayoutManager(this@MainActivity, 2)
        recyclerView.layoutManager = layoutManager
        mAdapter = ImageAdapter(listOf(),this)
        recyclerView.adapter = mAdapter
        fetchData()
      // var userRepository = ImageRepository(ApiService.invoke())
      //  viewModel = ImageViewModel(userRepository)
        //viewModel = ViewModelProvider(this).get(ImageViewModel::class.java)
      /*  recyclerView.apply {
            setHasFixedSize(true)
            val layoutManager: LinearLayoutManager = GridLayoutManager(this@MainActivity, 2)
            this.layoutManager = layoutManager
        }*/
       /* viewModel.executeNetworkCall()
        viewModel.getNetworkCallSuccess()
        viewModel.getNetworkCallResults().observe(this, Observer { userData -> initRecyclerView(userData) })*/
    }

    private fun fetchData() {

            compositeDisposable.add(
                myAPI!!.getImageDetails()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.d("dataa","Received UIModel $it users.")
                        displayData(it)
                    }, {
                        Log.w("errorrr",it)
                        showError()
                    }))


    }

    private fun displayData(posts: List<UserData>) {
        val layoutManager: LinearLayoutManager = GridLayoutManager(this@MainActivity, 2)
        recyclerView.layoutManager = layoutManager
        val adapter = ImageAdapter( posts,this)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }
    val subscriptions = CompositeDisposable()
    fun subscribe(disposable: Disposable): Disposable {
        subscriptions.add(disposable)
        return disposable
    }
    fun showError() {
        Toast.makeText(this, "An error occurred :(", Toast.LENGTH_SHORT).show()
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