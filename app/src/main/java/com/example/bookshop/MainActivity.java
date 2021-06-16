package com.example.bookshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.SearchView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.bookshop.Adapters.NewestAdapter;
import com.example.bookshop.Fragment.BasketFragment;
import com.example.bookshop.Fragment.CatalogFragment;
import com.example.bookshop.Fragment.FavoritesFragment;
import com.example.bookshop.Fragment.HomeFragment;
import com.example.bookshop.Fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity{

    private MeowBottomNavigation bottomNavigation;
    private Toolbar toolbar;
    private int favId,btnBasket;
    private int itemBasket = 0;

    private static final int HOME = 1;
    private static final int CATALOG = 2;
    private static final int BASKET = 3;
    private static final int FAVORITES = 4;
    private static final int PROFILE = 5;
    public static final int REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.main_activity );
        inflateToolbar();
        bottomNavigation=findViewById(R.id.bottom_navigation);
        bottomNavigation.add(new MeowBottomNavigation.Model(CATALOG,R.drawable.ic_contacts));
        bottomNavigation.add(new MeowBottomNavigation.Model(BASKET,R.drawable.ic_add_shopping_cart));
        bottomNavigation.add(new MeowBottomNavigation.Model(HOME,R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(FAVORITES,R.drawable.ic_favorite_wight));
        bottomNavigation.add(new MeowBottomNavigation.Model(PROFILE,R.drawable.ic_person));
        bottomNavigation.setOnShowListener( new MeowBottomNavigation.ShowListener(){
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

        bottomNavigation.setCount(BASKET,"3");
        bottomNavigation.show(HOME,true);
        bottomNavigation.setOnClickMenuListener( new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
               // Toast.makeText( MainActivity.this, "setOnClickMenuListener "+item.getId(), Toast.LENGTH_SHORT ).show();
            }
        } );
        bottomNavigation.setOnReselectListener( new MeowBottomNavigation.ReselectListener(){
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

            }
        } );
    }



    public void inflateToolbar(){
        toolbar=findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    public void bottomNavigationBar(){ }
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.framelayout,fragment)
                .commit();
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
                Toast.makeText(getBaseContext(), "Close ", Toast.LENGTH_SHORT ).show();
                return false;
            }
        } );
        return true;
    }
}
