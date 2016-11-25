package eccrypto;

import java.math.BigInteger;
import java.util.InputMismatchException;
import java.util.Scanner;

import eccrypto.dsa.DSA;
import eccrypto.dsa.DSAMessage;
import eccrypto.dsa.DSAPrivateKey;
import eccrypto.ecdh.DHParam;
import eccrypto.ecdh.ECDH;
import eccrypto.elgamal.ElMessage;
import eccrypto.elgamal.Elgamal;
import eccrypto.math.Point;
import eccrypto.sts.STS;
import eccrypto.sts.STSMessage;

public class CmdUi {
	private static DSAPrivateKey dsaKey = null;

	public static void main(String[] args) {
		System.out.println("-----------------------------------");
		System.out.println("Cryptographie en courbe elliptiques");
		System.out.println("-----------------------------------");
		System.out.println("par Vivien Galuchot & Vincent Hernandez");
		System.out.println("Polytech Marseille");

		Scanner input = new Scanner(System.in);

		boolean quit = false;
		do {
			try {
				System.out.println("\n--- Fonctions disponnibles ---");
				System.out.println("1/ Echange Diffie-Hellman");
				System.out.println("2/ Envois ElGammal");
				System.out.println("3/ Reception ElGammal");
				System.out.println("4/ Generation de cle DSA");
				System.out.println("5/ Signature DSA");
				System.out.println("6/ Verification de signature");
				System.out.println("7/ Initiateur STS");
				System.out.println("8/ Recepteur STS");
				System.out.println("9/ Quit");
				System.out.print("\nVotre choix : ");

				int inputNumber = 0;
				try {
					inputNumber = input.nextInt();
				} catch (InputMismatchException e) {
					input.nextLine();
					inputNumber = 0;
				}

				Point p;
				Point q;
				DHParam param;
				BigInteger i;
				BigInteger j;

				switch (inputNumber) {
				case (1):
					System.out.println("-> 1/ Echange Diffie-Hellman/n");
					System.out.println("Utilisation de la courbe par defaut");
					ECDH ecdh = new ECDH();
					System.out.println("Parametre a echanger au pair : ");
					System.out.println(ecdh.getPublicParam());
					System.out.println("Parametre du pair : ");
					p = enterPoint(input);
					param = new DHParam(p);
					ecdh.setReceivedParam(param);
					System.out.println("Secret commun : ");
					System.out.println(ecdh.getCommonSecret());
					break;
				case (2):
					System.out.println("-> 2/ Envois ElGammal");
					System.out.println("Utilisation de la courbe par defaut");
					Elgamal elgamal = new Elgamal();
					System.out.println("Parametre du pair : ");
					p = enterPoint(input);
					param = new DHParam(p);
					System.out.println("Message (entier) : ");
					i = input.nextBigInteger();
					System.out.println("Message chiffre : ");
					System.out.println(elgamal.getCypher(param, i));
					break;
				case (3):
					System.out.println("-> 3/ Reception ElGammal");
					System.out.println("Utilisation de la courbe par defaut");
					Elgamal elgamalR = new Elgamal();
					System.out.println("Parametre a echanger au pair : ");
					System.out.println(elgamalR.getPublicParam());
					System.out.println("Message a dechiffrer : ");
					p = enterPoint(input);
					i = input.nextBigInteger();
					System.out.println("Message : ");
					System.out.println(elgamalR.uncypher(new ElMessage(new DHParam(p), i)));
					break;
				case (4):
					System.out.println("-> 4/ Generation de cle DSA");
					System.out.println("Utilisation de la courbe par defaut");
					if (dsaKey == null)
						dsaKey = DSA.generatePrivateKey();
					else
						System.out.println("Cle existante");
					System.out.println(dsaKey.key);
					break;
				case (5):
					System.out.println("-> 5/ Signature DSA");
					System.out.println("Utilisation de la courbe par defaut");
					if (dsaKey == null)
						System.out.println("Clef privee DSA non generee");
					else {
						DSA dsa = new DSA(dsaKey);
						System.out.println("Message (entier) : ");
						i = input.nextBigInteger();
						System.out.println("Clef publique, Message et signature : ");
						System.out.println(dsa.getPublicParam());
						System.out.println(dsa.sign(i));
					}
					break;
				case (6):
					System.out.println("-> 6/ Verification de signature");
					System.out.println("Utilisation de la courbe par defaut");
					DSA dsaR = new DSA();
					System.out.println("Clef publique, message et signature : ");
					p = enterPoint(input);
					i = input.nextBigInteger();
					q = enterPoint(input);
					boolean b;
					try {
						b = dsaR.verify(new DHParam(p), new DSAMessage(new DHParam(p), i, q));
					} catch (Exception e) {
						b = false;
					}
					if (b)
						System.out.println("Signature verifiee");
					else
						System.out.println("Signature invalide");
					break;
				case (7):
					System.out.println("-> 7/ Initiateur STS");
					System.out.println("Utilisation de la courbe par defaut");
					if (dsaKey == null)
						System.out.println("Clef privee DSA non generee");
					else {
						STS sts = new STS(dsaKey);
						System.out.println("Clef publique DSA et parametre Diffie Hellman : ");
						System.out.println(sts.getDSAPublicKey());
						System.out.println(sts.getPublicKey());
						System.out.println("Clef publique DSA, parametre Diffie Hellman et Message STS :");
						p = enterPoint(input);
						DHParam dsaPublic = new DHParam(p);
						q = enterPoint(input);
						DHParam dhParam = new DHParam(q);
						i = input.nextBigInteger();
						j = input.nextBigInteger();
						STSMessage stsMessage = new STSMessage(dhParam, i.toByteArray(), j.toByteArray());
						System.out.println("Message STS : ");
						System.out.println(sts.getSTSMessage(stsMessage));
						boolean b2;
						try {
							b2 = sts.verifySTSMessage(dsaPublic, stsMessage);
						} catch (Exception e) {
							b2 = false;
						}
						if (b2) {
							System.out.println("Echange STS valide, secret commun :");
							System.out.println(sts.getCommonSecret());
						}
					}
					break;
				case (8):
					System.out.println("-> 8/ Recepteur STS");
					System.out.println("Utilisation de la courbe par defaut");
					if (dsaKey == null)
						System.out.println("Clef privee DSA non generee");
					else {
						STS stsR = new STS(dsaKey);
						System.out.println("Clef publique DSA et parametre Diffie Hellman : ");
						p = enterPoint(input);
						DHParam dsaPublicR = new DHParam(p);
						q = enterPoint(input);
						DHParam dhParamR = new DHParam(q);
						System.out.println("Clef publique DSA, parametre Diffie Hellman et Message STS :");
						System.out.println(stsR.getDSAPublicKey());
						System.out.println(stsR.getPublicKey());
						System.out.println(stsR.getSTSMessage(dhParamR));
						System.out.println("Message STS : ");
						i = input.nextBigInteger();
						j = input.nextBigInteger();
						STSMessage stsMessageR = new STSMessage(dhParamR, i.toByteArray(), j.toByteArray());
						boolean b2;
						try {
							b2 = stsR.verifySTSMessage(dsaPublicR, stsMessageR);
						} catch (Exception e) {
							b2 = false;
						}
						if (b2) {
							System.out.println("Echange STS valide, secret commun :");
							System.out.println(stsR.getCommonSecret());
						}
					}
					break;
				case (9):
					System.out.println("-> 9/ Quit");
					quit = true;
					break;
				default:
					System.out.println("Erreur : choix entre 1 et 7");
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (quit == false);

		input.close();
		System.out.println("\nFin de programme");
	}

	public static Point enterPoint(Scanner input) {
		// forme
		// x 12657...
		// y 87655...
		Point p = new Point();
		String text = input.next();
		if (!text.equals("x"))
			return null;
		p.x = input.nextBigInteger();
		text = input.next();
		if (!text.equals("y"))
			return null;
		p.y = input.nextBigInteger();
		return p;
	}
}