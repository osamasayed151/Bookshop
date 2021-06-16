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

import com.example.bookshop.Helpers.FavoriteBook;
import com.example.bookshop.Helpers.FavoriteDB;
import com.example.bookshop.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.viewHolder> {

    private Context context;
    private List<FavoriteBook> mFavoriteBook;
    private FavoriteDB mFavoriteDB;
    private SQLiteDatabase db;

    public FavoriteAdapter(Context context, List<FavoriteBook> mFavoriteBook) {
        this.context = context;
        this.mFavoriteBook = mFavoriteBook;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.favorite_item,parent,false);
        mFavoriteDB = new FavoriteDB(context);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        FavoriteBook fav = mFavoriteBook.get(position);

            sureAddbasket( fav, holder );
            holder.mImageView.setImageResource( fav.getImage() );
            holder.before.setText( "$ " + fav.getBefore_discount() );
            holder.after.setText( "$ " + fav.getAfter_discount() );
            holder.bookName.setText( fav.getBookName() );
            holder.bookDes.setText( fav.getBookDes() );
    }

    @Override
    public int getItemCount() {
        return mFavoriteBook.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        private ImageView mImageView;
        private TextView before, after, bookName, bookDes;
        private Button favStatus, addBasket;
        public viewHolder(@NonNull View itemView) {
            super( itemView );
            mImageView = itemView.findViewById(R.id.imageView);
            before = itemView.findViewById(R.id.before);
            after = itemView.findViewById(R.id.after);
            bookName = itemView.findViewById(R.id.bookName);
            bookDes = itemView.findViewById(R.id.bookDes);
            favStatus = itemView.findViewById(R.id.favStatus);
            addBasket = itemView.findViewById(R.id.addBasket);

            favStatus.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final FavoriteBook favoriteBook = mFavoriteBook.get(position);
                    mFavoriteDB.removeFavoriteItem(favoriteBook.getId());
                    removeBook(position);
                }
            } );

            addBasket.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    FavoriteBook book = mFavoriteBook.get(position);
                    if (book != null && book.getAddBasket().equals("0")){
                        book.setAddBasket("1");
                        mFavoriteDB.insertIntoTheDatabase(book.getId(),book.getImage(),book.getBookName(), book.getBookDes(),book.getAfter_discount(),book.getBefore_discount(),book.getAddBasket(),book.getFavStatus());
                        addBasket.setText("Added");
                        addBasket.setBackgroundResource(R.color.d);
                    }else{
                        book.setAddBasket("0");
                        mFavoriteDB.removeBasketItem(book.getId());
                        addBasket.setText("Add Basket");
                        addBasket.setBackgroundResource(R.color.text_signup);
                    }
                }
            } );
        }
    }

    private void removeBook(int position) {
        mFavoriteBook.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeRemoved(position,mFavoriteBook.size());
    }

    private void sureAddbasket(FavoriteBook fav, viewHolder holder) {
        Cursor cursor = mFavoriteDB.readAllData(fav.getId());
        db = mFavoriteDB.getReadableDatabase();
        if (cursor != null && cursor.moveToFirst()){
            do{
                String itemAddBasket = cursor.getString(cursor.getColumnIndex(String.valueOf(FavoriteDB.ADD_BASKET)));
                fav.setAddBasket(String.valueOf(itemAddBasket));
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
