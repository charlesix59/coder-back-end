package xyz.charlespro.coder.service;

public interface QueueMessageService {
    boolean sendMessage(String sentTo,String content);
    void receiveMessage(String content);
}
