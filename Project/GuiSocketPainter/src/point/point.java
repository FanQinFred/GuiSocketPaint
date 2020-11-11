package point;

import java.io.Serializable;

public class point implements Serializable {
	private float x;
	private float y;

	/*
	 * point构造函数
	 * @param a 点的x坐标
	 * @param b 点的y坐标
	 */
	public point(float a, float b) {
		x=a;
		y=b;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	/*
	 *  两个点是否相等
	 *  @param p 接收一个point类型的参数
	 *  @return 如果不等则返回-1，否则返回1
	 */
	public boolean equals(point p){
		if(p.x==this.x&&p.y==this.y){
			return true;
		}else{
			return  false;
		}
	}
	
}
