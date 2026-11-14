# plc-grp4
# Project Overview:
Authors: Lucas Foster, Arnav Gawas, Avalon Gomez, Alora Smith, Joey Tam

How to build project: open JotlinTokenizerTester and call your *input file*, *output file*, and *compare file* in that order
- **1:** Navigate to testing folder
- **(Windows) 2:** java JotlinTokenizerTester.java /path/to/input.jott output.txt /path/to/compare.txt
- **(Windows) 3:** java -cp out provided.JotlinParser src/samples/*.jott
- **(Linux) 2:** java JotlinTokenizerTester ./input.jott output.txt ./compare.txt
- **(Linux) 3:** javac JotlinParser.java && java JotlinParser samples/\*.jott moreExamples/\*.jott
- **(Mac) 2:** javac JotlinTokenizerTester.java /path/to/input.jott output.txt /path/to/compare.txt
- **(Mac) 3:** javac -d out provided/\*.java created/\*.java
- **(Mac) 4:** java -cp out provided.JotlinParser samples/\*.jott moreExamples/\*.jott

  
The project is to create a compiler to translate Jotlin to Java, it has been split into phases with each phase representing a different component of the compiler. 

The project has been built through Java and any third party packages

- **Phase 1:** tokenizing through Jotlin
- **Phase 2:** creating a parse tree from the tokens
- **Phase 3:** validating the created parse tree
- **Phase 4:** translating Java code from the validated parse tree

# Phase 1:
In phase one the tokenizers for our compiler were built, with the tokenizers split into groups representing similar tokens. The groups are the following: Strings/Keywords/Ids, MathOps/RelOps/Arrows, Symbols, Doubles/Integers, and Tabs/New lines/Comments. 
There are currently no known issues with **phase 1**, and it passes all provided test cases.
**To Build**:
- **1:** Navigate to testing folder
- **(Windows) 2:** java JotlinTokenizerTester.java /path/to/input.jott output.txt /path/to/compare.txt
- **(Linux) 2:** java JotlinTokenizerTester ./input.jott output.txt ./compare.txt
- **(Mac) 2:** javac JotlinTokenizerTester.java /path/to/input.jott output.txt /path/to/compare.txt


	
# Phase 2:
In phase two we implemented all the nodes representing different parts of the grammar to build the JotlinTree. Each node is a separate class implementing JotlinTree so they can have different implementations of the validate, convertToJotlin and convertToJava methods for use later in the project. The convert to Jotlin mave have some differences in spacing.
**To Build**:
- **(Windows) 1:** Run java -cp out provided.JotlinParser src/samples/*.jott
- **(Linux) 2:** javac JotlinParser.java && java JotlinParser samples/\*.jott moreExamples/\*.jott
- **(Mac) 2:** javac -d out provided/\*.java created/\*.java
- **(Mac) 3:** java -cp out provided.JotlinParser samples/\*.jott moreExamples/\*.jott



# Phase 3:
In phase three the analyzer was implemented which takes and traverses through the JotlinTree created in phase two. The analyzer checks the tree and validates the nodes to make sure that semantic rules were followed. This includes variable declaration checking, verifying variable types, and validating the expressions and function calls.
**To Build**:
- **1:** Navigate to project root directory
- **(Windows) 2:** Run javac -d out src/created/*.java src/provided/*.java
- **(Windows) 3:** Run java -cp out provided.JotlinMain src/morePhase3Examples/*.jott
- **(Linux) 2:** Run javac -d out src/created/*.java src/provided/*.java
- **(Linux) 3:** Run java -cp out provided.JotlinMain src/morePhase3Examples/*.jott
- **(Mac) 2:** Run javac -d out src/created/*.java src/provided/*.java
- **(Mac) 3:** Run java -cp out provided.JotlinMain src/morePhase3Examples/*.jott

# Phase 4:
# Notes Section:
