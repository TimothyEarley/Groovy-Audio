import javafx.embed.swing.JFXPanel
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer

/**
 * Created 22/04/16
 * @author Timothy Earley
 */
class Sound {

	static {
		// Provides a headless JavaFX for the media player FIXME
		def fxPanel = new JFXPanel()
	}

	static Map<String, Sound> sounds = [:];

	/**
	 * The ID identifies the sound. If two sounds have the same ID (even if they are different), they will no be played at the same time
	 * If left blank, it wont match any other id
 	 */
	String id

	MediaPlayer mediaPlayer

	/**
	 * Loads the file into the sound system
	 * @param file absolute path //TODO maybe make it relative (class.getResource(...))
	 * @param name The name this sound will be stored under (default: file)
	 * @param id the id of the sound (default: "" (unique))
	 * @return The sound loaded
	 */
	static load(String path, String name = path, String id = "") {
		def media = new Media(path)
		def mediaPlayer = new MediaPlayer(media)
		def sound = new Sound(mediaPlayer: mediaPlayer)
		sounds[name] = sound
	}

	// Only constructable via load
	private Sound() {}

	def play() {
		//TODO loop, make it actually work
		mediaPlayer.play()
	}

	@Override
	boolean equals(Object obj) {
		obj.is(this) || (id != "" && obj instanceof Sound && obj.id == id)
	}

	@Override
	int hashCode() {
		if (id == "") return ais.hashCode()
		else id.hashCode()
	}
}
