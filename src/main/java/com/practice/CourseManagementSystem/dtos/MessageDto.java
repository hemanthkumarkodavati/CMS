package com.practice.CourseManagementSystem.dtos;

import com.practice.CourseManagementSystem.commons.MessageType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDto implements Serializable {

    private String message;
    private MessageType type;

    public MessageDto(String message, MessageType type) {
        this.message = message;
        this.type = type;
    }

}
