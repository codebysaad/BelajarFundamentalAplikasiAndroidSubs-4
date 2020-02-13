package xyz.webflutter.moviecatalogue.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import xyz.webflutter.moviecatalogue.R;
import xyz.webflutter.moviecatalogue.adapters.FragmentPageAdapter;

public class FavActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    ViewPager viewPager;
    FragmentPageAdapter pageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        toolbar = findViewById(R.id.act_bar_fav);
        toolbar.setTitle(getResources().getString(R.string.favorite));
        setSupportActionBar(toolbar);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        pageAdapter = new FragmentPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    toolbar.setBackgroundColor(ContextCompat.getColor(FavActivity.this,
                            R.color.colorPrimary));
                    tabLayout.setBackgroundColor(ContextCompat.getColor(FavActivity.this,
                            R.color.colorPrimary));
                } else {
                    toolbar.setBackgroundColor(ContextCompat.getColor(FavActivity.this,
                            R.color.green_accent));
                    tabLayout.setBackgroundColor(ContextCompat.getColor(FavActivity.this,
                            R.color.green_accent));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}