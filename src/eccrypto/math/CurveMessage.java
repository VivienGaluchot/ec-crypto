package eccrypto.math;

import java.io.Serializable;

import eccrypto.ecdh.DHMessage;

public class CurveMessage implements Serializable{
	private static final long serialVersionUID = 1L;

	public Point P;
	public Corps corps;

	public CurveMessage(Point P, Corps corps) {
		this.P = P;
		this.corps = corps;
	}
	
	@Override
	public boolean equals(Object m){
		if(m instanceof DHMessage){
			DHMessage msg = (DHMessage) m;
			return P.equals(msg.P) && corps.equals(msg.corps);
		}
		return false;
	}
}

