package com.glisco03.hexaleaf.ui.modes;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.glisco03.hexaleaf.MainActivity;
import com.glisco03.hexaleaf.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.Slider;

public class ModesFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_modes, container, false);


        //
        //Twinkle
        //

        final ImageView twinkle_color_1_preview = view.findViewById(R.id.twinkle_color_1_preview);
        twinkle_color_1_preview.setOnClickListener((View interacted) -> openColorPicker((ImageView) interacted));

        final ImageView twinkle_color_2_preview = view.findViewById(R.id.twinkle_color_2_preview);
        twinkle_color_2_preview.setOnClickListener((View interacted) -> openColorPicker((ImageView) interacted));

        final Slider twinkle_time_slider = view.findViewById(R.id.twinkle_time_slider);
        twinkle_time_slider.setLabelFormatter(value -> Math.round(value) + "s");

        final MaterialButton set_twinkle_button = view.findViewById(R.id.set_twinkle_button);
        set_twinkle_button.setOnClickListener((View button) -> {
            int color1 = twinkle_color_1_preview.getImageTintList().getDefaultColor();
            int color2 = twinkle_color_2_preview.getImageTintList().getDefaultColor();
            MainActivity.httpGET(MainActivity.getLampIP() + "/twinkle?r1=" + Color.red(color1) + "&g1=" + Color.green(color1) + "&b1=" + Color.blue(color1)
                    + "&r2=" + Color.red(color2) + "&g2=" + Color.green(color2) + "&b2=" + Color.blue(color2)
                    + "&time=" + Math.round(twinkle_time_slider.getValue()), null);
        });


        //
        //Waterfall
        //

        final ImageView waterfall_base_color_preview = view.findViewById(R.id.waterfall_base_color_preview);
        waterfall_base_color_preview.setOnClickListener((View interacted) -> openColorPicker((ImageView) interacted));

        final ImageView waterfall_color_1_preview = view.findViewById(R.id.waterfall_color_1_preview);
        waterfall_color_1_preview.setOnClickListener((View interacted) -> openColorPicker((ImageView) interacted));

        final ImageView waterfall_color_2_preview = view.findViewById(R.id.waterfall_color_2_preview);
        waterfall_color_2_preview.setOnClickListener((View interacted) -> openColorPicker((ImageView) interacted));

        final Slider waterfall_speed_slider = view.findViewById(R.id.waterfall_speed_slider);
        waterfall_speed_slider.setLabelFormatter(value -> Math.round(value) + "%");

        final ImageView waterfall_direction_icon = view.findViewById(R.id.waterfall_direction_icon);
        waterfall_direction_icon.setTag(R.drawable.ic_round_arrow_circle_up_24);

        final MaterialButton waterfall_direction_button = view.findViewById(R.id.waterfall_direction_button);
        waterfall_direction_button.setOnClickListener((View button) -> {
            int direction_icon_resource = (int) waterfall_direction_icon.getTag();
            if (direction_icon_resource == R.drawable.ic_round_arrow_circle_up_24) {
                waterfall_direction_icon.setImageResource(R.drawable.ic_round_arrow_circle_down_24);
                waterfall_direction_icon.setTag(R.drawable.ic_round_arrow_circle_down_24);
            } else {
                waterfall_direction_icon.setImageResource(R.drawable.ic_round_arrow_circle_up_24);
                waterfall_direction_icon.setTag(R.drawable.ic_round_arrow_circle_up_24);
            }
        });

        final MaterialButton set_waterfall_button = view.findViewById(R.id.set_waterfall_button);
        set_waterfall_button.setOnClickListener((View button) -> {

            int base_color = waterfall_base_color_preview.getImageTintList().getDefaultColor();

            int color1 = waterfall_color_1_preview.getImageTintList().getDefaultColor();
            int color2 = waterfall_color_2_preview.getImageTintList().getDefaultColor();

            float waterfallSpeed = 0.75f * (waterfall_speed_slider.getValue() / 100f);
            if ((int) waterfall_direction_icon.getTag() == R.drawable.ic_round_arrow_circle_up_24)
                waterfallSpeed *= -1;

            String url = MainActivity.getLampIP() + "/waterfall?r1=" + Color.red(base_color) + "&g1=" + Color.green(base_color) + "&b1=" + Color.blue(base_color)
                    + "&r2=" + Color.red(color1) + "&g2=" + Color.green(color1) + "&b2=" + Color.blue(color1)
                    + "&r3=" + Color.red(color2) + "&g3=" + Color.green(color2) + "&b3=" + Color.blue(color2)
                    + "&time=" + waterfallSpeed;

            MainActivity.httpGET(url, null);
        });


        //
        //Crossfade
        //

        final ImageView crossfade_color_1_preview = view.findViewById(R.id.crossfade_color_1_preview);
        crossfade_color_1_preview.setOnClickListener((View interacted) -> openColorPicker((ImageView) interacted));

        final ImageView crossfade_color_2_preview = view.findViewById(R.id.crossfade_color_2_preview);
        crossfade_color_2_preview.setOnClickListener((View interacted) -> openColorPicker((ImageView) interacted));

        final ImageView crossfade_color_3_preview = view.findViewById(R.id.crossfade_color_3_preview);
        crossfade_color_3_preview.setOnClickListener((View interacted) -> openColorPicker((ImageView) interacted));

        final ImageView crossfade_color_4_preview = view.findViewById(R.id.crossfade_color_4_preview);
        crossfade_color_4_preview.setOnClickListener((View interacted) -> openColorPicker((ImageView) interacted));

        final Slider crossfade_speed_slider = view.findViewById(R.id.crossfade_speed_slider);
        crossfade_speed_slider.setLabelFormatter(value -> Math.round(value) + "%");

        final MaterialButton set_crossfade_button = view.findViewById(R.id.set_crossfade_button);
        set_crossfade_button.setOnClickListener((View button) -> {

            int color1 = crossfade_color_1_preview.getImageTintList().getDefaultColor();
            int color2 = crossfade_color_2_preview.getImageTintList().getDefaultColor();
            int color3 = crossfade_color_3_preview.getImageTintList().getDefaultColor();
            int color4 = crossfade_color_4_preview.getImageTintList().getDefaultColor();

            float waterfallSpeed = 0.75f * (waterfall_speed_slider.getValue() / 100f);

            String url = MainActivity.getLampIP() + "/crossfade?r1=" + Color.red(color1) + "&g1=" + Color.green(color1) + "&b1=" + Color.blue(color1)
                    + "&r2=" + Color.red(color2) + "&g2=" + Color.green(color2) + "&b2=" + Color.blue(color2)
                    + "&r3=" + Color.red(color3) + "&g3=" + Color.green(color3) + "&b3=" + Color.blue(color3)
                    + "&r4=" + Color.red(color4) + "&g4=" + Color.green(color4) + "&b4=" + Color.blue(color4)
                    + "&time=" + waterfallSpeed;

            MainActivity.httpGET(url, null);
        });

        return view;
    }

    private void openColorPicker(ImageView preview) {

        ColorPickerDialogBuilder.with(preview.getContext())
                .setTitle("Choose color")
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .setPositiveButton("OK", (d, lastSelectedColor, allColors) -> preview.setImageTintList(ColorStateList.valueOf(lastSelectedColor)))
                .setNegativeButton("Cancel", (dialogInterface, i) -> {
                })
                .showLightnessSlider(false).showAlphaSlider(false).build().show();
    }

}