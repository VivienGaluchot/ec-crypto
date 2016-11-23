package eccrypto.sts;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import eccrypto.SerializationUtil;
import eccrypto.dsa.DSA;
import eccrypto.dsa.DSAMessage;
import eccrypto.dsa.DSAPrivateKey;
import eccrypto.ecdh.DHMessage;
import eccrypto.ecdh.ECDH;
import eccrypto.math.Point;

public class STS {
	ECDH ecdh;
	DSA dsa;
	Cipher cipher;

	public STS(DSAPrivateKey privateKey) {
		ecdh = new ECDH(privateKey);
		dsa = new DSA(privateKey);
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}

	public DHMessage getPublicKey() {
		return ecdh.getPublicKey();
	}

	public Point getCommonSecret() throws Exception {
		return ecdh.getCommonSecret();
	}

	public STSMessage getSTSMessage(DHMessage pairPublicKey) throws Exception {
		ecdh.setReceivedKey(pairPublicKey);
		Point myKey = ecdh.getPublicKey().key;
		Point pairKey = pairPublicKey.key;

		BigInteger concat = myKey.x.or(myKey.y.shiftLeft(myKey.x.bitCount()));
		BigInteger concat2 = pairKey.x.or(pairKey.y.shiftLeft(pairKey.x.bitCount()));
		concat = concat.or(concat2.shiftLeft(concat.bitCount()));

		DSAMessage sign = dsa.sign(concat);

		byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		IvParameterSpec ivspec = new IvParameterSpec(iv);
		SecretKeySpec skeySpec = new SecretKeySpec(ecdh.getCommonSecret().x.toByteArray(), "AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivspec);

		byte[] encrypted = cipher.doFinal(SerializationUtil.serializeToByte(sign.sign));
		return new STSMessage(ecdh.getPublicKey(), encrypted);
	}

	public boolean verifySTSMessage(DHMessage pairDsaKey, STSMessage msg) throws Exception {
		ecdh.setReceivedKey(msg);

		Point myKey = ecdh.getPublicKey().key;
		Point pairKey = msg.key;

		BigInteger concat = myKey.x.or(myKey.y.shiftLeft(myKey.x.bitCount()));
		BigInteger concat2 = pairKey.x.or(pairKey.y.shiftLeft(pairKey.x.bitCount()));
		concat = concat2.or(concat.shiftLeft(concat2.bitCount()));

		byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		IvParameterSpec ivspec = new IvParameterSpec(iv);
		SecretKeySpec skeySpec = new SecretKeySpec(ecdh.getCommonSecret().x.toByteArray(), "AES");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivspec);
		
		byte[] signBytes = cipher.doFinal(msg.signCypher);
		Point sign = (Point) SerializationUtil.deserialize(signBytes);

		DSAMessage dsaMsg = new DSAMessage(msg, concat, sign);

		return dsa.verify(pairDsaKey, dsaMsg);
	}
}
