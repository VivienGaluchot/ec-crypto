package eccrypto.dsa;

import java.math.BigInteger;

import eccrypto.ecdh.DHParam;
import eccrypto.math.Point;

public class DSAMessage extends DHParam{
	static final long serialVersionUID = 1L;
	
	public BigInteger m;
	public Point sign;
	
	public DSAMessage(DHParam dh, BigInteger m, Point sign){
		super(dh.P);
		this.m = m;
		this.sign = sign;
	}
	
	public String toString(){
		return m + "\n" + sign;
	}
}
