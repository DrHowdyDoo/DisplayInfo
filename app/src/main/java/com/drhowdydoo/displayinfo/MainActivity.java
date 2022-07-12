package com.drhowdydoo.displayinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;

import com.drhowdydoo.displayinfo.databinding.ActivityMainBinding;
import com.google.android.material.color.DynamicColors;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TEST";
    private ActivityMainBinding mainBinding;
    private Display display;
    private DisplayMetrics dm;
    private Configuration config;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (DynamicColors.isDynamicColorAvailable()) DynamicColors.applyToActivityIfAvailable(this);
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mainBinding.getRoot();
        setContentView(view);

        dm = getResources().getDisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        display = wm.getDefaultDisplay();
        config = getResources().getConfiguration();

//        mainBinding.txtViewResolution.setText(getResolution());
//        mainBinding.textViewAspectRatio.setText(getAspectRatio());
//        mainBinding.txtViewScreenSizeInInch.setText(getScreenSizeInInch());
//        mainBinding.textViewDensity.setText(getDensity());
//        mainBinding.textViewPpi.setText(getPpi());
//        mainBinding.textViewSswdp.setText(config.smallestScreenWidthDp + " dp");
//        if(display.isHdr()) mainBinding.icHrd.setVisibility(View.VISIBLE);
//
//        mainBinding.textViewHdr.setText(getHdrCapabilities());
//        mainBinding.textViewLuminance.setText(getLuminance());
//        mainBinding.textViewRefreshRate.setText(getRefreshRate());
//        mainBinding.textViewWcg.setText(getColorGamut());
//        mainBinding.textViewModes.setText(getDisplayModes());
//        mainBinding.textViewModel.setText(getModel());

        ArrayList<Data> list = new ArrayList<>();
        Data d1 = new Data(R.drawable.ic_round_smartphone_24, R.drawable.ic_resolution,
                R.drawable.ic_aspect_ratio, R.drawable.ic_dpi, R.drawable.ic_screen, getModel(), getResolution(), getAspectRatio(), getDensity(), "", "");
        Data d2 = new Data(R.drawable.ic_refresh_rate, 0, 0, 0, 0, "Refresh rate", "", "", "", getRefreshRate(), "");
        Data d3 = new Data(R.drawable.ic_smallest_width_dp, R.drawable.ic_luminance, 0, 0, 0, "Smallest Width", "Luminance", "", "", config.smallestScreenWidthDp + " dp", getLuminance());
        Data d4 = new Data(R.drawable.ic_hdr, 0, 0, 0, 0, "HDR capabilities", "", "", "", getHdrCapabilities(), "");
        Data d5 = new Data(R.drawable.ic_ppi, 0, 0, 0, 0, "Pixels per Inch", "", "", "", getPpi(), "");
        Data d6 = new Data(R.drawable.ic_wide_color_gamut, 0, 0, 0, 0, "Wide Color Gamut", "", "", "", getColorGamut(), "");
        Data d7 = new Data(R.drawable.ic_display_modes, 0, 0, 0, 0, "Supported Display Modes", "", "", "", getDisplayModes(), "");

        list.add(d1);
        list.add(d2);
        list.add(d3);
        list.add(d4);
        list.add(d5);
        list.add(d6);
        list.add(d7);

        RecyclerView recyclerView = mainBinding.recyclerView;
        Adapter adapter = new Adapter(list);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (list.get(position).getIc_screen() != 0 || list.get(position).getTitle_1().equalsIgnoreCase("supported display modes")) return 2;
                return 1;
            }
        });

        ItemTouchHelper.Callback _ithCallback = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG,
                        ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.START | ItemTouchHelper.END);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                Collections.swap(list, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                adapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        };

        ItemTouchHelper ith = new ItemTouchHelper(_ithCallback);
        ith.attachToRecyclerView(recyclerView);

    }

    private String getResolution() {
        StringBuilder resolution = new StringBuilder();
        int height = display.getMode().getPhysicalHeight();
        int width = display.getMode().getPhysicalWidth();
        resolution.append(height).append(" x ").append(width);
        if (width == 720) resolution.append(" (HD)");
        if (width >= 1080 && width < 1440) {
            if (height == 1920) resolution.append(" (FHD)");
            if (height > 1920) resolution.append(" (FHD+)");
        }
        if (width >= 1440 && width < 2160) {
            if (height == 2560) resolution.append(" (QHD)");
            if (height > 2560) resolution.append(" (QHD+)");
        }
        if (width >= 2160 && width < 4320) resolution.append(" (UHD)");
        if (width >= 4320) resolution.append(" (8K)");
        return resolution.toString();
    }

    private String getAspectRatio() {
        return ratio(display.getMode().getPhysicalHeight(), display.getMode().getPhysicalWidth());
    }

    private String ratio(float a, float b) {

        DecimalFormat A = new DecimalFormat("#.#");
        DecimalFormat B = new DecimalFormat("#.#");
        final float gcd = (b / 720) * 80;

        if (a > b) {
            return A.format(a / gcd) + " : " + B.format(b / gcd);
        } else {
            return A.format(b / gcd) + " : " + B.format(a / gcd);
        }
    }

    private String getScreenSizeInInch() {

        getWindowManager().getDefaultDisplay().getMetrics(dm);
        double widthInch = display.getMode().getPhysicalWidth() / dm.xdpi;
        double heightInch = display.getMode().getPhysicalHeight() / dm.ydpi;
        double x = Math.pow(widthInch, 2);
        double y = Math.pow(heightInch, 2);
        double screenInches = Math.sqrt(x + y);
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(screenInches) + " in";
    }

    private String getDensity() {
        return dm.densityDpi + " dpi" + " (" + getDensityName() + ")";
    }

    private String getDensityName() {
        float density = dm.density;
        if (density >= 4.0) {
            return "xxxhdpi";
        }
        if (density >= 3.0) {
            return "xxhdpi";
        }
        if (density >= 2.0) {
            return "xhdpi";
        }
        if (density >= 1.5) {
            return "hdpi";
        }
        if (density >= 1.0) {
            return "mdpi";
        }
        return "ldpi";
    }

    private String getPpi() {
        return "X : " + (int) dm.xdpi + " ppi" + "\n" + "Y : " + (int) dm.ydpi + " ppi";
    }

    private String getHdrCapabilities() {
        int[] hdr = display.getHdrCapabilities().getSupportedHdrTypes();
        StringBuilder hdrCapabilities = new StringBuilder();

        if (hdr.length == 0) {
            hdrCapabilities.append("N/A").append("\n").append("N/A");
        } else {
            for (int i : hdr) {

                if (i == 1) hdrCapabilities.append("Dolby Vision HDR");
                if (i == 2) hdrCapabilities.append("HDR10");
                if (i == 3) hdrCapabilities.append("HLG HDR");
                if (i == 4) hdrCapabilities.append("HDR10+");
                hdrCapabilities.append("\n");
            }
        }

        return hdrCapabilities.toString().trim();
    }

    private String getLuminance() {
        float min = display.getHdrCapabilities().getDesiredMinLuminance();
        float max = display.getHdrCapabilities().getDesiredMaxLuminance();

        return "Min : " + (int) min + " nits" + "\n" + "Max : " + (int) max + " nits";
    }

    private String getRefreshRate() {

        int currRefreshRate = (int) display.getMode().getRefreshRate();

        return currRefreshRate + " Hz";
    }

    private String getColorGamut() {
        StringBuilder wideColorGamut = new StringBuilder();
        wideColorGamut.append("Display : ");
        if (display.isWideColorGamut()) {
            wideColorGamut.append("Supported").append("\n");
        } else wideColorGamut.append("N/A").append("\n");
        wideColorGamut.append("Device : ");
        if (config.isScreenWideColorGamut()) wideColorGamut.append("Supported");
        else wideColorGamut.append("N/A");

        return wideColorGamut.toString();
    }

    private String getDisplayModes() {
        Display.Mode[] modes = display.getSupportedModes();
        StringBuilder displayModes = new StringBuilder();
        for (Display.Mode mode : modes) {
            displayModes.append(mode.getPhysicalHeight()).append(" x ").append(mode.getPhysicalWidth())
                    .append(" @ ").append((int) mode.getRefreshRate()).append(" Hz").append("\n");
        }
        return displayModes.toString().trim();
    }

    private String getModel() {

        return Build.BRAND + " " + Build.DEVICE;
    }


}