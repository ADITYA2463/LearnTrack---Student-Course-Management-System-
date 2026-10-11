# Setup Instructions

## JDK version

Recommended version: **JDK 17**. LearnTrack does not require third-party libraries.

To check whether Java is installed, open a terminal and run:

```bash
java -version
javac -version
```

Both commands should print a version. `java` runs compiled Java programs; `javac` compiles `.java` source files into `.class` bytecode files.

## Hello World check

Create a file named `HelloWorld.java`:

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

Compile and run it in the folder containing the file:

```bash
javac HelloWorld.java
java HelloWorld
```

Expected output:

```text
Hello, World!
```

The source file is compiled first. The Java launcher then runs the generated class.

## Run LearnTrack

From the LearnTrack project root:

### macOS / Linux

```bash
mkdir -p out
find src -name "*.java" -print0 | xargs -0 javac -d out
java -cp out com.airtribe.learntrack.ui.Main
```

### Windows PowerShell

```powershell
New-Item -ItemType Directory -Force out
$files = Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName }
javac -d out $files
java -cp out com.airtribe.learntrack.ui.Main
```

## Screenshot evidence

Before submitting, run the Hello World program and LearnTrack in your own IDE or terminal and add genuine screenshots here if your evaluator requires them. Screenshots are intentionally not fabricated in this document.
