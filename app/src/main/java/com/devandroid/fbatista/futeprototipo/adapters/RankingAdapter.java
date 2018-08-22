package com.devandroid.fbatista.futeprototipo.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devandroid.fbatista.futeprototipo.R;
import com.devandroid.fbatista.futeprototipo.dao.RankPosition;

import java.util.ArrayList;
import java.util.List;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.MyViewHolder>{

    List<RankPosition> list;

    private static String TAG = RankingAdapter.class.getSimpleName();

    public RankingAdapter(List<RankPosition> list){
        this.list = list;
    }


    @NonNull
    @Override
    public RankingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //TODO A RECYCLER VIEW NAO ESTA CARREGANDO NO APP
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankingAdapter.MyViewHolder holder, int position) {

        RankPosition rankPosition = list.get(position);
        Log.d(TAG, "Inserting element " + rankPosition.getName());
        holder.mRank.setText(String.valueOf(rankPosition.getRank()));
        holder.mName.setText(rankPosition.getName());
        holder.mScore.setText(String.valueOf(rankPosition.getScore()));




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView mRank;
        private TextView mName;
        private TextView mScore;



        public MyViewHolder(View itemView) {
            super(itemView);

            mRank = itemView.findViewById(R.id.tv_rank);
            mName = itemView.findViewById(R.id.tv_user_name);
            mScore = itemView.findViewById(R.id.tv_score);

        }
    }

}
