package activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.arpit.bookappp.R
import com.google.android.material.navigation.NavigationView
import fragment.AboutFragment
import fragment.DashBoardFragment
import fragment.FavouriteFragment
import fragment.LogoutFragment

class MainActivity : AppCompatActivity() {

  private  lateinit var drawerlayout:DrawerLayout
     private lateinit var coordinatorlayout:CoordinatorLayout
     private lateinit var toolbar :Toolbar
   private lateinit var framelayout:FrameLayout
     private lateinit var navigationview:NavigationView

    var previousMenuItem:MenuItem?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerlayout=findViewById(R.id.drawerlayout)
        coordinatorlayout= findViewById(R.id.coordinatorlayout)
        toolbar = findViewById(R.id.toolbar)
        framelayout = findViewById(R.id.framelayout)
        navigationview = findViewById(R.id.navigationview)


        setUpToolbar()
        openDashboard()

        val actionBarDrawerToggle = ActionBarDrawerToggle(this@MainActivity , drawerlayout,
            R.string.open_drawer,
            R.string.close_drawer
        )

        drawerlayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationview.setNavigationItemSelectedListener {

                 if(previousMenuItem!=null){
                     previousMenuItem?.isChecked=false
                 }
                   it.isCheckable=true
                   it.isChecked=true
                     previousMenuItem= it


            when(it.itemId) {
                R.id.dashboard -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.framelayout,
                            DashBoardFragment()
                        )

                        .commit()
                    supportActionBar?.title="DashBoard"

                    drawerlayout.closeDrawers()
                }
                R.id.favourite -> {

                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.framelayout,
                            FavouriteFragment()
                        )

                        .commit()
                        supportActionBar?.title="Favourite"
                    drawerlayout.closeDrawers()

                }
                R.id.logout -> {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Confirmation")
                            .setMessage("Are you sure you want exit?")
                            .setPositiveButton("Yes") { _, _ ->
                                ActivityCompat.finishAffinity(this)
                            }
                            .setNegativeButton("No") { _, _ ->
                               openDashboard()
                                drawerlayout.closeDrawers()
                            }
                            .create()
                            .show()
                            }
                R.id.aboutapp -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.framelayout,
                            AboutFragment()
                        )

                        .commit()
                          supportActionBar?.title="AboutApp"
                    drawerlayout.closeDrawers()
                }

            }
            return@setNavigationItemSelectedListener true

        }


    }

    fun setUpToolbar()
    {
      setSupportActionBar(toolbar)
        supportActionBar?.title="Toolbar Drawer"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId
        if (id== android.R.id.home){
            drawerlayout.openDrawer(GravityCompat.START)
        }

        return super.onOptionsItemSelected(item)
    }

    fun openDashboard() {

        val fragment = DashBoardFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.framelayout, fragment)
        transaction.commit()

        supportActionBar ?.title ="Dashboard"
                   navigationview.setCheckedItem(R.id.dashboard)
            }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.framelayout)

        when(frag) {
            !is DashBoardFragment -> openDashboard()

            else -> super.onBackPressed()
        }
    }
}
