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

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.editEmailText);
        mPassword = findViewById(R.id.editPasswordText);
        mRegisterAccountButton = findViewById(R.id.btnLoginRegister);
        mLoginButton = findViewById(R.id.btnLogin);

        loginAccountController = new LoginAccountController(this);


        mRegisterAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRegisterNewAccount();
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: add a try/catch on validating login; check for refactoring login method
                loginAccountController.validateLogin(
                        mEmail.getText().toString(),
                        mPassword.getText().toString()
                );
            }
        });

        Log.i("test", "a");
    }

    private void onRegisterNewAccount()
    {
        Intent intentRegister = new Intent(getApplicationContext(), RegisterAccountActivity.class);
        startActivity(intentRegister);
    }

    @Override
    public void onLoginAccount()
    {

        if (mUser != null) {
            //Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
            onStartMainActivity();
        } else {
            Toast.makeText(this, "Failed to login user", Toast.LENGTH_SHORT).show();
        }
    }

    private void onStartMainActivity()
    {
        finish();
    }

    @Override
    public void setUser(User mUser)
    {
        this.mUser = mUser;
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}