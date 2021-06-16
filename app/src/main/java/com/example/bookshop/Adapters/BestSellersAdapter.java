package com.example.bookshop.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookshop.Helpers.BestsellersBook;
import com.example.bookshop.Helpers.FavoriteDB;
import com.example.bookshop.R;
import com.example.bookshop.welcome.Login;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BestSellersAdapter extends RecyclerView.Adapter<BestSellersAdapter.ViewHolder> {

    private ArrayList<BestsellersBook> bestsellers;
    private Context context;
    private FavoriteDB favdb;
    private SQLiteDatabase db;
    private String BESTSELLERS = "bestsellers";

    public BestSellersAdapter(ArrayList<BestsellersBook> bestsellers, Context context) {
        this.bestsellers = bestsellers;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        favdb = new FavoriteDB(context);
        // prefs
        SharedPreferences sharedPreferences = context.getSharedPreferences( Login.BOOK_FILE,Context.MODE_PRIVATE);
        // firstStart
        boolean start =sharedPreferences.getBoolean(BESTSELLERS,true);
        if (start){
            createTableOnFirstStart();
        }

        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.see_all,parent,false);
        favdb = new FavoriteDB(context);
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BestsellersBook best = bestsellers.get(position);

        FavoriteAndBasket(best,holder);
        holder.img.setImageResource(best.getImage());
        holder.bookName.setText(best.getBookName());
        holder.bookDes.setText(best.getBookDes());
        holder.bookSalary.setText(String.valueOf(best.getSalary()));
        holder.before.setText(String.valueOf(best.getBefore()));
    }

    @Override
    public int getItemCount() {
        return bestsellers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView bookName, bookDes, bookSalary, before;
        private Button addBasket, favStatus;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            img = itemView.findViewById(R.id.seeAllImageView);
            bookName = itemView.findViewById(R.id.seeAllBookName);
            bookDes = itemView.findViewById(R.id.seeAllBookDes);
            bookSalary = itemView.findViewById(R.id.seeAllSalary);
            before = itemView.findViewById(R.id.seeAllBefore);
            addBasket = itemView.findViewById(R.id.seeAllAddBasket);
            favStatus = itemView.findViewById(R.id.seeAllFavStatus);


            favStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    BestsellersBook best = bestsellers.get(position);

                    if (best.getFavStatus() != null && best.getFavStatus().equals("0")){
                        best.setFavStatus("1");
                        favdb.insertIntoTheDatabase(best.getId(),best.getImage(),best.getBookName(), best.getBookDes(),best.getSalary(),best.getBefore(),best.getAddBasket(),best.getFavStatus());
                        favStatus.setBackgroundResource(R.drawable.ic_favorite_red);
                    }else {
                        best.setFavStatus("0");
                        favdb.removeFavoriteItem(best.getId());
                        favStatus.setBackgroundResource(R.drawable.ic_favorite_wight);
                    }
                }
            } );

            addBasket.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    BestsellersBook best = bestsellers.get(position);

                    if (best.getAddBasket() != null && best.getAddBasket().equals("0")){
                        best.setAddBasket("1");
                        favdb.insertIntoTheDatabase(best.getId(),best.getImage(),best.getBookName(), best.getBookDes(),best.getSalary(),best.getBefore(),best.getAddBasket(),best.getFavStatus());
                        addBasket.setText("Added");
                        addBasket.setBackgroundResource(R.color.d);
                    }else {
                        best.setFavStatus("0");
                        favdb.removeBasketItem(best.getId());
                        addBasket.setText("Add to Basket");
                        addBasket.setBackgroundResource(R.color.text_signup);
                    }
                }
            } );
        }
    }
    private void createTableOnFirstStart() {
        favdb.insertEmpty();
        SharedPreferences sharedPreferences = context.getSharedPreferences(Login.BOOK_FILE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(BESTSELLERS,false);
        editor.apply();
    }
    private void FavoriteAndBasket(BestsellersBook best, ViewHolder holder) {
        db = favdb.getReadableDatabase();
        Cursor cursor = favdb.readAllData(best.getId());
        if (cursor != null && cursor.moveToFirst()){
            do{
                String itemFavorite = cursor.getString(cursor.getColumnIndex(FavoriteDB.FAVORITE_STATUS));
                String itemAddBasket = cursor.getString(cursor.getColumnIndex(FavoriteDB.ADD_BASKET));
                best.setFavStatus(itemFavorite);
                best.setAddBasket(itemAddBasket);

                if (itemFavorite != null && itemFavorite.equals("1")){
                    holder.favStatus.setBackgroundResource(R.drawable.ic_favorite_red);
                }else{
                    holder.favStatus.setBackgroundResource(R.drawable.ic_favorite_wight);
                }
                if(itemAddBasket != null && itemAddBasket.equals("1")){
                    holder.addBasket.setText("Added");
                    holder.addBasket.setBackgroundResource(R.color.d);
                }else{
                    holder.addBasket.setText("Add Basket");
                    holder.addBasket.setBackgroundResource(R.color.text_signup);
                }

            }while(cursor.moveToNext());
            cursor.close();
            db.close();
        }
    }
}
