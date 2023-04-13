package ru.job4j.pooh;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    private final Map<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> queue
            = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String src = req.getSourceName();
        if ("POST".equals(req.httpRequestType())) {
            for (ConcurrentLinkedQueue<String> clientQueue : queue.get(src).values()) {
                clientQueue.offer(req.getParam());
            }
            return new Resp("", "200");
        }
        if ("GET".equals(req.httpRequestType())) {
            queue.putIfAbsent(src, new ConcurrentHashMap<>());
            queue.get(req.getSourceName()).putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
            String text = queue.get(req.getSourceName()).get(req.getParam()).poll();
            return text == null ? new Resp("", "204") : new Resp(text, "200");
        }
        return new Resp("", "400");
    }
}