package eccrypto.ecdh;

import java.io.Serializable;

import eccrypto.math.Corps;
import eccrypto.math.CurveMessage;
import eccrypto.math.Point;

public class DHMessage extends CurveMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	public DHParam dhParam;

	public DHMessage(Point P, Corps corps, DHParam dhParam) {
		super(P, corps);
		this.dhParam = dhParam;
	}

	public String toString() {
		return dhParam.toString();
	}
	
	@Override
	public boolean equals(Object m){
		if(m instanceof DHMessage){
			DHMessage msg = (DHMessage) m;
			return super.equals(msg) && dhParam.equals(msg.dhParam);
		}
		return false;
	}
}
