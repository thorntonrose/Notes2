package notes

import groovy.json.*
import org.junit.*
import static groovy.test.GroovyAssert.*

class NotesTest {
	@Before
	void setUp() {
		Notes.notes.clear()
	}

	//--------------------------------------------------------------------------

	@Test
	void testLoad_NoFile() {
		Notes.load("build/tmp/${this.class.simpleName}_testLoad_NoFile.json")
		assertEquals "notes:", [:], Notes.notes
	}

	@Test
	void testLoad() {
		def notes = ["1": Notes.newNote("foo", "bar") + [id: "1"]]
		def file = new File("build/tmp/${this.class.simpleName}_testLoad.json")
		file.text = JsonOutput.prettyPrint(JsonOutput.toJson(notes))

		Notes.load(file.path)
		assertEquals "notes:", notes, Notes.notes
	}

	@Test
	void testSave() {
		Notes.add("foo", "bar")
		Notes.file = new File("build/tmp/${this.class.simpleName}_testSave.json")
		Notes.save()
		assertEquals "json:", JsonOutput.prettyPrint(JsonOutput.toJson(Notes.notes)), Notes.file.text
	}

	//--------------------------------------------------------------------------

	@Test
	void testAdd() {
		def id = Notes.nextId()
		def note = Notes.add("foo", "bar")
		assertEquals "note:", [id: id, title: "foo", text: "bar"], note
		assertEquals "notes:", ["1": note], Notes.notes
	}

	@Test
	void testUpdate() {
		def note1 = Notes.add("foo", "bar")
		def note2 = Notes.update(note1.id, "foo", "baz")
		assertEquals "note2:", [id: note1.id, title: note1.title, text: note2.text], note2
	}

	@Test
	void testUpdate_NotFound() {
		try {
			Notes.update "0", "foo", "bar"
		} catch (Exception e) {
			assertEquals "message:", "note 0 not found", e.message
		}
	}

	@Test
	void testDelete() {
		Notes.delete Notes.add("foo", "bar").id
		assertEquals "notes:", [:], Notes.notes
	}

	@Test
	void testDelete_NotFound() {
		Notes.add("foo", "bar")
		Notes.delete "0"
		assertEquals "notes.size:", 1, Notes.notes.size()
	}
}
