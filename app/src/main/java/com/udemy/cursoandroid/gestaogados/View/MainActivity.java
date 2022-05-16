package com.udemy.cursoandroid.gestaogados.View;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.udemy.cursoandroid.gestaogados.Helper.NfcHelper;
import com.udemy.cursoandroid.gestaogados.R;
import com.udemy.cursoandroid.gestaogados.View.login.LoginActivity;
import com.udemy.cursoandroid.gestaogados.View.main.consult.ConsultBovineFragment;
import com.udemy.cursoandroid.gestaogados.View.main.register.RegisterBovineFragment;
import com.udemy.cursoandroid.gestaogados.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private NfcHelper nfc = null;
    private PendingIntent pendingIntent = null;

    private boolean mAlreadyInitialized = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_register_bovine, R.id.nav_consult_bovine)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        nfc = new NfcHelper(getApplicationContext());
        pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP), 0);
        nfc.setPendingIntent(pendingIntent);

        Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intentLogin);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        setIntent(intent);
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);

        Fragment currentFragment = null;
        if (navHostFragment != null)
        {
            currentFragment = navHostFragment.getChildFragmentManager().getFragments().get(0);
        }

        if(currentFragment instanceof RegisterBovineFragment)
        {
            RegisterBovineFragment registerFragment = (RegisterBovineFragment) currentFragment;
            registerFragment.setNfcIntent(intent, nfc);
        }
        else if (currentFragment instanceof ConsultBovineFragment)
        {
            ConsultBovineFragment consultFragment = (ConsultBovineFragment) currentFragment;
            consultFragment.getFromTag(intent, nfc);
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();

        if (nfc == null && !mAlreadyInitialized)
        {
            nfc = new NfcHelper(getApplicationContext());
        }

        if (nfc != null)
        {
            pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP), 0);
            nfc.setPendingIntent(pendingIntent);
            nfc.foregroundDispatch(this, true);
        }

        if (!mAlreadyInitialized)
        {
            mAlreadyInitialized = true;
        }

    }

    @Override
    public void onStop()
    {
        super.onStop();
        if (nfc != null)
        {
            // Disabling foreground dispatch does not work properly when the application grows
            //nfc.getNfcAdapter().disableForegroundDispatch(this);
            //nfc.foregroundDispatch(this, false);
            mAlreadyInitialized = false;
            nfc = null;
        }
    }
}