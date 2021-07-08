package com.htinf.sm.common;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/log/{userName}/{password}/{ip}/{port}/{command}/log.do")
@Component
public class WebSocketController {

    private static Session session;

    @OnOpen
    public void onOpen(Session session, @PathParam("userName") String userName, @PathParam("password") String password,
                       @PathParam("ip") String ip, @PathParam("port") int port, @PathParam("command") String command) {
        System.out.println("有新的客户端连接了：" + session.getId());
        // 一定要启动新的线程，防止InputStream阻塞处理WebSocket的线程

        command = command.replaceAll("~", "/");
        LogThread thread = new LogThread(session, userName, password, ip, port, command);
        thread.start();
    }

    @OnClose
    public void onClose() {
//        System.out.println("有客户端断开了：" + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("服务端收到客户端" + session.getId() + "发来的消息：" + message);
        try {
            sendMessage(message + "：开始收集");
        } catch (Exception e) {
            System.out.println("发送异常");
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public static void sendMessage(String message) throws Exception {
        if (session != null) {
            if (WebSocketController.session.isOpen()) {
                WebSocketController.session.getBasicRemote().sendText(message);
            }
        }
    }
}