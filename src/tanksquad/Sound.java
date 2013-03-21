package tanksquad;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;

public class Sound {
	
	public static synchronized void playSound(final String url) {
	    new Thread(new Runnable() { // the wrapper thread is unnecessary, unless it blocks on the Clip finishing, see comments
	      public void run() {
	        try {
	          Clip clip = AudioSystem.getClip();
	          AudioInputStream inputStream = AudioSystem.getAudioInputStream(Sound.class.getResourceAsStream(url));
	          clip.open(inputStream);
	          clip.start(); 
	        } catch (Exception e) {
	          System.err.println(e.getMessage());
	        }
	      }
	    }).start();
	  }

}
