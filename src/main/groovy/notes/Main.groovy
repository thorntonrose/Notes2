package notes

class Main {
	public static void main(String[] args) {
		println args
		def cli = new CliBuilder(usage: "notes <options> <args>")
		cli._ longOpt: "help", "show usage"
		cli._ longOpt: "add", args: 2, argName: "args", "add note (args = <title> <text>)"
		cli._ longOpt: "update", args: 2, argName: "args", "update note (args = <id> <text>)"
		cli._ longOpt: "delete", args: 1, argName: "id", "delete note"
		cli._ longOpt: "list", "list notes"
		def opts = cli.parse(args)
		if (!opts) { return }

		if (opts.options.size() == 0 || opts.help) {
			cli.usage()
			return
		}

		Notes.load()
		opts.adds && Notes.add(*opts.adds)
		opts.updates && Notes.update(*opts.updates)
		opts.delete && Notes.delete(*opts.delete)
		opts.list && Notes.list()
		Notes.save()
	}
}
