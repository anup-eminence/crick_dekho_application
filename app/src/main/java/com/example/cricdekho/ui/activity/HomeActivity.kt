package com.example.cricdekho.ui.activity

import MySharedPreferences
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dolatkia.animatedThemeManager.AppTheme
import com.dolatkia.animatedThemeManager.ThemeActivity
import com.dolatkia.animatedThemeManager.ThemeManager
import com.example.cricdekho.R
import com.example.cricdekho.data.model.getMatchDetails.Squad
import com.example.cricdekho.databinding.ActivityHomeBinding
import com.example.cricdekho.theme.CurrentTheme
import com.example.cricdekho.theme.DarkTheme
import com.example.cricdekho.theme.LightTheme
import com.example.cricdekho.theme.MyAppTheme
import com.example.cricdekho.theme.SystemDefaultTheme
import com.example.cricdekho.ui.home.navigation_drawer.NavUtils
import com.example.cricdekho.ui.home.navigation_drawer.NavigationItem
import com.example.cricdekho.ui.home.navigation_drawer.adapter.NavigationDrawerAdapter
import com.example.cricdekho.ui.settings.SettingUI
import com.example.cricdekho.ui.settings.ThemeType
import com.example.cricdekho.util.Constants
import com.example.cricdekho.util.ProgressbarListener
import com.example.cricdekho.util.ToolbarListener
import com.example.cricdekho.util.showToast

class HomeActivity : ThemeActivity(), ToolbarListener, ProgressbarListener,
    SettingUI.ThemeChangeListener {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var settingDialog: SettingUI
    private val navController: NavController by lazy {
        Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    private var show = false

    override fun getStartTheme(): AppTheme {
        val themeId = MySharedPreferences.readInt(Constants.PrefConstantas.THEME_ID, 0)
        return if (themeId == 1) {
            DarkTheme()
        }  else if(themeId == 2){
            SystemDefaultTheme()
        }else {
            LightTheme()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBottomNavigation()
        showSideDrawer()
        settingDialog = SettingUI()
        settingDialog.setLister(this@HomeActivity)
        binding.layoutToolbar.ivSetting.setOnClickListener {
            show = true
            settingDialog.show(supportFragmentManager, "dialog")
        }
    }

    private fun showSideDrawer() {
        val inflater = LayoutInflater.from(this)
        val customDrawerView =
            inflater.inflate(R.layout.drawer_layout, binding.sideNavigation, false)
        val rv = customDrawerView.findViewById<RecyclerView>(R.id.rv_menu)
        val root_layot  = customDrawerView.findViewById<ConstraintLayout>(R.id.root)
        CurrentTheme.changeBackgroundColor(root_layot,root_layot.context)
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

    override fun syncTheme(appTheme: AppTheme) {
        val myAppTheme = appTheme as MyAppTheme
        binding.root.setBackgroundColor(myAppTheme.activityBackgroundColor(this))
        CurrentTheme.setCurrentTheme(
            myAppTheme.activityTextColor(this@HomeActivity),
            myAppTheme.id()
        )
        binding.layoutToolbar.apply {
            this.ivSearch.setColorFilter(myAppTheme.activityIconColor(this@HomeActivity))
            this.ivLogo.setColorFilter(myAppTheme.activityIconColor(this@HomeActivity))
            this.ivMenu.setColorFilter(myAppTheme.activityIconColor(this@HomeActivity))
            this.ivSetting.setColorFilter(myAppTheme.activityIconColor(this@HomeActivity))
            this.ivLogo.setColorFilter(myAppTheme.activityIconColor(this@HomeActivity))
            this.tvTitle.setTextColor(myAppTheme.activityTextColor(this@HomeActivity))
            this.ivShare.setColorFilter(myAppTheme.activityIconColor(this@HomeActivity))
            this.ivBack.setColorFilter(myAppTheme.activityIconColor(this@HomeActivity))
        }
        println(">>>>>>>>>>>>>>>>>>>>.daadadada")
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
        share: Boolean,
        squad: Squad?
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
                navController.popBackStack()
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
                try {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.setType("text/plain")
                    intent.putExtra(Intent.EXTRA_SUBJECT, "")
                    intent.putExtra(Intent.EXTRA_TEXT, "Watch ${squad?.title} Only At TejScore http://172.105.56.131/livecricket-score/${squad?.topic_slug}")
                    startActivity(Intent.createChooser(intent, "Share Via"))
                }catch (e : Exception){
                    e.printStackTrace()
                }

            }
        }
    }

    override fun showProgressBar(progressColor: Int?) {
        if (progressColor != null) {
            binding.progressBar.setProgressTintList(ColorStateList.valueOf(progressColor));
        }
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    override fun changeTheme(theme: ThemeType) {
        when (theme) {
            ThemeType.LIGHT -> {
                ThemeManager.instance.changeTheme(LightTheme(), binding.progressBar)
            }

            ThemeType.DARK -> {
                ThemeManager.instance.changeTheme(DarkTheme(), binding.progressBar)
            }

            ThemeType.SYSTEM_DEFAULT -> {
                ThemeManager.instance.changeTheme(SystemDefaultTheme(), binding.progressBar)
            }

            ThemeType.DEFAULT -> {
                ThemeManager.instance.changeTheme(LightTheme(), binding.progressBar)

            }

            ThemeType.CLASSIC -> {
                ThemeManager.instance.changeTheme(LightTheme(), binding.progressBar)

            }

            ThemeType.SPORTY -> {
                ThemeManager.instance.changeTheme(LightTheme(), binding.progressBar)
            }

        }
        settingDialog?.dismiss()
        recreate()
    }
}