package com.assignment.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.assignment.myapplication.api.UserInfo;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.info_container)
    LinearLayout infoContainer;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.username_lookup)
    EditText userNameEt;
    @BindView(R.id.profile_img)
    ImageView profileImage;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.username)
    TextView userName;
    @BindView(R.id.followers)
    TextView followers;
    @BindView(R.id.company)
    TextView company;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.link)
    TextView link;
    @BindView(R.id.repo_button)
    Button repoButton;

    HomePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mPresenter = new HomePresenter(this);

        userNameEt.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                mPresenter.perfomUserSearch(userNameEt.getText().toString());
                return true;
            }
            return false;
        });

        search.setOnClickListener(view -> mPresenter.perfomUserSearch(userNameEt.getText().toString()));
        repoButton.setOnClickListener(view -> mPresenter.openRepoList(userName.getText().toString()));
    }

    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        infoContainer.setVisibility(View.GONE);
    }

    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        infoContainer.setVisibility(View.VISIBLE);
    }

    public void setupData(UserInfo info) {
        Picasso.with(this).load(info.getAvatarUrl()).into(profileImage);
        setText(name, info.getName());
        setText(userName, info.getLogin());
        setText(followers, info.getFollowers() + " - " + info.getFollowing());
        setText(company, info.getCompany());
        setText(location, info.getLocation());
        setText(email, info.getEmail());
        setText(link, info.getBlog());
    }

    private void setText(TextView view, String text) {
        if (text != null && !text.isEmpty()) {
            view.setText(text);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    public void hideKeyBoard(){
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}