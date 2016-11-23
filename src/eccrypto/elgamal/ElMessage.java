package eccrypto.elgamal;

import eccrypto.ecdh.DHMessage;
import eccrypto.math.Point;

public class ElMessage extends DHMessage {
	static final long serialVersionUID = 1L;
	Point m;
	
	public ElMessage(DHMessage m, Point cypher){
		super(m.P, m.corps, m.dhParam);
		this.m = cypher;
	}
}
