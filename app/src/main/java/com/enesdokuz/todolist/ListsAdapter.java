package com.enesdokuz.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.enesdokuz.todolist.model.ListName;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/***
 * TodoList
 * Enes Dokuz enesdokuz
 * www.enesdokuz.com
 * 27.07.2019
 ***/
public class ListsAdapter extends RecyclerView.Adapter<ListsAdapter.ListsHolder> {

    private List<ListName> listNames = new ArrayList<>();
    private OnItemClickListener listener;
    private OnLongItemClickListener listenerLong;

    @NonNull
    @Override
    public ListsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lists_item, parent, false);
        return new ListsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListsHolder holder, int position) {
        ListName listName = listNames.get(position);
        holder.txtTitle.setText(listName.getTitle());
        holder.txtDescription.setText(listName.getDescription());

    }

    @Override
    public int getItemCount() {
        return listNames.size();
    }

    public void setListNames(List<ListName> listNames) {
        this.listNames = listNames;
        notifyDataSetChanged();
    }

    public ListName getListAt(int position) {
        return listNames.get(position);
    }

    class ListsHolder extends RecyclerView.ViewHolder {

        private TextView txtTitle;
        private TextView txtDescription;

        public ListsHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle_listsItem);
            txtDescription = itemView.findViewById(R.id.txtDescription_listsItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(listNames.get(position));
                    }

                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (listenerLong != null && position != RecyclerView.NO_POSITION) {
                        listenerLong.onLongItemClick(listNames.get(position));
                    }
                    return true;
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(ListName listName);
    }

    public interface OnLongItemClickListener {
        void onLongItemClick(ListName listName);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnLongItemClickListener(OnLongItemClickListener listenerLong) {
        this.listenerLong = listenerLong;
    }


}
