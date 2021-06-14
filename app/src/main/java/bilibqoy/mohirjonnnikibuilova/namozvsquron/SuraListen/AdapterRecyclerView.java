package bilibqoy.mohirjonnnikibuilova.namozvsquron.SuraListen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.R;

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.MyViewHolder>  {
    List<Sure> sureList = new ArrayList<>();
    List<Sure> sureListFiltered = new ArrayList<>();
    Context context;
    InterfaceSure interfaceSure;



    public AdapterRecyclerView(List<Sure> sureList, Context context, InterfaceSure interfaceSure)
     {
        this.sureList = sureList;
        this.context = context;
        this.interfaceSure=interfaceSure;
        this.sureListFiltered = new ArrayList<>();

         //  this.sureListFiltered=sureList;
         this.sureListFiltered.addAll(sureList);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardiew, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.textView1.setText(sureList.get(i).getNumber());
        myViewHolder.textView2.setText(sureList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return sureList.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView1, textView2;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.numberSure);
            textView2 = itemView.findViewById(R.id.nameSure);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    interfaceSure.play(textView1.getText().toString(),textView2.getText().toString());
                }
            });
        }
    }




    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        sureList.clear();
        if (charText.length() == 0) {
            sureList.addAll(sureListFiltered);
        } else {
            for (Sure sure : sureListFiltered) {
                if (sure.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    sureList.add(sure);
                }
            }
        }
        notifyDataSetChanged();
    }

}
