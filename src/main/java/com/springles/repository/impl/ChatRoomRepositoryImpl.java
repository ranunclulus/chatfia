package com.springles.repository.impl;

import com.springles.domain.constants.ChatRoomCode;
import com.springles.domain.dto.chatroom.ChatRoomResponseDto;
import com.springles.domain.entity.ChatRoom;
import com.springles.repository.custom.ChatRoomCustomRepository;
import com.springles.repository.support.Querydsl4RepositorySupport;
import jakarta.transaction.Transactional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.springles.domain.entity.QChatRoom.chatRoom;

@Repository
@Transactional
@Slf4j
public class ChatRoomRepositoryImpl extends Querydsl4RepositorySupport implements
    ChatRoomCustomRepository {

    public ChatRoomRepositoryImpl() {
        super(ChatRoom.class);
    }

    @Override
    public List<ChatRoomResponseDto> findAllByOwnerId(Long ownerId) {
        return selectFrom(chatRoom)
            .where(chatRoom.ownerId.eq(ownerId))
            .fetch()
            .stream().map(ChatRoomResponseDto::of).collect(Collectors.toList());

    }

    @Override
    public List<ChatRoomResponseDto> findAllByCloseFalseAndState(ChatRoomCode chatRoomCode) {
        return selectFrom(chatRoom)
            .where(
                chatRoom.close.isFalse(),
                chatRoom.state.eq(chatRoomCode.WAITING)
            )
            .orderBy(chatRoom.capacity.subtract(chatRoom.head).asc())
            .fetch()
            .stream().map(ChatRoomResponseDto::of).collect(Collectors.toList());
        
    }

}
