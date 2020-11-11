package app;

import graph.QFGraphics;
import graph.shape;
import point.point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class OpenGLApp extends JFrame {

    public Graphics2D g2;   // 画板的画笔

    public QFGraphics graphics = new QFGraphics();   // 需要绘制的所有图形

    public DrawListener drawListener;   //事件监听类

    public OpenGLApp() {   // 构造方法
        addComponentListener(new ComponentAdapter() {//让窗口响应大小改变事件
            public void componentResized(ComponentEvent e) {
                System.out.println("窗口大小改变了~");
                if (drawListener != null) {
                    drawListener.rePaintG();
                }
            }

            /**
             * Invoked when the component's position changes.
             */
            public void componentMoved(ComponentEvent e) {
                System.out.println("componentMoved~");
                if (drawListener != null) {
                    drawListener.rePaintG();
                }
            }
        });

    }

    public actionInfor getActionInfo() {
        return drawListener.getActionInfo();
    }

    public void delHandleInfor() {
        drawListener.delHandleInfor();
    }

    public void handleActionInfo(actionInfor a) {
        //drawListener.setAddedShape(addedShape);
        drawListener.handleActionInfo(a);
    }

    public void showdraw() {
        graphics.setG2(g2);
        JPanel panel = new JPanel();
        setTitle("Paint Together");
        panel.setSize(1100, 700);
        FlowLayout f1 = new FlowLayout();
        panel.setLayout(f1);
        setSize(1250, 750);
        setDefaultCloseOperation(3);
        setLocationRelativeTo(null);
        //实例化监听器
        drawListener = new DrawListener(this, graphics);
        //创建图形按钮
        String[] array = {"Move", "Reshape", "FillRect", "Rect", "FillTriangle", "Triangle", "FillCube", "Cube"};
        for (int i = 0; i < array.length; i++) {
            JButton button = new JButton(array[i]);
            panel.add(button);
            button.addActionListener(drawListener);
        }
        //颜色按钮
        Color[] colorArray = {Color.black, Color.blue, Color.green, Color.red, Color.CYAN, Color.magenta};
        for (int i = 0; i < colorArray.length; i++) {
            JButton button = new JButton();
            button.setBackground(colorArray[i]);
            button.setPreferredSize(new Dimension(30, 30));//设置按钮大小
            panel.add(button);
            button.addActionListener(drawListener);
        }
        this.add(panel);  //面板添加
        this.setVisible(true); //setVisible设置窗体可见，放在所有组件后
        Graphics g = this.getGraphics();
        this.addMouseListener(drawListener);//鼠标点击
        this.addMouseMotionListener(drawListener);  //鼠标移动
        drawListener.setG(g);
    }

    // 重载paint（）方法，实现双缓冲技术，去掉闪烁
    public void paint(Graphics g) {
        //创建Image空对象，保存绘制内容
        Image iBuffer = null;
        //创建Graphics空对象，作为参数传递给原方法函数的输入
        Graphics gBuffer = null;
        if (iBuffer == null) {
            //创建Image空对象，并设置大小
            iBuffer = this.createImage(this.getSize().width, this.getSize().height);
            //获得Graphics实例
            gBuffer = iBuffer.getGraphics();
        }
        //设置背景
        gBuffer.setColor(this.getBackground());
        //调用原本的方法，只是传入的是我们自己的Graphics
        super.paint(gBuffer);
        //画出，呈现
        g.drawImage(iBuffer, 0, 0, this);
    }

    public void paintComponents(Graphics g) {
        super.paintComponents(g);
    }

    public void moveShape(point t1, point t2, Color color) {
        graphics.moveShape(t1, t2, color);
        this.paint(g2);
        graphics.draw(g2);
    }

    public static void main(String args[]) throws InterruptedException {
        OpenGLApp dr = new OpenGLApp();
        dr.showdraw();
    }

}
