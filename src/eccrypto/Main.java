package eccrypto;

import java.math.BigInteger;

import eccrypto.ecdh.ECDH;
import eccrypto.elgamal.ElMessage;
import eccrypto.elgamal.Elgamal;
import eccrypto.dsa.DSA;
import eccrypto.dsa.DSAMessage;
import eccrypto.ecdh.DHMessage;
import eccrypto.math.Corps;
import eccrypto.math.EllipticCurve;
import eccrypto.math.Point;

public class Main {

	// p=8884933102832021670310856601112383279507496491807071433260928721853918699951
	// n=8884933102832021670310856601112383279454437918059397120004264665392731659049
	// a4=2481513316835306518496091950488867366805208929993787063131352719741796616329
	// a6=4387305958586347890529260320831286139799795892409507048422786783411496715073
	// r4=5473953786136330929505372885864126123958065998198197694258492204115618878079
	// r6=5831273952509092555776116225688691072512584265972424782073602066621365105518
	// gx=7638166354848741333090176068286311479365713946232310129943505521094105356372
	// gy=762687367051975977761089912701686274060655281117983501949286086861823169994
	// r=8094458595770206542003150089514239385761983350496862878239630488323200271273

	public static void main(String[] args) {
		EllipticCurve curve = new EllipticCurve(new BigInteger("0"), new BigInteger("0"), new BigInteger("0"),
				new BigInteger("2481513316835306518496091950488867366805208929993787063131352719741796616329"),
				new BigInteger("4387305958586347890529260320831286139799795892409507048422786783411496715073"));

		Corps corps = new Corps(
				new BigInteger("8884933102832021670310856601112383279507496491807071433260928721853918699951"),
				new BigInteger("8884933102832021670310856601112383279454437918059397120004264665392731659049"), curve);

		Point g = new Point(
				new BigInteger("7638166354848741333090176068286311479365713946232310129943505521094105356372"),
				new BigInteger("762687367051975977761089912701686274060655281117983501949286086861823169994"));
		BigInteger n = new BigInteger("8884933102832021670310856601112383279454437918059397120004264665392731659049");
		Point inf = new Point(true);
		Point p = null;

		System.out.println(" --- Math ---");

		System.out.println("g is in curve ? \t" + corps.contain(g));
		System.out.println("infini is in curve ? \t" + corps.contain(inf));

		p = corps.oppose(g);
		System.out.println("-g is in curve ? \t" + corps.contain(p));

		p = corps.add(g, g);
		System.out.println("g+g is in curve ? \t" + corps.contain(p));
		p = corps.add(inf, g);
		System.out.println("ing + g is in curve ? \t" + corps.contain(p));
		p = corps.add(g, inf);
		System.out.println("g + inf is in curve ? \t" + corps.contain(p));
		p = corps.add(inf, inf);
		System.out.println("ing+inf is in curve ? \t" + corps.contain(p));

		p = corps.mutiply(new BigInteger("10"), g);
		System.out.println("10*g is in curve ? \t" + corps.contain(p));
		p = corps.mutiply(new BigInteger("10"), inf);
		System.out.println("10*inf is in curve ? \t" + corps.contain(p));
		p = corps.mutiply(n, g);
		System.out.println("n*g is in curve ? \t" + corps.contain(p));
		p = corps.mutiply(n, g);
		System.out.println("n*g == inf ? \t\t" + p.equals(inf));

		System.out.println("\n --- ECDH --- ");
		ECDH alice = new ECDH();
		DHMessage pa = alice.getPublicKey();
		System.out.println("Alice key : \n" + pa);

		ECDH bob = new ECDH();
		DHMessage pb = bob.getPublicKey();
		System.out.println("Bob key : \n" + pb);

		try {
			alice.setReceivedKey(pb);
			bob.setReceivedKey(pa);

			System.out.println("Alice secret : \n" + alice.getCommonSecret());
			System.out.println("Bob secret : \n" + bob.getCommonSecret());
			System.out.println("equals alice secret ? \t" + alice.getCommonSecret().equals(bob.getCommonSecret()));

			System.out.println("Bob2 in passive mode");
			ECDH bob2 = new ECDH(pa);
			DHMessage pb2 = bob2.getPublicKey();
			System.out.println("Bob2 key : \n" + pb2);

			try {
				alice.setReceivedKey(pb2);
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("Bob2 secret : \n" + bob.getCommonSecret());
			System.out.println("equals alice secret ? \t" + alice.getCommonSecret().equals(bob2.getCommonSecret()));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		BigInteger m = new BigInteger("7896542365786324486235");

		System.out.println("\n --- ElGamal --- ");
		try {

			// Key generation
			Elgamal alice2 = new Elgamal();
			DHMessage pubAlice = alice2.getPublicKey();

			// Encryption
			Elgamal bob2 = new Elgamal();
			ElMessage cypher = bob2.getCypher(pubAlice, m);

			// Decryption
			System.out.println("bob m equals alice m ? \t" + alice2.uncypher(cypher).equals(m));
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("\n --- DSA --- ");
		DSA alice3 = new DSA();
		DHMessage alicePubKey = alice3.getPublicKey();
		String str = SerializationUtil.serialize(alicePubKey);
		alicePubKey = (DHMessage) SerializationUtil.deserialize(str);
		
		DSA bob3 = new DSA();
		DSA jack = new DSA();
		DHMessage jackPubKey = jack.getPublicKey();
		try {
			DSAMessage signedMsg = alice3.sign(m);
			System.out.println("bob verify alice's signature ? \t" + bob3.verify(alicePubKey, signedMsg));
			System.out.println("bob verify alice signature ? \t" + bob3.verify(jackPubKey, signedMsg));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(SerializationUtil.serialize(alicePubKey));
	}

}
