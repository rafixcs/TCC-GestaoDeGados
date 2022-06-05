package com.udemy.cursoandroid.gestaogados.View.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.udemy.cursoandroid.gestaogados.Controller.MainController;
import com.udemy.cursoandroid.gestaogados.Controller.login.ILoginAccountController;
import com.udemy.cursoandroid.gestaogados.Controller.login.IRegisterAccountController;
import com.udemy.cursoandroid.gestaogados.Controller.login.LoginAccountController;
import com.udemy.cursoandroid.gestaogados.Helper.NfcHelper;
import com.udemy.cursoandroid.gestaogados.Model.User.User;
import com.udemy.cursoandroid.gestaogados.R;
import com.udemy.cursoandroid.gestaogados.View.MainActivity;

public class LoginActivity extends AppCompatActivity implements ILoginView
{

    private EditText mEmail;
    private EditText mPassword;
    private TextView mRegisterAccountButton;
    private Button mLoginButton;

    private ILoginAccountController loginAccountController;
    private IRegisterAccountController registerAccountController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.editEmailText);
        mPassword = findViewById(R.id.editPasswordText);
        mRegisterAccountButton = findViewById(R.id.btnLoginRegister);
        mLoginButton = findViewById(R.id.btnLogin);

        loginAccountController = new LoginAccountController(this, this);


        mRegisterAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRegisterNewAccount();
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*loginAccountController.validateLogin(
                        mEmail.getText().toString(),
                        mPassword.getText().toString()
                );*/
                loginAccountController.validateLogin(
                        "rafael.camargo.rs@gmail.com",
                        "123"
                );
                /*loginAccountController.validateLogin(
                        "rafael@teste",
                        "321"
                );*/
            }
        });
    }

    private void onRegisterNewAccount()
    {
        Intent intentRegister = new Intent(getApplicationContext(), RegisterAccountActivity.class);
        startActivity(intentRegister);
    }

    @Override
    public void onLoginAccount(boolean automaticLogin)
    {
        MainController mainController = MainController.getInstance();
        User user = mainController.getCurrentUser();

        if (user != null) {
            onStartMainActivity();
        } else {
            if (!automaticLogin)
            {
                Toast.makeText(this, "Failed to login user", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void onStartMainActivity()
    {
        finish();
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        onLoginAccount(true);
    }
}