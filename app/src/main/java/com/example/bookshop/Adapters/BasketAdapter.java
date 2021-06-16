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
import com.example.bookshop.Helpers.BasketBook;
import com.example.bookshop.Helpers.FavoriteDB;
import com.example.bookshop.R;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ViewHolder> {
    private Context context;
    private List<BasketBook> basketBooks;
    private FavoriteDB favdb;
    private SQLiteDatabase db;

    public BasketAdapter(){

    }
    public BasketAdapter(Context context, List<BasketBook> basketBooks) {
        this.context = context;
        this.basketBooks = basketBooks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.basket_item,parent,false);
        favdb = new FavoriteDB(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BasketBook book = basketBooks.get(position);

        FavStatus(book,holder);
        holder.mImageView.setImageResource(book.getImage());
        holder.bookName.setText(book.getBookName());
        holder.bookDes.setText(book.getBookDes());
        holder.before.setText("$ "+book.getBefore_discount());
        holder.after.setText("$ "+book.getAfter_discount());
        holder.counter.setText("1");
    }

    @Override
    public int getItemCount() {
        return basketBooks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView mImageView;
        private TextView before, after, bookName, bookDes, counter;
        private Button negative, add, favStatus;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            mImageView = itemView.findViewById(R.id.basketimageView);
            before = itemView.findViewById(R.id.basketbefore);
            after = itemView.findViewById(R.id.basketafter);
            bookName = itemView.findViewById(R.id.basketbookName);
            bookDes = itemView.findViewById(R.id.basketbookDes);
            counter = itemView.findViewById(R.id.counter);
            negative = itemView.findViewById(R.id.negative);
            add = itemView.findViewById(R.id.add);
            favStatus = itemView.findViewById(R.id.basketfavStatus);

            favStatus.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final BasketBook basket = basketBooks.get(position);

                    if (basket != null && basket.getFavorite_Status().equals("0")){
                        basket.setFavorite_Status("1");
                        favdb.insertIntoTheDatabase(basket.getId(),basket.getImage(),basket.getBookName(),basket.getBookDes(),basket.getAfter_discount(),basket.getBefore_discount(),basket.getAdd_basket(),basket.getFavorite_Status());
                        favStatus.setBackgroundResource(R.drawable.ic_favorite_red);
                        Toast.makeText( context, "this "+getItemCount(), Toast.LENGTH_SHORT ).show();
                    }else {
                        basket.setFavorite_Status("0");
                        favdb.removeFavoriteItem(basket.getId());
                        favStatus.setBackgroundResource(R.drawable.ic_favorite_wight);
                    }

                }
            } );
            add.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int count = Integer.parseInt(counter.getText().toString());
                    counter.setText(String.valueOf(count+1));

                }
            } );
            negative.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int count = Integer.parseInt(counter.getText().toString());
                    if (count > 1) {
                        counter.setText( String.valueOf(count - 1));
                    }

                }
            } );
        }

    }
    private void FavStatus(BasketBook book, ViewHolder holder) {
        Cursor cursor = favdb.readAllData(book.getId());
        db = favdb.getReadableDatabase();
        if (cursor != null && cursor.moveToFirst()){
            do{
                String itemFavStatus = cursor.getString(cursor.getColumnIndex(String.valueOf(FavoriteDB.FAVORITE_STATUS)));
                book.setFavorite_Status(String.valueOf(itemFavStatus));

                //check fav status
                if(itemFavStatus != null && itemFavStatus.equals("1")){
                    holder.favStatus.setBackgroundResource(R.drawable.ic_favorite_red);
                }else{
                    holder.favStatus.setBackgroundResource(R.drawable.ic_favorite_wight);
                }

            }while (cursor.moveToNext());
            db.close();
            cursor.close();
        }
    }
}
