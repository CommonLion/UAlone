package com.example.youlivealone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
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
        // 아이템 레이아웃을 Inflate
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chatroom, parent, false);
        return new ChatRoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRoomViewHolder holder, int position) {
        // 임시 데이터에서 이름과 참여자 수를 가져옴
        String chatRoomName = (String) chatRooms.get(position).get("name");
        int participantCount = (int) chatRooms.get(position).get("participantCount");

        holder.chatRoomName.setText(chatRoomName);
        holder.participantCount.setText("참여자 수: " + participantCount);

        // 항목 클릭 시 이벤트
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
