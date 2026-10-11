# JVM Basics

## JDK, JRE, and JVM

- **JVM (Java Virtual Machine):** Runs Java bytecode. It translates bytecode into instructions the current operating system and processor can execute.
- **JRE (Java Runtime Environment):** The runtime components needed to run Java applications, including the JVM and supporting libraries. In modern Java distributions, a separate JRE download may not be provided.
- **JDK (Java Development Kit):** The tools used to develop Java applications. It includes the Java runtime and tools such as `javac`, the Java compiler.

In simple terms: use the **JDK to build** Java programs, and the **JVM to run** compiled Java bytecode.

## What is bytecode?

When you compile a `.java` file with `javac`, Java creates a `.class` file. The `.class` file contains bytecode, an intermediate instruction format understood by the JVM. It is not normally tied to one specific operating system.

## What does “write once, run anywhere” mean?

Java source code can be compiled into bytecode once. That bytecode can run on different operating systems when a compatible JVM is available. For example, a compiled Java program can run on Windows, macOS, or Linux without changing the program's source code.

This does not mean every program will run identically in every environment: it still needs a compatible Java version, and programs that rely on operating-system-specific features may need changes.
