package game;

import acm.graphics.GImage;
import acm.graphics.GOval;

public class Tourelles {
	
	private float attaque, varUpAttaque;
	private byte prix, limiteAmeliorationZone, limiteAmeliorationAttaque, compteurMine, maxAmeliorationZone, maxAmeliorationAttaque;
	private GImage tourelle;
	private GOval tir;
	private String tourelleType;
	private boolean bloque = false, place = false;
	Monstres focus;
	
	Tourelles(){
	}
	
	public void setMinus(double x, double y) {
		attaque = 1f;
		prix = 10;
		varUpAttaque = 0.7f;
		limiteAmeliorationZone= limiteAmeliorationAttaque = 0;
		maxAmeliorationZone = maxAmeliorationAttaque = 3;
		tourelle = new GImage("../visuel/mitrailleuse.gif", x, y);
		tir = new GOval(x, y, 60, 60);
		tourelleType = "minus";
	}
	
	public void setMortier(double x, double y) {
		attaque = 20;
		prix = 20;
		varUpAttaque = 1;
		limiteAmeliorationZone= limiteAmeliorationAttaque = 0;
		maxAmeliorationZone = maxAmeliorationAttaque = 3;
		tourelle = new GImage("../visuel/fournaise.gif", x, y);
		tir = new GOval(x, y, 90, 90);
		tourelleType = "mortier";
	}
	
	public void setMine(double x, double y) {
		attaque = 210;
		prix = 15;
		varUpAttaque = 2;
		limiteAmeliorationZone= limiteAmeliorationAttaque = 0;
		maxAmeliorationZone = maxAmeliorationAttaque = 3;
		tourelle = new GImage("../visuel/mine.png", x, y);
		tir = new GOval(x, y, 30, 30);
		tourelleType = "mine";
		compteurMine = 0;
	}
	
	public void setBoss(double x, double y) {
		attaque = 3f;
		prix = 40;
		varUpAttaque = 0.5f;
		limiteAmeliorationZone= limiteAmeliorationAttaque = 0;
		maxAmeliorationZone = maxAmeliorationAttaque = 5;
		tourelle = new GImage("../visuel/faucheuse.png", x, y);	
		tir = new GOval(x, y, 60, 60);
		tourelleType = "boss";
	}
	
	public boolean getPlace() {
		return place;
	}
	
	public Monstres getFocus() {
		return focus;
	}
	
	public float getVarUpAttaque() {
		return varUpAttaque;
	}
	
	public boolean bloqueFocus() {
		return bloque;
	}
	
	public String getTourelleType() {
		return tourelleType;
	}
	
	public byte getMaxAmeliorationZone() {
		return maxAmeliorationZone;
	}
	
	public byte getMaxAmeliorationAttaque() {
		return maxAmeliorationAttaque;
	}
	
	public byte getUpZone() {
		return limiteAmeliorationZone;
	}
	
	public byte getUpAttaque() {
		return limiteAmeliorationAttaque;
	}
	
	public GImage getTourelle() {
		return tourelle;
	}
	
	public GOval getZoneTir() {
		return tir;
	}

	public float getAttaque() {
		return attaque;
	}
	
	public byte getPrix() {
		return prix;
	}
	
	public void setPlace() {
		place = true;
	}
	
	public void setPrix(byte prix) {
		this.prix = prix;
	}
	
	public void setAttaque(byte attaque) {
		this.attaque = attaque;
	}
	
	public void setUpZone() {
		++limiteAmeliorationZone;
	}
	
	public void setUpAttaque() {
		++limiteAmeliorationAttaque ;
	}
	
	public void setBloqueFocus(boolean bloque) {
		this.bloque = bloque;
	}
	
	public void setFocus(Monstres focus) {
		this.focus = focus;
	}
	
	public void setMaxAmeliorationAttaque(byte maxAmeliorationAttaque) {
		this.maxAmeliorationAttaque = maxAmeliorationAttaque;
	}
	
	public byte getSetCompteurMine() {
		compteurMine++;
		return compteurMine;
	}

}
