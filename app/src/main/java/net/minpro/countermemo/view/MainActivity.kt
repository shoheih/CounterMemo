package net.minpro.countermemo.view

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import net.minpro.countermemo.R
import net.minpro.countermemo.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_master, MasterFragment())
                .commit()
        }

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        menu.apply {
            findItem(R.id.action_record).isVisible = true
            findItem(R.id.action_regist).isVisible = true
            findItem(R.id.action_photo).isVisible = true
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_record -> {
                goRecordScreen()
            }
            R.id.action_regist -> {
                viewModel.recordToRealm()
            }
            R.id.action_photo -> {
                goPhotoScreen()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goPhotoScreen() {
        val intent = Intent(this@MainActivity, PhotoActivity::class.java)
        startActivity(intent)
    }

    private fun goRecordScreen() {
        val intent = Intent(this@MainActivity, RecordActivity::class.java)
        startActivity(intent)
    }
}
