package ir.vahidhoseini.testtraining1.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ir.vahidhoseini.testtraining1.R;

public class BannerViewHolder extends RecyclerView.ViewHolder {
    ImageView mBannerView;
    public BannerViewHolder(@NonNull View itemView) {
        super(itemView);
        mBannerView = itemView.findViewById(R.id.banner_view);

    }

}
