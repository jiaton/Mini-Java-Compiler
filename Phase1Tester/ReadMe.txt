=========================================
Test Script for Phase 1

IMPORTANT: The grader is going to test your program on the department
machines. Be sure you run this script on those machines before submitting.

If you encounter any problems, e-mail the TA.

1. Tar up your source code into "hw1.tgz"
  - All your files should be in a subdirectory named "hw1".
  - Include only source code that you wrote.  DO NOT include ".class'
    files, ".jar" files, JavaCC grammar files or any Java files generated
	 by JavaCC or JTB.
  - If you have comments for the grader, put it in a file called
    "hw1/ReadMe.txt".  That is the only non-Java file allowed.

2. Execute the "run" script (located in the same directory as this ReadMe
   file) and give it the path to your "hw1.tgz" file:

      # ./run SelfTestCases ../hw1.tgz

   This will compile and run your program on all the tests in the
	"SelfTestCases/" directory.

IMPORTANT: Make sure you delete the "Output/" directory before giving this
script to someone else.  The "Output/" directory contains all the test
results as well as an unzipped copy of your entire homework submission!

IMPORTANT: The test cases in "SelfTestCases/" aren't the final test cases
used to score your submission.  These are an arbitrary set of test cases
to allow you to make sure your submission works with the grading script.

If you want, you can add more tests to the "SelfTestCases/" sub-directory.
Test cases that have a type error should be marked by adding the comment
"// TE" to the file (preferably on the line with the type error).

