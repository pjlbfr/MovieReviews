package com.moviereviews.critics;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class CriticsRecycleViewAdapter extends RecyclerView.Adapter<CriticsRecycleViewAdapter.ViewHolder> {

    private List<Critic> critics;
    private OnCriticCardClickListener onCriticCardClickListener;

    public CriticsRecycleViewAdapter(List<Critic> critics, OnCriticCardClickListener listener) {
        this.critics = new ArrayList<>(critics);
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
        Critic critic = critics.get(position);
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

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_name_critic)
        TextView displayName;
        @BindView(R.id.image_critic)
        ImageView imageCritic;

       private  CardView cardView;

        public ViewHolder(CardView itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cardView = itemView;
            // обработка нажатия на карточку критика
            cardView.setOnClickListener(view -> onCriticCardClickListener.onClick(critics.get(getAdapterPosition())));
        }
    }
}

