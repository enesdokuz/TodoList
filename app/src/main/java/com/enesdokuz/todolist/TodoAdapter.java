package com.enesdokuz.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enesdokuz.todolist.model.Todo;

import java.util.ArrayList;
import java.util.List;

/***
 * TodoList
 * Enes Dokuz enesdokuz
 * www.enesdokuz.com
 * 2019-07-30
 ***/
public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoHolder> {

    private List<Todo> todos = new ArrayList<>();
    private OnItemClickListener listener;
    private OnLongItemClickListener listenerLong;

    @NonNull
    @Override
    public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todos_item, parent, false);
        return new TodoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoHolder holder, int position) {
        Todo todo = todos.get(position);
        holder.txtTitle.setText(todo.getName());
        holder.txtDescription.setText(todo.getDescription());
        holder.txtDeadline.setText(todo.getDeadline());
        holder.txtCreateDate.setText(todo.getCreate_date());
        holder.checkStatus.setChecked(todo.getStatus());
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
        notifyDataSetChanged();
    }

    public Todo getTodoAt(int position) {
        return todos.get(position);
    }

    class TodoHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle, txtDescription, txtCreateDate, txtDeadline;
        private CheckBox checkStatus;

        public TodoHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle_todosItem);
            txtDescription = itemView.findViewById(R.id.txtDescription_todosItem);
            txtCreateDate = itemView.findViewById(R.id.txtCreateDate_todosItem);
            txtDeadline = itemView.findViewById(R.id.txtDeadline_todosItem);
            checkStatus = itemView.findViewById(R.id.checkStatus_todosItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(todos.get(position));
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (listenerLong != null && position != RecyclerView.NO_POSITION) {
                        listenerLong.onLongItemClick(todos.get(position));
                    }
                    return true;
                }
            });

            //TODO:Checkbox will fixed.
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Todo todo);
    }

    public interface OnLongItemClickListener {
        void onLongItemClick(Todo todo);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnLongItemClickListener(OnLongItemClickListener listenerLong) {
        this.listenerLong = listenerLong;
    }

}
