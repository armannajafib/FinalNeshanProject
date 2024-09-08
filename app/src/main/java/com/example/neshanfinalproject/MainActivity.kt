package com.example.neshanfinalproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // اضافه کردن BottomFragment به اکتیویتی
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        // ایجاد نمونه‌ای از BottomFragment
        val fragment: Fragment = CardMaterialFragment()

        // اضافه کردن فرگمنت به FrameLayout
        fragmentTransaction.add(R.id.fragment_container, fragment)
        fragmentTransaction.commit()


    }
}