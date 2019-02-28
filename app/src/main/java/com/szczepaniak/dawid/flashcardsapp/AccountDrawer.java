package com.szczepaniak.dawid.flashcardsapp;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountDrawer {

    private NavigationView drawer;
    private ApiService api;
    private TextView nicknameText;
    private TextView emailText;
    private Button changeNickname;
    private TextInputEditText newNicknameText;

    public AccountDrawer(NavigationView drawer) {
        this.drawer = drawer;
        View headerLayout = drawer.getHeaderView(0);
        nicknameText = headerLayout.findViewById(R.id.nickname);
        emailText = headerLayout.findViewById(R.id.email);
        api = RetroClient.getApiService();
        changeNickname = drawer.findViewById(R.id.changeName);
        newNicknameText = drawer.findViewById(R.id.newNameText);
        loadUser();
    }


    private void loadUser() {

        retrofit2.Call<User> userCall = api.getUser(1L);

        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(retrofit2.Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {

                    final User user = response.body();
                    nicknameText.setText(user.getName());
                    emailText.setText(user.getEmail());

                    changeNickname.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            user.setName(newNicknameText.getText().toString());
                            Call<User> changeUser = api.putUser(user.getId(), user);
                            changeUser.enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    nicknameText.setText(response.body().getName());
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {
                                    Log.e("flash","ERROR", t);
                                }
                            });
                        }
                    });

                }
            }

            @Override
            public void onFailure(retrofit2.Call<User> call, Throwable t) {
                Log.e("flash", "ERROR", t);
            }
        });
    }
}
