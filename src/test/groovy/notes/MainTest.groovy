package notes

import groovy.json.*
import org.junit.*
import static groovy.test.GroovyAssert.*

class MainTest {
	@Before
	void setUp() {
		Notes.notes.clear()
	}

	@Test
	void testAdd() {
		Main.fileName = "build/tmp/${this.class.simpleName}_testAdd.json"
		def output = capture { Main.main(["--add", "foo", "bar"] as String[]) }.trim()
		assertEquals "output:", "1 foo bar", output
	}

	@Test
	void testUpdate() {
		Main.fileName = "build/tmp/${this.class.simpleName}_testUpdate.json"
		capture { Main.main(["--add", "foo", "bar"] as String[]) }

		def output = capture { Main.main(["--update", "1", "foo", "baz"] as String[]) }.trim()
		assertEquals "output:", "1 foo baz", output
	}

	@Test
	void testDelete() {
		Main.fileName = "build/tmp/${this.class.simpleName}_testDelete.json"
		capture { Main.main(["--add", "foo", "bar"] as String[]) }

		def output = capture { Main.main(["--delete", "1"] as String[]) }.trim()
		assertEquals "output:", "1 (deleted)", output
	}

	@Test
	void testList() {
		Main.fileName = "build/tmp/${this.class.simpleName}_testList.json"
		capture { ["bar", "baz"].each { Main.main(["--add", "foo", it] as String[]) } }

		def output = capture { Main.main(["--list"] as String[]) }.trim()
		assertEquals "output:", "1 foo bar\n2 foo baz", output
	}

	//--------------------------------------------------------------------------

	def capture(Closure block) {
		def out = System.out
		def buffer = new ByteArrayOutputStream()

		try {
			System.out = new PrintStream(buffer)
			block()
		} finally {
			System.out = out
		}

		buffer.toString()
	}
}
