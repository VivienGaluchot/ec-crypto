package eccrypto.elgamal;

import java.math.BigInteger;

import eccrypto.ecdh.DHParam;

public class ElMessage extends DHParam {
	static final long serialVersionUID = 1L;
	BigInteger m;

	public ElMessage(DHParam m, BigInteger cypher) {
		super(m.P);
		this.m = cypher;
	}

	public String toString() {
		return P + "\n" + m;
	}
}
