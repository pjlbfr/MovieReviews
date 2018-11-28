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

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewsRecycleViewAdapter extends RecyclerView.Adapter<ReviewsRecycleViewAdapter.ViewHolder>{

    private List<Review> reviews;

    public ReviewsRecycleViewAdapter(List<Review> reviews) {
        this.reviews = reviews;
    }

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

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.image_review)
        ImageView imageReview;
        @BindView(R.id.text_display_title_review)
        TextView displayTitle;
        @BindView(R.id.text_summary_short_review)
        TextView summaryShort;
        @BindView(R.id.text_byline_review)
        TextView byline;
        @BindView(R.id.text_date_updated_review)
        TextView dateUpdated;
        @BindView(R.id.card_view_reviews)
        CardView cardView;

        public ViewHolder(CardView itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cardView = itemView;
        }
    }
}
