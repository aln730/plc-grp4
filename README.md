# plc-grp4
# Project Overview:
Authors: Lucas Foster, Arnav Gawas, Avalon Gomez, Alora Smith, Joey Tam

How to build project: open JotlinTokenizerTester and call your *input file*, *output file*, and *compare file* in that order
- **1:** Navigate to testing folder
- **2:** Run  java JotlinTokenizerTester.java /path/to/input.jott output.txt /path/to/compare.txt
  
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
- **2:** Run  java JotlinTokenizerTester.java /path/to/input.jott output.txt /path/to/compare.txt
	
# Phase 2:
In phase two we implemented all the nodes representing different parts of the grammar to build the JotlinTree. Each node is a separate class implementing JotlinTree so they can have different implementations of the validate, convertToJotlin and convertToJava methods for use later in the project. The convert to Jotlin mave have some differences in spacing.
**To Build**:
- **1:** Navigate to testing folder
- **(Windows) 2:** Run java -cp out provided.JotlinParser src/samples/*.jott
- **(Mac) 2:** javac -d out provided/\*.java created/\*.java
- **(Mac) 3:** java -cp out provided.JotlinParser samples/\*.jott moreExamples/\*.jott


# Phase 3:
# Phase 4:
# Notes Section:
