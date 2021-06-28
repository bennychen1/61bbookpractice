package images.animals.dog;

public class Dog {
    protected int size;
}
/** images.animals.dog means images/animals/dog folder
 * every file that has package x should go in the same folder
 * Move a file to a package (example to this package)
 *  add package images.animals.dog to the file, then move to this folder
 *  package [file_path]
 *  Need to import or use package_path.className to get a class from a different pacakge
 *  If a file does not have an explicit package, it belongs in the default package
 *          can't import it to use the class outside a package
 * JAR files - a way to export class files to others (a bundle of class files)
 * File->Project Structure->Artifacts->+->JAR->from modules with dependencies
 *      click ok a few times
 *         Build->Build Artifacts->Build
 *      go to source root folder -> out -> artifacts
 *
 * Access modifiers
 *      public - everyone can access/modify
 *                 the class is guranteed to have this function/variable
 *                 make deprecated if no longer in use
 *      protected - the class itself, classes in the same package, and subclasses
 *              subclasses can be in other packages
 *      package-private - the class itself and classes (and subclasses) in the same package
 *              other classes in the package need the member for their implementation
 *      private - only the class itself can access
 *              code that only the implementation of the class needs
 *      can modify the entire class
 *          instead of public class Dog, could be class Dog (package-private)*/
