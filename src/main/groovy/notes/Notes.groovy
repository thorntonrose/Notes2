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
		notes.size() + 1
	}

	static load(path="notes.json") {
		file = new File(path)
		notes = file.exists() ? slurper.parse(file) : []
	}

	static save() {
		file.text = JsonOutput.prettyPrint(JsonOutput.toJson(notes))
	}

	static add(title, text) {
		def note = newNote(title, text) + [id: nextId()]
		notes << note
		note
	}

	static update(id, text) {

	}

	static delete(id) {}

	static list() {
		notes.collect { "${it.id} ${it.title} ${it.text}" }.join("\n")
	}
}
