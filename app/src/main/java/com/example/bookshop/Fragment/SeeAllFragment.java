package com.example.bookshop.Fragment;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookshop.Adapters.BestSellersAdapter;
import com.example.bookshop.Helpers.BestsellersBook;
import com.example.bookshop.Helpers.FavoriteDB;
import com.example.bookshop.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SeeAllFragment extends Fragment {

    private ArrayList<BestsellersBook> booksellers = new ArrayList<>(  );
    private RecyclerView mRvSeeAll;
    private BestSellersAdapter adapter;

    public SeeAllFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate( R.layout.fragment_see_all, container, false );
        mRvSeeAll = view.findViewById(R.id.RVSeeAll);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        dataBestSellers();
    }
    private void dataBestSellers(){
        booksellers.add(new BestsellersBook("9",R.drawable.clean_code,"Clean Code","Hello ",3.0,0.40,"0","0"));
        booksellers.add(new BestsellersBook("4",R.drawable.mindset,"Mindset","Hello World",10.1,40.0,"0","0"));
        booksellers.add(new BestsellersBook("7",R.drawable.the_sustainability,"The Sustainability","Hello World",34.0,40.0,"0","0"));
        booksellers.add(new BestsellersBook("10",R.drawable.img3,"fav","man",2.8,40.0,"0","0"));
        booksellers.add(new BestsellersBook("1",R.drawable.english,"English","Hello World",35.0,40.0,"0","0"));
        booksellers.add(new BestsellersBook("0",R.drawable.ko,"Kotlin ","Kotlin is a powerful language",36.4,40.0,"0","0"));
        booksellers.add(new BestsellersBook("3",R.drawable.max,"Max","Hello World",20.0,40.0,"0","0"));
        booksellers.add(new BestsellersBook("5",R.drawable.the_7habits,"The Seven Habits","Hello",60.0,40.0,"0","0"));
        booksellers.add(new BestsellersBook("2",R.drawable.java,"Java","“A few days ago I received",30.2,0.76,"0","0"));
        booksellers.add(new BestsellersBook("6",R.drawable.the_farlex,"The Farlex","Hello World",35.4,40.0,"0","0"));
        booksellers.add(new BestsellersBook("8",R.drawable.eff,"I am","Hello World",40.6,40.0,"0","0"));

        adapter = new BestSellersAdapter(booksellers,getActivity());
        mRvSeeAll.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new GridLayoutManager(getActivity(),2,RecyclerView.VERTICAL,false);
        mRvSeeAll.setLayoutManager(lm);
        mRvSeeAll.setAdapter(adapter);
    }
}
