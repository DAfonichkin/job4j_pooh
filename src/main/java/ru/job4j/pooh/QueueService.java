package ru.job4j.pooh;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    private final Map<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String src = req.getSourceName();
        if ("POST".equals(req.httpRequestType())) {
            queue.putIfAbsent(src, new ConcurrentLinkedQueue<>());
            queue.get(src).add(req.getParam());
            return new Resp("", "200");
        }
        if ("GET".equals(req.httpRequestType())) {
            return new Resp(queue.getOrDefault(src, new ConcurrentLinkedQueue<>()).poll(), "200");
        }
        return new Resp("", "400");
    }
}