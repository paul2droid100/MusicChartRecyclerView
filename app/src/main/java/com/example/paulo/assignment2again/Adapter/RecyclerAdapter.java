package com.example.paulo.assignment2again.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.paulo.assignment2again.Model.Muscian;
import com.example.paulo.assignment2again.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import static android.support.v4.content.ContextCompat.startActivity;



public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private Context mContext;
    private final  ArrayList<Muscian> mMusician = new ArrayList<>();


    public RecyclerAdapter(Context context) {

        this.mContext = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.artist_viewholder, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {

        final Context context = holder.mImageArtist.getContext();
        Picasso.with(context).load(mMusician.get(position).getArtworkUrl60()).into(holder.mImageArtist);
        holder.mNameArtist.setText("Musician: "+mMusician.get(position).getArtistName());
        holder.mPriceArtist.setText("Price: £"+mMusician.get(position).getTrackPrice());
        holder.mContentArtist.setText(mMusician.get(position).getCollectionName());
        System.out.println(mMusician.get(position).getCollectionName());
        holder.CL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    play(mMusician, position);
                }
            });

    }


    private void play(ArrayList<Muscian> mMusician, int position){
       //Checking for network connection
        if(isNetworkConnected(mContext)) {
            String url = mMusician.get(position).getPreviewUrl();
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setDataAndType(uri, "audio/m4a");
            startActivity(mContext, intent, null);
        }else{
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
            mBuilder.setTitle("CONNECTION");
            mBuilder.setMessage("No Network Connectivity");
            mBuilder.setCancelable(false);
            mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            mBuilder.show();
        }
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageArtist;
        TextView mNameArtist;
        TextView mContentArtist;
        TextView mPriceArtist;
        ConstraintLayout CL;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            CL = itemView.findViewById(R.id.artist_holder);
            mImageArtist = itemView.findViewById(R.id.artist_imageView);
            mNameArtist = itemView.findViewById(R.id.name_textView);
            mContentArtist = itemView.findViewById(R.id.contentname_textview);
            mPriceArtist = itemView.findViewById(R.id.price_textview);
        }
    }


    public void addMoreMusicians(ArrayList<Muscian> muscians) {
        mMusician.addAll(muscians);
        notifyDataSetChanged();
    }


    public void removeAllMusicians() {
        mMusician.clear();
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mMusician.size();
    }

    public static boolean isNetworkConnected(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}
