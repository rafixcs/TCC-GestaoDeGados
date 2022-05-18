package com.udemy.cursoandroid.gestaogados.View.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.udemy.cursoandroid.gestaogados.Controller.login.IRegisterAccountController;
import com.udemy.cursoandroid.gestaogados.Controller.login.RegisterAccountController;
import com.udemy.cursoandroid.gestaogados.R;
import com.udemy.cursoandroid.gestaogados.View.farm.RegisterFarmActivity;

public class RegisterAccountActivity extends AppCompatActivity implements IRegisterView {

    private EditText mName;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mRetypedPassword;
    private Button mRegisterButton;

    private IRegisterAccountController registerController;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);

        mName = findViewById(R.id.editNameRegister);
        mEmail = findViewById(R.id.editEmailRegister);
        mPassword = findViewById(R.id.editPasswordRegister);
        mRetypedPassword = findViewById(R.id.editPasswordRetypeRegister);
        mRegisterButton = findViewById(R.id.btnAccountRegister);

        mRegisterButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                String name = mName.getText().toString();
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                String retypedPassword = mRetypedPassword.getText().toString();

                boolean result = checkFields(name, email, password, retypedPassword);

                registerNewAccount(result, name, email, password);
            }
        });

    }

    private void registerNewAccount(boolean result, String name, String email, String password)
    {
        if (result)
        {
            registerController = new RegisterAccountController(this);
            registerController.createNewAccount(name, email, password);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Failed on completing all fields", Toast.LENGTH_SHORT).show();
        }
    }

    //TODO: refactoring needed on register check fields
    private boolean checkFields(String name, String email, String password, String retypedPassword) {

        if (name == null || name.isEmpty())
        {
            return false;
        }
        else if (email == null || email.isEmpty())
        {
            return false;
        }
        else if (password == null || password.isEmpty())
        {
            return false;
        }
        else if (retypedPassword == null || retypedPassword.isEmpty())
        {
            return checkMatchingPassword(password, retypedPassword);
        }

        return true;
    }

    private boolean checkMatchingPassword(String password, String retyped)
    {
        return password.equals(retyped);
    }

    @Override
    public void onRegisterNewAccountResult(boolean result)
    {
        if (result)
        {
            Intent intent = new Intent(getApplicationContext(), RegisterFarmActivity.class);
            intent.putExtra("firstAccess",true);
            startActivity(intent);
            //Toast.makeText(getApplicationContext(), "Created new user", Toast.LENGTH_SHORT).show();
            finish();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Failed on creating new user", Toast.LENGTH_SHORT).show();
        }
    }
}