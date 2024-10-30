package com.example.youlivealone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatRoomActivity extends AppCompatActivity {

    private EditText roomTitleInput, roomDescriptionInput, maxParticipantsInput;
    private CheckBox publicCheckBox, privateCheckBox;
    private Button confirmButton;
    private TextView warningTextView;
    private RecyclerView chatRoomListView;
    private ChatRoomAdapter chatRoomAdapter;
    private List<HashMap<String, Object>> chatRoomList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.livetalk);

        roomTitleInput = findViewById(R.id.roomTitleInput);
        roomDescriptionInput = findViewById(R.id.roomDescriptionInput);
        maxParticipantsInput = findViewById(R.id.maxParticipantsInput);
        publicCheckBox = findViewById(R.id.publicCheckBox);
        privateCheckBox = findViewById(R.id.privateCheckBox);
        confirmButton = findViewById(R.id.confirmButton);
        warningTextView = findViewById(R.id.warningTextView);
        chatRoomListView = findViewById(R.id.chatAll);

        chatRoomList = new ArrayList<>();
        chatRoomAdapter = new ChatRoomAdapter(chatRoomList, chatRoomName -> {
            // 클릭 이벤트 처리 로직
            Log.d("ChatRoomActivity", "Chat room clicked: " + chatRoomName);
        });
        chatRoomListView.setLayoutManager(new LinearLayoutManager(this));
        chatRoomListView.setAdapter(chatRoomAdapter);

        confirmButton.setOnClickListener(v -> handleConfirmButtonClick());

        loadChatRooms();
    }

    private void handleConfirmButtonClick() {
        String roomTitle = roomTitleInput.getText().toString().trim();
        String roomDescription = roomDescriptionInput.getText().toString().trim();
        String maxParticipantsText = maxParticipantsInput.getText().toString().trim();
        boolean isPublic = publicCheckBox.isChecked();
        boolean isPrivate = privateCheckBox.isChecked();

        warningTextView.setVisibility(View.GONE);

        if (!validateInputs(roomTitle, roomDescription, maxParticipantsText, isPublic, isPrivate)) {
            return;
        }

        int maxParticipants = Integer.parseInt(maxParticipantsText);
        String category = isPublic ? "public" : "private";
        createChatRoom(roomTitle, roomDescription, maxParticipants, category);
    }

    private boolean validateInputs(String roomTitle, String roomDescription, String maxParticipantsText, boolean isPublic, boolean isPrivate) {
        if (roomTitle.isEmpty() || roomTitle.length() > 20) {
            showWarning("방 제목은 1자 이상 20자 이내로 작성해주세요.");
            return false;
        }

        if (roomDescription.isEmpty() || roomDescription.length() > 20) {
            showWarning("방 소개는 1자 이상 20자 이내로 작성해주세요.");
            return false;
        }

        if (maxParticipantsText.isEmpty() || !isValidParticipantCount(maxParticipantsText)) {
            showWarning("최대 인원수는 2명 이상 100명 이하로 설정해주세요.");
            return false;
        }

        if ((!isPublic && !isPrivate) || (isPublic && isPrivate)) {
            showWarning("공개 또는 비공개 중 하나만 선택해주세요.");
            return false;
        }

        return true;
    }

    private void showWarning(String message) {
        warningTextView.setText(message);
        warningTextView.setVisibility(View.VISIBLE);
    }

    private boolean isValidParticipantCount(String count) {
        try {
            int participants = Integer.parseInt(count);
            return participants >= 2 && participants <= 100;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void createChatRoom(String name, String description, int maxParticipants, String category) {
        String url = "http://localhost:8080/chat/room"; // 로컬 서버 주소로 변경

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("jwtToken", null);

        if (token == null) {
            Toast.makeText(getApplicationContext(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("name", name);
            jsonBody.put("description", description);
            jsonBody.put("maxParticipants", maxParticipants);
            jsonBody.put("category", category);

            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    url,
                    response -> {
                        Toast.makeText(getApplicationContext(), "채팅방 생성 완료", Toast.LENGTH_SHORT).show();
                        loadChatRooms(); // 생성 후 채팅방 목록 새로고침
                    },
                    error -> Toast.makeText(getApplicationContext(), "채팅방 생성 실패", Toast.LENGTH_LONG).show()
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", "Bearer " + token);
                    return headers;
                }

                @Override
                public byte[] getBody() {
                    return jsonBody.toString().getBytes();
                }
            };

            int timeoutMs = 10000;
            RetryPolicy retryPolicy = new DefaultRetryPolicy(timeoutMs, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(retryPolicy);

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(stringRequest);
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "JSON 오류 발생", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadChatRooms() {
        String url = "http://localhost:8080/chat/room/list"; // 모든 채팅방 조회 엔드포인트

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    chatRoomList.clear();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject chatRoomJson = response.getJSONObject(i);
                            HashMap<String, Object> chatRoomData = new HashMap<>();
                            chatRoomData.put("name", chatRoomJson.getString("name"));
                            chatRoomData.put("description", chatRoomJson.getString("description"));
                            chatRoomData.put("maxParticipants", chatRoomJson.getInt("maxParticipants"));
                            chatRoomData.put("category", chatRoomJson.getString("category"));
                            chatRoomList.add(chatRoomData);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    chatRoomAdapter.notifyDataSetChanged();
                },
                error -> Log.e("LoadChatRoomsError", "Failed to load chat rooms")
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonArrayRequest);
    }
}
