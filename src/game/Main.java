package game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.SwingTimer;

public class Main extends GraphicsProgram{
	
	
	
	GImage FondEteJour, FondHiverJour, FondEteNuit, FondHiverNuit, FondMenuEteJour, FondMenuEteNuit, FondMenuHiverJour, FondMenuHiverNuit,
 	lock, lock1, lock2,
 	info1,info2,info3,info4,info5,info6,info7,info8,info9,info10,info11,info12,flecheG,flecheD,croixRouge,
 	infoLu, btShop, btJouer,btQuit,fondShop,nop, croixJeu,
 	fournaise, mitrailleuse, faucheuse, mine,zonePoison;
	int Heure,Jour, lvlUp = 20;
	static byte gemmePoison,gemmeGel,gemmeVie,gemmeTremblement;
	byte upBoss , gemmeUpBoss, unlockFaucheuse, unlockFournaise, unlockMine, randomGemme, page = 1, lu, P = 0, G = 0, V = 0, T = 0;
	SwingTimer b;
	public static int niveau;
	static double exp;
	static boolean poison, actif;
	static char etat = 'm';
	GRect quit, play, info, shop, infogauche, infodroite;
	GLabel niv, nGp,nGv,nGt,nGg, ah, pG,pP, pT, pV ;
	GOval  Poiz, Vi, Trembl, Froi;
	Music son = new Music();
	String pseudo;
	Fichier rec;
	GregorianCalendar calendar = new GregorianCalendar();
	

	byte idUp = 0, secAccelere, compteurVague = 0, safeTpsAcc = 32;
	short resistanceOiseau = 4, resistanceMonstreVert= 6, resistanceBlurp = 8, resistanceBoss = 10;
	int tempsEcouleReal = 0, delayPoisonGel, delayTremblement;
	//variable qui gere apparition nb ennemi
	float facteurCreationMonstre = 0.1f, facteurCreationOiseau = 0.1f, facteurCreationBlurp = -0.5f;
	boolean actBouge = false, pause = false, actTremblement = false, placePoisonGel = false, posPoisonGel = false, 
			actPoisonGel = false, verifTourelleBoss = true, bouge = false;
	//verif mort permet a classe colision gerer 1 ennemi a la fois pr tourelleUnique
	final byte x = 45, y= 18, taille = 30, posX = -2, posY = 35, prixTourelleMinus = 10, prixTourelleMortier = 20, prixTourelleMine = 15, prixTourelleBoss = 40;
	static long tpsAccelere = 0;
	static short monaie = 300;
	
	GOval iconPotionGel, iconPotionVie, iconPotionPoison, iconTremblement,iconTourelleBoss;
	GLabel affichageTemps, affichageVague;
	GRect iconTourelle, iconMortier, iconUp, iconMine, iconPause, iconPoubelle, iconAccTps, 
	chemin1, chemin2, chemin3, chemin4, chemin5, map, cheminDirect;
	static GRect vieJoueur;
	static GLabel affichageMonaie;
	
	GRect [][] cases;
	
	SwingTimer timer, timerAffichage;
	
	Collision col = new Collision(this.getGCanvas());
	Deplacement dep;
	
	static ArrayList <Monstres> monstreList = new ArrayList<Monstres>();
	static ArrayList <Tourelles> tourelleList = new ArrayList<Tourelles>();
	
	
	
	public void init() {
		
		//taille de la fenetre
		this.setSize(1350, 680);
		
		//demande d'un pseudo au moment du lancement
		do {
			pseudo = JOptionPane.showInputDialog(this, "Saisie ton Pseudo :", "", JOptionPane.QUESTION_MESSAGE);
			}	while(pseudo == null);
				pseudo.toLowerCase();
				try {
					rec = new Fichier(pseudo);
				} catch (IOException e1) {
					e1.printStackTrace();
				}							
					
		addKeyListeners();
		addMouseListeners();
		
		//Rectangles (posX, posY, tailleX,tailleY)
		shop = new GRect (110,548,170,80);
		play = new GRect (592,166,100,27);
		quit = new GRect (581,240,128,32);
		info = new GRect (1285,14,40,45);
		quit.setFilled(false); // false => carré vide, true => carré plein.
		play.setFilled(false);
		info.setFilled(false);
		shop.setFilled(false);
		vieJoueur = new GRect(50, 5, 1250, 20);
		vieJoueur.setFilled(true);
		vieJoueur.setFillColor(new Color(230, 2, 2));
		
		//cubes affichés à l'écran (pendant jeu)
		iconTourelle = new GRect(1048, 566, 61, 61);
		iconMortier = new GRect(780, 565, 61, 61);
        iconMine = new GRect(918, 575, 40, 40);
        iconTourelleBoss = new GOval(1190, 566, 61, 61);
        iconTremblement = new GOval(421, 628,46,44);
        iconPotionVie = new GOval(387, 573,46,44);
        iconPotionPoison = new GOval (314, 573, 46,44);
        iconPotionGel = new GOval(350, 628, 46,44);
        iconPause = new GRect(1310, 3, 32, 32);
        iconAccTps = new GRect(1310, 43, 32, 32);
        iconPoubelle = new GRect (1290, 470, 65, 55);
        iconPotionGel.setFilled(false);
        iconPotionPoison.setFilled(false);
        iconPotionVie.setFilled(false);
        iconPause.setFilled(false);
        iconAccTps.setFilled(false);
        iconPoubelle.setFilled(false);
        iconMortier.setFilled(false);
        iconTourelle.setFilled(false);
        iconMine.setFilled(false);
        iconTourelleBoss.setFilled(false);
		
		
		
		//labels pendant jeu
		affichageTemps = new GLabel("0:0", 28, 645);
		affichageTemps.setFont("Papyrus-50");
		affichageMonaie = new GLabel(monaie+"", 510, 635);
		affichageMonaie.setFont("Papyrus-50");
		affichageVague = new GLabel("0", 220, 660);
		affichageVague.setFont("Papyrus-35");
		pG = new GLabel ("" + G, 0,0);
		pG.setFont("Gill Sans Nova-20");
		pV = new GLabel ("" + V, 0,0);
		pV.setFont("Gill Sans Nova-20");
		pT = new GLabel ("" + T, 0,0);
		pT.setFont("Gill Sans Nova-20");
		pP = new GLabel ("" + P, 0,0);
		pP.setFont("Gill Sans Nova-20");
		pP.setColor(Color.RED);
		pG.setColor(Color.RED);
		pT.setColor(Color.RED);
		pV.setColor(Color.RED);
		
		//cercles
		Froi = new GOval (679, 233, 90 ,90);
		Vi = new GOval (1045, 435, 90 ,90);
		Poiz = new GOval (621, 535, 90 ,90);
		Trembl = new GOval (990, 236, 90 ,90);
		Froi.setFilled(false);
		Vi.setFilled(false);
		Poiz.setFilled(false);
		Trembl.setFilled(false);
		
		//images		
		FondEteJour = new GImage ("../visuel/FEJ.png",0,0);
		FondEteNuit = new GImage ("../visuel/FEN.png",0,0);
		FondHiverJour = new GImage ("../visuel/FHJ.png",0,0);
		FondHiverNuit = new GImage ("../visuel/FHN.png",0,0);
		FondMenuEteJour = new GImage ("../visuel/FMEJ.png",0,0);
		FondMenuEteNuit = new GImage ("../visuel/FMEN.png",0,0);
		FondMenuHiverJour = new GImage ("../visuel/FMHJ.png",0,0);
		FondMenuHiverNuit = new GImage ("../visuel/FMHN.png",0,0);
		infoLu = new GImage ("../visuel/exclamation.png",0,0);
		btQuit = new GImage ("../visuel/quitter.png",0,0);
		btJouer = new GImage ("../visuel/jouer.png",0,0);
		btShop = new GImage ("../visuel/shop.png",0,0);
		flecheG = new GImage ("../visuel/flechegauche.png",0,0);
		flecheD = new GImage ("../visuel/flechedroite.png",0,0);
		croixRouge = new GImage ("../visuel/croixrouge.png",0,0);
		info1 = new GImage ("../visuel/page1.png",0,0);
		info2 = new GImage ("../visuel/page2.png",0,0);
		info3 = new GImage ("../visuel/page3.png",0,0);
		info4 = new GImage ("../visuel/page4.png",0,0);
		info5 = new GImage ("../visuel/page5.png",0,0);
		info6 = new GImage ("../visuel/page6.png",0,0);
		info7 = new GImage ("../visuel/page7.png",0,0);
		info8 = new GImage ("../visuel/page8.png",0,0);
		info9 = new GImage ("../visuel/page9.png",0,0);
		info10 = new GImage ("../visuel/page10.png",0,0);
		info11 = new GImage ("../visuel/page11.png",0,0);
		info12 = new GImage ("../visuel/page12.png",0,0);
		fondShop = new GImage ("../visuel/fondShop.png",0,0);
		lock = new GImage ("../visuel/lock1.png",883,530);
		lock1 = new GImage ("../visuel/lock2.png",747,530);
		lock2 = new GImage ("../visuel/lock.png",1155,530);
		nop = new GImage ("../visuel/nop.png",0,0);
		croixJeu = new GImage("../visuel/croixJeu.png",4,31);
				
		gemmePoison = 0;
		gemmeGel = 0;
		gemmeVie = 0;
		gemmeTremblement = 0;
		exp = 0;
		rec.Recuperer();
		//récupération des infos des parties précédentes du joueur
		niveau = rec.getNiveau();	
		gemmePoison = rec.getPoison();
		gemmeGel = rec.getGel();
		gemmeVie = rec.getVie();
		gemmeTremblement = rec.getTremblement();
		//labels shop ("texte" + variable, posX, posY)
		niv = new GLabel ("Niveau actuel: " + niveau, 1150, 610);
		niv.setFont("Gill Sans Nova-20"); //("police-taille")
		nGp = new GLabel ("" + gemmePoison, 56,36);
		nGp.setFont("Gill Sans Nova-30");
		nGv = new GLabel ("" + gemmeVie, 56,225);
		nGv.setFont("Gill Sans Nova-30");
		nGt = new GLabel ("" + gemmeTremblement, 56,160);
		nGt.setFont("Gill Sans Nova-30");
		nGg = new GLabel ("" + gemmeGel, 56,95);
		nGg.setFont("Gill Sans Nova-30");
		ah = new GLabel ("Vous n'avez pas assez de pouvoir !", 310,540);
		ah.setFont("Gill Sans Nova-20");
		ah.setColor(Color.RED);
		
		timer = new SwingTimer(safeTpsAcc, this);
		timerAffichage = new SwingTimer(1000, this);
		
		cases = new GRect[x][y];
		for (byte a=0;a<x; a++) { 
		for (byte b=0;b<y; b++) { 
		cases[a][b] = new GRect(posX+taille*a,posY+taille*b,taille,taille); 
		}
		}
		dep = new Deplacement(cases);

		//chemin invisible mais necessaire pour placer les tours
		chemin1 = new GRect(cases[0][0].getX(), cases[0][10].getY(), taille*20, taille);
		chemin2 = new GRect(cases[20][0].getX(), cases[0][2].getY(), taille, taille*9);
		chemin3 = new GRect(cases[20][0].getX(), cases[0][1].getY(), taille*15, taille);
		chemin4 = new GRect(cases[35][0].getX(), cases[0][1].getY(), taille, taille*10);
		chemin5 = new GRect(cases[35][0].getX(), cases[0][12].getY(), taille*10, taille);
		map = new GRect(cases[0][0].getX(), cases[0][0].getY() ,taille*x, taille*y);
		
		Menu();
		son.MMenu();
	}

	public void Menu() {
		//récupérer la date du calendrier du pc
				GregorianCalendar calendar = new GregorianCalendar();
				Heure = calendar.get(java.util.Calendar.HOUR_OF_DAY);
				Jour = calendar.get(java.util.Calendar.DAY_OF_YEAR);
				
		//fond selon date et heure
				if ( Jour<60 || Jour >335) {if (Heure<9 || Heure>19) {add(FondMenuHiverNuit);} else add(FondMenuHiverJour);
				}else if (Heure<9 || Heure>19) {add(FondMenuEteNuit);} else add(FondMenuEteJour);
				
				if (lu == 0) add(infoLu);
				add(niv);
				
	}
	
	public void Info() {
		
		//fond selon date et heure
		if ( Jour<60 || Jour >335) {if (Heure<9 || Heure>19) {add(FondMenuHiverNuit);} else add(FondMenuHiverJour);
		}else if (Heure<9 || Heure>19) {add(FondMenuEteNuit);} else add(FondMenuEteJour);
		
		Page();
		
	}
	public void Page() {
		
		switch(page)
        {
            case 1:
            	add(info1);
            break;
            case 2:
            	add(info2);
            break;
            case 3:
            	add(info3);
            break;
            case 4:
            	add(info4);
            break;
            case 5:
            	add(info5);
            break;
            case 6:
            	add(info6);
            break;
            case 7:
            	add(info7);
            break;
            case 8:
            	add(info8);
            break;
            case  9:
            	add(info9);
            break;
            case 10:
            	add(info10);
            break;
            case 11:
            	add(info11);
            break;
            case 12:
            	add(info12);
            break;
            default:
            	add(info1);
            break;
        }
		
	}
	
	
	//le magasin du jeu
		public void Shop() {
			son.MShop();
			add(fondShop);			
			nGv.setLabel(""+gemmeVie);
			nGp.setLabel(""+gemmePoison);
			nGt.setLabel(""+gemmeTremblement);
			nGg.setLabel(""+gemmeGel);
			add(nGv); add(nGp); add(nGt); add(nGg);
			add(pT);add(pV);add(pP);add(pG);
			pG.setLocation(770,270);
			pT.setLocation(1070,270);
			pP.setLocation(610,570);
			pV.setLocation(1030,470);
		}
	
		public void quit() {
	    	try {
				rec.Sauvegarder(niveau, gemmePoison, gemmeTremblement, gemmeVie, gemmeGel);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	System.exit(0);
	    }

	public void Jeu() {
		facteurCreationMonstre = facteurCreationOiseau = 0.1f;
		facteurCreationBlurp = -0.5f;
		timer.restart();
		timerAffichage.restart();
		tempsEcouleReal =0;
		//convertir en secondes
		byte sec = 0;
		byte min = 0;
		affichageTemps.setLabel(min + ":" + sec);
		compteurVague = 1;
		affichageVague.setLabel(compteurVague+"");
		monaie = 300;
		affichageMonaie.setLabel(monaie+"");
		//genere un tableau a cadrillages
				
				//fond selon date et heure
				if ( Jour<60 || Jour >335) {
					if (Heure<9 || Heure>19) {
						add(FondHiverNuit);
						FondHiverNuit.sendToBack();
						} 
					else {add(FondHiverJour);
					FondHiverJour.sendToBack();
					}
				}else if (Heure<9 || Heure>19) {
					add(FondEteNuit);
					FondEteNuit.sendToBack();
					}
				else {add(FondEteJour);
				FondEteJour.sendToBack();
					}
				
				son.MJeu();
		
				//différents boutons, textes,...
				add(vieJoueur);
				add(affichageVague);
				add(affichageTemps);
				add(affichageMonaie);
				if (niveau == 1) {
					add(lock);
					add(lock1);
					add(lock2);
				}
				if (niveau >= 2 && niveau < 5) {
					add(lock1);
					add(lock2);
				}
				if (niveau >= 5 && niveau < 10) {
					add (lock2);
				}
		
				add(pT);add(pV);add(pP);add(pG);
				pG.setLocation(370,625);
				pT.setLocation(440,625);
				pP.setLocation(330,635);
				pV.setLocation(405,635);
				
		//timer
				timerAffichage.start();
				timer.start();
	}
	
	public void mouseExited() {
	    	try {
	    		rec.Sauvegarder(niveau, gemmePoison, gemmeTremblement, gemmeVie, gemmeGel);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public void mouseClicked(MouseEvent e) {
		double x = e.getX();
		double y = e.getY();
		
		if (etat == 'm') {
			if (info.contains(e.getX(), e.getY())) {removeAll();son.BClicBt(); Info(); lu=1; etat = 'i';}
			if (shop.contains(e.getX(), e.getY())) {removeAll();son.BClicBt(); son.stopMusique(); Shop(); etat = 's';}
			if (play.contains(e.getX(), e.getY())) {removeAll();son.BClicBt(); son.stopMusique(); Jeu(); etat ='j';}
			if (quit.contains(e.getX(), e.getY())) {son.BClicBt();quit();}
			}
		
		if (etat == 'i') {
			if(x>905 && x<963 && y>29 && y<58) {
			removeAll();
			Menu();
			son.BClicBt();
			etat = 'm';
			}
		}
		
		
	if (etat == 'j') {
		if(x>4 && x<70 && y>31 && y<88) {
			quitterJeu();
		}
	}
		if( etat == 's') {
		if(x>1267 && x<1325 && y>16 && y<45) {
			removeAll();
			son.BClicBt();
			son.stopMusique();
			son.MMenu();
			Menu();
			etat = 'm';
			}
		}
		
		if (etat == 's') {
			
			if (Vi.contains(x, y) && gemmeVie >=2) {
				gemmeVie = (byte) (gemmeVie - 2);
				nGv.setLabel(""+gemmeVie);
				add(nGv);
				V +=1;
				pV.setLabel("" + V);
				} else if (Vi.contains(x, y) && gemmeVie <2) add(nop);
			if (Froi.contains(x, y) && gemmeGel >=2) {
				gemmeGel = (byte) (gemmeGel - 2);
				nGg.setLabel(""+gemmeGel);
				add(nGg);
				G +=1 ;
				pG.setLabel("" + G);
			} else if (Froi.contains(x, y) && gemmeGel <2) add(nop);
			if (Trembl.contains(x, y) && gemmeTremblement >=2) {
				gemmeTremblement = (byte) (gemmeTremblement - 2);
				nGt.setLabel(""+gemmeTremblement);
				add(nGt);
				T += 1;
				pT.setLabel("" + T);
			} else if (Trembl.contains(x, y) && gemmeTremblement <2) add(nop);
			if (Poiz.contains(x, y) && gemmePoison >=2) {
				gemmePoison = (byte) (gemmePoison - 2);
				nGp.setLabel(""+gemmePoison);
				add(nGp);
				P +=1;
				pP.setLabel("" + P);
			} else if (Poiz.contains(x, y) && gemmePoison <2) add(nop);
			
		}
			
		if (etat == 'i') {
		if (page>1 && x>180 && x<350 && y>300 && y<380)  {page--; removeAll(); Info();}
		if (page<12 && x>977 && x<1150 && y>300 && y<380) { page++; removeAll(); Info();}
		}
		
	}
	
	
	public void mouseEntered(MouseEvent e) {
		this.setSize(1350,680);
	}
	

	public void mouseReleased(MouseEvent e) { 
	if (etat == 'j') {
		bouge = false;
		 //si appuie sur pause
		 if (iconPause.contains(e.getX(),e.getY())) {
			 if (!pause) pause = true;
			 else if(pause) pause = false;
		 }
		 if (iconAccTps.contains(e.getX(),e.getY())) {
			 if (safeTpsAcc == 32) {
				 safeTpsAcc = 16;
				 timer.setDelay(safeTpsAcc);
			 } else  if (safeTpsAcc == 16) {
					 safeTpsAcc = 32;
					 timer.setDelay(safeTpsAcc);
				 }
		 }
		 
		 //si on clique sur carré alors il peut bouger
		 //achat tourelles
	        if(iconTourelle.contains(e.getX(), e.getY()) && monaie>=prixTourelleMinus && !bouge) {
	        	//facteur de grossiement de la zone de tir de la tourelle
	        	Tourelles tminus = new Tourelles();
	        	tminus.setMinus(e.getX()-15, e.getY()-15);
	        	tourelleList.add(tminus);
	        	creationTourelles();
	        }
	        if (iconMortier.contains(e.getX(), e.getY()) && monaie>=prixTourelleMortier && !bouge && niveau >=5) {
	        	Tourelles tmortier = new Tourelles();
	        	tmortier.setMortier(e.getX()-15, e.getY()-15);
	        	tourelleList.add(tmortier);
	        	creationTourelles();
	        }
	        if (iconMine.contains(e.getX(), e.getY()) && monaie >=prixTourelleMine && !bouge&& niveau >=2) {
	        	Tourelles tmine = new Tourelles();
	        	tmine.setMine(e.getX()-15, e.getY()-15);
	        	tourelleList.add(tmine);
	        	creationTourelles();
	        }
	        	
	      if (iconTourelleBoss.contains(e.getX(), e.getY()) && monaie >= prixTourelleBoss && !bouge && niveau >= 10) {
	    	  if (verifTourelleBoss) {
	    		    Tourelles tboss = new Tourelles();
		        	tboss.setBoss(e.getX()-15, e.getY()-15);
		        	tourelleList.add(tboss);
		        	creationTourelles();
		        	verifTourelleBoss = false;
	    	   }
	        }
	       
	       //si tempsEcouleReal = 1000 soit 1s
	      if (!bouge) {
	       if (iconTremblement.contains(e.getX(), e.getY()) && T>=1 && tempsEcouleReal > 1000 && !actTremblement) {
	    	   delayTremblement = (int) tpsAccelere;
	    	   actTremblement = true;
	    	   T -=1;
	    	   pT.setLabel("" + T);
	       }else if (iconTremblement.contains(e.getX(), e.getY()) && T == 0) add(ah);
	       
	       //bonus vie
	       if (iconPotionVie.contains(e.getX(),e.getY()) && V>=1) if (vieJoueur.getWidth()<1250-250) {
	    	   vieJoueur.setSize(vieJoueur.getWidth()+250, vieJoueur.getHeight());
	    	   V-=1;
	    	   pV.setLabel("" + V);
	       }else if (iconPotionVie.contains(e.getX(),e.getY()) && V == 0) add(ah);
	       
	       //bonus gel
	       if (iconPotionGel.contains(e.getX(), e.getY()) && !placePoisonGel && !posPoisonGel && G>=1) {
	    	   poison = false;
	    	   zonePoison = new GImage("../visuel/gel.png",e.getX()-40, e.getY()-40);
	    	   add(zonePoison);
	    	   placePoisonGel = true;
	    	   G-=1;
	    	   pG.setLabel("" + G);
	       } else if (G == 0 && iconPotionGel.contains(e.getX(), e.getY())) add(ah);
	       
	       //bonus poison
	       if (iconPotionPoison.contains(e.getX(), e.getY()) && !placePoisonGel && !posPoisonGel && P>=1) {
	    	   poison = true;
	    	   zonePoison = new GImage("../visuel/poizon.png",e.getX()-40, e.getY()-40);
	    	   add(zonePoison);
	    	   placePoisonGel = true;
	    	   P-=1;
	    	   pP.setLabel("" + P);
	       }else if (P == 0 && iconPotionPoison.contains(e.getX(), e.getY())) add(ah);
	      }
	       
	       //lacher poison
	       if (placePoisonGel && actPoisonGel) {
	    	   posPoisonGel = true;
	    	   placePoisonGel = false;
	    	   actPoisonGel = false;
	    	   delayPoisonGel = (int) tpsAccelere;
	       }
	       
	       //-----
	       if (iconPoubelle.contains(e.getX(), e.getY())) {
	    	   bouge = false;
	    	   actBouge = false;
	    	   remove(tourelleList.get(tourelleList.size()-1).getTourelle());
	    	   remove(tourelleList.get(tourelleList.size()-1).getZoneTir());
	    	   monaie+=tourelleList.get(tourelleList.size()-1).getPrix();
	    	   tourelleList.remove(tourelleList.size()-1);
	    	   affichageMonaie.setLabel(monaie + "");
	    	   }
	       
	        //----------------------
	        //clique relaché et placement tourelle
	        if (bouge == false && actBouge){
	        	//repositionner tourelle
	        	if (map.contains(e.getX(), e.getY())) posChemin(e.getX(), e.getY());
	        	if (!(map.contains(e.getX(), e.getY()))){
	        		remove(tourelleList.get(tourelleList.size()-1).getTourelle());
	        		remove(tourelleList.get(tourelleList.size()-1).getZoneTir());
	        		actBouge = false;
	        		monaie+=tourelleList.get(tourelleList.size()-1).getPrix();
	        		affichageMonaie.setLabel(monaie + "");
	        		if (tourelleList.get(tourelleList.size()-1).getTourelleType() == "boss") verifTourelleBoss = true;
	        		tourelleList.remove(tourelleList.size()-1);
	        	}
	        }
	        
	        //améliorations : detection reclique sur tour
	        for (byte i= 0; i<tourelleList.size(); i++) {
	        	if (tourelleList.get(i).getTourelle().contains(e.getX(), e.getY()) && !bouge) {
	        		if (tourelleList.get(i).getZoneTir().isVisible()) tourelleList.get(i).getZoneTir().setVisible(false);
	        		else tourelleList.get(i).getZoneTir().setVisible(true);
	        		idUp = (byte) i;
	        	}
	        }
	        }
	 	}
	

	
	 public void mouseMoved(MouseEvent e){
		 double x = e.getX();
		double y = e.getY();
			if (etat == 'm') {
				if (shop.contains(e.getX(), e.getY())) { add(btShop); } 
				if (!shop.contains(e.getX(), e.getY())) { remove(btShop); } 
				if (quit.contains(e.getX(), e.getY())) { add(btQuit); } 
				if (!quit.contains(e.getX(), e.getY())) { remove(btQuit); } 
				if (play.contains(e.getX(), e.getY())) { add(btJouer); }
				if (!play.contains(e.getX(), e.getY())) { remove(btJouer); }
			}
			if (etat == 'i') {
				if (x>905 && x<963 && y>29 && y<58) {add(croixRouge); croixRouge.setLocation(0,0);}else remove(croixRouge);
				if (x>977 && x<1150 && y>300 && y<380) add(flecheD);else remove(flecheD);
				if (x>180 && x<350 && y>300 && y<380) add(flecheG); else remove(flecheG);
			}
			if (etat == 'j') {
				if (x>4 && x<70 && y>31 && y<88) {add(croixJeu);}else remove(croixJeu);
				if (!iconPotionPoison.contains(x,y) && !iconPotionGel.contains(x,y) && !iconTremblement.contains(x,y) && !iconPotionVie.contains(x,y)) {
					remove(ah);
				}
			}
			if (etat == 's') {
				if (x>1267 && x<1325 && y>16 && y<45) {add(croixRouge); croixRouge.setLocation(362,-13);} else remove(croixRouge);
				if (!Vi.contains(x,y) && !Trembl.contains(x,y) && !Froi.contains(x,y) && !Poiz.contains(x,y))remove(nop);
			}
			
		 if (etat == 'j') {
	        if (bouge){
	        		tourelleList.get(tourelleList.size()-1).getTourelle().setLocation(e.getX()-(taille/2), e.getY()-(taille/2));
	        		tourelleList.get(tourelleList.size()-1).getZoneTir().setLocation(tourelleList.get(tourelleList.size()-1).getTourelle().getX()-(tourelleList.get(tourelleList.size()-1)
	        				.getZoneTir().getHeight()/2)+15,tourelleList.get(tourelleList.size()-1).getTourelle().getY()-(tourelleList.get(tourelleList.size()-1).getZoneTir().getHeight()/2)+15);
		        	actBouge = true;
	         }
	        if (placePoisonGel) {
	        	zonePoison.setLocation(e.getX()-40, e.getY()-40);
	        	actPoisonGel = true;
	        }
		 }
	    }
	    
	
	
	public void keyPressed(KeyEvent e) {
		if (etat == 'j') {		
		//ameliorer zone : espace
		if(e.getKeyCode()==32) {
			 if (tourelleList.size()>0) if (monaie>=tourelleList.get(idUp).getPrix() && tourelleList.get(idUp).getUpZone()<tourelleList.get(idUp).getMaxAmeliorationZone()) {
				tourelleList.get(idUp).getZoneTir().setSize(tourelleList.get(idUp).getZoneTir().getWidth()+tourelleList.get(idUp).getZoneTir().getWidth()/3, tourelleList.get(idUp).getZoneTir().getHeight()
						+tourelleList.get(idUp).getZoneTir().getHeight()/3);
				tourelleList.get(idUp).getZoneTir().setLocation(tourelleList.get(idUp).getTourelle().getX()-(tourelleList.get(idUp).getZoneTir().getHeight()/2)+taille/2,tourelleList.get(idUp).getTourelle().getY()
						-(tourelleList.get(idUp).getZoneTir().getHeight()/2)+taille/2);
				monaie-=tourelleList.get(idUp).getPrix();
				tourelleList.get(idUp).setPrix((byte) (tourelleList.get(idUp).getPrix()+15)); //a modifier pour prix amelioration
				tourelleList.get(idUp).setUpZone();
				affichageMonaie.setLabel(monaie + "");
			 }
		}
		
		//ameliorer attaque : haut
		if (e.getKeyCode()==38) {
			if (tourelleList.size()>0) if (monaie>=tourelleList.get(idUp).getPrix() && tourelleList.get(idUp).getUpAttaque()<tourelleList.get(idUp).getMaxAmeliorationAttaque()) {
				tourelleList.get(idUp).setAttaque((byte) (tourelleList.get(idUp).getAttaque()+tourelleList.get(idUp).getVarUpAttaque())); //a equilibrer
				monaie-=tourelleList.get(idUp).getPrix();
				tourelleList.get(idUp).setPrix((byte) (tourelleList.get(idUp).getPrix()+15)); //a modifier pour prix amelioration
				tourelleList.get(idUp).setUpAttaque();
			    affichageMonaie.setLabel(monaie + "");
			}
		}
		
		//touhe du bas = vendre tourelle
		if (e.getKeyCode()==40) {
			if (tourelleList.size()>0) {
			remove(tourelleList.get(idUp).getTourelle());
			remove(tourelleList.get(idUp).getZoneTir());
			if (tourelleList.get(idUp).getTourelleType() == "boss") verifTourelleBoss = true;
			monaie+=tourelleList.get(idUp).getPrix()/2;
			affichageMonaie.setLabel(monaie + "");
			tourelleList.remove(idUp);
				}
		}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if (etat == 'j') {	
		//temps nomrmal affiché
		if (e.getSource().equals(timerAffichage) && !pause) {
			tempsEcouleReal += 1000;
			//convertir en secondes
			byte sec = (byte) (tempsEcouleReal/1000%60);
			byte min = (byte) (tempsEcouleReal/1000/60);
			affichageTemps.setLabel(min + ":" + sec);
			//------
			if(monstreList.size()==0) {
			// créer ennemis (voir methodes)
					vaguesEnnemis(5, 5);
					//augemente nb ennemis dans la vague + leur vie
					facteurCreationOiseau+=0.3;
					facteurCreationMonstre+=0.5;
					facteurCreationBlurp+=0.2;
					compteurVague++;
					affichageVague.setLabel(compteurVague + "");
					resistanceOiseau+=1;
					resistanceMonstreVert +=2;
					resistanceBlurp +=2;
			if (compteurVague%6 == 0) {
				Monstres boss = new Monstres();
				boss.setBoss(resistanceBoss);
				monstreList.add(boss);
				boss.getImage().sendToFront();
				monstreList.get(monstreList.size()-1).getImage().setLocation(cases[0][0].getX()-100, cases[0][10].getY());
				monstreList.get(monstreList.size()-1).getVieAffiche().setLocation(monstreList.get(monstreList.size()-1).getImage().getX()+10, monstreList.get(monstreList.size()-1).getImage().getY()-2);
				add(monstreList.get(monstreList.size()-1).getImage());
				add(monstreList.get(monstreList.size()-1).getVieAffiche());
				resistanceBoss+=8;
					}
			}
			if (compteurVague%10 == 0) {
			//remet facteur a 0 et donne + vie aux ennemis
			facteurCreationOiseau = 1;
			facteurCreationBlurp=2;
			facteurCreationMonstre=0;
				}
			for (Tourelles t : tourelleList) {
				if (!t.getTourelle().isVisible()) {
				tourelleList.remove(t);
				break;
					}
				}
			}
		//temps accéléré
		if (e.getSource().equals(timer) && !pause) {
			tpsAccelere+=safeTpsAcc;
			secAccelere = (byte) (tpsAccelere/100%30);
			for (Monstres m: monstreList) {
				if (m.getMonstre()!="oiseau") dep.deplacementTerre(m.getImage(), m.getVieAffiche(), m.getVitesse());
				else dep.deplacementAir(m.getImage(), m.getVieAffiche(), m.getVitesse());
			}
			if (posPoisonGel && delayPoisonGel+(safeTpsAcc*70)<tpsAccelere) {
				remove(zonePoison);
				posPoisonGel = false;
			}	else if (posPoisonGel) col.poisonGel(zonePoison);
			if (actTremblement && delayTremblement + (safeTpsAcc*40)>=tpsAccelere) col.tremblement();
			else {
				actTremblement = false;
			}
			col.colisionTour(secAccelere);
			col.verifMort();
			
			
			if (col.collisionChateau()) {
			quitterJeu();
				}
			}
		}
		}
	
	public void quitterJeu() {
		removeAll();
		timer.stop();
		timerAffichage.stop();
		son.stopMusique();
		Menu();
		son.MMenu();
		etat ='m';
		vieJoueur.setSize(1250, 20);
		col.setFin(false);
		monstreList.removeAll(monstreList);
		tourelleList.removeAll(tourelleList);
		//nombre de points d'éxperiences pour passer au niveau supérieur
		lvlUp = niveau * 50;
		//montée de niveau
		byte a = 1;
		if (exp > lvlUp) niveau++; exp = 0; lvlUp = lvlUp + 50 ;
		if (niveau != a) {
			randomGemme =(byte) ( Math.random()*(4+1));
			if (randomGemme==1) gemmePoison = (byte) (gemmePoison + niveau);
			if (randomGemme==2) gemmeGel = (byte) (gemmeGel + niveau);
			if (randomGemme==3) gemmeVie = (byte) (gemmeVie + niveau);
			if (randomGemme==4) gemmeTremblement = (byte) (gemmeTremblement + niveau);
		}
		try {
			rec.Sauvegarder(niveau, gemmePoison, gemmeTremblement, gemmeVie, gemmeGel);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	   public void vaguesEnnemis(int decalement, int largeur) {
		   if (etat == 'j') {
	    	//generation aléatoire 
		   for (byte i = 0; i<Math.random()*facteurCreationBlurp; i++) {
			   Monstres blurp = new Monstres();
			   blurp.setBlurp(resistanceBlurp);
			   monstreList.add(blurp);
			   blurp.getImage().sendToFront();
			  vagueGestion(i, decalement,largeur);
			}
		   for (byte i = 0; i<Math.random()*facteurCreationMonstre; i++) {
			Monstres monstre = new Monstres();
			monstre.setMonstreVert(resistanceMonstreVert);
			monstreList.add(monstre);
			monstre.getImage().sendToFront();
			vagueGestion(i, decalement,largeur);
		}
			for (byte i = 0; i<Math.random()*facteurCreationOiseau; i++) {
				Monstres oiseau = new Monstres();
				oiseau.setOiseau(resistanceOiseau);
				monstreList.add(oiseau);
				oiseau.getImage().sendToFront();
				vagueGestion(i, decalement,largeur);
					}
		   }
	    }
	 
	   public void vagueGestion(int i, int decalement, int largeur) {
		   if (etat == 'j') {
		    int aleaPlus = (int) (Math.random()*60);
			add(monstreList.get(monstreList.size()-1).getImage());
			monstreList.get(monstreList.size()-1).getImage().setLocation(cases[0][0].getX()-i*largeur-aleaPlus, cases[0][10].getY()+decalement);
			add(monstreList.get(monstreList.size()-1).getVieAffiche());
			monstreList.get(monstreList.size()-1).getVieAffiche().setLocation(cases[0][0].getX()-(i*largeur)+8-aleaPlus, cases[0][10].getY()-4+decalement);
	   }
	   }
	  
	   public void creationTourelles() {
		   if (etat == 'j') {
       	monaie-=tourelleList.get(tourelleList.size()-1).getPrix();
       	affichageMonaie.setLabel(monaie + "");
       	tourelleList.get(tourelleList.size()-1).getZoneTir().setLocation(tourelleList.get(tourelleList.size()-1).getTourelle().getX()+15-
       			(tourelleList.get(tourelleList.size()-1).getZoneTir().getWidth()/2), tourelleList.get(tourelleList.size()-1).getTourelle().getY()+15-(tourelleList.get(tourelleList.size()-1).getZoneTir().getHeight()/2));
       	add(tourelleList.get(tourelleList.size()-1).getTourelle());
       	add(tourelleList.get(tourelleList.size()-1).getZoneTir());
       	bouge = true;
		   }
	   }
	
	   public void posChemin(double posX, double posY) {
		   if (etat == 'j') {
		   for (byte i = 0; i<x; i++) {
       		for (byte o = 0; o< y; o++) {
       		if (cases[i][o].contains(posX, posY)) {
       				//quand tourelle
       				//empeche de poser sur autre tourelle
       			if (tourelleList.get(tourelleList.size()-1).getTourelleType() != "mine") {
       				if (!(chemin1.contains(posX, posY) || chemin2.contains(posX, posY)
   	       					|| chemin3.contains(posX, posY) || chemin4.contains(posX, posY) 
   	       					|| chemin5.contains(posX, posY))) {
       					tourelleList.get(tourelleList.size()-1).getTourelle().setLocation(cases[i][o].getLocation());
       					tourelleList.get(tourelleList.size()-1).setPlace();
       					}  else {
							if(posX<1350/2) {
								tourelleList.get(tourelleList.size()-1).getTourelle().setLocation(cases[i+1][o+1].getLocation());
								tourelleList.get(tourelleList.size()-1).setPlace();
								} else {
									tourelleList.get(tourelleList.size()-1).getTourelle().setLocation(cases[i-1][o+1].getLocation());
									tourelleList.get(tourelleList.size()-1).setPlace();
								}
       					}
       				tourelleList.get(tourelleList.size()-1).getZoneTir().setLocation(tourelleList.get(tourelleList.size()-1).getTourelle().getX()-
       						(tourelleList.get(tourelleList.size()-1).getZoneTir().getWidth()/2)+15,tourelleList.get(tourelleList.size()-1).getTourelle().getY()-(tourelleList.get(tourelleList.size()-1).getZoneTir().getHeight()/2)+15);
       			} else {
       				if (chemin1.contains(posX, posY) || chemin2.contains(posX, posY)
   	       					|| chemin3.contains(posX, posY) || chemin4.contains(posX, posY) 
   	       					|| chemin5.contains(posX, posY)) {
       					tourelleList.get(tourelleList.size()-1).getTourelle().setLocation(cases[i][o].getLocation());
       					tourelleList.get(tourelleList.size()-1).setPlace();
       				tourelleList.get(tourelleList.size()-1).getZoneTir().setLocation(tourelleList.get(tourelleList.size()-1).getTourelle().getX()-
       						(tourelleList.get(tourelleList.size()-1).getZoneTir().getWidth()/2)+15,tourelleList.get(tourelleList.size()-1).getTourelle().getY()-(tourelleList.get(tourelleList.size()-1).getZoneTir().getHeight()/2)+15);
       				} else {
       				remove(tourelleList.get(tourelleList.size()-1).getTourelle());
					remove(tourelleList.get(tourelleList.size()-1).getZoneTir());
					if (tourelleList.get(tourelleList.size()-1).getTourelleType() == "boss")verifTourelleBoss = true;
					monaie+=tourelleList.get(tourelleList.size()-1).getPrix();
					tourelleList.remove(tourelleList.get(tourelleList.size()-1));
					bouge = actBouge = false;
					affichageMonaie.setLabel(monaie + "");
						}
       			}
       				for (Tourelles t : tourelleList) {
       					//si emplacement d'une autre tourelle
       					if (t!=tourelleList.get(tourelleList.size()-1)) if (t.getTourelle().contains(tourelleList.get(tourelleList.size()-1).getTourelle().getLocation())) {
       						remove(tourelleList.get(tourelleList.size()-1).getTourelle());
       						remove(tourelleList.get(tourelleList.size()-1).getZoneTir());
       						if (tourelleList.get(tourelleList.size()-1).getTourelleType() == "boss")verifTourelleBoss = true;
       						monaie+=tourelleList.get(tourelleList.size()-1).getPrix();
       						tourelleList.remove(tourelleList.size()-1);
       						bouge = actBouge = false;
							affichageMonaie.setLabel(monaie + "");
							break; //arrete la boucle si trouvé
       						}
       					}
       				}
       			}
		   }
		   actBouge = false;
		   }  
	}
	   
}