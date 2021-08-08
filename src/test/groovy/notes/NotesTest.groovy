package notes

import groovy.json.*
import org.junit.*
import static groovy.test.GroovyAssert.*

class NotesTest {
	@Before
	void setUp() {
		Notes.notes = []
	}

	@Test
	void testLoad() {
		def notes = [Notes.newNote("foo", "bar")]
		def file = new File("build/tmp/testLoad.json")
		file.text = JsonOutput.toJson(notes)

		Notes.load(file.path)
		assertEquals "notes:", notes, Notes.notes
	}

	@Test
	void testLoad_NoFile() {
		Notes.load("build/tmp/testLoad_NoFile.json")
		assertEquals "notes:", [], Notes.notes
	}

	//--------------------------------------------------------------------------

	@Test
	void testList() {
		Notes.notes = [Notes.newNote("foo", "bar"), Notes.newNote("spam", "eggs")]
		assertEquals "list:", Notes.notes.collect { "${it.id} ${it.title} ${it.text}" }.join("\n"), Notes.list()
	}

	@Test
	void testAdd() {
		def id = Notes.nextId()
		def note = Notes.add("foo", "bar")
		assertEquals "note:", [id: id, title: "foo", text: "bar"], note
		assertEquals "notes[-1]:", note, Notes.notes[-1]
	}

	// @Test
	// void testUpdate() {
	// 	def note1 = Notes.add("foo", "bar")
	// 	def note2 = Notes.update(note1.id, "baz")
	// 	assertEquals "note2:", [id: note1.id, note1.title, note2.text], note2
	// 	assertEquals "notes.size:", 1, notes.size()
	// }
}
