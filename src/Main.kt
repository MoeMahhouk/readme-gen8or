fun main(args: Array<String>) {
    var root : String= System.getProperty("user.dir") // default value
    //var root: String = Paths.get("").toString()
    var outputFileName = "README.md"       // default value
    when (args.size) {
        1 -> root = args[0]
        2 -> {
            root = args[0]
            outputFileName = args[1]
        }
        else -> {
            println("please give maximum 2 parameters, one for the start directory path and the other for the output file name")
        }
    }
    val readmeBuilder = Readme(root, outputFileName)
    readmeBuilder.generateReadme()

}