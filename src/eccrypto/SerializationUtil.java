package eccrypto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

public class SerializationUtil {
	
	public static Object deserialize(String serializedObject) {
		try {
			byte b[] = Base64.getDecoder().decode(serializedObject);
			ByteArrayInputStream bi = new ByteArrayInputStream(b);
			ObjectInputStream si = new ObjectInputStream(bi);
			return si.readObject();
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	public static String serialize(Object obj) {
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream so = new ObjectOutputStream(bo);
			so.writeObject(obj);
			so.flush();
			return new String(Base64.getEncoder().encode(bo.toByteArray()));
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
}
