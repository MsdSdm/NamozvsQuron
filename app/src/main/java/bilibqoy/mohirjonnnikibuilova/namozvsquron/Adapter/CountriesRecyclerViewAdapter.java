package bilibqoy.mohirjonnnikibuilova.namozvsquron.Adapter;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CountriesRecyclerViewAdapter extends RecyclerView.Adapter<CountriesRecyclerViewAdapter.CountriesRecyclerViewHolder>{
    private List<TextView> list;
    public CountriesRecyclerViewAdapter(List<TextView> clist) {
        list = clist;
    }
    @NonNull
    @Override
    public CountriesRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout layout = new LinearLayout(parent.getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        View view = new View(parent.getContext());
        LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                1);
        view.setLayoutParams(dividerParams);
        view.setBackgroundColor(Color.DKGRAY);
        layout.addView(view);
        return new CountriesRecyclerViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull CountriesRecyclerViewHolder holder, int position) {
        TextView tx = list.get(position);
        tx.setPadding(15,15,15,15);
        tx.setTextColor(Color.WHITE);
        tx.setTextSize(15);
        tx.setGravity(Gravity.LEFT);
        holder.viewLayout.addView(tx);
    }

    @Override
    public void onViewRecycled(@NonNull CountriesRecyclerViewHolder holder) {
        super.onViewRecycled(holder);
        holder.viewLayout.removeViewAt(1);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CountriesRecyclerViewHolder  extends RecyclerView.ViewHolder{
        LinearLayout viewLayout;
        public CountriesRecyclerViewHolder(@NonNull LinearLayout itemView) {
            super(itemView);
            viewLayout = itemView;
        }
    }
}
