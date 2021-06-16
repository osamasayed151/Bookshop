package com.example.bookshop.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookshop.Helpers.BestsellersBook;
import com.example.bookshop.Helpers.FavoriteDB;
import com.example.bookshop.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SeeAllAdapter extends RecyclerView.Adapter<SeeAllAdapter.ViewHolder>{

    private Context context;
    private ArrayList<BestsellersBook> newestList;
    private FavoriteDB favdb;
    private SQLiteDatabase db;

    public SeeAllAdapter(Context context, ArrayList<BestsellersBook> newestsList) {
        this.context = context;
        this.newestList = newestsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.see_all,parent,false);
        favdb = new FavoriteDB(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BestsellersBook book = newestList.get(position);

        FavAndAdd(book,holder);
        holder.mImageView.setImageResource(book.getImage());
        holder.bookName.setText(book.getBookName());
        holder.bookDes.setText(book.getBookDes());
        holder.salary.setText("$ "+book.getSalary());
    }

    @Override
    public int getItemCount() {
        return newestList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView bookName, bookDes, salary;
        private Button addbasket, favStatus;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            mImageView = itemView.findViewById(R.id.seeAllImageView);
            bookName = itemView.findViewById(R.id.seeAllBookName);
            bookDes = itemView.findViewById(R.id.seeAllBookDes);
            salary = itemView.findViewById(R.id.seeAllSalary);
            addbasket = itemView.findViewById(R.id.seeAllAddBasket);
            favStatus = itemView.findViewById(R.id.seeAllFavStatus);

            favStatus.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final BestsellersBook news = newestList.get(position);
                    if (news != null && news.getFavStatus().equals("0")){
                        news.setFavStatus("1");
                        favdb.insertIntoTheDatabase(news.getId(),news.getImage(),news.getBookName(),news.getBookDes(),news.getSalary(),news.getBefore(),news.getAddBasket(),news.getFavStatus());
                        favStatus.setBackgroundResource(R.drawable.ic_favorite_red);
                        Toast.makeText( context, "this "+getItemCount(), Toast.LENGTH_SHORT ).show();
                    }else {
                        news.setFavStatus("0");
                        favdb.removeFavoriteItem(news.getId());
                        favStatus.setBackgroundResource(R.drawable.ic_favorite_wight);
                    }
                }
            });
            addbasket.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    BestsellersBook news = newestList.get(position);
                    if (news != null && news.getAddBasket().equals("0")){
                        news.setAddBasket("1");
                        favdb.insertIntoTheDatabase(news.getId(),news.getImage(),news.getBookName(),news.getBookDes(),news.getSalary(),news.getBefore(),news.getAddBasket(),news.getFavStatus());
                        addbasket.setText("Added");
                        addbasket.setBackgroundResource(R.color.d);
                    }else{
                        news.setAddBasket("0");
                        favdb.removeBasketItem(news.getId());
                        addbasket.setText("Add Basket");
                        addbasket.setBackgroundResource(R.color.text_signup);
                    }
                }
            });
        }
    }

    private void FavAndAdd(BestsellersBook book, ViewHolder holder) {
        Cursor cursor = favdb.readAllData(book.getId());
        db = favdb.getReadableDatabase();
        if (cursor != null && cursor.moveToFirst()){
            do{
                String itemAddBasket = cursor.getString(cursor.getColumnIndex(String.valueOf(FavoriteDB.ADD_BASKET)));
                String favStatus = cursor.getString(cursor.getColumnIndex(String.valueOf(FavoriteDB.FAVORITE_STATUS)));
                book.setFavStatus(String.valueOf(favStatus));
                book.setAddBasket(String.valueOf(itemAddBasket));

                // check add favStatus
                if(favStatus != null && favStatus.equals("1")){
                    holder.favStatus.setBackgroundResource(R.drawable.ic_favorite_red);
                }else{
                    holder.favStatus.setBackgroundResource(R.drawable.ic_favorite_wight);
                }

                // check add basket
                if (itemAddBasket != null && itemAddBasket.equals("1")){
                    holder.addbasket.setText("Added");
                    holder.addbasket.setBackgroundResource(R.color.d);
                }else {
                    holder.addbasket.setText("Add Basket");
                    holder.addbasket.setBackgroundResource(R.color.text_signup);
                }
            }while (cursor.moveToNext());
            db.close();
            cursor.close();
        }
    }
}
