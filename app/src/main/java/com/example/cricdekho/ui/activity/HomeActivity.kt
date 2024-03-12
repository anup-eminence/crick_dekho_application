package com.example.cricdekho.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cricdekho.R
import com.example.cricdekho.databinding.ActivityHomeBinding
import com.example.cricdekho.ui.home.navigation_drawer.NavUtils
import com.example.cricdekho.ui.home.navigation_drawer.NavigationItem
import com.example.cricdekho.ui.home.navigation_drawer.adapter.NavigationDrawerAdapter
import com.example.cricdekho.util.ProgressbarListener
import com.example.cricdekho.util.ToolbarListener

class HomeActivity : AppCompatActivity(), ToolbarListener, ProgressbarListener {
    private lateinit var binding: ActivityHomeBinding
    private val navController: NavController by lazy {
        Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBottomNavigation()
        showSideDrawer()
    }

    private fun showSideDrawer() {
        val inflater = LayoutInflater.from(this)
        val customDrawerView = inflater.inflate(R.layout.drawer_layout, binding.sideNavigation, false)
        val rv = customDrawerView.findViewById<RecyclerView>(R.id.rv_menu)
        val navAdapter = NavigationDrawerAdapter()
        rv.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = navAdapter
            navAdapter.submitList(NavUtils.provideNavigationList(this@HomeActivity))

        }
        binding.sideNavigation.addView(customDrawerView)
        binding.layoutToolbar.ivMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setBottomNavigation() {
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    navController.navigate(R.id.homeFragment)
                }

                R.id.nav_video -> {
                    navController.navigate(R.id.videosFragment)
                }

                R.id.nav_fantasy -> {
                    navController.navigate(R.id.fantasyFragment)
                }

                R.id.nav_match -> {
                    navController.navigate(R.id.matchesFragment)
                }
            }
            true
        }
    }

    override fun showToolBarMethod(
        title: String,
        menu: Boolean,
        logo: Boolean,
        search: Boolean,
        setting: Boolean,
        back: Boolean,
        share: Boolean
    ) {
        val toolbarBinding = binding.layoutToolbar
        toolbarBinding.apply {
            ivMenu.visibility = if (menu) View.VISIBLE else View.GONE
            ivLogo.visibility = if (logo) View.VISIBLE else View.GONE
            ivSearch.visibility = if (search) View.VISIBLE else View.GONE
            ivSetting.visibility = if (setting) View.VISIBLE else View.GONE
            ivBack.visibility = if (back) View.VISIBLE else View.GONE
            ivShare.visibility = if (share) View.VISIBLE else View.GONE

            tvTitle.visibility = View.VISIBLE
            tvTitle.text = title

            ivBack.setOnClickListener {
                navController.popBackStack(R.id.homeFragment, false)
                showToolBarMethod(
                    title = "",
                    menu = true,
                    logo = true,
                    search = true,
                    setting = true,
                    back = false,
                    share = false
                )
            }
            ivSearch.setOnClickListener {
                navController.navigate(R.id.action_homeFragment_to_bottomSheet)
            }
            ivShare.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.setType("text/plain")
                intent.putExtra(Intent.EXTRA_SUBJECT, "")
                intent.putExtra(Intent.EXTRA_TEXT, "www.google.com")
                startActivity(Intent.createChooser(intent, "Share Via"))
            }
        }
    }

    override fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }
}