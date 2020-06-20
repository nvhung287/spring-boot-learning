package me.loda.springredis;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Student {
    String name;
    int old;
}
