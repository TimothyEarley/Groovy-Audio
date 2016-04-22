import spock.lang.Specification

/**
 * Created 23/04/16
 * @author Timothy Earley
 */
class SoundTest extends Specification {


	def "load a sound"() {
		given: "a file to load"
		def path = getClass().getResource("thx.mp3").toExternalForm()

		when: "the sound is loaded"
		Sound.load(path, "Test")

		then: "The sound should be present"
		Sound.sounds.Test
	}

	def "play a sound"() {
		given: "a sound"
		def sound = Sound.load(getClass().getResource("thx.mp3").toExternalForm(), "Test")

		when: "the sound is played"
		sound.play()

		then: "listen"
		true
	}

	//TODO test SoundSystem


}
