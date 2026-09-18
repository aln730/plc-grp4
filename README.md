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
	
# Phase 2:
# Phase 3:
# Phase 4:
# Notes Section:
