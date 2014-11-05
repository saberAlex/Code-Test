Project Name: Organisation Chart Traversal
------------------------------------------
This is a README file for BT's Code Test Implementation.


File List
----------------------
src     / Employee.java 	    Class performing the task given: print the shortest path of communication between two employees
src     / MyProgram		        Unix Script to run the program 
src     / TestEmployee.java 	Class performing JUnit tests 
res     / *.txt		            input files for tests
README				                This file

Specifications 
---------------
- This program  takes details of organisational chart in .txt file format and the names of two people, and prints out the names of all the people in the chain between them.
- The list of employees provided is not guarantee to be in order. However, both Employee ID and Manager ID value will always be an Integer. 
- As mentioned in the specification, the Manager ID of the person at the top of the hierarchy will be left blank. 
- It's possible to have employees share the same name. In this case, the program prints one path between each pair of people with the same name. 
- When comparing names, the case of letters is not significant, and neither are leading or trailing spaces or runs of multiple spaces.

Assumptions
------------

- Other than the person at the top of hierarchy, we assume that both value of Employee ID and Manager ID are a positive Integer.
- Employee ID determines the hierarchy between employees with the same Manager ID 
- Employee ID is unique
- The text file provided should be correctly formatted as mentioned in the task's specification: one employee per line, including the header line.
- Employees might have more than two subordinates. However they are only allowed to have one manager
- Managers are not allowed to have subordinates from two different manager ID
- Managers with higher ID need to have subordinates before assigning subordinates for the managers with lower ID. 
- Given a list of manager ID = [0,1,1,1,2,2,3,3,4,4,5,5]. Below are the list of subordinates for each employees.
	<Manager Id> : <list of subordinates' Manager ID>
		0    : 1,1,1
		1    : 2,2
		1    : 3,3
		1    : 4,4
		2    : 5,5

Getting Started 
----------------

- This program takes input of three arguments: String filename (along with the path), String first employee name, String second employee name
- Compiling the program:
	javac Employee.java
- Generating the javadoc:
	javadoc Employee.java

Executing the Application
-------------------------
- You may execute the application through the bash file (MyProgram) or directly from the java class
- Example: ./MyProgram <filename.txt> <Name1> <Name2>
		   java Employee <filename.txt> <Name1> <Name2>
	- filename.txt
		(required) Specifies the path and the name of the file containing the list of Employees.
- Execute on Windows: java Employee <filename.txt> <Name1> <Name2>



Testing
-------
- The test cases and data are stored in TestEmployee.java. Test cases were implemented with JUnit 4. 

Environment
-----------
The application was successfully run on MacOS X 10.9.5, Ubuntu 14.04.1 LTS, and Windows 7

