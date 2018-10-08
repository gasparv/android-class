package sk.tuke.smartlab.lab2_recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MovieHolder extends RecyclerView.ViewHolder {
    TextView title;
    TextView releaseYear;
    TextView directorName;

    public MovieHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.tv_title);
        releaseYear = itemView.findViewById(R.id.tv_released);
        directorName = itemView.findViewById(R.id.tv_director);
    }
}
