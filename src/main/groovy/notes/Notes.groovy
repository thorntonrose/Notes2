package notes

import groovy.json.*

class Notes {
	static file
	static notes = []
	static slurper = new JsonSlurper()

	static newNote(title, text) {
		[id: 0, title: title, text: text]
	}

	static nextId() {
		(notes*.id.max() ?: 0) + 1
	}

	static load(fileName) {
		file = new File(fileName)
		notes = file.exists() ? slurper.parse(file) : []
	}

	static save() {
		file.text = JsonOutput.prettyPrint(JsonOutput.toJson(notes))
	}

	static add(String title, String text) {
		def note = newNote(title, text) + [id: nextId()]
		notes << note
		note
	}

	static update(String id, String text) {
		update id as int, text
	}

	static update(int id, String text) {
		def note = notes.find { it.id == id }
		if (!note) { throw new Exception("note $id not found") }
		note += [text: text]
		note
	}

	static delete(String id) {
		delete id as int
	}

	static delete(int id) {
		notes = notes.findAll { it.id != id }
		id
	}
}
