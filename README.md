<div align="center">
  <img src="assets/ict12-01.png" alt="ICT 12-01 Logo" width="60" />
  <img src="assets/30th.png" alt="30th Anniversary Logo" width="60" />
  <img src="assets/shs.png" alt="SHS Logo" width="50" />

  <h1>JavaPlayground</h1>
  <p><b>A growing collection of Java programs I wrote for my Computer Programming classes.</b></p>
  <p>Mostly school stuff, but hey it's mine, and I'm proud of it.</p>

<!-- Badges -->
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white" />
  <img src="https://img.shields.io/badge/NetBeans-1B6AC6?style=for-the-badge&logo=apache-netbeans&logoColor=white" />
  <img src="https://img.shields.io/github/actions/workflow/status/gmguillergan/JavaPlayground/ci.yml?branch=main&style=for-the-badge&logo=githubactions&logoColor=white&label=CI" />

</div>

**Author:** Gabriel Martin G. Guillergan <br>
**Academic Year:** 2026 - 2027 <br>
**Instructor:** Ms. ___________ <br>
**Section:** ICT 12-01 <br>

---

## Curriculum Flow & Repository Structure

The exercises in this repository follow the lecture sequence of **Computer Programming 3 (2nd Quarter Period)**:

### Quarter 2 Overview

| Lesson | Topic | Associated Work / File | Status |
|---|---|---|---|
| **Lesson 2.1** | Applying Basics of Java Programming (Syntax, Variables, Scanner, JVM/JDK/JRE) | [`Guillergan_Operator_ICT1201.java`](Quarter-2/2Q_Guillergan_ICT%201/src/Guillergan_Operator_ICT1201.java) (Worksheet 2.1) | Completed |
| **Lesson 2.2** | Fundamentals of OOP (Classes, Objects, Methods, Constructors, Attributes) | [`Guillergan_JavaBasics_ICT1201.java`](Quarter-2/2Q_Guillergan_ICT%201/src/Guillergan_JavaBasics_ICT1201.java) (Worksheet 2.2) | Completed |
| **Lesson 2.3** | Basic OOP Concepts: Inheritance & Polymorphism (`extends`, `@Override`, polymorphic references) | `Lesson 2.3 Lab Activity` | Upcoming |
| **Lesson 4** | Basic OOP Concepts: Encapsulation & Abstraction (getters/setters, `abstract`, interfaces) | `Lesson 4 Lab Activity` | Upcoming |

### Quarter 2 Worksheets Breakdown

#### 1. `Guillergan_Operator_ICT1201.java` (Worksheet 2.1)
- **Lecture Focus:** Lesson 2.1 (Syntax, Variable Declarations, Execution Flow).
- **Implementation:** Prompts the user for two numerical grades via `java.util.Scanner`, evaluates which grade is higher or if both are identical, and verifies if each grade satisfies the passing threshold of 75.

#### 2. `Guillergan_JavaBasics_ICT1201.java` (Worksheet 2.2)
- **Lecture Focus:** Lesson 2.2 (Class Blueprint, Object Instantiation, Instance Methods).
- **Implementation:** Demonstrates custom class structure, instance methods with parameters, and object instantiation via the `new` keyword.

---

## Running the Code
> wait... kailangan pa ba ito?? 😭😭 okay, I don't really care but here it is anyway lolol.

### Prerequisites

- Java Development Kit (JDK) 8 or later (tested on OpenJDK 21)
- Apache NetBeans IDE (optional, for NetBeans project management)

> [!NOTE]
> Netbeans 8.2 is used in the worksheets and in the computer laboratory. I use a newer version of Netbeans on my workstation, but this repo is backward-compatible since it uses Java with Ant (which Netbeans 8.2 natively supports).

### Option 1: Command Line (4 d nerds)

> If you want to make it VERY VERY VERY COMPLICATED, do this.

Navigate to the source directory for Quarter 2:

```bash
cd "Quarter-2/2Q_Guillergan_ICT 1/src"
```

Compile and execute either worksheet:

```bash
# Worksheet 2.1 (Grade Checker)
javac Guillergan_Operator_ICT1201.java
java Guillergan_Operator_ICT1201

# Worksheet 2.2 (Java Basics)
javac Guillergan_JavaBasics_ICT1201.java
java Guillergan_JavaBasics_ICT1201
```

### Option 2: Apache NetBeans (the rightest way)

1. Open NetBeans IDE.
2. Select **File** > **Open Project**.
3. Browse to `Quarter-2/2Q_Guillergan_ICT 1` and click **Open Project**.
4. Right-click the file you want to execute in the Projects pane and select **Run File** (or press `Shift + F6`).

## License

This repository is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.