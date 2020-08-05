package com.assignment.myapplication.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitServices {

    @GET("/users/{userName}")
    Call<UserInfo> getUserInfo(@Path("userName") String userName);

    @GET("/users/{userName}/repos?sort=updated")
    Call<List<RepoInfo>> getRepoList(
            @Path("userName") String userName,
            @Query("page") int page,
            @Query("per_page") int size
    );

}
