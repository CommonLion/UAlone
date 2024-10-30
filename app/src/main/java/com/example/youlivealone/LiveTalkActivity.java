package com.example.youlivealone;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class LiveTalkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.livetalk);  // livetalk.xml을 사용

        // chatAdd 버튼 클릭 시 팝업창을 열도록 설정
        ImageButton addButton = findViewById(R.id.chatAdd);
        addButton.setOnClickListener(v -> showAddChatRoomDialog(LiveTalkActivity.this));
    }

    // 팝업창을 띄우는 메서드 (위에서 정의한 showAddChatRoomDialog)
    private void showAddChatRoomDialog(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_add_chatroom);  // 팝업 레이아웃 설정

        dialog.getWindow().setLayout((int) (getResources().getDisplayMetrics().widthPixels * 0.9),
                (int) (getResources().getDisplayMetrics().heightPixels * 0.5));


        // 팝업창의 동작 설정 (닫기 버튼, 완료 버튼 등)
        ImageButton closeButton = dialog.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(v -> dialog.dismiss());

        // 완료 버튼 클릭 시 처리할 로직 설정
        dialog.findViewById(R.id.confirmButton).setOnClickListener(v -> {
            // TODO: 방 제목, 설명, 체크박스 등 정보 처리
            dialog.dismiss();  // 팝업창 닫기
        });

        dialog.show();  // 팝업창 표시
    }
}
