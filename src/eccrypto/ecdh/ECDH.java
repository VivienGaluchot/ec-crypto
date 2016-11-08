package eccrypto.ecdh;

import java.math.BigInteger;
import java.security.SecureRandom;

import eccrypto.math.Corps;
import eccrypto.math.EllipticCurve;
import eccrypto.math.Point;

public class ECDH {
	/**
	 * Public parameters for ECDH exchange
	 */
	private Corps corps;
	private Point P;

	/**
	 * Private number
	 */
	private BigInteger d;

	/**
	 * Exchange
	 */
	private Point receivedPoint;

	public ECDH() {
		BigInteger p = new BigInteger("8884933102832021670310856601112383279507496491807071433260928721853918699951");
		BigInteger n = new BigInteger("8884933102832021670310856601112383279454437918059397120004264665392731659049");
		BigInteger a4 = new BigInteger("2481513316835306518496091950488867366805208929993787063131352719741796616329");
		BigInteger a6 = new BigInteger("4387305958586347890529260320831286139799795892409507048422786783411496715073");
		BigInteger gx = new BigInteger("7638166354848741333090176068286311479365713946232310129943505521094105356372");
		BigInteger gy = new BigInteger("762687367051975977761089912701686274060655281117983501949286086861823169994");

		EllipticCurve curve = new EllipticCurve(new BigInteger("0"), new BigInteger("0"), new BigInteger("0"), a4, a6);

		corps = new Corps(p, n, curve);

		P = new Point(gx, gy);

		SecureRandom randomGenerator = new SecureRandom();
		d = new BigInteger(256, randomGenerator);
		receivedPoint = null;
	}

	public Point getPointToExchange() {
		return corps.mutiply(d, P);
	}

	public void setReceivedPoint(Point r) {
		receivedPoint = r;
	}

	public Point getCommonSecret() {
		return corps.mutiply(d, receivedPoint);
	}
}
