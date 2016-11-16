package eccrypto.dsa;

import java.math.BigInteger;
import java.security.SecureRandom;

import eccrypto.ecdh.ECDH;
import eccrypto.math.Point;

public class DSA extends ECDH {

	public DSA() {
		super();
	}

	public DSA(DSAMessage key) {
		super(key);
	}

	public DSAMessage sign(BigInteger m) throws Exception {
		if (m.compareTo(corps.getP()) > 0 || m.compareTo(BigInteger.ZERO) < 0)
			throw new IllegalArgumentException("Message out of bounds");

		// calculer sign
		Point sign = new Point();

		SecureRandom randomGenerator = new SecureRandom();
		BigInteger x = BigInteger.ZERO;
		BigInteger y = BigInteger.ZERO;
		while (y.equals(BigInteger.ZERO)) {
			BigInteger k = null;
			while (x.equals(BigInteger.ZERO)) {
				k = new BigInteger(256, randomGenerator);
				Point A = corps.mutiply(k, P);
				x = A.x;
			}
			y = k.modInverse(corps.getP()).multiply(m.add(d.multiply(x)));
		}
		
		sign.x = x;
		sign.y = y;

		return new DSAMessage(getPublicKey(), m, sign);
	}

	public BigInteger verify(DSAMessage cypher) throws Exception {
		setReceivedKey(cypher);
		
		return null;
	}
}
