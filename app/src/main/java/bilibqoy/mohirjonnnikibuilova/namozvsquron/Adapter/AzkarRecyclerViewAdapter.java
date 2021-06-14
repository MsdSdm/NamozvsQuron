package bilibqoy.mohirjonnnikibuilova.namozvsquron.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import bilibqoy.mohirjonnnikibuilova.namozvsquron.R;

public class AzkarRecyclerViewAdapter extends RecyclerView.Adapter<AzkarRecyclerViewAdapter.AzkarRecyclerViewHolder> {
    private List<String> m,s,r,sp,tr;
    private boolean[] b;
    public AzkarRecyclerViewAdapter(List<String> main,List<String> sanad,List<String> rep, List<String> spel,List<String> trans,boolean[] transVisible){
        m = main;
        s = sanad;
        r = rep;
        sp = spel;
        tr = trans;
        b = transVisible;
    }
    @NonNull
    @Override
    public AzkarRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout clayout = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.azkar_unit,parent,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        clayout.setLayoutParams(lp);
        return new AzkarRecyclerViewHolder(clayout);
    }

    @Override
    public void onBindViewHolder(@NonNull AzkarRecyclerViewHolder holder, int position) {
        ((TextView) holder.layout.findViewById(R.id.azkar_unit_main)).setText(m.get(position));
        ((TextView) holder.layout.findViewById(R.id.azkar_unit_sanad)).setText(s.get(position));
        ((TextView) holder.layout.findViewById(R.id.azkar_unit_repetition)).setText(r.get(position));
        ((TextView) holder.layout.findViewById(R.id.azkar_unit_spel)).setText(sp.get(position));
        ((TextView) holder.layout.findViewById(R.id.azkar_unit_translate)).setText(tr.get(position));
        holder.layout.findViewById(R.id.azkar_unit_spel).setVisibility(b[0]?View.VISIBLE:View.GONE);
        holder.layout.findViewById(R.id.azkar_unit_translate).setVisibility(b[0]?View.VISIBLE:View.GONE);
    }

    @Override
    public void onViewRecycled(@NonNull AzkarRecyclerViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return m.size();
    }

    public class AzkarRecyclerViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout layout;
        public AzkarRecyclerViewHolder(@NonNull ConstraintLayout itemView) {
            super(itemView);
            layout = itemView;
        }
    }
}
