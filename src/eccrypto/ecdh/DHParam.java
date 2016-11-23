package eccrypto.ecdh;

import java.io.Serializable;

import eccrypto.math.Point;

public class DHParam implements Serializable {
	private static final long serialVersionUID = 1L;

	public Point P;

	public DHParam(Point P) {
		this.P = P;
	}

	public String toString() {
		return P.toString();
	}

	@Override
	public boolean equals(Object m) {
		if (m instanceof DHParam) {
			DHParam msg = (DHParam) m;
			return P.equals(msg.P);
		}
		return false;
	}
}
