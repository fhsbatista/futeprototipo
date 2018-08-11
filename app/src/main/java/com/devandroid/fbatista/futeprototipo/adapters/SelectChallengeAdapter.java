package com.devandroid.fbatista.futeprototipo.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devandroid.fbatista.futeprototipo.R;
import com.devandroid.fbatista.futeprototipo.dao.Challenge;

import java.util.List;

public class SelectChallengeAdapter extends RecyclerView.Adapter<SelectChallengeAdapter.ViewHolder> {

    private List<Challenge> mChallenges;

    public SelectChallengeAdapter(List<Challenge> challenges){
        this.mChallenges = challenges;
    }


    @NonNull
    @Override
    public SelectChallengeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.challenge_layout, parent, false);

        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull SelectChallengeAdapter.ViewHolder holder, int position) {


        Challenge challenge = this.mChallenges.get(position);

        holder.title.setText(challenge.getTitle());
        holder.level.setText(String.valueOf(challenge.getLevel()));




    }

    @Override
    public int getItemCount() {
        return this.mChallenges.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView title;
        private TextView level;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_challenge_description);
            level = itemView.findViewById(R.id.tv_challenge_level);
        }


    }
}
