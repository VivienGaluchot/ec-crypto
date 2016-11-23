package eccrypto;

import java.math.BigInteger;
import java.util.InputMismatchException;
import java.util.Scanner;

import eccrypto.ecdh.DHParam;
import eccrypto.ecdh.ECDH;
import eccrypto.math.Point;

public class CmdUi {
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
				System.out.println("2/ Chiffrement ElGammal");
				System.out.println("3/ Dechiffrement ElGammal");
				System.out.println("4/ Signature DSA");
				System.out.println("5/ Verification de signature");
				System.out.println("6/ Echange STS");
				System.out.println("7/ Quit");
				System.out.print("\nVotre choix : ");

				int inputNumber = 0;
				try {
					inputNumber = input.nextInt();
				} catch (InputMismatchException e) {
					input.nextLine();
					inputNumber = 0;
				}

				switch (inputNumber) {
				case (1):
					System.out.println("-> 1/ Echange Diffie-Hellman/n");
					System.out.println("Utilisation de la courbe par defaut");
					ECDH ecdh = new ECDH();
					System.out.println("Parametre a echanger au pair : ");
					System.out.println(ecdh.getPublicKey());
					System.out.println("Parametre du pair : ");
					Point p = enterPoint(input);
					DHParam param = new DHParam(p);
					ecdh.setReceivedParam(param);
					System.out.println("Secret commun : ");
					System.out.println(ecdh.getCommonSecret());
					break;
				case (2):
					System.out.println("-> 2/ Chiffrement ElGammal");
					break;
				case (3):
					System.out.println("-> 3/ Dechiffrement ElGammal");
					break;
				case (4):
					System.out.println("-> 4/ Signature DSA");
					break;
				case (5):
					System.out.println("-> 5/ Verification de signature");
					break;
				case (6):
					System.out.println("-> 6/ Echange STS");
					break;
				case (7):
					System.out.println("-> 7/ Quit");
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