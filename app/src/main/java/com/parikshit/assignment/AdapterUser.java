package com.parikshit.assignment;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.MyViewHolder> {
   ArrayList<String> strings;
   Context context;

    public AdapterUser(ArrayList<String> strings, Context context) {
        this.strings = strings;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
       return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder,final int position) {

        String s = strings.get(position);
        holder.textView.setText(s);
        holder.checkBox.setOnCheckedChangeListener(null);
        //now attach click listener on checkBox here

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
       Cursor res = databaseHelper.getAllData();
        int id=0;
        while (res.moveToNext()){
            StringBuffer buffer = new StringBuffer();
            String Age = Integer.toString(res.getInt(2));
            String Selected = Integer.toString(res.getInt(3));
            String Name = res.getString(1);

            buffer.append("Name is : "+Name+"\n");
            buffer.append("Age is :"+Age);
            String str = buffer.toString();


            if (str.equals(s)){
             //   Log.v("TAGGID", "found "+"id is " +Integer.toString(res.getInt(0)));

                id = res.getInt(0);
                holder.checkBox.setTag(id);
                holder.checkBox.setOnCheckedChangeListener(null);
              if (res.getInt(3)==0){
                  holder.checkBox.setChecked(false);
              } else{
                  holder.checkBox.setChecked(true);
              }
            }

        }

        holder.checkBox.setTag(id);


        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseHelper databaseHelper = new DatabaseHelper(context);
                Cursor res = databaseHelper.getAllData();
                String s = strings.get(position);
                while (res.moveToNext()){
                    StringBuffer buffer = new StringBuffer();
                    String Age = Integer.toString(res.getInt(2));
                    String Selected = Integer.toString(res.getInt(3));
                    String Name = res.getString(1);

                    buffer.append("Name is : "+Name+"\n");
                    buffer.append("Age is :"+Age);
                    String str = buffer.toString();

                    if (str.equals(s)){
                        holder.checkBox.setText("This wll be deleted");
                        String id = Integer.toString(res.getInt(0));
                        String name = res.getString(1);
                        String age = Integer.toString(res.getInt(2));
                        String checked = Integer.toString(res.getInt(3));

                        String checkedStatus ;
                        if (Integer.parseInt(checked)==0){
                            checkedStatus = Integer.toString(1);
                        }else{
                            checkedStatus = Integer.toString(0);
                        }

                       boolean boo= databaseHelper.UpdateChecked(id,name,age,checkedStatus);


                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return strings.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.ItemTextView);
            checkBox = itemView.findViewById(R.id.ItemcheckBox);

        }
    }
}
