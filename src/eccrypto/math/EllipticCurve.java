package eccrypto.math;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Weierstrass curve y^2 + a1.x.y + a3.y = x^3 + a2.x^2 + a4.x + a6
 * 
 * @author Vivien
 */
public class EllipticCurve implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public BigInteger a1;
	public BigInteger a2;
	public BigInteger a3;
	public BigInteger a4;
	public BigInteger a6;

	public EllipticCurve(BigInteger a1, BigInteger a2, BigInteger a3, BigInteger a4, BigInteger a6) {
		this.a1 = a1;
		this.a2 = a2;
		this.a3 = a3;
		this.a4 = a4;
		this.a6 = a6;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof EllipticCurve))
			return false;
		EllipticCurve C = (EllipticCurve) o;
		return a1.equals(C.a1) && a2.equals(C.a2) && a3.equals(C.a3) && a4.equals(C.a4) && a6.equals(C.a6);
	}
}
