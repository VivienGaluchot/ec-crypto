package eccrypto.sts;

import eccrypto.ecdh.DHMessage;

public class STSMessage extends DHMessage {
	static final long serialVersionUID = 1L;

	public byte[] signCypher;

	public STSMessage(DHMessage dh, byte[] signCypher) {
		super(dh.P, dh.corps, dh.key);
		this.signCypher = signCypher;
	}
}
