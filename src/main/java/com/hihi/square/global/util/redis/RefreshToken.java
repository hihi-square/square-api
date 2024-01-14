package com.hihi.square.global.util.redis;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Setter
@RedisHash(value = "refreshToken", timeToLive = 1209600)    //2주
@ToString
public class RefreshToken{
    @Indexed
    private String uid;
    @Id
    private String refreshToken;

    public RefreshToken(String uid, String refreshToken){
        this.uid = uid;
        this.refreshToken = refreshToken;
    }
}
