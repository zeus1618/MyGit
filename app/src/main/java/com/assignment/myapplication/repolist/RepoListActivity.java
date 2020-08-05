package com.assignment.myapplication.repolist;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment.myapplication.R;
import com.assignment.myapplication.api.RepoInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepoListActivity extends AppCompatActivity {

    public static final String EXTRA_USER_NAME = "RepoListActivity.username";

    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    RepoListPresenter mPresenter;
    boolean isLoading = false;
    private RepoListAdapter mAdapter;
    private List<RepoInfo> mRepoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_list);
        ButterKnife.bind(this);

        mPresenter = new RepoListPresenter(this);

        initScrollListener();
        showLoading();
        mPresenter.loadRepo();
    }

    public String getUserName(){
        return getIntent().getStringExtra(EXTRA_USER_NAME);
    }

    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    public void showList(List<RepoInfo> repoList) {
        mRepoList = repoList;
        mAdapter = new RepoListAdapter(mRepoList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }

    private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == mRepoList.size() - 1) {
                        //bottom of list!
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });


    }

    private void loadMore() {
        mRepoList.add(null);
        mAdapter.notifyItemInserted(mRepoList.size() - 1);
        mPresenter.loadMore();

    }

    public void addList(List<RepoInfo> newItems) {
        mRepoList.remove(mRepoList.size() - 1);
        int scrollPosition = mRepoList.size();
        mAdapter.notifyItemRemoved(scrollPosition);

        mRepoList.addAll(newItems);
        mAdapter.notifyDataSetChanged();
        isLoading = false;
    }
}
