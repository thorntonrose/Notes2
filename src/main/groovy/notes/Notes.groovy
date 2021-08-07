package notes

import groovy.json.*

class Notes {
	static file
	static doc
	static slurper = new JsonSlurper()

	static newNote(title, text) {
		[id: 0, title: title, text: text]
	}

	static load(path="notes.json") {
		file = new File(path)
		doc = file.exists() ? slurper.parse(file) : []
	}

	static save() {
		file.text = JsonOutput.prettyPrint(JsonOutput.toJson(doc))
	}

	static add(title, text) {}
	static update(id, text) {}
	static delete(id) {}

	static list() {
		doc.each { println "${it.id} ${it.title} ${it.text}" }
	}
}
