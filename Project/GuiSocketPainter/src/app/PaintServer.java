package app;

import graph.QFGraphics;
import graph.shape;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

//服务器类
public class PaintServer extends JFrame {
    public static actionInfor ai = null;
    public ArrayList<HandleASession> threadArray = new ArrayList<HandleASession>();//存储图形
    public static void main(String[] args) {
        PaintServer frame = new PaintServer();
    }
    public PaintServer() {
        JTextArea jtaLog = new JTextArea();
        // Create a scroll pane to hold text area
        JScrollPane scrollPane = new JScrollPane(jtaLog);
        // Add the scroll pane to the frame
        add(scrollPane, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setLocation(800, 300);
        setTitle("PaintServer");
        setVisible(true);
        try {
            // Create a server socket
            ServerSocket serverSocket = new ServerSocket(8009);
            jtaLog.append(new Date() + ": Server started at socket 8009\n");
            // Number a session
            int sessionNo = 1;
            // Ready to create a session for every two players
            Socket painter;
            while (true) {
                jtaLog.append(new Date() + ": Current number of painter " + (sessionNo - 1) + '\n');
                // Connect to player 1
                painter = serverSocket.accept();
                jtaLog.append(new Date() + ": Player  joined session " + sessionNo + '\n');
                jtaLog.append("Painter IP address" + painter.getInetAddress().getHostAddress() + '\n');
                HandleASession aSession = new HandleASession(painter, ai);
                threadArray.add(aSession);
                Thread t = new Thread(aSession); // Start the new thread
                t.start();
                sessionNo++;
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}

// Define the thread class for handling a new session for two players
class HandleASession implements Runnable {
    public static actionInfor ai = null;
    public Socket player;
    /**
     * Construct a thread
     */
    public HandleASession(Socket player, actionInfor ai) throws IOException {
        this.player = player;
        //this.addedShape=addedShape;
    }
    /**
     * Implement the run() method for the thread
     */
    public void run() {
        //接收操作内部类
        class get implements Runnable {
            @Override
            public void run() {
                while (true) {
                    // 后端接收到了新图像就马上发送到各个用户
                    InputStream in = null;
                    try {
                        in = player.getInputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ObjectInputStream fromPainter = null;
                    try {
                        fromPainter = new ObjectInputStream(in);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        ai = (actionInfor) fromPainter.readObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    System.out.println("后端 GET到的信息" + ai.toString());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        //发送操作内部类
        class post implements Runnable {
            @Override
            public void run() {
                while (true) {
                    if (ai != null) {
                        // 后端发送
                        OutputStream out = null;
                        try {
                            out = player.getOutputStream();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ObjectOutputStream toPainter = null;
                        try {
                            toPainter = new ObjectOutputStream(out);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("后端 POST的信息" + ai.toString());
                        System.out.println(Thread.currentThread().getName() + "线程名称: ");
                        try {
                            toPainter.writeObject(ai);
                            //addedShape=null;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            toPainter.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        //启动接收操作内部类和发送操作内部类
        post sd = new post();
        Thread sdt = new Thread(sd);
        sdt.start();
        get gt = new get();
        Thread gtt = new Thread(gt);
        gtt.start();

    }
}
