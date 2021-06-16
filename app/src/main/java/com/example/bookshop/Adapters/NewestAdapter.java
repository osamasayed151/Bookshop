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
import com.example.bookshop.Helpers.FavoriteDB;
import com.example.bookshop.Helpers.NewestBook;
import com.example.bookshop.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewestAdapter extends RecyclerView.Adapter<NewestAdapter.ViewHolder> {

    private ArrayList<NewestBook> mBookNewest;
    private Context context;
    private FavoriteDB mFavoriteDB;

    private SharedPreferences sharedPreferences;
    public String FIRST_START = "firstStart";
    private SQLiteDatabase db;

    public NewestAdapter(){

    }
    public NewestAdapter(ArrayList<NewestBook> mBookNewest, Context context) {
        this.mBookNewest = mBookNewest;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mFavoriteDB = new FavoriteDB(context);
        // prefs
        sharedPreferences = context.getSharedPreferences("prefs",Context.MODE_PRIVATE);
        // firstStart
        boolean start =sharedPreferences.getBoolean("firstStart",true);
        if (start){
            createTableOnFirstStart();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.recycler_newest_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final NewestBook book = mBookNewest.get(position);
        readCursorData(book,holder);
        holder.mImageView.setImageResource(book.getImage());
        holder.bookName.setText(book.getBookName());
        holder.bookDes.setText(book.getBookDes());
        holder.salary.setText("$ "+book.getSalary());
        holder.befor.setText(String.valueOf(book.getBefore()));


    }
    @Override
    public int getItemCount() {
        return mBookNewest.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private TextView bookName, bookDes, salary, befor;
        private Button addBasket, favStatus;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            mImageView = itemView.findViewById(R.id.imageView);
            bookName = itemView.findViewById(R.id.bookName);
            bookDes = itemView.findViewById(R.id.bookDes);
            salary = itemView.findViewById(R.id.salary);
            addBasket = itemView.findViewById(R.id.addBasket);
            favStatus = itemView.findViewById(R.id.favoriteStatus);
            befor = itemView.findViewById(R.id.before2);

            favStatus.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    NewestBook book = mBookNewest.get(position);
                    if (book != null && book.getFavStatus().equals("0")){
                        book.setFavStatus("1");
                        mFavoriteDB.insertIntoTheDatabase(book.getId(),book.getImage(),book.getBookName(),
                                book.getBookDes(),book.getSalary(),book.getBefore(),book.getAddBasket(),book.getFavStatus());
                        favStatus.setBackgroundResource(R.drawable.ic_favorite_red);
                    }else {
                        book.setFavStatus("0");
                        mFavoriteDB.removeFavoriteItem(book.getId());
                        favStatus.setBackgroundResource(R.drawable.ic_favorite_wight);
                    }
                }
            } );
            addBasket.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    NewestBook books = mBookNewest.get(position);
                    if ( books != null && books.getAddBasket().equals("0")){
                        books.setAddBasket("1");
                        mFavoriteDB.insertIntoTheDatabase(books.getId(),books.getImage(),books.getBookName(),
                                books.getBookDes(),books.getSalary(),books.getBefore(),books.getAddBasket(),books.getFavStatus());
                        addBasket.setText("Added");
                        addBasket.setBackgroundResource(R.color.d);
                    }else{
                        books.setAddBasket("0");
                        mFavoriteDB.removeBasketItem(books.getId());
                        addBasket.setText("Add Basket");
                        addBasket.setBackgroundResource(R.color.text_signup);

                    }
                }
            } );
        }
    }

    private void createTableOnFirstStart() {
        mFavoriteDB.insertEmpty();
        sharedPreferences = context.getSharedPreferences("prefs",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firstStart",false);
        editor.apply();
    }
    private void readCursorData(NewestBook book, ViewHolder holder) {
        Cursor cursor = mFavoriteDB.readAllData(book.getId());
        db = mFavoriteDB.getReadableDatabase();
        if (cursor != null && cursor.moveToFirst()){
            do{
                String itemFavStatus = cursor.getString(cursor.getColumnIndex(String.valueOf(FavoriteDB.FAVORITE_STATUS)));
                String itemAddBasket = cursor.getString(cursor.getColumnIndex(String.valueOf(FavoriteDB.ADD_BASKET)));
                book.setFavStatus(String.valueOf(itemFavStatus));
                book.setAddBasket(String.valueOf(itemAddBasket));

                //check fav status
                if(itemFavStatus != null && itemFavStatus.equals("1")){
                    holder.favStatus.setBackgroundResource(R.drawable.ic_favorite_red);
                }else{
                    holder.favStatus.setBackgroundResource(R.drawable.ic_favorite_wight);
                }
                // check add basket
                if (itemAddBasket != null && itemAddBasket.equals("1")){
                    holder.addBasket.setText("Added");
                    holder.addBasket.setBackgroundResource(R.color.d);
                }else {
                    holder.addBasket.setText("Add Basket");
                    holder.addBasket.setBackgroundResource(R.color.text_signup);
                }
            }while (cursor.moveToNext());
            db.close();
            cursor.close();
        }
    }

}
