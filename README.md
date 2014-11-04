**Project Name: Organisation Chart Traversal**
---------
- This is a simple documentation for BT's Code Test Implementation.

Specifications 
---------
- This program  takes details of organisational chart in .txt file format and the names of two people, and prints out the names of all the people in the chain between them.
- The list of employees provided is not guarantee to be in order. However, both Employee ID and Manager ID value will always be an Integer. As mentioned in the specification, the Manager ID of the person at the top of the hierarchy will be left blank. 
- The program will receive three separate command line arguments. The first is the path to the filename containing the employee data, and the other two are employee names. It should print the shortest path of communication between them that follows the hierarchy. 
- It's possible to have employees share the same name. In this case, the program prints one path between each pair of people with the same name. 
- When comparing names, the case of letters is not significant, and neither are leading or trailing spaces or runs of multiple spaces.

Assumptions
---------
- Other than the person at the top of hierarchy, we assume that both value of Employee ID and Manager ID will be a positive Integer.
- The text file provided should be correctly formatted as mentioned in the task's specification: one employee per line, including the header line. 





