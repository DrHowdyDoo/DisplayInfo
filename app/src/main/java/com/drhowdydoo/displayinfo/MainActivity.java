package com.drhowdydoo.displayinfo;

import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TEST";
    private ActivityMainBinding mainBinding;
    private Display display;
    private DisplayMetrics dm;
    private Configuration config;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(DynamicColors.isDynamicColorAvailable()) DynamicColors.applyToActivityIfAvailable(this);
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mainBinding.getRoot();
        setContentView(view);

        dm = getResources().getDisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        display = wm.getDefaultDisplay();
        config = getResources().getConfiguration();

        mainBinding.txtViewResolution.setText(getResolution());
        mainBinding.textViewAspectRatio.setText(getAspectRatio());
        mainBinding.txtViewScreenSizeInInch.setText(getScreenSizeInInch());
        mainBinding.textViewDensity.setText(getDensity());
        mainBinding.textViewPpi.setText(getPpi());
        mainBinding.textViewSswdp.setText(config.smallestScreenWidthDp + " dp");
        if(display.isHdr()) mainBinding.icHrd.setVisibility(View.VISIBLE);

        mainBinding.textViewHdr.setText(getHdrCapabilities());
        mainBinding.textViewLuminance.setText(getLuminance());
        mainBinding.textViewRefreshRate.setText(getRefreshRate());
        mainBinding.textViewWcg.setText(getColorGamut());
        mainBinding.textViewModes.setText(getDisplayModes());
        mainBinding.textViewModel.setText(getModel());


    }

    private String getResolution(){
        StringBuilder resolution = new StringBuilder();
        int height = display.getMode().getPhysicalHeight();
        int width = display.getMode().getPhysicalWidth();
        resolution.append(height).append(" x ").append(width);
        if(width == 720) resolution.append(" (HD)");
        if(width >= 1080 && width < 1440) {
            if(height == 1920) resolution.append(" (FHD)");
            if(height > 1920) resolution.append(" (FHD+)");
        }
        if(width >= 1440 && width < 2160){
            if(height == 2560) resolution.append(" (QHD)");
            if(height > 2560) resolution.append(" (QHD+)");
        }
        if(width >= 2160 && width < 4320) resolution.append(" (UHD)");
        if(width >= 4320) resolution.append(" (8K)");
        return resolution.toString();
    }
    private String getAspectRatio(){
        return ratio(display.getMode().getPhysicalHeight(),display.getMode().getPhysicalWidth());
    }

    private String ratio(float a, float b) {

        DecimalFormat A = new DecimalFormat("#.#");
        DecimalFormat B = new DecimalFormat("#.#");
        final float gcd = (b/720) * 80;

        if(a > b) {
            return A.format(a/gcd) + " : " + B.format(b/gcd);
        } else {
            return A.format(b/gcd) + " : " + B.format(a/gcd);
        }
    }


    private String getScreenSizeInInch(){

        getWindowManager().getDefaultDisplay().getMetrics(dm);
        double widthInch = display.getMode().getPhysicalWidth() / dm.xdpi;
        double heightInch = display.getMode().getPhysicalHeight() / dm.ydpi;
        double x = Math.pow(widthInch, 2);
        double y = Math.pow(heightInch, 2);
        double screenInches = Math.sqrt(x + y) ;
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(screenInches) + " in";
    }
    private String getDensity(){
        return dm.densityDpi + " dpi" + " (" + getDensityName() + ")" ;
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
    private String getPpi(){
        return "X : " +(int)dm.xdpi + " ppi" + "\n" + "Y : " +(int)dm.ydpi + " ppi";
    }
    private String getHdrCapabilities(){
        int[] hdr = display.getHdrCapabilities().getSupportedHdrTypes();
        StringBuilder hdrCapabilities = new StringBuilder();

        if(hdr.length == 0){
            hdrCapabilities.append("N/A").append("\n").append("N/A");
        }else {
            for(int i : hdr){

                if(i == 1) hdrCapabilities.append("Dolby Vision HDR");
                if(i == 2) hdrCapabilities.append("HDR10");
                if(i == 3) hdrCapabilities.append("HLG HDR");
                if(i == 4) hdrCapabilities.append("HDR10+");
                hdrCapabilities.append("\n");
            }
        }

        return hdrCapabilities.toString().trim();
    }
    private String getLuminance(){
        float min = display.getHdrCapabilities().getDesiredMinLuminance();
        float max = display.getHdrCapabilities().getDesiredMaxLuminance();

        return "Min : " +(int)min + " nits" + "\n" + "Max : " +(int)max + " nits";
    }
    private String getRefreshRate(){

        int currRefreshRate = (int) display.getMode().getRefreshRate();

        return currRefreshRate + " Hz";
    }
    private String getColorGamut(){
        StringBuilder wideColorGamut = new StringBuilder();
        wideColorGamut.append("Display : ");
        if(display.isWideColorGamut()){
            wideColorGamut.append("Supported").append("\n");
        }
        else wideColorGamut.append("N/A").append("\n");
        wideColorGamut.append("Device : ");
        if(config.isScreenWideColorGamut()) wideColorGamut.append("Supported");
        else wideColorGamut.append("N/A");

        return wideColorGamut.toString();
    }
    private String getDisplayModes(){
        Display.Mode[] modes = display.getSupportedModes();
        StringBuilder displayModes = new StringBuilder();
        for(Display.Mode mode : modes){
             displayModes.append(mode.getPhysicalHeight()).append(" x ").append(mode.getPhysicalWidth())
                     .append(" @ ").append((int) mode.getRefreshRate()).append(" Hz").append("\n");
        }
        return displayModes.toString().trim();
    }
    private String getModel(){

        return Build.BRAND + " " + Build.DEVICE;
    }


}