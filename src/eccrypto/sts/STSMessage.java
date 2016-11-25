package eccrypto.sts;

import java.math.BigInteger;

import eccrypto.ecdh.DHParam;

public class STSMessage extends DHParam {
	static final long serialVersionUID = 1L;

	public byte[] signCypher;
	public byte[] iv;

	public STSMessage(DHParam dh, byte[] signCypher, byte[] iv) {
		super(dh.P);
		this.signCypher = signCypher;
		this.iv = iv;
	}
	
	public String toString(){
		return new BigInteger(signCypher) + "\n" + new BigInteger(iv);
	}
}
