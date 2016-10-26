package eccrypto.math;

import java.math.BigInteger;

/**
 * Elliptic Curve point
 * 
 * @author Vivien
 */
public class Point {
	BigInteger x;
	BigInteger y;
	
	public Point(){
		x = new BigInteger("0");
		y = new BigInteger("0");
	}
	
	public Point(BigInteger x, BigInteger y){
		this.x = x;
		this.y = y;
	}
}
