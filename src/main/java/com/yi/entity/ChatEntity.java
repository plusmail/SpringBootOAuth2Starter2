package com.yi.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "chat_entity")
public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "chat_room_id")
    @JdbcTypeCode(SqlTypes.BIGINT)
    private Long chatRoomId;

    @Column(name = "member_id")
    @JdbcTypeCode(SqlTypes.BIGINT)
    private Long memberId;

    @Column(name = "message")
    private String message;

    @Column(name = "region")
    private String region;

    private LocalDateTime regDate;

    @Column(name = "nickname")
    private String nickname;

}