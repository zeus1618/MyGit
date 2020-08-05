package com.assignment.myapplication.repolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment.myapplication.R;
import com.assignment.myapplication.api.RepoInfo;

import java.util.List;

public class RepoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public List<RepoInfo> mItemsList;

    public RepoListAdapter(List<RepoInfo> list) {
        mItemsList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repo, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ItemViewHolder) {
            populateItemRows((ItemViewHolder) viewHolder, position);
        } else if (viewHolder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mItemsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mItemsList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView description;
        TextView forks;
        TextView watchers;
        TextView issues;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            forks = itemView.findViewById(R.id.fork);
            watchers = itemView.findViewById(R.id.watchers);
            issues = itemView.findViewById(R.id.issues);
        }
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed
    }

    private void populateItemRows(ItemViewHolder viewHolder, int position) {
        RepoInfo item = mItemsList.get(position);
        setText(viewHolder.name, item.getName());
        setText(viewHolder.description, item.getDescription());
        setText(viewHolder.forks, item.getForks());
        setText(viewHolder.watchers, item.getWatchers());
        setText(viewHolder.issues, item.getOpenIssues());
    }

    private void setText(TextView view, String text) {
        if (text != null && !text.isEmpty()) {
            view.setText(text);
        } else {
            view.setVisibility(View.GONE);
        }
    }
}
