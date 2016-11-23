package eccrypto.ecdh;

import java.io.Serializable;

import eccrypto.math.Corps;
import eccrypto.math.CurveMessage;
import eccrypto.math.Point;

public class DHMessage extends CurveMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	public Point key;

	public DHMessage(Point P, Corps corps, Point key) {
		super(P, corps);
		this.key = key;
	}

	public String toString() {
		return key.toString();
	}
	
	@Override
	public boolean equals(Object m){
		if(m instanceof DHMessage){
			DHMessage msg = (DHMessage) m;
			return super.equals(msg) && key.equals(msg.key);
		}
		return false;
	}
}
