package com.glisco03.hexaleaf.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.flask.colorpicker.ColorPickerView;
import com.glisco03.hexaleaf.FragmentCallback;
import com.glisco03.hexaleaf.MainActivity;
import com.glisco03.hexaleaf.R;
import com.glisco03.hexaleaf.SimpleCallback;
import com.glisco03.hexaleaf.ui.SavingFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.glisco03.hexaleaf.MainActivity.getLampIP;


public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        final MaterialButton LOAD_1_BUTTON = view.findViewById(R.id.slot_1_button);
        final MaterialButton LOAD_2_BUTTON = view.findViewById(R.id.slot_2_button);
        final MaterialButton LOAD_3_BUTTON = view.findViewById(R.id.slot_3_button);
        final MaterialButton LOAD_4_BUTTON = view.findViewById(R.id.slot_4_button);

        LOAD_1_BUTTON.setOnClickListener(view1 -> MainActivity.httpGET(getLampIP() + "/load?slot=0", new EmptySlotCallback()));
        LOAD_2_BUTTON.setOnClickListener(view1 -> MainActivity.httpGET(getLampIP() + "/load?slot=1", new EmptySlotCallback()));
        LOAD_3_BUTTON.setOnClickListener(view1 -> MainActivity.httpGET(getLampIP() + "/load?slot=2", new EmptySlotCallback()));
        LOAD_4_BUTTON.setOnClickListener(view1 -> MainActivity.httpGET(getLampIP() + "/load?slot=3", new EmptySlotCallback()));

        LOAD_1_BUTTON.setOnLongClickListener(view1 -> {
            MainActivity.httpGET(getLampIP() + "/setdefault?slot=0", new SavingCallback(new SavingFragment(), 1));
            return true;
        });

        LOAD_2_BUTTON.setOnLongClickListener(view1 -> {
            MainActivity.httpGET(getLampIP() + "/setdefault?slot=1", new SavingCallback(new SavingFragment(), 2));
            return true;
        });
        LOAD_3_BUTTON.setOnLongClickListener(view1 -> {
            MainActivity.httpGET(getLampIP() + "/setdefault?slot=2", new SavingCallback(new SavingFragment(), 3));
            return true;
        });

        LOAD_4_BUTTON.setOnLongClickListener(view1 -> {
            MainActivity.httpGET(getLampIP() + "/setdefault?slot=3", new SavingCallback(new SavingFragment(), 4));
            return true;
        });


        //
        //Static
        //

        final ColorPickerView static_picker = view.findViewById(R.id.staticColorPicker);
        static_picker.setShowBorder(true);

        final MaterialButton set_static_button = view.findViewById(R.id.set_static_button);
        set_static_button.setOnClickListener((View button) -> {
            int color = static_picker.getSelectedColor();
            System.out.println(color + " - " + Color.red(color) + ":" + Color.green(color) + ":" + Color.blue(color));
            MainActivity.httpGET(MainActivity.getLampIP() + "/static?r=" + Color.red(color) + "&g=" + Color.green(color) + "&b=" + Color.blue(color), null);
        });

        final ImageView static_color_1_preview = view.findViewById(R.id.static_color_1_preview);
        static_color_1_preview.setOnClickListener(v -> {
            static_picker.setColor(static_color_1_preview.getImageTintList().getDefaultColor(), false);
        });

        return view;
    }

    private class EmptySlotCallback extends SimpleCallback {
        @Override
        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            if (response.body().string().equals("empty_slot")) {
                Snackbar.make(getActivity().findViewById(R.id.fab_layout), R.string.slot_unassigned, Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    public class SavingCallback implements FragmentCallback {

        final DialogFragment fragment;
        final int slotNumber;

        public SavingCallback(DialogFragment fragment, int slotNumber) {
            this.fragment = fragment;
            this.slotNumber = slotNumber;
        }

        @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e) {
            fragment.dismiss();
            Snackbar.make(getActivity().findViewById(R.id.fab_layout), R.string.setdefault_failed, Snackbar.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            fragment.dismiss();
            Snackbar.make(getActivity().findViewById(R.id.fab_layout), getString(R.string.slot_setdefault, slotNumber), Snackbar.LENGTH_SHORT).show();
        }

        @Override
        public void show() {
            fragment.show(getActivity().getSupportFragmentManager(), null);
        }
    }
}