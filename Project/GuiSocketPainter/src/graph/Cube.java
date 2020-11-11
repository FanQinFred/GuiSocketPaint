package graph;

import point.point;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

//正方体类
public class Cube extends shape implements Serializable {
	private point a;  //左下角点的坐标
	private float width;//正方体的点和宽
	private boolean show=false;   //时候显示该图像
	private Color color;  //图像颜色
	public boolean fill=false;   //时候是填充图像

	/*
	 * Cube构造函数
	 * @param q 构造的Cube对象的左上角顶点
	 * @param e 构造的Cube对象的边长
	 */
	public Cube(point q,float e,Color c) {
		a=q;
		width=e;
		color=c;
	}
	public void setColor(Color bk) {
		color=bk;
	}
	/*
	 *  将Cube画出
	 */
	@Override
	public void draw(Graphics2D g) {
		//show=true;
		if(fill==false){  //不填充
			g.setColor(color);
			g.drawLine((int)a.getX(),(int)a.getY(),(int)a.getX()+(int)width,(int)a.getY());
			g.drawLine((int)a.getX(),(int)a.getY(),(int)a.getX(),(int)a.getY()+(int)width);
			g.drawLine((int)a.getX()+(int)width,(int)a.getY(),(int)a.getX()+(int)width,(int)a.getY()+(int)width);
			g.drawLine((int)a.getX(),(int)a.getY()+(int)width,(int)a.getX()+(int)width,(int)a.getY()+(int)width);
		}else{//填充
			System.out.println("绘制填充立方");
			g.setColor(color);
			int [] aa,bb;
			aa= new int[] {(int)(a.getX()),(int)(a.getX()), (int)(a.getX()+width), (int)(a.getX()+width)};
			bb= new int[] {(int)(a.getY()),(int)(a.getY()+width), (int)(a.getY()+width), (int)(a.getY())};
			g.fillPolygon(aa,bb,4);
			g.drawPolygon(aa,bb,4);
		}
	}

	//方便打印信息，调试
	public String toString(){
		return ("Cube:左上坐标("+a.getX()+","+a.getY()+"),width："+width);
	}

	public point getA() {
		return a;
	}
	public void setA(point a) {
		this.a = a;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	//设置时候为填充的图形
	public void setFill(boolean f) {
		this.fill = f;
	}
	/*
	 *  判断传入的点是否是Cube的顶点
	 *  @param mousePoint 接收一个point类型的参数
	 *  @return 判断改参数是否包含在Cube的顶点中，是则返回true，否则返回false
	 */
	@Override
	public boolean isPointInShape(point mousePoint) {
		if(((a.getX()+width)>=mousePoint.getX()&&mousePoint.getX()>=a.getX()) && ((a.getY()+width)>=mousePoint.getY()&&mousePoint.getY()>=a.getY())) {
			return true;
		}
		return false;
	}
	/*
	 *  根据传入的点编号以及偏移量对Cube的点坐标进行修改
	 *  @param index 点编号，通过该参数可以找到与之对应的图形中的点
	 *  @param offset 点偏移量，点的坐标+offset=新的坐标
	 */
	@Override
	public void reshape(int index, point offset) {   //待完善
		if(index==1) {
			a.setX(a.getX()+offset.getX());
			a.setY(a.getY()+offset.getY());
		}else{
			if(index==2) {
				a.setX(a.getX()+offset.getX());
				a.setY(a.getY()+offset.getY());
			}else{
				if(index==3) {
					a.setX(a.getX()+offset.getX());
					a.setY(a.getY()+offset.getY());
				}else{
					if(index==4) {
						a.setX(a.getX()+offset.getX());
						a.setY(a.getY()+offset.getY());
					}
				}
			}
		}
	}
	/*
	 *  找到传入点对应图形的编号
	 *  @param mousePoint 接收一个point类型的参数
	 *  @return 返回该参数在图形中所对应的编号，如果不在则返回-1
	 */
	@Override
	public int FindVertexPoint(point mousePoint) {  //待完善
		if(((a.getX()+width)>=mousePoint.getX()&&mousePoint.getX()>=a.getX()) && ((a.getY()+width)>=mousePoint.getY()&&mousePoint.getY()>=a.getY())) {
			System.out.println("return 1;");
			return 1;
		}
		return -1;
	}//找到传入点对应图形的编号

	public boolean getShowStatus() {
		return show;
	}

	@Override
	public void move(point offset) {   //不再使用  offset 的实际意义
		a.setX(a.getX()+offset.getX());
		a.setY(a.getY()+offset.getY());
	}
	/*
	 *  两个正方体是否相等
	 *  @param s 接收一个shape类型的参数
	 *  @return 如果不等则返回-1，否则返回1
	 */
	public boolean equalsQF(shape s){
		if(this.a.equals(((Cube)s).a) && this.width==((Cube)s).width){
			return true;
		}
		return false;
	}
    // 获得当前图形是什么图形
	public String getType(){
		return "Cube";
	}
}
