package graph;

import point.point;

import javax.swing.*;
import java.awt.*;

public abstract class shape {

	/*
	 * 画图函数
	 */
	abstract void draw(Graphics2D g);
	/*
	 *  判断传入的点是否是shape的顶点
	 *  @param mousePoint 接收一个point类型的参数
	 *  @return 判断改参数是否包含在Rectangle的顶点中，是则返回true，否则返回false
	 */
	abstract boolean isPointInShape(point mousePoint);
	/*
	 *  找到传入点对应图形的编号
	 *  @param mousePoint 接收一个point类型的参数
	 *  @return 返回该参数在图形中所对应的编号
	 */
	abstract int FindVertexPoint(point mousePoint); 
	/*
	 *  根据传入的点编号以及偏移量对Triangle的点坐标进行修改
	 *  @param index 点编号，通过该参数可以找到与之对应的图形中的点
	 *  @param offset 点偏移量，点的坐标+offset=新的坐标
	 */
	abstract void reshape(int index, point offset);

	public abstract boolean getShowStatus();

	public abstract void setColor(Color bc);

	public abstract void move(point offseted);

	public abstract boolean equalsQF(shape addedShape);

	public abstract String getType();
}
	