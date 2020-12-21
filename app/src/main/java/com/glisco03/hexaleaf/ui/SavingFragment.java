package com.glisco03.hexaleaf.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.fragment.app.DialogFragment;

import com.glisco03.hexaleaf.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.jetbrains.annotations.NotNull;

public class SavingFragment extends DialogFragment {

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity()).setView(inflater.inflate(R.layout.dialog_saving, null));
        builder.setCancelable(false);
        return builder.create();
    }
}
