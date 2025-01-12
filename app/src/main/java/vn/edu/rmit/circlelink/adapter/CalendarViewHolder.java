package vn.edu.rmit.circlelink.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

import vn.edu.rmit.circlelink.R;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final ArrayList<LocalDate> daysOfMonth;
    public final TextView dayOfMonth;
    public final ImageView redDot;
    private final CalendarAdapter.OnItemListener onItemListener;

    public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener, ArrayList<LocalDate> daysOfMonth) {
        super(itemView);
        dayOfMonth = itemView.findViewById(R.id.cellDayText);
        redDot = itemView.findViewById(R.id.redDot);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
        this.daysOfMonth = daysOfMonth;
    }

    @Override
    public void onClick(View v) {
        onItemListener.onItemClick(getAdapterPosition(), daysOfMonth.get(getAdapterPosition()));
    }
}
