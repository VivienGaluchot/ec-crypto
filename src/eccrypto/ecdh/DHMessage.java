package eccrypto.ecdh;

import java.io.Serializable;

import eccrypto.math.Corps;
import eccrypto.math.Point;

public class DHMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	public Point P;
	public Corps corps;

	public Point key;

	public DHMessage(Point P, Corps corps, Point key) {
		this.P = P;
		this.corps = corps;
		this.key = key;
	}

	public String toString() {
		return key.toString();
	}
}
