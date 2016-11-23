package eccrypto.sts;

import eccrypto.ecdh.DHMessage;

public class STSMessage extends DHMessage {
	static final long serialVersionUID = 1L;

	public byte[] signCypher;
	public byte[] iv;

	public STSMessage(DHMessage dh, byte[] signCypher, byte[] iv) {
		super(dh.P, dh.corps, dh.dhParam);
		this.signCypher = signCypher;
		this.iv = iv;
	}
}
