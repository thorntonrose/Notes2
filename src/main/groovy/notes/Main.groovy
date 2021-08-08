package notes

class Main {
	static fileName = "notes.json"

	public static void main(String[] args) {
		def cli = new CliBuilder(usage: "notes <options> <args>")
		cli._ longOpt: "help", "show usage"
		cli._ longOpt: "add", args: 2, argName: "args", "add note (args = <title> <text>)"
		cli._ longOpt: "update", args: 2, argName: "args", "update note (args = <id> <text>)"
		cli._ longOpt: "delete", args: 1, argName: "id", "delete note"
		cli._ longOpt: "list", "list notes"
		def opts = cli.parse(args)
		if (!opts) { return }

		if (opts.getOptions().size() == 0 || opts.help) {
			cli.usage()
			return
		}

		Notes.load(fileName)
		opts.adds && show(Notes.add(*opts.adds))
		opts.updates && show(Notes.update(*opts.updates))
		opts.delete && Notes.delete(*opts.delete)
		opts.list && println(Notes.list())
		Notes.save()
	}

	static show(note) {
		println "${note.id} ${note.title} ${note.text}"
	}
}
