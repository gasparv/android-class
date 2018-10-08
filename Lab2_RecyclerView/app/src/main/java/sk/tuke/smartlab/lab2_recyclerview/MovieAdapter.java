package sk.tuke.smartlab.lab2_recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieHolder>{
    private List<MovieModel> _data;
    private WeakReference<Context> _context;

    public MovieAdapter(List<MovieModel> data, WeakReference<Context> contextReference) {
        _context = contextReference;
        _data = data;
    }

    public void refreshData(List<MovieModel> data){
        _data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(_context.get()).inflate(R.layout.movie_item,viewGroup,false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder movieHolder, int i) {
        movieHolder.directorName.setText(_data.get(i).directorName);
        movieHolder.releaseYear.setText(_data.get(i).releaseYear);
        movieHolder.title.setText(_data.get(i).title);
    }

    @Override
    public int getItemCount() {
        if(_data!=null)
        return _data.size();
        return 0;
    }
}
