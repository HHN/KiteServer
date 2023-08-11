package com.hhn.kite2server.email;

public interface EmailSender {
    void send(String to, String email, String subject);
}