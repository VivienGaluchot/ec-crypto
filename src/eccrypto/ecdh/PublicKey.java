package eccrypto.ecdh;

import java.io.Serializable;

import eccrypto.math.Corps;
import eccrypto.math.Point;

public class PublicKey implements Serializable {
	private static final long serialVersionUID = 1L;

	public Point P;
	public Corps corps;

	public Point key;

	public PublicKey(Point P, Corps corps, Point key) {
		this.P = P;
		this.corps = corps;
		this.key = key;
	}

	public String toString() {
		return key.toString();
	}
}
