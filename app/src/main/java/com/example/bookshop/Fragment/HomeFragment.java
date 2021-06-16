package com.example.bookshop.Fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bookshop.Adapters.BestSellersAdapter;
import com.example.bookshop.Adapters.NewestAdapter;
import com.example.bookshop.Helpers.BestsellersBook;
import com.example.bookshop.Helpers.NewestBook;
import com.example.bookshop.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {

    private ArrayList<NewestBook> bookNewest = new ArrayList<>();
    private ArrayList<BestsellersBook> booksellers = new ArrayList<>();
    private RecyclerView mNewest, mBestsellers;
    private TextView seeAll;

    public HomeFragment() {
        // Required empty public constructor
    }
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_home, container, false );
        findViewElements(view);
        seeAllData();
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        BestSellersData();
        NewestData();
    }

    private void findViewElements(View view) {
        mNewest = view.findViewById(R.id.recyclerNewest);
        mBestsellers = view.findViewById(R.id.recyclerBestsellers);
        seeAll = view.findViewById(R.id.homeSeeAll);
    }
    private void BestSellersData() {

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

        mBestsellers.setHasFixedSize(true);
        mBestsellers.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        mBestsellers.setAdapter(new BestSellersAdapter( booksellers, getActivity()));
    }
    private void NewestData(){
        bookNewest.add(new NewestBook("0",R.drawable.ko,"Kotlin ","Kotlin is a powerful language",36.4,40.0,"0","0"));
        bookNewest.add(new NewestBook("1",R.drawable.english,"English","Hello World",35.0,40.0,"0","0"));
        bookNewest.add(new NewestBook("2",R.drawable.java,"Java","“A few days ago I received",30.2,0.76,"0","0"));
        bookNewest.add(new NewestBook("3",R.drawable.max,"Max","Hello World",20.0,40.0,"0","0"));
        bookNewest.add(new NewestBook("4",R.drawable.mindset,"Mindset","Hello World",10.1,40.0,"0","0"));
        bookNewest.add(new NewestBook("5",R.drawable.the_7habits,"The Seven Habits","Hello",60.0,40.0,"0","0"));
        bookNewest.add(new NewestBook("6",R.drawable.the_farlex,"The Farlex","Hello World",35.4,40.0,"0","0"));
        bookNewest.add(new NewestBook("7",R.drawable.the_sustainability,"The Sustainability","Hello World",34.0,40.0,"0","0"));
        bookNewest.add(new NewestBook("8",R.drawable.eff,"I am","Hello World",40.6,40.0,"0","0"));
        bookNewest.add(new NewestBook("9",R.drawable.clean_code,"Clean Code","Hello ",3.0,0.40,"0","0"));
        bookNewest.add(new NewestBook("10",R.drawable.img3,"fav","man",2.8,40.0,"0","0"));

        mNewest.setHasFixedSize(true);
        mNewest.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        mNewest.setAdapter(new NewestAdapter( bookNewest, getActivity()));
    }
    private void seeAllData(){
        seeAll.setOnClickListener( new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                Fragment fragment = new SeeAllFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        } );
    }
}
