package com.example.youlivealone;
//이거 없어도 될거 같긴함. 아닌가.................
/** 이게 더미데이터용이긴 한데, 백엔드에서 어떻게 보내줄지. DTO를 통째로 보내고, 해당 DTO를 여기서 어떻게
 * 여기서 처리해야 할지를 모르겠음.
 * model의 ChatRoom을 여기에 그대로 만들어야하나... 싶기도하고, DTO를 백에서 받아도 타입에다한 명시가 없으니
 * 아니면 코드에 직접하는게 맞는 거 같기도하고, 어지럽네
 * 일단 미사용
 */
public class ChatRoomDummy {
    private String name;
    private int participantCount;

    public ChatRoomDummy(String name, int participantCount) {
        this.name = name;
        this.participantCount = participantCount;
    }

    public String getName() {
        return name;
    }

    public int getParticipantCount() {
        return participantCount;
    }
}
