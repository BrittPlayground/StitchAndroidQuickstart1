package com.playground.britt.stitchandroidquickstart1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nexmo.sdk.conversation.client.ConversationClient;
import com.nexmo.sdk.conversation.client.User;
import com.nexmo.sdk.conversation.client.event.NexmoAPIError;
import com.nexmo.sdk.conversation.client.event.RequestHandler;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = LoginActivity.this.getClass().getSimpleName();
    private String CONVERSATION_ID = "CON-902a8de5-f0ce-4431-b285-6352d8d309d4";
    private String USER_JWT = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1MjU1MTE1ODAsImp0aSI6IjgyNWI0ZDYwLTUwNDQtMTFlOC1iYzZhLTE5YmEyZmIxZWQxNCIsInN1YiI6ImphbWllIiwiZXhwIjoiMTUyNTU5Nzk4MCIsImFjbCI6eyJwYXRocyI6eyIvdjEvc2Vzc2lvbnMvKioiOnt9LCIvdjEvdXNlcnMvKioiOnt9LCIvdjEvY29udmVyc2F0aW9ucy8qKiI6e319fSwiYXBwbGljYXRpb25faWQiOiI4ODE4ZmQ0MC1iNGVjLTQxNTktOTZlNi0wZmY2OTdhOTA1N2YifQ.GoHGEjiqWtgj-TIA-Vyo947q_M2l7z7CrIZV92CkkLdBxmdcV1hT7Z3USayAmr92Y5qlcevclBFe3DNgVNfDrvc5foOZB8uyUX48juDzm1ancuoqZSbHOAoTv-nttY5sAdrH855tx3GczfoVfxCugR3cWntMLhSjZ1VkHrqnmWwW3qjS4fH0oRl_tbXYQzwnKkBUuDicZ2PqkA7U4SjukBN4yV9fJb6Wr2K2s277nCLqU0SO8IkK3_o5V_tKRxQ2OcKrPbXvdRoKTae190CN5L6Rqp1yt7stHWmG7F51kuhqhDm7FbErKqjVJ5CedRw1jWCgtQjX_hcwCfphEAFqGg";

    private ConversationClient conversationClient;
    private TextView loginTxt;
    private Button loginBtn;
    private Button chatBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ConversationClientApplication application = (ConversationClientApplication) getApplication();
        conversationClient = application.getConversationClient();

        loginTxt = (TextView) findViewById(R.id.login_text);
        loginBtn = (Button) findViewById(R.id.login);
        chatBtn = (Button) findViewById(R.id.chat);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToChatActivity();
            }
        });
    }

    private void logAndShow(final String message) {
        Log.d(TAG, message);
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private String authenticate() {
        return USER_JWT;
    }

    private void login() {
        loginTxt.setText("Logging in...");

        String userToken = authenticate();
        conversationClient.login(userToken, new RequestHandler<User>() {
            @Override
            public void onSuccess(User user) {
                showLoginSuccess(user);
            }

            @Override
            public void onError(NexmoAPIError apiError) {
                logAndShow("Login Error: " + apiError.getMessage());
            }
        });
    }

    private void showLoginSuccess(final User user) {
        loginTxt.setText("Logged in as " + user.getName() + "\nStart a new conversation");
    }

    private void goToChatActivity() {
        Intent intent = new Intent(LoginActivity.this, ChatActivity.class);
        intent.putExtra("CONVERSATION-ID", CONVERSATION_ID);
        startActivity(intent);
    }


}