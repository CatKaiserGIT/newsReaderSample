package com.example.sample.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sample.myapplication.ItemDetailActivity;
import com.example.sample.myapplication.ItemDetailFragment;
import com.example.sample.myapplication.R;
import com.example.sample.myapplication.helpers.NewsRepository;
import com.example.sample.myapplication.models.NewsViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {
    private boolean _isTwoPane;
    private AppCompatActivity _activity;

    public NewsAdapter(AppCompatActivity activity, boolean isTwoPane){
        _isTwoPane = isTwoPane;
        _activity = activity;
    }


    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_content, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NewsViewHolder holder, final int position) {
        holder.mItem = NewsRepository.I().Get().get(position);
        holder.mContentView.setText(holder.mItem.getTitle());
        ImageLoader.getInstance().displayImage(holder.mItem.getEnclosure().getLink(), holder.imageView);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_isTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putInt(ItemDetailFragment.ARG_ITEM_ID, position);
                    ItemDetailFragment fragment = new ItemDetailFragment();
                    fragment.setArguments(arguments);
                    _activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, position);

                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return NewsRepository.I().Get().size();
    }
}
