package eccrypto.math;

import java.math.BigInteger;

public class Corps {
	BigInteger p;
	BigInteger n;
	EllipticCurve curve;

	private static BigInteger TWO = new BigInteger("2");
	private static BigInteger THREE = new BigInteger("3");

	public Corps(BigInteger p, BigInteger n, EllipticCurve curve) {
		this.p = p;
		this.n = n;
		this.curve = curve;
	}

	public Point oppose(Point point) {
		if (point.isInfinit)
			return point;

		Point res = new Point();
		// q.x = p.x²
		res.x = point.x.mod(p);

		// q.y = -p.y - a1*p.x - a3
		res.y = point.y.negate().subtract(curve.a1.multiply(point.x)).subtract(curve.a3).mod(p);
		return res;
	}

	public Point add(Point P, Point Q) {
		if (P.isInfinit) {
			return Q;
		} else if (Q.isInfinit) {
			return P;
		}

		if (P.equals(oppose(Q)))
			return new Point(true);

		BigInteger k;
		Point r = new Point();

		if (P.x.equals(Q.x)) {
			// k = (3*p.x^2 + 2*a2*p.x + a4 - a1*p.y) / (2*p.y + a1*p.x + a3)
			k = THREE.multiply(P.x.pow(2));
			k = k.add(TWO.multiply(curve.a2).mod(p).multiply(P.x)).mod(p);
			k = k.add(curve.a4.subtract(curve.a1.multiply(P.y).mod(p))).mod(p);
			k = k.multiply((TWO.multiply(P.y).mod(p).add(curve.a1.multiply(P.x).mod(p).add(curve.a3))).modInverse(p))
					.mod(p);
		} else {
			// k = (p.y - q.y) / (p.x - q.x)
			k = P.y.subtract(Q.y).mod(p).multiply((P.x.subtract(Q.x).mod(p)).modInverse(p)).mod(p);
		}

		// r.x = k^2 + a1*k - a2 - p.x - q.x
		r.x = k.pow(2).mod(p).add(curve.a1.multiply(k)).mod(p).subtract(curve.a2).mod(p).subtract(P.x).mod(p)
				.subtract(Q.x).mod(p);
		// r.y = -(k - a1) r.x + k * p.x - p.y - a3
		r.y = k.subtract(curve.a1).negate().multiply(r.x).mod(p).add(k.multiply(P.x)).mod(p).subtract(P.y)
				.subtract(curve.a3).mod(p);

		return r;
	}

	public Point mutiply(BigInteger m, Point p) {
		Point r = new Point(true);

		if (m.compareTo(BigInteger.ZERO) < 0) {
			m = m.negate();
			p = oppose(p);
		}
		
		m = m.mod(n);

		while (m.compareTo(BigInteger.ZERO) > 0) {
			if (m.mod(TWO).compareTo(BigInteger.ZERO) > 0) {
				r = add(r, p);
			}
			p = add(p, p);
			m = m.shiftRight(1);
		}

		return r;
	}

	public boolean contain(Point pt) {
		if (pt.isInfinit)
			return true;

		// A = y^2 + a1.x.y + a3.y
		BigInteger A = pt.y.pow(2);
		A = A.add(curve.a1.multiply(pt.x.multiply(pt.y)));
		A = A.add(curve.a3.multiply(pt.y));
		A = A.mod(p);

		// B = x^3 + a2.x^2 + a4.x + a6
		BigInteger B = pt.x.pow(3);
		B = B.add(curve.a2.multiply(pt.x.pow(2)));
		B = B.add(curve.a4.multiply(pt.x));
		B = B.add(curve.a6);
		B = B.mod(p);

		return A.equals(B);
	}
}
