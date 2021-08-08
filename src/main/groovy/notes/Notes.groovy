package notes

import groovy.json.*

class Notes {
	static file
	static notes = [:]
	static slurper = new JsonSlurper()

	static newNote(title, text) {
		// !!!: id is String because JSON requires Map keys to be Strings.
		[id: "0", title: title, text: text]
	}

	static nextId() {
		((notes.keySet().collect { it as int }.max() ?: 0) + 1) as String
	}

	static load(fileName) {
		file = new File(fileName)
		file.exists() && (notes = slurper.parse(file))
	}

	static save() {
		file.text = JsonOutput.prettyPrint(JsonOutput.toJson(notes))
	}

	static add(String title, String text) {
		def note = newNote(title, text) + [id: nextId()]
		notes[note.id] = note
	}

	static update(String id, String title, String text) {
		def note = notes[id]
		if (!note) { throw new Exception("note $id not found") }
		notes[id] = note + [title: title, text: text]
	}

	static delete(String id) {
		notes.remove(id)
		id
	}
}
