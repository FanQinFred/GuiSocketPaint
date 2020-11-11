package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import graph.Rectangle;
import point.point;
import graph.*;
import graph.QFGraphics;

//先继承类，后继承接口
public class DrawListener extends MouseAdapter implements ActionListener {
    //x1,y1分别是鼠标按下时的横坐标和纵坐标
    //x2,y2分别是鼠标释放时的横坐标和纵坐标
    private int x1, y1, x2, y2;
    // g2是用于绘制图形的画笔
    private Graphics2D g2;
    //str为功能按钮的字符，取值有“Move","Reshape","Rect", "Triangle","Cube"
    private String str = "Triangle";
    //绘制的图形的颜色
    private Color color;
    //所有要绘制的图形的集合
    private QFGraphics graphics;   // 自己写的图形集合类
    //绘制的面板
    private JFrame j;
    //用户新绘制的图形
    private shape addedShape;
    //用户找面板上的操作，要传递给服务器
    private actionInfor ai = null;

    //构造方法
    public DrawListener(JFrame j, QFGraphics graphics) {
        this.j = j;
        this.graphics = graphics;
    }

    //图形重绘
    public void rePaintG() {
        graphics.draw(g2);
    }

    //设置Graphics2D
    public void setG(Graphics g) {
        g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);// 设置画笔开启抗锯齿n
    }

    //获得按钮信息
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();//getSource返回事件源类型，也可以用getActionCommand返回String类型
        if (button.getText() != "") {//如果为图形按钮
            str = button.getText();
        } else {
            color = button.getBackground();
            System.out.println("color: " + color);
        }
    }

    //获得即将新增加的图形
    public shape getAddedShape() {
        return addedShape;
    }

    //获得操作的信息
    public actionInfor getActionInfo() {
        return ai;
    }

    //设置操作的信息
    public void setActionInfo(actionInfor a) {
        ai = a;
    }

    //删除新增的图形信息
    public void delAddedShape() {
        addedShape = null;
    }

    //删除操作信息
    public void delHandleInfor() {
        ai = null;
    }

    //将新增的图形绘制到画板
    public void drawAddedShape(shape s) {
        graphics.add(s);
        graphics.draw(g2);
        addedShape = null;
    }

    //处理用户的行为信息
    public void handleActionInfo(actionInfor a) {
        if (a.type == 0) {   //新增图形
            j.paint(g2);
            graphics.add(a.addedShape);
            graphics.draw(g2);
        } else {    // 移动图形
            if (a.type == 1) {
                j.paint(g2);
                graphics.moveShape(a.pointS, a.pointE, color);
                graphics.draw(g2);
            } else {   //填充图形
                    j.paint(g2);
                    graphics.reshape(a.pointS, a.pointE, color);
                    graphics.draw(g2);
            }
        }
    }

    public void mousePressed(MouseEvent e) {   //鼠标按下
        x1 = e.getX();  // 获得用户按下时 鼠标的位置
        y1 = e.getY();
        g2.setColor(color);
    }

    public void mouseReleased(MouseEvent e) {   //鼠标释放 弹起
        x2 = e.getX();
        y2 = e.getY();
        //画空心矩形
        if (str.equals("Rect")) {
            Rectangle s = new Rectangle(new point(x1, y1), new point(x2, y2), color);
            actionInfor aTemp = new actionInfor(s);
            setActionInfo(aTemp);
        }

        //画等腰三角形
        if (str.equals("Triangle")) {
            Triangle s = new Triangle(new point(x1, y1), new point(2 * x1 - x2, y2), new point(x2, y2), color);
            actionInfor aTemp = new actionInfor(s);
            setActionInfo(aTemp);
        }

        //画等腰三角形
        if (str.equals("Cube")) {
            int w = Math.abs(x2 - x1);
            Cube s = new Cube(new point(x1, y1), w, color);
            actionInfor aTemp = new actionInfor(s);
            setActionInfo(aTemp);
        }

        if (str.equals("FillRect")) {
            Rectangle s = new Rectangle(new point(x1, y1), new point(x2, y2), color);
            s.setFill(true);
            actionInfor aTemp = new actionInfor(s);
            setActionInfo(aTemp);
        }

        //画等腰三角形
        if (str.equals("FillTriangle")) {
            Triangle s = new Triangle(new point(x1, y1), new point(2 * x1 - x2, y2), new point(x2, y2), color);
            s.setFill(true);
            actionInfor aTemp = new actionInfor(s);
            setActionInfo(aTemp);
        }

        //画等腰三角形
        if (str.equals("FillCube")) {
            int w = Math.abs(x2 - x1);
            Cube s = new Cube(new point(x1, y1), w, color);
            s.setFill(true);
            actionInfor aTemp = new actionInfor(s);
            setActionInfo(aTemp);
        }

        if (str.equals("Reshape")) {
            actionInfor aTemp = new actionInfor(2, new point(x1, y1), new point(x2, y2));
            setActionInfo(aTemp);
        }

        if (str.equals("Move")) {
            actionInfor aTemp = new actionInfor(1, new point(x1, y1), new point(x2 - x1, y2 - y1));
            setActionInfo(aTemp);
        }

    }

    // 鼠标点击事件
    public void mouseClicked(MouseEvent e) {
        System.out.println("点击");//按下和释放要在同一位置才是点击
        x2 = e.getX();
        y2 = e.getY();
        if (str.equals("Fill")) {
            actionInfor aTemp = new actionInfor(new point(x2, y2));
            setActionInfo(aTemp);
        }
    }

    //鼠标拖拽事件 未用
    public void mouseDragged(MouseEvent e) {

    }
}
