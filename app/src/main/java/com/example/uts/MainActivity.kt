package com.example.uts

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.uts.api.ApiClient
import com.example.uts.api.adapter.UsersAdapter
import com.example.uts.api.model.Users
import com.example.uts.api.model.UsersResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var call: Call<UsersResponse>
    private lateinit var usersAdapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipeRefresh = findViewById(R.id.refresh_layout)
        recyclerView = findViewById(R.id.recycler_view)

        usersAdapter = UsersAdapter { users -> usersonClick(users) }
        recyclerView.adapter = usersAdapter
        recyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        swipeRefresh.setOnRefreshListener {
            getData()
        }
        getData()
    }

    private fun usersonClick(users: Users){
        Toast.makeText(applicationContext, users.email, Toast.LENGTH_SHORT ).show()

        startActivity(
            Intent(applicationContext, DetailActivity::class.java)
                .putExtra("intent_username", users.username)
                .putExtra("intent_image", users.image)
        )

    }

    private fun getData(){
        swipeRefresh.isRefreshing = true

        call = ApiClient.usersService.getAll()
        call.enqueue(object : Callback<UsersResponse>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                swipeRefresh.isRefreshing = false
                if (response.isSuccessful){
                    usersAdapter.submitList(response.body()?.users)
                    usersAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                swipeRefresh.isRefreshing = false
                Toast.makeText(applicationContext, t.localizedMessage, Toast.LENGTH_SHORT ).show()
            }

        })
    }
}
