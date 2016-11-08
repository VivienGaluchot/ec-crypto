package eccrypto.math;

import java.math.BigInteger;

/**
 * Elliptic Curve point
 * 
 * @author Vivien
 */
public class Point {
	public BigInteger x;
	public BigInteger y;
	public boolean isInfinit;
	
	public Point(){
		x = new BigInteger("0");
		y = new BigInteger("0");
		isInfinit = false;
	}
	
	public Point(boolean infinit){
		x = new BigInteger("0");
		y = new BigInteger("0");
		isInfinit = infinit;
	}
	
	public Point(BigInteger x, BigInteger y){
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Point Q){
		if(isInfinit)
			return Q.isInfinit;
		else
			return x.equals(Q.x) && y.equals(Q.y);
	}
	
	public String toString(){
		if(isInfinit)
			return "infinite";
		return "("+x.toString()+","+y.toString()+")";
	}
}
