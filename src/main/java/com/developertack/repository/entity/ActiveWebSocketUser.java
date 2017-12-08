package com.developertack.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Calendar;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveWebSocketUser {
    /**
     * 用户ID
     */
    @Id
    private String id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 连接时间
     */
    private Calendar connectionTime;

}
