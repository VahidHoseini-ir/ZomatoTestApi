package ir.vahidhoseini.testtraining1.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ir.vahidhoseini.testtraining1.R;

public class ViewHolderCollections extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView title, description ,count_places;
    ImageView imageView;
    OnClickListener onCollectionListener;

    public ViewHolderCollections(@NonNull View itemView, OnClickListener onCollectionListener) {
        super(itemView);
        this.onCollectionListener = onCollectionListener;
        count_places = itemView.findViewById(R.id.count_places);
        title = itemView.findViewById(R.id.title);
        description = itemView.findViewById(R.id.description);
        imageView = itemView.findViewById(R.id.imageView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onCollectionListener.onClickListener(getAdapterPosition());
    }
}
