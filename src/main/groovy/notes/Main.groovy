package notes

class Main {
	public static void main(String[] args) {
		def cli = new CliBuilder(usage: "notes <options> <args>")
		cli._ longOpt: "help", "show usage"
		cli._ longOpt: "add", args: 2, argName: "args", "add note (args = <title> <text>)"
		cli._ longOpt: "update", args: 3, argName: "args", "update note (args = <id> <title> <text>)"
		cli._ longOpt: "delete", args: 1, argName: "id", "delete note"
		cli._ longOpt: "list", "list notes"
		def opts = cli.parse(args)
		if (!opts) { return }

		if (opts.getOptions().size() == 0 || opts.help) {
			cli.usage()
			return
		}

		Notes.load("notes.json")
		opts.adds && println(format(Notes.add(*opts.adds)))
		opts.updates && println(format(Notes.update(*opts.updates)))
		opts.deletes && println(Notes.delete(*opts.deletes) + " (deleted)")
		opts.list && println(Notes.notes.collect { k, v -> format v }.join("\n"))
		Notes.save()
	}

	static format(Map note) {
		"${note.id} ${note.title} ${note.text}"
	}
}
