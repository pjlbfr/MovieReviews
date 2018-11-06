package com.moviereviews.critics;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moviereviews.R;
import com.moviereviews.interfaces.OnCriticCardClickListener;
import com.moviereviews.objectresponse.Critic;
import com.moviereviews.objectresponse.MultimediaCritic;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CriticsRecycleViewAdapter extends RecyclerView.Adapter<CriticsRecycleViewAdapter.ViewHolder> {

    private List<Critic> critics = new ArrayList<>();
    private OnCriticCardClickListener onCriticCardClickListener;

    public void setOnCriticCardClickListener(OnCriticCardClickListener listener){
        this.onCriticCardClickListener = listener;
    }

    @NonNull
    @Override
    public CriticsRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_critic, parent, false);
        return new CriticsRecycleViewAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull CriticsRecycleViewAdapter.ViewHolder holder, int position) {
        final Critic critic = critics.get(position);
        // подгрузка фотографии критика, если есть ссылка на нее,
        // иначе устанавливается critic_default.png
        final MultimediaCritic multimedia = critic.getMultimedia();
        if (multimedia != null)
            Picasso.get()
                   .load(multimedia.getResource().getSrc())
                   .into(holder.imageCritic);
        else
            holder.imageCritic.setImageResource(R.drawable.critic_default);
        holder.displayName.setText(critic.getDisplay_name());


    }

    @Override
    public int getItemCount() {
        return critics.size();
    }

    public void setData(List<Critic> list){
        critics.addAll(list);
        notifyDataSetChanged();
    }

    public void clear(){
        critics.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView displayName;
        private ImageView imageCritic;
        private CardView cardView;

        public ViewHolder(CardView itemView) {
            super(itemView);
            cardView = itemView;
            imageCritic = (ImageView) cardView.findViewById(R.id.image_critic);
            displayName= (TextView) cardView.findViewById(R.id.tv_name_critic);
            // обработка нажатия на карточку критика
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onCriticCardClickListener.onClick(critics.get(getAdapterPosition()));
                }
            });
        }
    }
}

