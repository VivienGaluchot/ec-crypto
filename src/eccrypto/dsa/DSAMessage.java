package eccrypto.dsa;

import java.math.BigInteger;

import eccrypto.ecdh.DHMessage;
import eccrypto.math.Point;

public class DSAMessage extends DHMessage{
	static final long serialVersionUID = 1L;
	BigInteger m;
	Point sign;
	
	public DSAMessage(DHMessage dh, BigInteger m, Point sign){
		super(dh.P, dh.corps, dh.key);
		this.m = m;
		this.sign = sign;
	}
}
