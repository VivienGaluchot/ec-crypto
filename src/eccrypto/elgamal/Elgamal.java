package eccrypto.elgamal;

import java.math.BigInteger;

import eccrypto.ecdh.DHMessage;
import eccrypto.ecdh.ECDH;
import eccrypto.math.CurveMessage;
import eccrypto.math.Point;

public class Elgamal extends ECDH {

	public Elgamal() {
		super();
	}

	public Elgamal(CurveMessage m) {
		super(m);
	}

	public Elgamal(ElMessage key) {
		super(key);
	}

	public ElMessage getCypher(DHMessage key, BigInteger m) throws Exception {
		Point cypher = new Point();
		setReceivedKey(key);

		if (m.compareTo(corps.getP()) > 0 || m.compareTo(BigInteger.ZERO) < 0)
			throw new IllegalArgumentException("Message out of bounds");

		if (getCommonSecret().isInfinit)
			throw new Exception("CommonSecret infinit");

		cypher.x = m.xor(getCommonSecret().x);
		return new ElMessage(getPublicKey(), cypher);
	}

	public BigInteger uncypher(ElMessage cypher) throws Exception {
		setReceivedKey(cypher);

		BigInteger m = cypher.m.x.xor(getCommonSecret().x);
		return m;
	}
}
