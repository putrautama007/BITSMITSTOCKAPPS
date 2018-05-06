package com.raion.putrautama.bitsmitstockapps

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import com.raion.putrautama.bitsmitstockapps.adapter.PageAdapter

class IntroSliderActivity : AppCompatActivity(){


    lateinit var mPager : ViewPager
    var layouts  = arrayOf(R.layout.first_slide,R.layout.second_slide,R.layout.third_slide)
    lateinit var dotsLayout : LinearLayout
    lateinit var dots : Array<ImageView>
    lateinit var mAdapter: PageAdapter
    lateinit var btnNext : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (PrefManager(this).checkPreference()){
            loadHome()
        }
        if (Build.VERSION.SDK_INT >=19){
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }else{
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        setContentView(R.layout.activity_intro_slider)
        mPager = findViewById<ViewPager>(R.id.pager)
        mAdapter = PageAdapter(layouts,applicationContext)
        mPager.adapter = mAdapter
        dotsLayout = findViewById<LinearLayout>(R.id.dots)
        btnNext = findViewById<Button>(R.id.btnNext)
        btnNext.setOnClickListener {
            loadNextSlide()
        }
        createDots(0)
        mPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                createDots(position)

//                if (position == layouts.size - 1){
//                    btnNext.visibility = View.VISIBLE
//                }else{
//                    btnNext.visibility = View.INVISIBLE
//                }
            }

        })

    }
//    override fun onClick(v: View?) {
//        when(v?.id){
//            R.id.btnNext ->{
//                loadNextSlide()
//            }
//        }
//    }

    private fun loadNextSlide() {
        var nextSlide: Int = mPager.currentItem + 1

        if (nextSlide < layouts.size){
            mPager.currentItem = nextSlide
        }else{
            loadHome()
            PrefManager(this).writeSP()
        }
    }

    private fun loadHome() {
//        startActivity(Intent(this, LoginActivity::class.java))
        val intent = Intent(this@IntroSliderActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun createDots(position : Int){
        if (dotsLayout!=null){
            dotsLayout.removeAllViews()
        }
        dots = Array(layouts.size,{i -> ImageView(this)  })

        for (i in 0..layouts.size - 1){
            dots[i] = ImageView(this)
            if (i == position ){
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.active_dots))
            }else{
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.inactive_dots))
            }

            var params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

            params.setMargins(4,0,4,0)
            dotsLayout.addView(dots[i],params)
        }
    }
}
