package com.leverx.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@RedisHash("code")
public class Code implements Serializable {
    @Id
    private int userId;
    @Indexed
    private String confirmationCode;
    private Date createdDate;
}