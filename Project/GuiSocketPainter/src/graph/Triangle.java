package graph;

import point.point;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Triangle extends shape implements Serializable {
	public point a;
	public point b;
	public point c;
	public Color color;
	public boolean fill=false;
	public boolean show=false;
	/*
	 * Rectangle构造函数
	 * @param a Triangle的第一个顶点
	 * @param b Triangle的第二个顶点
	 * @param c Triangle的第三个顶点
	 */
	public String toString(){
		return ("Triangle:a("+a.getX()+","+a.getY()+"),b("+b.getX()+","+b.getY()+"),c("+c.getX()+","+c.getY()+")");
	}


	public Triangle(point q,point w, point e,Color color) {
		a=q;
		b=w;
		c=e;
		this.color=color;
	}
	public void setColor(Color bk) {
		color=bk;
	}

	public void draw(Graphics2D g) {
		//show=true;
		if(fill==false){//不填充
			g.setColor(color);
			g.drawLine((int)a.getX(),(int)a.getY(),(int)b.getX(),(int)b.getY());
			g.drawLine((int)b.getX(),(int)b.getY(),(int)c.getX(),(int)c.getY());
			g.drawLine((int)c.getX(),(int)c.getY(),(int)a.getX(),(int)a.getY());
		}else{//填充
			g.setColor(color);
			int [] aa,bb;
			aa= new int[] {(int)(a.getX()), (int)(b.getX()), (int)(c.getX())};
			bb= new int[] {(int)(a.getY()), (int)(b.getY()), (int)(c.getY())};
			g.fillPolygon(aa,bb,3);
			g.drawPolygon(aa,bb,3);
		}
	}
	/*
	 *  将Triangle画出
	 */
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
	public point getC() {
		return c;
	}
	public void setC(point c) {
		this.c = c;
	}
	public void setFill(boolean f) {
		this.fill = f;
	}

	/*
	 *  判断传入的点是否是Triangle的顶点
	 *  @param mousePoint 接收一个point类型的参数
	 *  @return 判断改参数是否包含在Rectangle中，是则返回true，否则返回false
	 */
	@Override
	public boolean isPointInShape(point mousePoint) {
		return isInTriangle(a,b,c,mousePoint);
	}

	public boolean isInTriangle(point A, point B, point C, point P) {
		/*利用叉乘法进行判断,假设P点就是M点*/
		int a = 0, b = 0, c = 0;
		point MA = new point(P.getX() - A.getX(),P.getY() - A.getY());
		point MB = new point(P.getX() - B.getX(),P.getY() - B.getY());
		point MC = new point(P.getX() - C.getX(),P.getY() - C.getY());
		/*向量叉乘*/
		a = (int)(MA.getX() * MB.getY() - MA.getY() * MB.getX());
		b = (int)(MB.getX() * MC.getY() - MB.getY() * MC.getX());
		c = (int)(MC.getX() * MA.getY() - MC.getY() * MA.getX());
		if((a <= 0 && b <= 0 && c <= 0)|| (a > 0 && b > 0 && c > 0))
			return true;
		return false;
	}
	/*
	 *  根据传入的点编号以及偏移量对Triangle的点坐标进行修改
	 *  @param index 点编号，通过该参数可以找到与之对应的图形中的点
	 *  @param offset 点偏移量，点的坐标+offset=新的坐标
	 */
	@Override
	public void reshape(int index, point offseted) {   //不再使用  offset 的实际意义
		if(index==1) {
			a.setX(offseted.getX());
			a.setY(offseted.getY());
		}else if(index==2) {
			b.setX(offseted.getX());
			b.setY(offseted.getY());
		}else if(index==3) {
			c.setX(offseted.getX());
			c.setY(offseted.getY());
		}
	}
	/*
	 *  找到传入点对应图形的编号
	 *  @param mousePoint 接收一个point类型的参数
	 *  @return 返回该参数在图形中所对应的编号,如果没有对应则返回-1
	 */
	@Override
	public int FindVertexPoint(point mousePoint) {
		if((mousePoint.getY()-15)<=(int)a.getY()&&(int)a.getY()<=(mousePoint.getY()+15) && (mousePoint.getX()-15)<=(int)a.getX()&&(int)a.getX()<=(mousePoint.getX()+15)) {
			return 1;
		}else if((mousePoint.getY()-15)<=(int)b.getY()&&(int)b.getY()<=(mousePoint.getY()+15) && (mousePoint.getX()-15)<=(int)b.getX()&&(int)b.getX()<=(mousePoint.getX()+15)) {
			return 2;
		}else if((mousePoint.getY()-15)<=(int)c.getY()&&(int)c.getY()<=(mousePoint.getY()+15) && (mousePoint.getX()-15)<=(int)c.getX()&&(int)c.getX()<=(mousePoint.getX()+15)) {
			return 3;
		}
		return -1;
	}

	@Override
	public void move(point offset) {   //使用 offset 的实际意义
		a.setX(a.getX()+offset.getX());
		a.setY(a.getY()+offset.getY());
		b.setX(b.getX()+offset.getX());
		b.setY(b.getY()+offset.getY());
		c.setX(c.getX()+offset.getX());
		c.setY(c.getY()+offset.getY());
	}
	/*
	 *  两个三角形是否相等
	 *  @param triangle 接收一个shape类型的参数
	 *  @return 如果不等则返回-1，否则返回1
	 */
	public boolean equalsQF(shape triangle){
		if(((Triangle)triangle).a.equals(this.a)&&((Triangle)triangle).b.equals(this.b)&&((Triangle)triangle).c.equals(this.c)){
			return true;
		}else{
			return false;
		}
	}

	public String getType(){
		return "Triangle";
	}
}
