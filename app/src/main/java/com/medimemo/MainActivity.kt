package com.medimemo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.medimemo.databinding.ActivityMainBinding
import com.medimemo.ui.fragment.HomeFragment
import com.medimemo.ui.fragment.MedicineFragment
import com.medimemo.ui.fragment.ReminderFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())
        auth = FirebaseAuth.getInstance()

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){

                R.id.home -> replaceFragment(HomeFragment())
                R.id.fiture2 -> replaceFragment(MedicineFragment())
                R.id.fiture3 -> replaceFragment(ReminderFragment())
                R.id.fiture4 -> replaceFragment(fiture4())

            }
            true
        }


    }

    private fun replaceFragment (fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }

}