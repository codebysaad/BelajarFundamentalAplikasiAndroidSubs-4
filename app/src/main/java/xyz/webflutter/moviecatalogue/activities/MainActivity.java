package xyz.webflutter.moviecatalogue.activities;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import xyz.webflutter.moviecatalogue.fragments.MovieFragment;
import xyz.webflutter.moviecatalogue.R;
import xyz.webflutter.moviecatalogue.fragments.TvShowFragment;

public class MainActivity extends AppCompatActivity {

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        Fragment fragment;

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new MovieFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame_layout, fragment, fragment.getClass().getSimpleName())
                            .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                            .commit();
                    return true;
                case R.id.navigation_dashboard:
                    fragment = new TvShowFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame_layout, fragment, fragment.getClass().getSimpleName())
                            .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            navView.setSelectedItemId(R.id.navigation_home);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.change_language) {
            startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
        }
        if (item.getItemId() == R.id.favorite){
            startActivity(new Intent(getApplicationContext(), FavActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
