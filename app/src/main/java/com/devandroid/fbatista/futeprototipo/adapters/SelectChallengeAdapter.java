package com.devandroid.fbatista.futeprototipo.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devandroid.fbatista.futeprototipo.R;
import com.devandroid.fbatista.futeprototipo.activities.SelectChallengeActivity;
import com.devandroid.fbatista.futeprototipo.dao.Challenge;
import com.devandroid.fbatista.futeprototipo.dao.ChallengeForShowing;
import com.devandroid.fbatista.futeprototipo.dao.ParticipationChallenge;

import org.w3c.dom.Text;

import java.util.List;

public class SelectChallengeAdapter extends RecyclerView.Adapter<SelectChallengeAdapter.ViewHolder> {

    private List<ParticipationChallenge> mChallenges;

    public SelectChallengeAdapter(List<ParticipationChallenge> challenges){
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


        ParticipationChallenge challenge = this.mChallenges.get(position);

        holder.title.setText(challenge.getTitle());
        holder.level.setText(String.valueOf(challenge.getLevel()));


        switch (challenge.getStatus()) {
            case "STATUS_APPROVED" :
//                holder.status.getContext().getDrawable(R.drawable.challenge_status_shape).setTint(Color.parseColor("#00c853"));
                holder.status.setBackgroundColor(Color.parseColor("#00c853"));
                holder.status.setText("Aprovado");
                break;

            case "STATUS_WAITING_APPROVEMENT" :
//                holder.status.getContext().getDrawable(R.drawable.challenge_status_shape).setTint(Color.parseColor("#ff6d00"));
                holder.status.setBackgroundColor(Color.parseColor("#ff6d00"));
                holder.status.setText("Aguardando");
                break;

            case "STATUS_REJECTED" :
//                holder.status.getContext().getDrawable(R.drawable.challenge_status_shape).setTint(Color.parseColor("#ff1744"));
                holder.status.setBackgroundColor(Color.parseColor("#ff1744"));
                holder.status.setText("Nao aprovado");
                break;

            case "STATUS_NOT_STARTED" :
//                holder.status.getContext().getDrawable(R.drawable.challenge_status_shape).setTint(Color.parseColor("#0091ea"));
                holder.status.setBackgroundColor(Color.parseColor("#0091ea"));
                holder.status.setText("Nao iniciado");
                break;
        }


    }



    @Override
    public int getItemCount() {
        return this.mChallenges.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView title;
        private TextView level;
        private TextView status;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_challenge_description);
            level = itemView.findViewById(R.id.tv_challenge_level);
            status = itemView.findViewById(R.id.tv_challenge_status);
        }


    }


}
