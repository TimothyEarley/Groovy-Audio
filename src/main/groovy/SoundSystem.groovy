/**
 * Created 22/04/16
 * @author Timothy Earley
 */

@Singleton
class SoundSystem implements Runnable {

	//TODO check number of channels
	private int MAX_PER_UPDATES = 16
	private int SLEEP_TIME = 100
	private boolean running = false


	//TODO if the set is thread safe, the methods don't have to be
	def queue = new HashSet<Sound>()

	def start() {
		if (running) return
		new Thread(this, "Audio thread").start()
		running = true
	}

	def stop() {
		running = false
	}


	void run() {
		while (running) {
			update()
			Thread.sleep(SLEEP_TIME)
		}
	}

	synchronized play(Sound sound) {
		queue.add(sound)
	}

	def update() {
		queue.take(MAX_PER_UPDATES).reverseEach {
			// TODO async
			it.play()
			queue.remove(it)
		}
	}

}
