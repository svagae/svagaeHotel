package com.svagae.svagaeHotel.utils;
import java.util.Base64;
public class Base64Example {
        public static void main(String[] args) {
            String originalSecret = "MySuperSecureRandomGeneratedKey123456";
            String base64EncodedSecret = Base64.getEncoder().encodeToString(originalSecret.getBytes());
            System.out.println("Base64 Encoded Secret: " + base64EncodedSecret);
        }
    }

