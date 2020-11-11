package graph;

import point.point;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class Rectangle extends shape implements Serializable {
	private point a;
	private point b;
	private float width;
	private float height;
	private boolean show=false;
	private Color color;
	public boolean fill=false;

	/*
	 * Rectangle构造函数
	 * @param q Rectangle的一个顶点
	 * @param w Rectangle与q成对角的顶点
	 */
	public Rectangle(point q,point w,Color color) {
		if(w.getX()>q.getX()){
			a=q;
			b=w;
		}else{
			a=w;
			b=q;
		}
		this.color=color;
		float wi=q.getX()-w.getX();
		float he=q.getY()-w.getY();
		width=wi>0?wi:-wi;
		height=he>0?he:-he;
	}
	/*
	 *  将Rectangle画出
	 */
	@Override
	public void draw(Graphics2D g) {
		if(fill==false){//不填充
			System.out.println("绘制空的矩形");
			g.setColor(color);
			g.drawLine((int)a.getX(),(int)a.getY(),(int)a.getX(),(int)a.getY()+(int)height);
			g.drawLine((int)a.getX(),(int)a.getY(),(int)a.getX()+(int)width,(int)a.getY());
			g.drawLine((int)b.getX(),(int)b.getY(),(int)b.getX(),(int)b.getY()-(int)height);
			g.drawLine((int)b.getX(),(int)b.getY(),(int)b.getX()-(int)width,(int)b.getY());
		}else{//填充
			System.out.println("绘制填充矩形");
			g.setColor(color);
			int [] aa,bb;
			aa= new int[] {(int)(a.getX()),(int)(a.getX()+width), (int)(b.getX()), (int)(a.getX())};
			bb= new int[] {(int)(a.getY()),(int)(a.getY()), (int)(b.getY()), (int)(a.getY()+height)};
			g.fillPolygon(aa,bb,4);
			g.drawPolygon(aa,bb,4);

		}
	}

	//方便调试
	public String toString(){
		return 	("Rectangle:左上坐标("+a.getX()+","+a.getY()+"),右下坐标("+b.getX()+","+b.getY()+"),width:"+width+",height:"+height);
	}
	public boolean getShowStatus() {
		return show;
	}
	public point getA() {
		return a;
	}
	public void setA(point a) {
		this.a = a;
	}
	public point getB() {
		return b;
	}
	public void setB(point b) {
		this.b = b;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public void setFill(boolean f) {
		this.fill = f;
	}
	/*
	 *  判断传入的点是否是Rectangle的顶点
	 *  @param mousePoint 接收一个point类型的参数
	 *  @return 判断改参数是否包含在Rectangle中，是则返回true，否则返回false
	 */
	@Override
	public boolean isPointInShape(point mousePoint) {
		if(mousePoint.getX()>=a.getX() && mousePoint.getX()<=b.getX() && mousePoint.getY()>=a.getY() && mousePoint.getY()<=b.getY() ) {
			return true;
		}
		return false;
	}
	/*
	 *  根据传入的点编号以及偏移量对Rectangle的点坐标进行修改
	 *  @param index 点编号，通过该参数可以找到与之对应的图形中的点
	 *  @param offset 点偏移量，点的坐标+offset=新的坐标
	 */
	@Override
	public void reshape(int index, point offset) {
		if(index==1) {
			a.setX(offset.getX());
			a.setY(offset.getY());
			System.out.println("Rectangle:point a 修改成功");
		}else if(index==2) {
			b.setX(offset.getX());
			b.setY(offset.getY());
			System.out.println("Rectangle:point b 修改成功");
		}
		float wi=a.getX()-b.getX();
		float he=a.getY()-b.getY();
		width=wi>0?wi:-wi;
		height=he>0?he:-he;
	}

	public void setColor(Color bk) {
		color=bk;
	}
	/*
	 *  找到传入点对应图形的编号
	 *  @param mousePoint 接收一个point类型的参数
	 *  @return 返回该参数在图形中所对应的编号,没有对应则返回-1
	 */
	@Override
	public int FindVertexPoint(point mousePoint) {
		if(mousePoint.getX()<=(a.getX()+15)&& mousePoint.getX()>=(a.getX()-15) && mousePoint.getY()<=(a.getY()+15) &&mousePoint.getY()>=(a.getY()-15)) {
			return 1;
		}else if(mousePoint.getX()<=(b.getX()+15)&& mousePoint.getX()>=(b.getX()-15) && mousePoint.getY()<=(b.getY()+15) &&mousePoint.getY()>=(b.getY()-15)) {
			return 2;
		}
		return -1;
	}

	//移动矩形
	@Override
	public void move(point offset) {   //不再使用  offset 的实际意义
		a.setX(a.getX()+offset.getX());
		a.setY(a.getY()+offset.getY());
		b.setX(b.getX()+offset.getX());
		b.setY(b.getY()+offset.getY());
	}
	/*
	 *  两个矩形是否相等
	 *  @param s 接收一个shape类型的参数
	 *  @return 如果不等则返回-1，否则返回1
	 */
	public boolean equalsQF(shape s){
		if(this.a.equals(((Rectangle)s).a)&&this.b.equals(((Rectangle)s).b)){
			return  true;
		}
		return false;
	}

	public String getType(){
		return "Rectangle";
	}
	
}
