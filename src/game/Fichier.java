package game;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Fichier {
	
	//déclarations
	//objet file pour accéder a l'emplacement du fichier 
	private File f;
	//variable pour renvoyer ancien score
	private int niveau;
	private byte gP,gT,gV,gG;
	//objet scanner pour lire le document texte
	private Scanner sc;
	
	//constructeur
	public  Fichier(String pseudo) throws IOException {
		//créer le bloc note
		f = new File("Registre/Sauvegarde"+pseudo+".txt");
		if (!f.exists()) {
			f.createNewFile();
		}
	}
	
	public void Sauvegarder(int niveau, byte gP, byte gT, byte gV, byte gG) throws IOException {
		//permet d'écrire sur le bloc note
		FileWriter fw = new FileWriter(f);
		
		//le try, le catch et le finally permettent de capturer une erreur c'est a dire d'exécuter le programme même si cela peut engendrer des erreurs
		try {
			//écrire le nombre de pièces a sauvegarder
			fw.write(Integer.toString(niveau));
			fw.write("\r\n");
			fw.write(Integer.toString(gP));
			fw.write("\r\n");
			fw.write(Integer.toString(gT));
			fw.write("\r\n");
			fw.write(Integer.toString(gV));
			fw.write("\r\n");
			fw.write(Integer.toString(gG));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	finally {
			//fermer le bloc notes pour ne pas créer d'erreurs
			fw.close();
		}
	}
	
	public void Recuperer() {
			
		if (f.length()>0) {
			try {
				sc = new Scanner(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				niveau = sc.nextInt();
				sc.nextLine();
				gP = sc.nextByte();
				sc.nextLine();
				gT = sc.nextByte();
				sc.nextLine();
				gV = sc.nextByte();
				sc.nextLine();
				gG = sc.nextByte();
		} else {
			niveau = 1;
		}
			
			//retouner une valeur entière
		
	}

	public int getNiveau() {
		return niveau;
	}
	
	public byte getPoison() {
		return gP;
	}
	public byte getVie() {
		return gV;
	}
	public byte getTremblement() {
		return gT;
	}
	public byte getGel() {
		return gG;
	}
}


