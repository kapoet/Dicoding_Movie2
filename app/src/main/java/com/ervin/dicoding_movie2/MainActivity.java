package com.ervin.dicoding_movie2;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    int selected=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v4.app.FragmentManager a = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction b = a.beginTransaction();
        Fragment fragmentA = a.findFragmentByTag("f");
        if (fragmentA == null) {
            b.add(R.id.framku, new PlayingFragment(),"f");
            b.commit();
        }
        else{
//            b.replace(R.id.framku, new PlayingFragment(),"f");
//            b.addToBackStack(null);
//            b.commit();
            fragmentA = (Fragment) getSupportFragmentManager().findFragmentByTag("f");
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        android.support.v4.app.FragmentManager a = getSupportFragmentManager();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(a.getBackStackEntryCount()>1){
            for(int i = 0; i < a.getBackStackEntryCount(); ++i) {
                a.popBackStack();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        android.support.v4.app.FragmentManager a = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction b = a.beginTransaction();
        if (id == R.id.nav_playing) {
            if(selected!=0){
                b.replace(R.id.framku, new PlayingFragment(),"f");
                b.addToBackStack(null);
                b.commit();
            }
                selected=0;
        } else if (id == R.id.nav_upcoming) {
            if(selected!=1){
                b.replace(R.id.framku, new UpcomingFragment(),"f");
                b.addToBackStack(null);
                b.commit();
            }
                selected=1;
        } else if (id == R.id.nav_search) {
            if(selected!=2){
                b.replace(R.id.framku, new SearchFragment(),"f");
                b.addToBackStack(null);
                b.commit();
            }
                selected=2;
        } else if (id == R.id.nav_favorite){
            if(selected!=3){
                b.replace(R.id.framku, new FavoriteFragment());
                b.addToBackStack(null);
                b.commit();
            }
            selected=3;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
