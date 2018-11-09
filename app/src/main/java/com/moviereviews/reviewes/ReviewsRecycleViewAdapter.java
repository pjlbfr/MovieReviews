package com.moviereviews.reviewes;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moviereviews.R;
import com.moviereviews.objectresponse.MultimediaReview;
import com.moviereviews.objectresponse.Review;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ReviewsRecycleViewAdapter extends RecyclerView.Adapter<ReviewsRecycleViewAdapter.ViewHolder>{

    private List<Review> reviews = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MultimediaReview multimedia = reviews.get(position).getMultimedia();
        if (multimedia != null)
            Picasso.get()
                   .load(reviews.get(position).getMultimedia().getSrc())
                   .into(holder.imageReview);
        else
            holder.imageReview.setImageResource(R.drawable.review_default);

        holder.displayTitle.setText(reviews.get(position).getDisplayTitle());
        holder.summaryShort.setText(reviews.get(position).getSummaryShort());
        holder.byline.setText(reviews.get(position).getByline());
        holder.dateUpdated.setText(reviews.get(position).getDateUpdated().replace("-", "/"));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public void setData(List<Review> list){
        reviews.addAll(list);
        notifyDataSetChanged();
    }

    public void clear(){
        reviews.clear();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageReview;
        private TextView displayTitle;
        private TextView summaryShort;
        private TextView byline;
        private TextView dateUpdated;
        private CardView cardView;

        public ViewHolder(CardView itemView) {
            super(itemView);
            cardView = itemView;
            imageReview = (ImageView) cardView.findViewById(R.id.image_review);
            displayTitle = (TextView) cardView.findViewById(R.id.text_display_title_review);
            summaryShort = (TextView) cardView.findViewById(R.id.text_summary_short_review);
            byline = (TextView) cardView.findViewById(R.id.text_byline_review);
            dateUpdated = (TextView) cardView.findViewById(R.id.text_date_updated_review);
        }
    }
}
