package com.glisco03.hexaleaf;

import androidx.preference.PreferenceManager;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.net.InetAddress;

public class VerifyIPRunnable implements Runnable {

    private final String address;

    public VerifyIPRunnable(String address) {
        this.address = address;
    }

    @Override
    public void run() {
        InetAddress ip;
        try {
            ip = InetAddress.getByName(address);
            if (ip.isReachable(3000)) {
                PreferenceManager.getDefaultSharedPreferences(MainActivity.INSTANCE).edit().putString("lamp_ip", address).apply();
                Snackbar.make(MainActivity.getSnackbarContainer(), "IP saved", Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(MainActivity.getSnackbarContainer(), "Invalid IP", Snackbar.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
