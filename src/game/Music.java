package game;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

public class Music {

	AudioClip clip, musique;
	URL url;

	public void MMenu() {
		url = getClass().getResource("../audio/MMenu.wav");
		musique = Applet.newAudioClip(url);
		musique.play();
	}
	
	public void MShop() {
		url = getClass().getResource("../audio/MShop.wav");
		musique = Applet.newAudioClip(url);
		musique.play();
	}
	
	public void MJeu() {
		url = getClass().getResource("../audio/MJeu.wav");
		musique = Applet.newAudioClip(url);
		musique.play();
	}
	
	public void BClicBt() {
		url = getClass().getResource("../audio/BClicBt.wav");
		clip = Applet.newAudioClip(url);
		clip.play();
	}
	public void BPoseTour() {
		url = getClass().getResource("../audio/BPoseTour.wav");
		clip = Applet.newAudioClip(url);
		clip.play();
	}
	public void BMonstreJaune() {
		url = getClass().getResource("../audio/BMonstreJaune.wav");
		clip = Applet.newAudioClip(url);
		clip.play();
	}
	public void BMonstreVert() {
		url = getClass().getResource("../audio/BMonstreVert.wav");
		clip = Applet.newAudioClip(url);
		clip.play();
	}
	public void BMonstreRouge() {
		url = getClass().getResource("../audio/BMonstreRouge.wav");
		clip = Applet.newAudioClip(url);
		clip.play();
	}
	public void BMonstreVol() {
		url = getClass().getResource("../audio/BMonstreVol.wav");
		clip = Applet.newAudioClip(url);
		clip.play();
	}
	public void BCoup() {
		url = getClass().getResource("../audio/BCoup.wav");
		clip = Applet.newAudioClip(url);
		clip.play();
	}
	public void stopMusique() {
		musique.stop();
	}
	
}
