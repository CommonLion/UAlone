package com.example.youlivealone;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {

    private ArrayList<String> messages;
    private ArrayAdapter<String> messageAdapter;
    private ListView messageListView;
    private EditText inputMessage;
    private Button sendButton;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chat_room);  // 채팅방에 대한 레이아웃 XML 파일
//
//        // 채팅방 이름을 받아와서 표시하거나 처리할 수 있음
//        String chatroomName = getIntent().getStringExtra("chatroomName");
//
//        // 메시지 목록을 표시할 ListView와 어댑터 설정
//        messages = new ArrayList<>();
//        messageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messages);
//        messageListView = findViewById(R.id.message_list);
//        messageListView.setAdapter(messageAdapter);
//
//        // 메시지 입력과 전송 버튼 설정
//        inputMessage = findViewById(R.id.input_message);
//        sendButton = findViewById(R.id.send_button);
//
//        // 전송 버튼 클릭 리스너 설정
//        sendButton.setOnClickListener(v -> {
//            String message = inputMessage.getText().toString();
//            if (!message.isEmpty()) {
//                // 메시지 목록에 추가
//                messages.add(message);
//                messageAdapter.notifyDataSetChanged();
//
//                // 입력 필드 초기화
//                inputMessage.setText("");
//
//                // 실제로 서버로 메시지를 보내는 로직을 추가 가능 (추후 구현)
//            }
//        });
//    }
}
