package eccrypto.dsa;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import eccrypto.ecdh.DHMessage;
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
		Point sign = new Point(BigInteger.ZERO, BigInteger.ZERO);

		SecureRandom randomGenerator = new SecureRandom();
		while (sign.y.equals(BigInteger.ZERO)) {
			BigInteger k = null;
			while (sign.x.equals(BigInteger.ZERO) || sign.isInfinit) {
				k = new BigInteger(256, randomGenerator);
				k = k.mod(corps.getN());
				Point A = corps.mutiply(k, P);
				sign.x = A.x.mod(corps.getN());
			}
			sign.y = k.modInverse(corps.getN()).multiply(hash(m).add(d.multiply(sign.x))).mod(corps.getN());
		}

		return new DSAMessage(getPublicKey(), m, sign);
	}

	public boolean verify(DHMessage senderKey, DSAMessage msg) throws Exception {
		setReceivedKey(msg);

		BigInteger n = corps.getN();

		if (msg.key.isInfinit)
			throw new Exception("key is infinit");
		if (!corps.contain(msg.key))
			throw new Exception("wrong key, not in corps");
		if (!corps.mutiply(n, msg.key).isInfinit)
			throw new Exception("wrong key, n*key != infinit");
		if (!msg.sign.isInRangeZeroTo(n))
			throw new Exception("wrong key, out of range");
		if (!senderKey.equals(msg))
			throw new Exception("wrong publicKey");

		BigInteger w = msg.sign.y.modInverse(n).mod(n);
		BigInteger u1 = hash(msg.m).multiply(w).mod(n);
		BigInteger u2 = msg.sign.x.multiply(w).mod(n);
		Point X = corps.add(corps.mutiply(u1, P), corps.mutiply(u2, msg.key));

		System.out.println(X);

		return msg.sign.x.equals(X.x.mod(corps.getP()));
	}

	private BigInteger hash(BigInteger m) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(m.toByteArray());
			byte[] hash = md.digest();
			return new BigInteger(hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}
