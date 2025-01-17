/**
 * 
 */
package arpg.game.sound;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.sound.sampled.*;
import javax.xml.parsers.*;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Andrew
 * 
 */
public class SoundEngine {
	
	/**
	 * Maps Strings to the file location
	 */
	public final HashMap<String, String> soundMap;
	
	/**
	 * 
	 */
	public final HashMap<String, Playlist> playlistMap;
	
	/**
	 * The clip
	 */
	public Clip clip;
	
	/**
	 * The length of the clip
	 */
	public long clipLength;
	
	LineListener listener;
	
	/**
	 * 
	 */
	public boolean isCurrentlyPlaying = false;
	
	ClassLoader loader = this.getClass().getClassLoader();
	
	Thread thread;
	
	/**
	 * 
	 */
	public LinkedList<String> queue;
	
	/**
	 * The location of the sounds folder
	 */
	public static final String soundsFolderLocation = "arpg/assets/sounds/";
	
	boolean shouldStop;
	
	boolean isDaemon = true;
	
	/**
	 * 
	 */
	public static boolean debug = false;
	
	/**
	 * The path to the Sounds file. Should point directly to the Sounds file
	 */
	public static String pathToSoundsFile = "arpg/assets/sounds/Sounds.xml";
	
	/**
	 * 
	 */
	public SoundEngine () {
		
		soundMap = new HashMap<String, String>();
		// initSoundMap();
		
		queue = new LinkedList<String>();
		playlistMap = new HashMap<String, Playlist>();
		
		initSoundResourcesFromXML();
		
	}
	
	/**
	 * @param args
	 */
	public static void main (String[] args) {
		
		SoundEngine engine = new SoundEngine();
		debug = true;
		engine.isDaemon = false;
		
		System.out.println(engine.soundMap);
		System.out.println(engine.playlistMap);
		
		Playlist p = new Playlist("TestList");
		
		p.add("Adventure Meme");
		p.add("The Curtain Rises");
		p.add("Discovery Hit");
		
		engine.playPlaylist(p);
		
		// engine.play("Adventure Meme");
		
	}
	
	@SuppressWarnings("unused")
	private void debugPrint (String s) {
		if (debug) {
			System.out.println(s);
		}
		
	}
	
	/**
	 * @param name
	 */
	public void playPlaylist (String name) {
		
		playPlaylist(name, false);
		
	}
	
	/**
	 * @param name
	 * @param shuffle
	 */
	public void playPlaylist (String name, boolean shuffle) {
		
		Playlist p = getPlaylist(name);
		
		if (p != null) {
			if (shuffle) {
				
				Collections.shuffle(p);
				
			}
			playPlaylist(p);
		}
		
	}
	
	/**
	 * @param name
	 */
	public void addPlaylistToQueue (String name) {
		
		addPlaylistToQueue(name, false);
		
	}
	
	/**
	 * @param name
	 * @param shuffle
	 */
	public void addPlaylistToQueue (String name, boolean shuffle) {
		
		Playlist p = getPlaylist(name);
		
		if (p != null) {
			addToQueue(p);
		}
		
	}
	
	/**
	 * Resets the sound queue and adds p to the queue
	 * 
	 * @param p
	 * 
	 */
	public <T extends List<String>> void playPlaylist (final T p) {
		
		if (thread != null && thread.isAlive()) {
			shouldStop = true;
			try {
				thread.join();
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		shouldStop = false;
		if (clip != null && clip.isRunning()) {
			stop();
		}
		queue = new LinkedList<String>(p);
		
		playQueue();
		
	}
	
	/**
	 * 
	 */
	public void playQueue () {
		
		thread = new Thread(
				new Runnable() {
					@Override
					public void run () {
						
						try {
							String name;
							while (!shouldStop && !queue.isEmpty()) {
								
								name = queue.peekFirst();
								
								play(name);
								
								listener = new LineListener() {
									
									@Override
									public void update (LineEvent event) {
										
										if (event.getType() == LineEvent.Type.STOP) {
											queue.pollFirst();
											clip.removeLineListener(listener);
											// System.out.println("DEBUG: Removing the first song from the queue");
											
										}
										
									}
								};
								
								clip.addLineListener(listener);
								
								while (!shouldStop && clip.isRunning()) {
									// System.out.println("DEBUG: Playing Playlist");
									Thread.sleep(100);
									
								}
								
								// System.out.println("DEBUG: Done playing");
								
							}
						}
						catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				},
				"SoundThread"
				);
		
		thread.setDaemon(isDaemon);
		thread.start();
		
	}
	
	/**
	 * 
	 */
	public void playNextQueuedSound () {
		
		assert (queue.size() >= 1);
		
		String name = queue.peekFirst();
		
		play(name);
		
		listener = new LineListener() {
			
			@Override
			public void update (LineEvent event) {
				
				if (event.getType() == LineEvent.Type.STOP) {
					queue.pollFirst();
					clip.removeLineListener(listener);
				}
				
			}
		};
		
		clip.addLineListener(listener);
		
	}
	
	/**
	 * @param name
	 */
	public void addToQueue (String name) {
		
		queue.addLast(name);
		
	}
	
	/**
	 * @param list
	 */
	public void addToQueue (List<String> list) {
		
		for (String s : list) {
			
			queue.addLast(s);
			
		}
		
	}
	
	/**
	 * Clears the queue
	 */
	public void resetSoundQueue () {
		
		queue.clear();
		
	}
	
	/**
	 * Plays the matching sound
	 * 
	 * @param name
	 *            The name of the sound
	 */
	public void play (String name) {
		
		String location = soundMap.get(name);
		
		if (location == null) {
			throw new IllegalArgumentException(name + "could not be found");
		}
		
		// location = soundsFolderLocation + location;
		
		System.out.println(location);
		
		URL url = loader.getResource(location);
		
		AudioInputStream ais;
		
		try {
			
			ais = AudioSystem.getAudioInputStream(url);
			
			clipLength = (long) (ais.getFrameLength() / ais.getFormat().getFrameRate());
			clip = AudioSystem.getClip();
			System.out.println("Open");
			clip.open(ais);
			
		}
		catch (NullPointerException e) {
			e.printStackTrace();
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		
		clip.start();
		isCurrentlyPlaying = true;
		
	}
	
	/**
	 * Stops the clip
	 */
	public void stop () {
		
		clip.stop();
		clip.close();
		
	}
	
	/**
	 * @param name
	 * @return
	 */
	public Playlist getPlaylist (String name) {
		
		return playlistMap.get(name);
		
	}
	
	void initSoundResourcesFromXML () {
		
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp;
		
		try {
			URL url = loader.getResource(pathToSoundsFile);
			InputStream is = new FileInputStream(new File(url.toURI()));
			sp = spf.newSAXParser();
			SoundXMLHandler sxh = new SoundXMLHandler();
			sp.parse(is, sxh);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		catch (SAXException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Initialises the sound mappings using hardcoded mappings
	 */
	@Deprecated
	void initSoundMap () {
		
		soundMap.put("8bit Dungeon Boss", "8bit Dungeon Boss.wav");
		soundMap.put("Adventure Meme", "Adventure Meme.wav");
		soundMap.put("All This", "All This.wav");
		soundMap.put("Black Vortex", "Black Vortex.wav");
		soundMap.put("Call to Adventure", "Call to Adventure.wav");
		soundMap.put("Carefree", "Carefree.wav");
		soundMap.put("Cipher", "Cipher2.wav");
		soundMap.put("Cipher2", "Cipher2.wav");
		soundMap.put("Discovery Hit", "Discovery Hit.wav");
		soundMap.put("Fanfare for Space", "Fanfare for Space.wav");
		soundMap.put("Full On", "Full On.wav");
		soundMap.put("Hitman", "Hitman.wav");
		soundMap.put("Life of Riley", "Life of Riley.wav");
		soundMap.put("Take a Chance", "Take a Chance.wav");
		soundMap.put("Tempting Secrets", "Tempting Secrets.wav");
		soundMap.put("The Complex", "The Complex.wav");
		soundMap.put("The Curtain Rises", "The Curtain Rises.wav");
		soundMap.put("The Descent", "The Descent.wav");
		soundMap.put("Video Dungeon Boss", "Video Dungeon Boss.wav");
		
	}
	
	void initPlaylists () {
		
	}
	
	/**
	 * @author Andrew
	 * 
	 */
	public class SoundXMLHandler extends DefaultHandler {
		
		/**
		 * 
		 */
		public Stack<String> elementStack = new Stack<String>();
		
		/**
		 * 
		 */
		public Stack<Object> objectStack = new Stack<Object>();
		
		/**
		 * 
		 */
		public SoundXMLHandler () {
		}
		
		/* (non-Javadoc)
		 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
		 */
		@Override
		public void startElement (String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			
			elementStack.push(qName);
			
			switch (qName) {
			
				case "sound":
					
					if (attributes.getIndex("load") != -1) {
						Object o = objectStack.peek();
						if (o instanceof Playlist) {
							((Playlist) o).add(attributes.getValue("load"));
						}
					}
					else if (attributes.getIndex("name") != -1
							&& attributes.getIndex("src") != -1) {
						soundMap.put(attributes.getValue("name"), attributes.getValue("src"));
					}
					
					break;
				case "soundlist":
					if (attributes.getIndex("ID") != -1
							&& !attributes.getValue("ID").trim().isEmpty()) {
						objectStack.push(new Playlist(attributes.getIndex("name") != -1
								&& !attributes.getValue("name").trim().isEmpty()
								? attributes.getValue("name").trim()
								: attributes.getValue("ID")));
					}
					break;
			}
			
		}
		
		/* (non-Javadoc)
		 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
		 */
		@Override
		public void endElement (String uri, String localName, String qName)
				throws SAXException {
			
			elementStack.pop();
			if (qName == "soundlist") {
				System.out.println(((Playlist) objectStack.peek()).name);
				playlistMap.put(((Playlist) objectStack.peek()).name, (Playlist) objectStack.pop());
			}
			
		}
		
		/**
		 * @param <T>
		 * @param s
		 * 
		 */
		public <T> void printStack (Stack<T> s) {
			
			for (T t : s) {
				System.out.println(t);
			}
			
		}
		
	}
	
}
