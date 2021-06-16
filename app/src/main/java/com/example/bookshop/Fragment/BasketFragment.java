package com.example.bookshop.Fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bookshop.Adapters.BasketAdapter;
import com.example.bookshop.Adapters.FavoriteAdapter;
import com.example.bookshop.Helpers.BasketBook;
import com.example.bookshop.Helpers.FavoriteDB;
import com.example.bookshop.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class BasketFragment extends androidx.fragment.app.Fragment {

    private FavoriteDB favdb;
    private List<BasketBook> basketBooks = new ArrayList<>();
    private BasketAdapter basketAdapter;

    private RecyclerView recyclerView;

    public BasketFragment() {
        // Required empty public constructor
    }

    public static BasketFragment newInstance(String param1, String param2) {
        BasketFragment fragment = new BasketFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view = inflater.inflate( R.layout.fragment_basket, container, false );
        recyclerView = view.findViewById(R.id.basket_rv);
        favdb = new FavoriteDB(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fullData();
        return view;
    }

    private void fullData() {
        if (basketBooks != null) {
            basketBooks.clear();
        }
        SQLiteDatabase db = favdb.getReadableDatabase();
        Cursor cursor = favdb.selectAllBasketItem();
        if (cursor != null && cursor.moveToFirst()){
            do{
                String id = cursor.getString(cursor.getColumnIndex(FavoriteDB.ID));
                int image = cursor.getInt(cursor.getColumnIndex(FavoriteDB.BOOK_IMAGE));
                String bookName = cursor.getString(cursor.getColumnIndex(FavoriteDB.BOOK_NAME));
                String bookDes = cursor.getString(cursor.getColumnIndex(FavoriteDB.BOOK_DES));
                double after = cursor.getDouble(cursor.getColumnIndex(FavoriteDB.SALARY));
                double before = cursor.getDouble(cursor.getColumnIndex(FavoriteDB.BEFORE_DISCOUNT));
                String favStatus = cursor.getString(cursor.getColumnIndex(FavoriteDB.FAVORITE_STATUS));
                String addbasket = cursor.getString(cursor.getColumnIndex(FavoriteDB.ADD_BASKET));

                BasketBook basketItem = new BasketBook(id,image,bookName,bookDes,after,before,favStatus,addbasket);
                basketBooks.add(basketItem);

            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        basketAdapter = new BasketAdapter(getActivity(),basketBooks);
        recyclerView.setAdapter(basketAdapter);
    }
}
