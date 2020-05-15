package game;
import acm.graphics.GImage;
import acm.graphics.GRect;

public class Deplacement {
	
	private GRect [][] tableau;
	
	public Deplacement(GRect[][] tableau) {
		this.tableau = tableau.clone();
	}
	
	public void deplacementTerre(GImage image, GRect vieEnnemi, float vitesse) {
		if (image.getX()<=tableau[20][0].getX()) {
				image.move(vitesse, 0);
				vieEnnemi.move(vitesse, 0);
			}
			if (image.getX()>=tableau[20][0].getX() && image.getX() <=tableau[21][0].getX() && image.getY()>tableau[0][1].getY()) {
				image.move(0, -vitesse);
				vieEnnemi.move(0, -vitesse);
			}
			if (image.getX()<= tableau[35][0].getX() && image.getY() >= tableau[20][0].getY() && image.getY() <= tableau[0][1].getY()) {
				image.move(vitesse, 0);
				vieEnnemi.move(vitesse, 0);
			}
			if (image.getX()>=tableau[35][0].getX() && image.getX()<=tableau[36][0].getX() && image.getY()<tableau[0][12].getY()) {
				image.move(0, vitesse);
				vieEnnemi.move(0, vitesse);
			}
			if (image.getX()>= tableau[34][0].getX() && image.getY() >= tableau[0][12].getY()-5 && image.getY() <= tableau[0][12].getY()+5) {
				//le -5 et +5 servent de marge 
				image.move(vitesse, 0);
				vieEnnemi.move(vitesse, 0);
		}
	}
		public void deplacementAir(GImage image, GRect vieEnnemi, float vitesse){
					if (image.getX()<=tableau[20][0].getX()) {
						image.move(vitesse, 0);
						vieEnnemi.move(vitesse, 0);
					}
					if (image.getX()>=tableau[20][0].getX() && image.getX() <=tableau[21][0].getX() && image.getY()<tableau[0][12].getY()) {
						image.move(0, vitesse);
						vieEnnemi.move(0, vitesse);
					}
					if (image.getX()>= tableau[20][0].getX() && image.getY() >= tableau[0][12].getY()-5 && image.getY() <= tableau[0][12].getY()+5) {
						image.move(vitesse, 0);
						vieEnnemi.move(vitesse, 0);
			}
		}
}
