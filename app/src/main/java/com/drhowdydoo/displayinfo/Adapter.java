package com.drhowdydoo.displayinfo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.drhowdydoo.displayinfo.databinding.ItemCardBinding;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private ArrayList<Data> list;

    public Adapter(ArrayList<Data> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCardBinding binding = ItemCardBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Data data = list.get(position);

        if(data.getIc_screen() == 0) holder.ic_screen.setVisibility(View.GONE);
        else holder.ic_screen.setImageResource(data.getIc_screen());
        if(data.getIc1() == 0) holder.ic1.setVisibility(View.GONE);
        else holder.ic1.setImageResource(data.getIc1());
        if(data.getIc2() == 0) holder.ic2.setVisibility(View.GONE);
        else holder.ic2.setImageResource(data.getIc2());
        if(data.getIc3() == 0) holder.ic3.setVisibility(View.GONE);
        else holder.ic3.setImageResource(data.getIc3());
        if(data.getIc4() == 0) holder.ic4.setVisibility(View.GONE);
        else holder.ic4.setImageResource(data.getIc4());

        holder.title1.setText(data.getTitle_1());
        if(data.getTitle_2().isEmpty()) holder.title2.setVisibility(View.GONE);
        else holder.title2.setText(data.getTitle_2());

        if(data.getTitle_3().isEmpty()) holder.title3.setVisibility(View.GONE);
        else holder.title3.setText(data.getTitle_3());

        if(data.getTitle_4().isEmpty()) holder.title4.setVisibility(View.GONE);
        else holder.title4.setText(data.getTitle_4());

        if(data.getValue_1().isEmpty()) holder.value1.setVisibility(View.GONE);
        else holder.value1.setText(data.getValue_1());

        if(data.getValue_2().isEmpty()) holder.value2.setVisibility(View.GONE);
        else holder.value2.setText(data.getValue_2());

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ic1,ic2,ic3,ic4,ic_screen;
        public TextView title1,title2,title3,title4;
        public TextView value1,value2;

        public ViewHolder(@NonNull ItemCardBinding cardBinding) {
            super(cardBinding.getRoot());
            ic1 = cardBinding.ic1;
            ic2 = cardBinding.ic2;
            ic3 = cardBinding.ic3;
            ic4 = cardBinding.ic4;
            ic_screen = cardBinding.icScreen;

            title1 = cardBinding.txtTitle1;
            title2 = cardBinding.txtTitle2;
            title3 = cardBinding.txtTitle3;
            title4 = cardBinding.txtTitle4;

            value1 = cardBinding.txtValue1;
            value2 = cardBinding.txtValue2;
        }
    }

}
