import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip

/**
 * Created 22/04/16
 * @author Timothy Earley
 */
class Sound {

	/**
	 * The ID identifies the sound. If two sounds have the same ID (even if they are different), they will no be played at the same time
	 * If left blank, it wont match any other id
 	 */
	String id

	AudioInputStream ais

	int loops

	def playing = false

	Clip clip

	// Only constructable via SoundSystem
	protected Sound() {}

	synchronized play() {
		if (playing) return
		playing = true
		clip = AudioSystem.getClip()
		clip.open(ais)
		clip.loop(loops)
		clip.addLineListener {
			println "done playing"
			playing = false
		}
	}

	synchronized stop() {
		if (!playing) return
		clip.stop()
		playing = false
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
