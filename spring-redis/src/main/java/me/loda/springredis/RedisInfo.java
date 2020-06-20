package me.loda.springredis;

import lombok.Data;

@Data
public class RedisInfo {
    private String name;
    private String host;
    private int port;
}
