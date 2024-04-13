package com.devops;

import java.util.UUID;

public class Utils {
    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString().toUpperCase().replace("-",""));
    }
}
