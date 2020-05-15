package game;

import java.util.ArrayList;
import java.util.Random;
import acm.graphics.GCanvas;
import acm.graphics.GImage;
import acm.util.RandomGenerator;

public class Collision{

	private GCanvas canvas;
	private GImage explo; 
	private boolean fin = false;
	//peut pas utiliser hash map car se decale pas vers la droite...
	private ArrayList <GImage> listeClearEnnemi = new ArrayList<GImage>();
	private ArrayList <Long> listeTps = new ArrayList<Long>(); //penser a synchro avec listeclear pour array bidimensionnel ? optimisé?
	Monstres focus;
	Music son = new Music();
	GImage gemmeG,gemmeT,gemmeV,gemmeP;
	
	public Collision(GCanvas canvas) {
		//creer barre de vie joueur
		this.canvas = canvas;
	}

	public void colisionTour(byte secondes) {
		for (Tourelles t : Main.tourelleList) {
			if (t.getPlace()) {
			for (Monstres m : Main.monstreList) {
				if (m.getImage().getBounds().intersects(t.getZoneTir().getBounds())) {
					if (t.getTourelleType() == "minus") {
						if (!t.bloqueFocus()) {
							t.setFocus(m);
							t.setBloqueFocus(true);
						}
						t.getFocus().setVieAffiche(t.getAttaque());
					}
					if (t.getTourelleType()=="mortier" && secondes == 0) m.setVieAffiche(t.getAttaque() );
					if (t.getTourelleType() == "mine" && m.getMonstre() !="oiseau") {
						t.getTourelle().setColor(RandomGenerator.getInstance().nextColor());
						if (t.getSetCompteurMine()>30) {
							m.setVieAffiche(t.getAttaque());
							canvas.remove(t.getTourelle());
							canvas.remove(t.getZoneTir());
							t.getTourelle().setVisible(false);
						}
					}
					if (t.getTourelleType() == "boss") m.setVieAffiche(t.getAttaque());
				} else if (t.bloqueFocus()) {
					t.setBloqueFocus(false);
				}
				if (m.getVieAffiche().getWidth()<=0) {
					if (t.getTourelleType() == "minus")	t.setBloqueFocus(false);
					mort(m, m.getPieces());
					break;
					}
				}
			}
		}
	}
	
	public void tremblement() {
		for (Monstres m : Main.monstreList) {
			if (m.getMonstre()=="monstreVert" || m.getMonstre()=="blurp") m.getVieAffiche().setSize(m.getVieAffiche().getWidth()-(0.5*18)/m.getresistance(), m.getVieAffiche().getHeight()); 
			if (m.getVieAffiche().getWidth()<=0) {
				mort(m, m.getPieces());
				break;
			}
		}
	}
	
	public void poisonGel(GImage zonePoison) {
		for (Monstres m : Main.monstreList) {
		if (Main.poison == true) {
			if (m.getImage().getBounds().intersects(zonePoison.getBounds()))	m.getVieAffiche().setSize(m.getVieAffiche().getWidth()-(0.5*18)/m.getresistance(), m.getVieAffiche().getHeight()); 
		if (m.getVieAffiche().getWidth()<=0) {
			mort(m, m.getPieces());
			break;
				}
			} else if (Main.poison == false) {
				if (m.getImage().getBounds().intersects(zonePoison.getBounds())) if (m.getVitesse()>=0.3) m.setVitesse(m.getVitesse()-0.01f);
			}
		}
		if (Main.vieJoueur.getWidth()<=0) Main.actif = false;
		}
	
	public boolean collisionChateau() {
		for (Monstres m : Main.monstreList) {
			if (m.getImage().getX() > 1325) {
				canvas.remove(m.getImage());
				canvas.remove(m.getVieAffiche());
				Main.vieJoueur.setSize(Main.vieJoueur.getWidth()-m.getAttaque(), Main.vieJoueur.getHeight());
				Main.monstreList.remove(m);
				son.BCoup();
				fin = false;
				break;
			}
			if (Main.vieJoueur.getWidth()<=1) {
				son.BCoup();
				fin = true;
			}
		}
		return fin;
	}
	
	public void setFin(boolean fin) {
		this.fin = fin;
	}
	
	public void mort(Monstres m, byte pieces) {
			Main.monaie += (Math.random()*m.getPieces());
			Main.affichageMonaie.setLabel(Main.monaie + "");
			explo = new GImage("../visuel/explo.gif", m.getImage().getX()-10, m.getImage().getY()-10);
			explo.setSize(50, 50);
			listeClearEnnemi.add(explo);
			listeTps.add(Main.tpsAccelere);
			canvas.add(explo);
			Main.exp += Math.random();
			//bruit de mort des monstres
			if (m.getMonstre()=="monstreVert") son.BMonstreVert();
			if (m.getMonstre()=="blurp") son.BMonstreJaune();;
			if (m.getMonstre()=="boss") son.BMonstreRouge();
			if (m.getMonstre()=="oiseau") son.BMonstreVol();

			gemmeG = new GImage  ("../visuel/GemmeGel.png",0,0);
			gemmeV = new GImage  ("../visuel/GemmeVie.png",0,0);
			gemmeT = new GImage  ("../visuel/GemmeTremblement.png",0,0);
			gemmeP = new GImage  ("../visuel/GemmePoison.png",0,0);
				
			Random randomGemme = new Random(); byte n=(byte) (randomGemme.nextInt(100)+1);
			//chance de gagner une gemme de bonus quand un monstre meurt (16%)
			if (n < 4) { Main.gemmePoison += 1; listeClearEnnemi.add(gemmeP); listeTps.add(Main.tpsAccelere+1500); canvas.add(gemmeP); gemmeP.setLocation(m.getImage().getX(),m.getImage().getY());
			} else {
					if (n > 4 && n <= 8) {
						Main.gemmeGel += 1; listeClearEnnemi.add(gemmeG); listeTps.add(Main.tpsAccelere+1500); canvas.add(gemmeG);gemmeG.setLocation(m.getImage().getX(),m.getImage().getY());
					} else {
						if (n > 8 && n <= 12) {
							Main.gemmeVie += 1; listeClearEnnemi.add(gemmeV); listeTps.add(Main.tpsAccelere+1500); canvas.add(gemmeV);gemmeV.setLocation(m.getImage().getX(),m.getImage().getY());
						}else {
							if (n > 12 && n <= 16) {
								Main.gemmeTremblement += 1; listeClearEnnemi.add(gemmeT); listeTps.add(Main.tpsAccelere+1500); canvas.add(gemmeT);gemmeT.setLocation(m.getImage().getX(),m.getImage().getY());
							}
						}
					}
				}
				
				//expérience gagnée en fonction des monstres
				canvas.remove(m.getImage());
				canvas.remove(m.getVieAffiche());
				Main.monstreList.remove(m);
				
				
				
	}
	
	public void verifMort() {
		//supp animation delete ennemi 
		if (listeClearEnnemi.size()>0) {
			for(byte o = 0; o<listeClearEnnemi.size(); o++) {
				if (listeTps.get(o)+500<Main.tpsAccelere) {
					listeTps.remove(o);
					canvas.remove(listeClearEnnemi.get(o));
					listeClearEnnemi.remove(o);
				}
			}
		}
	}
}

