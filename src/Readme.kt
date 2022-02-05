import java.io.File

class Readme(
    private val rootPath: String,
    private val outputFileName: String,
) {
    private val rootDirName : String = File(rootPath).name

    init {
        println("created a Readme Builder with the start root directory $rootPath and the output file name is $outputFileName")
    }
    private fun openCollapsibleItem(dirName: String) : String {
        return "\n<details>\n" +
                "\t <summary>$dirName</summary> \n"
    }

    private fun closeCollapsibleItem() : String {
        return "</details>"
    }

    private fun addFileItem(fileName: String, filePath: String) : String {
        return "* [`$fileName`](`$filePath`): "
    }


    fun generateReadme() {
        val readmeBuilder = StringBuilder()
        travers(rootPath, readmeBuilder)
        readmeBuilder.appendLine(closeCollapsibleItem())
        File(outputFileName).writeText(readmeBuilder.toString())
    }

    private fun travers(curDirPath: String, readmeBuilder: StringBuilder) {
        /*ToDo include the depth of each directory/file to include them with the appropriate indentation
        *  and currently it is broken and sub directories are show directly under their parent
        *ToDo probably create 2 classes for directors and files such that each directory has
        * a vector of directories and files and each directories incrementally knows its position relative to its parent
        * and root directory that we started with
        */


        // iterate the content of the directory and list all the files first and then go to the next directory
        File(curDirPath).walk().forEach {
            if(it.isDirectory) {
                val relativePathName = it.relativeTo(File(rootPath)).toString()
                val dirPathName = if(relativePathName.isEmpty())    it.name + File.separator else rootDirName + File.separator + relativePathName
                readmeBuilder.appendLine(openCollapsibleItem(dirPathName))
                it.listFiles()?.forEach { inner_it -> readmeBuilder.appendLine(addFileItem(inner_it.name, inner_it.relativeTo(File(rootPath)).path))
                }
            }
        }


        // Some anomalies are happening here
        // add the files as items to the current collapse or create new one for
//        File(curDirPath).walk().forEach {
//            if(it.isFile) {
//                readme_builder.appendLine(add_file_item(it.name, it.relativeTo(File(root_path)).path))
//            } else {  // then its a director, and it should recursively travers it
//                val relative_path_name = it.relativeTo(File(root_path)).toString()
//                val dir_path_name = if(relative_path_name.isEmpty())    it.name + File.separator else root_dir_name + File.separator + relative_path_name
//                readme_builder.appendLine(open_collapsible_item(dir_path_name))
//            }
//        }


              // Recursive does not work because it leads to stack overflow
//        File(curDirPath).walk().forEach {
//            if(it.isFile) {
//                readme_builder.appendLine(add_file_item(it.path))
//            } else {  // then its a director, and it should recursively travers it
//                travers(it.path, readme_builder)
//            }
//        }

    }

}