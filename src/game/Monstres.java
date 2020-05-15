 package game;

import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GRect;

public class Monstres {
	
	private byte pieces, expe;
	private short attaque, resistance;
	private float vitesse;
	private GImage image;
	private GRect vieAffiche;
	private String monstreType;
	
	public Monstres(){
		vieAffiche = new GRect(18, 3);
		vieAffiche.setFilled(true);
		vieAffiche.setColor(Color.RED);
	}
	
	public void setBoss(short resistance) {
		this.resistance = resistance; 
		attaque = 300;
		pieces = 50;
		vitesse = 2;
		expe = 10;
		image = new GImage ("../visuel/boss.gif");
		monstreType = "boss";
	}
	
	public void setMonstreVert(short resistance) {
		this.resistance = resistance; 
		attaque = 180;
		pieces = 15;
		vitesse = 1f;
		expe = 2;
		image = new GImage ("../visuel/monstre.gif");
		monstreType = "monstreVert";
	}
	
	public void setOiseau(short resistance) {
		this.resistance = resistance; 
		attaque = 150;
		pieces = 20;
		vitesse = 2.3f;
		expe = 1;
		image = new GImage ("../visuel/oiseau.gif");
		monstreType = "oiseau";
		
	}
	
	public void setBlurp(short resistance) {
		this.resistance = resistance; 
		attaque = 200;
		pieces = 30;
		vitesse = 0.5f;
		expe = 5;
		image = new GImage ("../visuel/blurp.gif");
		monstreType = "blurp";
	}
	
	public String getMonstre() {
		return monstreType;
	}
	
	public GRect getVieAffiche() {
		return vieAffiche;
	}
	
	public GImage getImage() {
		return image;
	}
	
	public byte getPieces() {
		return pieces;
	}

	public short getresistance() {
		return resistance;
	}
	
	public byte getExpe() {
		return expe;
	}

	public void setResistance(int vie) {
		this.resistance = (byte) vie;
	}

	public short getAttaque() {
		return attaque;
	}

	public void setAttaque(int attaque) {
		this.attaque = (byte) attaque;
	}
	
	public float getVitesse() {
		return vitesse;
	}
	
	public void setVitesse(float vitesse) {
		this.vitesse = (float) vitesse;
	}
	
	public void setVieAffiche(float x) {
		vieAffiche.setSize(vieAffiche.getWidth()-x/resistance, vieAffiche.getHeight());
	}

}
