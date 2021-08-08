package notes

import groovy.json.*

class Notes {
	def file
	def doc
	def slurper = new JsonSlurper()

	Notes(path="notes.json") {
		file = new File(path)
		doc = file.exists() ? slurper.parse(file) : []
	}

	def save() {
		file.text = JsonOutput.prettyPrint(JsonOutput.toJson(doc))
	}

	def add(title, text) {}
	def update(id, text) {}
	def delete(id) {}

	def list() {
		doc.each { println "${it.id} ${it.title} ${it.text}" }
	}

	static newNote(title, text) {
		[id: 0, title: title, text: text]
	}
}
