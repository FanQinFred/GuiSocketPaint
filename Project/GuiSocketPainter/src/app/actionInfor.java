package app;

import graph.Triangle;
import graph.shape;
import point.point;

import java.awt.*;
import java.io.Serializable;

/*
 *用于前后端数据的传递
 */
public class actionInfor implements Serializable {
    public int type;   // 0代表新绘制图形， 1代表move， 2代表reshape
    public shape addedShape;
    public point pointS;
    public point pointE;

    //构造方法
    public actionInfor(shape addedShape) {
        this.type = 0;
        this.addedShape = addedShape;
        this.pointS = null;
        this.pointE = null;
    }

    //构造方法
    public actionInfor(point b) {
        this.type = 3;
        this.addedShape = null;
        this.pointS = b;
        this.pointE = null;
    }

    //构造方法
    public actionInfor(int type, point S, point E) {
        this.type = type;
        this.addedShape = null;
        this.pointS = S;
        this.pointE = E;
    }

    //判断两个actionInfor实例时候相等
    public boolean equals(actionInfor a) {
        if (this.pointE == null && this.pointS == null) {
            if (this.type == a.type && this.addedShape.getType().equals(a.addedShape.getType()) && this.addedShape.equalsQF(a.addedShape)) {
                return true;
            } else {
                return false;
            }
        } else {
            if (this.type == a.type && this.pointS.equals(a.pointS) && this.pointE.equals(a.pointE)) {
                return true;
            } else {
                return false;
            }
        }
    }

    //用于打印调试信息
    public String toString() {
        if (addedShape != null) {
            return ("图形" + addedShape.toString() + "\n类型：" + type);
        } else {
            if (pointS != null && pointE != null) {
                return ("\n类型：" + type + "\n 点一：" + this.pointS.toString() + "\n 点二：" + this.pointE.toString());
            }
        }
        return "Nothing";
    }

    //获得 addedShape 类型
    public String getType() {
        return addedShape.getType();
    }

    public static void main(String[] args) {
        shape addedShape = new Triangle(new point(0, 0), new point(100, 0), new point(0, 100), Color.red);
        actionInfor a1 = new actionInfor(addedShape);
        actionInfor a2 = new actionInfor(1, new point(0, 100), new point(100, 0));
        actionInfor a3 = new actionInfor(1, new point(0, 100), new point(100, 0));
        actionInfor a4 = new actionInfor(addedShape);
        if (a4.equals(a1)) {
            System.out.println("似乎没问题");
        } else {
            System.out.println("No");
        }
    }
}

