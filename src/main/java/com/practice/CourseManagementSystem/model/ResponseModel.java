package com.practice.CourseManagementSystem.model;

import com.practice.CourseManagementSystem.dtos.MessageDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ResponseModel {

    private Integer statusCode;
    private Object dataModel;
    private List<MessageDto> responseMessage = new ArrayList<>();

    public ResponseModel(Integer statusCode, Object dataModel, MessageDto message){
        this.statusCode = statusCode;
        this.dataModel = dataModel;
        responseMessage.add(message);
    }

    public ResponseModel(Integer statusCode, MessageDto messageDto){
        this.statusCode = statusCode;
        responseMessage.add(messageDto);
    }
}
