package com.example.proiect;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.Toast;

import com.example.proiect.fragments.ChartsFragment;
import com.example.proiect.fragments.HomeFragment;
import com.example.proiect.fragments.MijlocFixFragment;
import com.example.proiect.fragments.ObiectInventarFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView ;

    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configNavigation();

        apasareMeniu();
        openDefaultFragment(savedInstanceState);
    }

    private void configNavigation() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.info_menu){
            Toast.makeText(getApplicationContext(),getString( R.string.toast_info_menu),Toast.LENGTH_LONG).show();
        }
        return true ;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return true ;
    }

    public void apasareMeniu(){
        navigationView = findViewById(R.id.nav_view) ;
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()== R.id.nav_home){
                    currentFragment = HomeFragment.newInstance();
                }
                if(item.getItemId()== R.id.nav_mij_fixe){
                    currentFragment = MijlocFixFragment.newInstance();
                }
                if(item.getItemId()== R.id.nav_obiecte_inventar){
                    currentFragment = ObiectInventarFragment.newInstance();
                }
                if(item.getItemId()== R.id.nav_rapoarte){
                    currentFragment = ChartsFragment.newInstance();
                }
                drawerLayout.closeDrawer(GravityCompat.START);

                openFragment();
                return true;
            }
        });
    }

    private void openDefaultFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            currentFragment = HomeFragment.newInstance();
            openFragment();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    private void openFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame_container, currentFragment)
                .commit();
    }
}