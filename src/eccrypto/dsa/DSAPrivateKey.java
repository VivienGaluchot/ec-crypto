package eccrypto.dsa;

import java.math.BigInteger;

import eccrypto.ecdh.DHMessage;
import eccrypto.math.Corps;
import eccrypto.math.CurveMessage;
import eccrypto.math.Point;

public class DSAPrivateKey extends CurveMessage {
	private static final long serialVersionUID = 1L;
	public BigInteger key;

	public DSAPrivateKey(Point P, Corps corps, BigInteger key) {
		super(P, corps);
		this.key = key;
	}
	
	@Override
	public boolean equals(Object m){
		if(m instanceof DHMessage){
			DHMessage msg = (DHMessage) m;
			return super.equals(msg) && key.equals(msg.dhParam);
		}
		return false;
	}
}
