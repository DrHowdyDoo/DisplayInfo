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




    }

    private String getResolution(){
        return display.getMode().getPhysicalHeight() + " x " + display.getMode().getPhysicalWidth();
    }
    private String getAspectRatio(){
        return ratio(display.getMode().getPhysicalHeight(),display.getMode().getPhysicalWidth());
    }
    private int gcd(int p, int q) {
        if (q == 0) return p;
        else return gcd(q, p % q);
    }
    private String ratio(int a, int b) {
        final int gcd = gcd(a,b);
        if(a > b) {
            return a/gcd + " : " + b/gcd;
        } else {
            return b/gcd + " : " + a/gcd;
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
        return "x : " +(int)dm.xdpi + "   " + "y : " +(int)dm.ydpi ;
    }
    private String getHdrCapabilities(){
        int[] hdr = display.getHdrCapabilities().getSupportedHdrTypes();
        StringBuilder hdrCapabilities = new StringBuilder();

        for(int i : hdr){

             if(i == 1) hdrCapabilities.append("Dolby Vision HDR");
             if(i == 2) hdrCapabilities.append("HDR10");
             if(i == 3) hdrCapabilities.append("HLG HDR");
             if(i == 4) hdrCapabilities.append("HDR10+");
            hdrCapabilities.append("\n");
        }
        return hdrCapabilities.toString().trim();
    }
    private String getLuminance(){
        float min = display.getHdrCapabilities().getDesiredMinLuminance();
        float max = display.getHdrCapabilities().getDesiredMaxLuminance();

        return "Min : " +(int)min + " nits" + "\n" + "Max : " +(int)max + " nits";
    }
    private String getRefreshRate(){
        StringBuilder frameRates = new StringBuilder();
        int currRefreshRate = (int) display.getMode().getRefreshRate();
        frameRates.append(currRefreshRate).append(" Hz");
        Display.Mode[] modes = display.getSupportedModes();
        for(Display.Mode mode : modes){
            frameRates.append("\n");
            frameRates.append((int) mode.getRefreshRate()).append(" Hz");
        }
        return frameRates.toString().trim();
    }
    private String getColorGamut(){
        StringBuilder wideColorGamut = new StringBuilder();
        wideColorGamut.append("Display : ");
        if(display.isWideColorGamut()){
            wideColorGamut.append("Supported").append("\n");
        }
        else wideColorGamut.append("Not Supported").append("\n");
        wideColorGamut.append("Device : ");
        if(config.isScreenWideColorGamut()) wideColorGamut.append("Supported");
        else wideColorGamut.append("Not Supported");

        return wideColorGamut.toString();
    }


}