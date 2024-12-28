package com.dicoding.capstone

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet.Layout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Dashboard : AppCompatActivity() {

    private var recyclerView : RecyclerView? = null
    private var recycleViewHairstyleAdapter : RecycleViewHairstyleAdapter? = null
    private var hairstyleList = mutableListOf<Hairstyle>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val ExitBtn = findViewById<ImageView>(R.id.ExitBtn)
        ExitBtn.setOnClickListener{
            val Intent = Intent(this, MainActivity::class.java)
            startActivity(Intent)
        }

        val ScanBtn = findViewById<LinearLayout>(R.id.btnScan)
        ScanBtn.setOnClickListener{
            val Intent = Intent(this, ScanView::class.java)
            startActivity(Intent)
        }

        hairstyleList = ArrayList()

        recyclerView = findViewById<View>(R.id.rvHairstyleList) as RecyclerView
        recycleViewHairstyleAdapter = RecycleViewHairstyleAdapter(this@Dashboard, hairstyleList)
        val layoutManager : RecyclerView.LayoutManager = GridLayoutManager(this, 2)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.adapter = recycleViewHairstyleAdapter

        prepareHairstylListData()

    }

    private fun prepareHairstylListData() {
        var hairstyle = Hairstyle("Buzzcut", R.drawable.buzzcut)
        hairstyleList.add(hairstyle)
        hairstyle = Hairstyle("Undercut", R.drawable.undercut)
        hairstyleList.add(hairstyle)
        hairstyle = Hairstyle("Crew Cut", R.drawable.crew_cut)
        hairstyleList.add(hairstyle)
        hairstyle = Hairstyle("Fringe Up", R.drawable.fringe_up)
        hairstyleList.add(hairstyle)
        hairstyle = Hairstyle("Quiff", R.drawable.quiff)
        hairstyleList.add(hairstyle)
        hairstyle = Hairstyle("Side Fringe", R.drawable.side_fringe)
        hairstyleList.add(hairstyle)
        hairstyle = Hairstyle("Side Part", R.drawable.side_part)
        hairstyleList.add(hairstyle)
        hairstyle = Hairstyle("Side Swept Bangs", R.drawable.side_swept_bangs)
        hairstyleList.add(hairstyle)
        hairstyle = Hairstyle("Spike", R.drawable.spike)
        hairstyleList.add(hairstyle)


        recycleViewHairstyleAdapter!!.notifyDataSetChanged()
    }
}