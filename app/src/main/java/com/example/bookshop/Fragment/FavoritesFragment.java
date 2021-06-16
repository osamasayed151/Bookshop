package com.example.bookshop.Fragment;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookshop.Adapters.FavoriteAdapter;
import com.example.bookshop.Helpers.FavoriteBook;
import com.example.bookshop.Helpers.FavoriteDB;
import com.example.bookshop.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class FavoritesFragment extends Fragment  {


    private FavoriteDB favDB;
    private ArrayList<FavoriteBook> favItemList = new ArrayList<>();
    private FavoriteAdapter favAdapter;

    private TextView txtTitle;
    private ImageView img;
    private RecyclerView recyclerView;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    public static FavoritesFragment newInstance() {
        FavoritesFragment fragment = new FavoritesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_favorites, container, false );
        recyclerView = view.findViewById(R.id.favorites_rv);
        txtTitle = view.findViewById(R.id.favorite_tv);
        img = view.findViewById(R.id.favorite_im);
        hideItems();
        favDB = new FavoriteDB(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadData();
        int size = favAdapter.getItemCount();
        Toast.makeText( getActivity(), "Favorite size"+size, Toast.LENGTH_SHORT ).show();
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
    }
    public void hideItems(){
        txtTitle.setVisibility(View.GONE);
        img.setVisibility(View.GONE);
    }
    public void showItems(){
        txtTitle.setVisibility(View.GONE);
        img.setVisibility(View.GONE);
    }


    private void loadData() {
        if (favItemList != null) {
            favItemList.clear();
        }
        SQLiteDatabase db = favDB.getReadableDatabase();
        Cursor cursor = favDB.selectAllFavoriteItem();
        try {
            while (cursor.moveToNext()) {

                String id = cursor.getString(cursor.getColumnIndex(String.valueOf(FavoriteDB.ID)));
                int image = cursor.getInt(cursor.getColumnIndex(String.valueOf(FavoriteDB.BOOK_IMAGE)));
                String bookName =cursor.getString(cursor.getColumnIndex(FavoriteDB.BOOK_NAME));
                String bookDes =cursor.getString(cursor.getColumnIndex(FavoriteDB.BOOK_DES));
                double before =cursor.getDouble(cursor.getColumnIndex(String.valueOf(FavoriteDB.BEFORE_DISCOUNT)));
                double after =cursor.getDouble(cursor.getColumnIndex(String.valueOf(FavoriteDB.SALARY)));
                String addBasket =cursor.getString(cursor.getColumnIndex(FavoriteDB.ADD_BASKET));
                String favstatus =cursor.getString(cursor.getColumnIndex(FavoriteDB.FAVORITE_STATUS));

                FavoriteBook favItem = new FavoriteBook(id ,image,bookName,bookDes,before,after,addBasket,favstatus);
                favItemList.add(favItem);
            }
        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
                db.close();
        }

        favAdapter = new FavoriteAdapter(getActivity(), favItemList);
        recyclerView.setAdapter(favAdapter);

    }
}