package eccrypto.math;

import java.math.BigInteger;

/**
 * Weierstrass curve y^2 + a1.x.y + a3.y = x^3 + a2.x^2 + a4.x + a6
 * 
 * @author Vivien
 */
public class EllipticCurve {
	BigInteger a1;
	BigInteger a2;
	BigInteger a3;
	BigInteger a4;
	BigInteger a6;

	public EllipticCurve(BigInteger a1, BigInteger a2, BigInteger a3, BigInteger a4, BigInteger a6) {
		this.a1 = a1;
		this.a2 = a2;
		this.a3 = a3;
		this.a4 = a4;
		this.a6 = a6;
	}

	public boolean isOnCurve(Point p) {
		// A = y^2 + a1.x.y + a3.y
		BigInteger A = p.y.pow(2);
		A.add(a1.multiply(p.x.multiply(p.y)));
		A.add(a3.multiply(p.y));
		
		// B = x^3 + a2.x^2 + a4.x + a6
		BigInteger B = p.x.pow(3);
		B.add(a2.multiply(p.x.pow(2)));
		B.add(a4.multiply(p.x));
		B.add(a6);
		
		return A.equals(B);
	}
}
