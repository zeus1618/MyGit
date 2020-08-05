package com.assignment.myapplication;

import android.content.Intent;
import android.widget.Toast;

import com.assignment.myapplication.api.GitServices;
import com.assignment.myapplication.api.RetrofitClient;
import com.assignment.myapplication.api.UserInfo;
import com.assignment.myapplication.repolist.RepoListActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class HomePresenter {
    HomeActivity mView;

    HomePresenter(HomeActivity view) {
        mView = view;
    }

    public void perfomUserSearch(String userName) {
        mView.hideKeyBoard();
        if (userName == null || userName.isEmpty()){
            Toast.makeText(mView, "Please input username", Toast.LENGTH_SHORT).show();
            return;
        }

        mView.showLoading();
        GitServices service = RetrofitClient.getRetrofitInstance().create(GitServices.class);
        Call<UserInfo> call = service.getUserInfo(userName);
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                mView.hideLoading();
                mView.setupData(response.body());
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Toast.makeText(mView, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openRepoList(String userName) {
        Intent intent = new Intent(mView, RepoListActivity.class);
        intent.putExtra(RepoListActivity.EXTRA_USER_NAME, userName);
        mView.startActivity(intent);
    }
}
