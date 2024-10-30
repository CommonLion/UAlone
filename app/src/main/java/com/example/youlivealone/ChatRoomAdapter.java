package com.example.youlivealone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.ChatRoomViewHolder> {

    private List<HashMap<String, Object>> chatRooms;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String chatRoomName);
    }

    public ChatRoomAdapter(List<HashMap<String, Object>> chatRooms, OnItemClickListener listener) {
        this.chatRooms = chatRooms;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChatRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chatroom, parent, false);
        return new ChatRoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRoomViewHolder holder, int position) {
        HashMap<String, Object> chatRoom = chatRooms.get(position);
        String chatRoomName = (String) chatRoom.get("name");
        int participantCount = (int) chatRoom.get("participantCount");

        holder.chatRoomName.setText(chatRoomName);
        holder.participantCount.setText("참여자 수: " + participantCount);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(chatRoomName);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatRooms.size();
    }

    public static class ChatRoomViewHolder extends RecyclerView.ViewHolder {
        public TextView chatRoomName;
        public TextView participantCount;

        public ChatRoomViewHolder(@NonNull View itemView) {
            super(itemView);
            chatRoomName = itemView.findViewById(R.id.chatroom_name);
            participantCount = itemView.findViewById(R.id.chatroom_participants);
        }
    }
}