package eccrypto.math;

import java.math.BigInteger;

public class Corps {
	BigInteger p;
	EllipticCurve curve;

	public Corps(BigInteger p, EllipticCurve curve) {
		this.p = p;

	}

	public Point oppose(Point point) {
		Point res = new Point();
		// q.x = p.x
		res.x = point.x.mod(p);
		
		// q.y = -p.y - a1*p.x - a3
		res.y = point.y.negate().subtract(curve.a1.multiply(point.x)).subtract(curve.a3).mod(p);
		return res;
	}

	public Point add(Point p, Point q) {
		BigInteger k;
		Point r = new Point();

		if (p.x.equals(q.x)) {
			
		} else {
			
		}
		
		return r;
	}
}
