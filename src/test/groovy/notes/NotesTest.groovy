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

		Notes.load(file.path)
		assertEquals "doc:", doc, Notes.doc
	}

	@Test
	void testLoad_NoFile() {
		Notes.load("build/tmp/testLoad_NoFile.json")
		assertEquals "doc:", [:], Notes.doc
	}
}
