package com.example.bookshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.bookshop.UIHome.BasketFragment;
import com.example.bookshop.UIHome.CatalogFragment;
import com.example.bookshop.UIHome.FavoritesFragment;
import com.example.bookshop.UIHome.HomeFragment;
import com.example.bookshop.UIHome.ProfileFragment;

public class MainActivity extends AppCompatActivity{

    private MeowBottomNavigation bottomNavigation;
    private Toolbar toolbar;
    private Intent in;

    private static final int HOME = 1;
    private static final int CATALOG = 2;
    private static final int BASKET = 3;
    private static final int FAVORITES = 4;
    private static final int PROFILE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.main_activity );

        toolbar=findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        bottomNavigation=findViewById(R.id.bottom_navigation);
        bottomNavigation.add(new MeowBottomNavigation.Model(CATALOG,R.drawable.ic_contacts));
        bottomNavigation.add(new MeowBottomNavigation.Model(BASKET,R.drawable.ic_add_shopping_cart));
        bottomNavigation.add(new MeowBottomNavigation.Model(HOME,R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(FAVORITES,R.drawable.ic_favorite_border));
        bottomNavigation.add(new MeowBottomNavigation.Model(PROFILE,R.drawable.ic_person));
        bottomNavigation.setOnShowListener( new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment = null;
                switch (item.getId()){
                    case CATALOG:
                        fragment = new CatalogFragment();
                        break;
                    case BASKET:
                        fragment = new BasketFragment();
                        break;
                    case HOME:
                        fragment = new HomeFragment();
                        break;
                    case FAVORITES:
                        fragment = new FavoritesFragment();
                        break;
                    case PROFILE:
                        fragment = new ProfileFragment();
                        break;
                }
                loadFragment(fragment);
            }
        } );
        bottomNavigation.setCount(BASKET,"31");
        bottomNavigation.show(HOME,true);
        bottomNavigation.setOnClickMenuListener( new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                Toast.makeText( MainActivity.this, "setOnClickMenuListener "+item.getId(), Toast.LENGTH_SHORT ).show();
            }
        } );
        bottomNavigation.setOnReselectListener( new MeowBottomNavigation.ReselectListener(){
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

            }
        } );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.main_search).getActionView();
        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Toast.makeText( MainActivity.this, "text change", Toast.LENGTH_SHORT ).show();
                return false;
            }
        } );
        searchView.setOnCloseListener( new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Toast.makeText( MainActivity.this, "closed", Toast.LENGTH_SHORT ).show();
                return false;
            }
        } );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected( item );
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.framelayout,fragment)
                .commit();
    }

}
