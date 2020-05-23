package com.towhid.firebaserecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context mcontext;
    List<User> mData; //contact clalss type array list object declare kora hoese just , instantiate kora hoy nai

    public RecyclerViewAdapter(Context mcontext) {
        this.mcontext = mcontext;
        this.mData = new ArrayList<>();

    }


    public void addAll(ArrayList<User> newUser){
        int initsize=mData.size();
        mData.addAll(newUser);
        notifyItemRangeChanged(initsize, newUser.size());

    }

    public String getLastItemId(){
        return mData.get(mData.size() - 1).getId();

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mcontext).inflate(R.layout.item_hublist, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.hub_name.setText(mData.get(position).getId());



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView hub_name;


        public MyViewHolder(View itemView) {

            super(itemView);

            hub_name = (TextView) itemView.findViewById(R.id.hub_name);


        }
    }


}
