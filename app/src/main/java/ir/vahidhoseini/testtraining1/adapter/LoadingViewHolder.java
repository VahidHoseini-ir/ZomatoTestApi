package ir.vahidhoseini.testtraining1.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ir.vahidhoseini.testtraining1.R;

public class LoadingViewHolder extends RecyclerView.ViewHolder {
    TextView textview;
    public LoadingViewHolder(@NonNull View itemView) {
        super(itemView);
        textview = itemView.findViewById(R.id.textview);
    }
}
