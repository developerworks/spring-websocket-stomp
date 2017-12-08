package com.developertack.repository.entity;

import lombok.Data;

import java.util.Calendar;

/**
 * 协议消息
 */
@Data
public class InstantMessage {

    private String to;
    private String from;
    private String message;
    private Calendar created = Calendar.getInstance();
}
