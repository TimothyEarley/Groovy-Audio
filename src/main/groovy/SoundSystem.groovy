import org.apache.commons.io.FilenameUtils

import javax.sound.sampled.AudioSystem

/**
 *
 * Static singleton for managing sound output
 *
 * Created 22/04/16
 * @author Timothy Earley
 */
class SoundSystem {

	// Always start the system when running
	static {
		start()
	}

	private SoundSystem() {}

	static final Map<String, Sound> sounds = [:];

	//TODO check number of channels
	static private int MAX_PER_UPDATES = 16
	static int SLEEP_TIME = 10
	static private boolean running = false

	//TODO if the set is thread safe, the methods don't have to be
	static private playQueue = new HashSet<Sound>()

	/**
	 * Loads the file into the sound system
	 * @param file can be URL, File or InputStream
	 * @param name The name this sound will be stored under (default: file)
	 * @param id the id of the sound (default: "" (unique))
	 * @param loops how many times to loop (default: 0)
	 * @return
	 */
	static load(URL file, String name = FilenameUtils.getName(file.file), String id = "", loops = 0) {
		println "Loading $file"
		def ais = AudioSystem.getAudioInputStream(file)
		def sound = new Sound(ais: ais, id: id, loops: loops)
		sounds[name] = sound
	}

	static private start() {
		if (running) return
		new Thread(SoundSystem.&run, "Audio thread").start()
		running = true
	}

	static stop() {
		running = false
	}

	synchronized static play(String name) {
		play(sounds[name])
	}

	/**
	 * Use only if necessary, otherwise load sounds centrally and call {@link SoundSystem#play(java.lang.String)}
	 * @param sound
	 * @return
	 */
	synchronized static play(Sound sound) {
		if (sound)
			playQueue.add(sound)
		else
			println "Given null sound..." //TODO replace with logger
	}

	static private run() {
		while (running) {
			update()
			Thread.sleep(SLEEP_TIME)
		}
	}

	synchronized static private update() {
		playQueue.take(MAX_PER_UPDATES).reverseEach {
			// TODO async
			it.play()
			playQueue.remove(it)
		}
	}

}
