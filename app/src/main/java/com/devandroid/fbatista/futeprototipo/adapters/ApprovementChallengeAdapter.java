package com.devandroid.fbatista.futeprototipo.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devandroid.fbatista.futeprototipo.R;
import com.devandroid.fbatista.futeprototipo.dao.ParticipationChallenge;

import java.util.List;

public class ApprovementChallengeAdapter extends RecyclerView.Adapter<ApprovementChallengeAdapter.MyViewHolder> {

    private List<ParticipationChallenge> mList;

    public ApprovementChallengeAdapter(List<ParticipationChallenge> list){

        this.mList = list;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.approvement_challenge_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ParticipationChallenge participationChallenge = mList.get(position);

        holder.mChallengeName.setText(participationChallenge.getDescription());
        holder.mUserName.setText(participationChallenge.getNomeUser());


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView mUserName;
        private TextView mChallengeName;

        public MyViewHolder(View itemView) {
            super(itemView);
            mUserName = itemView.findViewById(R.id.tv_user_name);
            mChallengeName = itemView.findViewById(R.id.tv_challenge_name);

        }
    }
}
