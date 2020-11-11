package graph;

import point.point;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

public class QFGraphics implements Serializable {
	private Graphics2D g;
	private ArrayList<shape> arrs=new ArrayList<shape>();//存储图形

	public void setG2(Graphics2D g){
		this.g=g;
	}

	public int getShapeNum(){
		return arrs.size();
	}

	/*
	 * 向arrs中添加新的图形
	 * @param e 待添加的图形
	 */
	public void add(shape e) {
		arrs.add(e);
	}

	/*
	 * 调用arrs中存储的图形的draw函数，画出每一个图形
	 */
	public void draw(Graphics2D g) {
		for(int i=0;i<arrs.size();i++) {
			if(!arrs.get(i).getShowStatus()) {
				arrs.get(i).draw(g);  //已经绘制的图形就不用再次绘制了
			}
		}
	}

	/*
	 * 改变存储图形的属性，找到传入点是否属于某个存储的图形。
	 * 如果属于，则对那个图形的那个点进行增加偏移量的修改
	 * @param source 传入的点
	 * @param offset 该点的偏移量
	 */
	public void reshape(point source, point offseted,Color setColor) {
		for(int i=0;i<arrs.size();i++) {
			if(arrs.get(i).isPointInShape(source)) {
				int index=arrs.get(i).FindVertexPoint(source);
				arrs.get(i).reshape(index, offseted);
				break;
			}
		}
	}

	public void moveShape(point source, point offset,Color setColor) {
		for(int i=0;i<arrs.size();i++) {
			if(arrs.get(i).isPointInShape(source)) {
				arrs.get(i).move(offset);
				break;
			}
		}
	}
}
