import spock.lang.Ignore
import spock.lang.Specification
/**
 * Created 23/04/16
 * @author Timothy Earley
 */
class SoundTest extends Specification {


	def "load a sound"() {
		given: "a file to load"
		def path = getClass().getResource("thx.wav")

		when: "the sound is loaded"
		SoundSystem.load(path, "Test")

		then: "The sound should be present"
		SoundSystem.sounds.Test
	}

	@Ignore
	def "play a sound (requires manual verification)"() {
		given: "a sound"
		def sound = SoundSystem.load(getClass().getResource("thx.wav"))

		when: "the sound is played"
		sound.play()
		while (sound.playing) Thread.sleep(100)

		then: "listen"
		true
	}

	def "playing and stopping"() {
		given: "a sound"
		def sound = SoundSystem.load(getClass().getResource("thx.wav"))

		when: "the sound is started, then stopped"
		sound.play()
		sound.stop()

		then: "it should be stopped"
		!sound.playing
	}

	def "starting the sound system"() {

		given: "a sound system with a sound"
		SoundSystem.load(getClass().getResource("thx.wav"))
		def sound = SoundSystem.sounds["thx.wav"]

		when: "the system is running"
		SoundSystem.play("thx.wav")

		then: "the sound should play (if not, check the timing first)"
		sound.playing
	}

	//TODO test multiple sounds

}
