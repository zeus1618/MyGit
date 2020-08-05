package com.assignment.myapplication.repolist;

import android.widget.Toast;

import com.assignment.myapplication.api.GitServices;
import com.assignment.myapplication.api.RepoInfo;
import com.assignment.myapplication.api.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class RepoListPresenter {
    RepoListActivity mView;
    final int size = 10;
    int page = 1;

    private final GitServices service;

    RepoListPresenter(RepoListActivity activity){
        mView = activity;
        service = RetrofitClient.getRetrofitInstance().create(GitServices.class);
    }

    public void loadRepo() {
        String userName = mView.getUserName();
        mView.showLoading();

        Call<List<RepoInfo>> call = service.getRepoList(userName, page, size);
        call.enqueue(new Callback<List<RepoInfo>>() {
            @Override
            public void onResponse(Call<List<RepoInfo>> call, Response<List<RepoInfo>> response) {
                mView.hideLoading();
                mView.showList(response.body());
            }

            @Override
            public void onFailure(Call<List<RepoInfo>> call, Throwable t) {
                Toast.makeText(mView, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadMore() {
        String userName = mView.getUserName();
        ++page;

        Call<List<RepoInfo>> call = service.getRepoList(userName, page, size);
        call.enqueue(new Callback<List<RepoInfo>>() {
            @Override
            public void onResponse(Call<List<RepoInfo>> call, Response<List<RepoInfo>> response) {
                mView.hideLoading();
                mView.addList(response.body());
            }

            @Override
            public void onFailure(Call<List<RepoInfo>> call, Throwable t) {
                Toast.makeText(mView, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
