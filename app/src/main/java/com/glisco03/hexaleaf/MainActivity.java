package com.glisco03.hexaleaf;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;
import androidx.viewpager2.widget.ViewPager2;

import com.glisco03.hexaleaf.ui.EditIPFragment;
import com.glisco03.hexaleaf.ui.SavingFragment;
import com.glisco03.hexaleaf.ui.main.ViewPagerAdapter;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.nambimobile.widgets.efab.ExpandableFab;
import com.nambimobile.widgets.efab.ExpandableFabLayout;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static String LAMP_IP_CACHE = "";

    public static final OkHttpClient httpClient = new OkHttpClient.Builder().callTimeout(5, TimeUnit.SECONDS).build();
    public static MainActivity INSTANCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        BottomAppBar navView = findViewById(R.id.nav_view);
        ViewPager2 viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                animateFab(position);
                navView.getMenu().getItem(viewPager.getCurrentItem()).setChecked(false);
                navView.getMenu().getItem(position).setChecked(true);
            }
        });

        /*navView.setOnNavigationItemSelectedListener(item -> {
            int itemID = item.getItemId();
            if (itemID == R.id.navigation_home) {
                viewPager.setCurrentItem(0);
            } else if (itemID == R.id.navigation_modes) {
                viewPager.setCurrentItem(1);
            }
            return true;
        });*/

        FloatingActionButton fab_power = findViewById(R.id.fab_power);
        fab_power.setOnClickListener(view -> httpGET(getLampIP() + "/toggle", null));

        fab_power.setOnLongClickListener(button -> {
            new EditIPFragment().show(getSupportFragmentManager(), null);
            return true;
        });

        ExpandableFab fab_save = findViewById(R.id.fab_save);
        fab_save.hide();
        fab_save.setOnLongClickListener(view -> {
            if (!((ExpandableFabLayout) findViewById(R.id.fab_layout)).isOpen()) return false;

            new MaterialAlertDialogBuilder(this).setTitle("Clear all save slots?")
                    .setPositiveButton("Confirm", (dialogInterface, i) -> {
                        httpGET(getLampIP() + "/clearstorage", new SimpleCallback());
                        Snackbar.make(findViewById(R.id.fab_layout), "Slots cleared", Snackbar.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    })
                    .create().show();
            return true;
        });

        LAMP_IP_CACHE = "http://" + PreferenceManager.getDefaultSharedPreferences(this).getString("lamp_ip", "");

        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener((sharedPreferences, key) -> {
            if (key.equals("lamp_ip")) {
                LAMP_IP_CACHE = "http://" + sharedPreferences.getString(key, "");
                System.out.println("preference changed to " + LAMP_IP_CACHE);
            }
        });

        INSTANCE = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        INSTANCE = null;
    }

    public static View getSnackbarContainer(){
        return MainActivity.INSTANCE.findViewById(R.id.fab_layout);
    }

    private void animateFab(int position) {
        if (position == 1) {
            ((FloatingActionButton) findViewById(R.id.fab_power)).hide();
            ((FloatingActionButton) findViewById(R.id.fab_save)).show();
        } else {
            ((FloatingActionButton) findViewById(R.id.fab_power)).show();
            ((FloatingActionButton) findViewById(R.id.fab_save)).hide();
            ((ExpandableFabLayout) findViewById(R.id.fab_layout)).close();
        }
    }

    public void saveFabSlot1Listener(View view) {
        httpGET(getLampIP() + "/save?slot=0", new SavingCallback(new SavingFragment()));
    }

    public void saveFabSlot2Listener(View view) {
        httpGET(getLampIP() + "/save?slot=1", new SavingCallback(new SavingFragment()));
    }

    public void saveFabSlot3Listener(View view) {
        httpGET(getLampIP() + "/save?slot=2", new SavingCallback(new SavingFragment()));
    }

    public void saveFabSlot4Listener(View view) {
        httpGET(getLampIP() + "/save?slot=3", new SavingCallback(new SavingFragment()));
    }

    public static void httpGET(String url, Callback callback) {

        if (url.startsWith("http:///")) {
            Snackbar.make(MainActivity.getSnackbarContainer(), "The Lamp's IP is unknown", Snackbar.LENGTH_SHORT).show();
            return;
        }

        Request request = new Request.Builder().url(url).build();

        if (callback == null) callback = new SimpleCallback();
        if (callback instanceof FragmentCallback) ((FragmentCallback) callback).show();

        httpClient.newCall(request).enqueue(callback);
    }

    public static String getLampIP() {
        return LAMP_IP_CACHE;
    }

    public class SavingCallback implements FragmentCallback {

        final DialogFragment fragment;

        public SavingCallback(DialogFragment fragment) {
            this.fragment = fragment;
        }

        @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e) {
            fragment.dismiss();
            Snackbar.make(findViewById(R.id.fab_layout), R.string.saving_failed, Snackbar.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            fragment.dismiss();
            Snackbar.make(findViewById(R.id.fab_layout), R.string.saving_success, Snackbar.LENGTH_SHORT).show();
        }

        @Override
        public void show() {
            fragment.show(getSupportFragmentManager(), null);
        }
    }

}