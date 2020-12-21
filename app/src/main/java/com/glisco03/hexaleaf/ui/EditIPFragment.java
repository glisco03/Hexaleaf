package com.glisco03.hexaleaf.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

import com.glisco03.hexaleaf.R;
import com.glisco03.hexaleaf.VerifyIPRunnable;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

public class EditIPFragment extends DialogFragment {

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_edit_ip, null);
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity()).setView(view);

        final TextInputEditText editText = view.findViewById(R.id.edit_ip);
        editText.setText(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("lamp_ip", ""));

        builder.setTitle("Edit Lamp IP");
        builder.setPositiveButton("Save", (dialog, which) -> {
            new Thread(new VerifyIPRunnable(editText.getText().toString())).start();
        });
        return builder.create();
    }
}
