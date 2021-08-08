package notes

import groovy.json.*
import org.junit.*
import static groovy.test.GroovyAssert.*

class NotesTest {
	@Test
	void testLoad() {
		def doc = [Notes.newNote("foo", "bar")]
		def file = new File("build/tmp/testLoad.json")
		file.text = JsonOutput.toJson(doc)

		def notes = new Notes(file.path)
		assertEquals "doc:", doc, notes.doc
	}

	@Test
	void testLoad_NoFile() {
		def notes = new Notes("build/tmp/testLoad_NoFile.json")
		assertEquals "doc:", [], notes.doc
	}
}
