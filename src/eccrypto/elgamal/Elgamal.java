package eccrypto.elgamal;

import java.math.BigInteger;

import eccrypto.ecdh.DHParam;
import eccrypto.ecdh.ECDH;
import eccrypto.math.CurveMessage;

public class Elgamal extends ECDH {

	public Elgamal() {
		super();
	}

	public Elgamal(CurveMessage m) {
		super(m);
	}

	public ElMessage getCypher(DHParam key, BigInteger m) throws Exception {
		BigInteger cypher;
		setReceivedParam(key);

		if (m.compareTo(corps.getP()) > 0 || m.compareTo(BigInteger.ZERO) < 0)
			throw new IllegalArgumentException("Message out of bounds");

		if (getCommonSecret().P.isInfinit)
			throw new Exception("CommonSecret infinit");

		cypher = m.xor(getCommonSecret().P.x);
		return new ElMessage(getPublicParam(), cypher);
	}

	public BigInteger uncypher(ElMessage cypher) throws Exception {
		setReceivedParam(cypher);

		BigInteger m = cypher.m.xor(getCommonSecret().P.x);
		return m;
	}
}
