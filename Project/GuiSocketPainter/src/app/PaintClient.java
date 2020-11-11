package app;

import javax.swing.*;
import java.io.*;
import java.net.*;

//绘制的客户端类
public class PaintClient extends JFrame implements Runnable {
    //用户产生的操作
    private actionInfor ai = null;
    // Create and initialize a title label
    private JLabel jlblTitle = new JLabel();
    // Create and initialize a status label
    private JLabel jlblStatus = new JLabel();
    // Input and output streams from/to server
    private ObjectInputStream fromServer = null;
    private ObjectOutputStream toServer = null;
    // Host name or ip
    private String host = "localhost";

    public PaintClient() {

    }

    public void run() {
        OpenGLApp op = new OpenGLApp();
        op.showdraw();
        Socket socket = null;
        InputStream in = null;
        OutputStream out = null;
        try {   //通过端口进行连接
            socket = new Socket(host, 8009);
            in = socket.getInputStream();
            out = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        OutputStream finalOut = out;
        //发送操作内部类
        class postShape extends Thread {
            public void run() {
                while (true) {
                    if (op.getActionInfo() != null) {
                        try {
                            toServer = new ObjectOutputStream(finalOut);  //大坑
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            ai = op.getActionInfo();
                            toServer.writeObject(ai);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            toServer.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("前端 POST信息为：" + ai.toString());
                        op.delHandleInfor();
                    }
                    try {  //注意：sleep语句要在if语句外
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        postShape pS = new postShape();
        Thread pT = new Thread(pS);
        pT.start();
        InputStream finalIn = in;
        //接收操作内部类
        class getShape extends Thread {
            public void run() {
                boolean firsetGet = true;
                actionInfor aGet = null;
                while (true) {
                    try {  //从后端获得需要新绘制的图形
                        //相同图像操作接受一次就够了
                        if (firsetGet) {
                            fromServer = new ObjectInputStream(finalIn);  //大坑
                            aGet = (actionInfor) fromServer.readObject();
                            System.out.println("前端firsetGet GET的信息：" + aGet.toString());
                            op.handleActionInfo(aGet);
                            firsetGet = false;
                        } else {
                            actionInfor temp = null;
                            fromServer = new ObjectInputStream(finalIn);  //大坑
                            temp = (actionInfor) fromServer.readObject();
                            if (!temp.equals(aGet)) {
                                aGet = temp;
                                System.out.println("前端 GET的信息：" + aGet.toString());
                                op.handleActionInfo(aGet);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        getShape gS = new getShape();
        Thread gT = new Thread(gS);
        gT.start();
    }

    private void connectToServer() {
        // Control the game on a separate thread
        Thread thread = new Thread(this);
        thread.start();
    }
    /**
     * This main method enables the applet to run as an application
     */
    public static void main(String[] args) {
        PaintClient p = new PaintClient();
        p.connectToServer();
    }
}

