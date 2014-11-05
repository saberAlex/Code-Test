import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * BT Code Test.
 * This Employee class is the implementation of BT's programming task:
 * Organization Chart Traversal
 * Our main objective is to print out the name of all the people in the
 * chain between two employees
 *
 * @author Agastya Silvina
 * @version 1.2
 * @since  04/10/2014
 * */
public final class  Employee {
    /**
     * Employee name column's index.
     * */
    private static final int EMPLOYEE_NAME = 2;
    /**
     * Employee ID column's index.
     * */
    private static final int EMPLOYEE_ID = 1;
    /**
     * Manager ID column's index.
     * */
    private static final int MANAGER_ID = 3;
    /**
     * Total Column of input file.
     * */
    private static final int TOTAL_ATTRIBUTE = 4;
    /**
     * Manager ID.
     * */
    private int managerID;
    /**
     * Employee ID.
     * EmployeeID is unique
     * */
    private int employeeID;
    /**
     * Name of employee.
     * */
    private String name;
    /**
     * Employees' manager.
     * */
    private Employee manager;
    /**
     * List of subordinates.
     * */
    private ArrayList<Employee> subordinates;
    
    //Another attributes for version 1.2 and above implementation:
    /**
     * Keep the value of minimal distance from of particular employee.
     * */
    private int distanceValue;
    /**
     * Pointer to certain employee.
     * */
    private Employee leastDistance;
    
    /**
     * ArrayList contains all information of employees within organization.
     * */
    static ArrayList<Employee>
    listOfEmployees = new ArrayList<Employee>();

    /**
     * Employee Constructor.
     * @param m integer of manager ID
     * @param e integer of Employee ID
     * @param n String Employee's name
     * */
    private Employee(final int m, final int e, final String n) {
        managerID = m;
        employeeID = e;
        name = n;
        manager = null;
        subordinates = new ArrayList<Employee>();
        
        distanceValue = -1;
    }

    /**
     * Main method of the Employee class.
     * @param args input value from user
     * */
    public static void main(final String[] args)  {
        //get the list of employees from .txt file.
        //Worst time complexity: O(N)
        try {
            String fileName = args[0];
            String [] connection = new String[2];
            for (int i = 1; i < args.length; i++) {
                connection[i - 1] = args[i].replaceAll("\\s", "").toLowerCase();
            }
            
            readFromFile(fileName);
            Employee.formConnection(listOfEmployees);
            //Finding the connection between two employees
            //Worst time complexity: O(NLogN)
<<<<<<< HEAD
            Employee.findPath(listOfEmployees, connection[0], connection[1]);
=======
            Employee.findPath(listOfEmployees,connection[0],connection[1]);

>>>>>>> FETCH_HEAD
        } catch (FileNotFoundException e1) {
            System.err.print("Invalid File Name.");
        } catch (NumberFormatException e1) {
            System.err.print("Invalid ID");
        } catch (IOException e1) {
            System.err.print("Unable to load file.");
        } catch (ArrayIndexOutOfBoundsException e1) {
            System.err.print("Invalid Argument: too many arguments");
        } catch (NullPointerException e1) {
            System.err.print("Invalid Argument");
        }
        
    }
    
    /**
     * Read the input .txt and put Employees' detail into ArrayList.
     * @param fileName String of fileName (required: specific path)
     * */
    public static void readFromFile(String  fileName)
            throws  IOException {
        //Worst time complexity: O(NLogN)
        BufferedReader reader;
        
        reader = new BufferedReader(new FileReader(fileName));
        String line = null;
        int counter = 0;
        while ((line = reader.readLine()) != null) {
            counter++;
            if (counter > 1) {
                String []parts = line.split("\\|");
                String [] staffs = new String[TOTAL_ATTRIBUTE];
                for (int i = 0; i < parts.length; i++) {
                    staffs[i] = parts[i].trim();
                }
                int empId = Integer.valueOf(staffs[EMPLOYEE_ID].trim());
                int manId;
                if (staffs[MANAGER_ID].trim().equals("")) {
                    manId = 0;
                } else {
                    manId = Integer.valueOf(staffs[MANAGER_ID].trim());
                }
                String name = staffs[EMPLOYEE_NAME];
                listOfEmployees.add(new Employee(manId, empId, name));
            }
        }
        reader.close();
    }
    /**
     * Forming connection within Employees.
     * Set the value of the manager or subordinates.
     * @param staffs ArrayList of Staff within company
     * */
    public static void formConnection(final ArrayList<Employee>  staffs) {
        //Worst Time Complexity: O(NLogN)
        sort(staffs);
        Employee manager = staffs.get(0);
        int sId = staffs.get(1).managerID;
        int mIndex = 0;
        for (int i = 1; i < staffs.size(); i++) {
            if (staffs.get(i).managerID != sId) {
                sId =  staffs.get(i).managerID;
                mIndex++;
                manager = staffs.get(mIndex);
                staffs.get(i).manager = manager;
                manager.subordinates.add(staffs.get(i));
            } else {
                staffs.get(i).manager = manager;
                manager.subordinates.add(staffs.get(i));
            }
        }
    }
    /**
     * Find the shortest part between two employees and print it out.
     * @param emp ArrayList of Employee
     * @param s1 String Name employee 1
     * @param s2 String Name employee 2
     * */
    public static void findPath(ArrayList<Employee> emp
            , final String s1, final String s2) {
        //Total time complexity: O(NLogN)
        //assume that string input is not null.
        //this method handles the case of having employees with the same name.
        //array list of employee might not sorted but the connection
        //between employees has formed
        
        //find the list of employee with name: s1 & s2
        //Worst time complexity: O(N)
        ArrayList<Employee> list1 = new ArrayList<Employee>();
        ArrayList<Employee> list2 = new ArrayList<Employee>();
        for (int i = 0; i < emp.size(); i++) {
            if (s1.equalsIgnoreCase(emp.get(i).name.replaceAll("\\s", ""))) {
                list1.add(emp.get(i));
            }
            if (s2.equalsIgnoreCase(emp.get(i).name.replaceAll("\\s", ""))) {
                list2.add(emp.get(i));
            }
        }
        if (list1.size() == 1 && list2.size() == 1
                && list1.get(0) == list2.get(0)) {
            System.out.println("Only one employee found: "
                    + list1.get(0).name + " (" + list1.get(0).employeeID + ")");
            return;
        } else if (list1.size() == 0 || list2.size() == 0) {
            System.out.println("Unable to find employee");
            return;
        }
        //Finding two employees with the shortest connection
        //To handle the cases where employees share the same name
        //Worst time complexity: O(NLogN)
        int minDistance = Integer.MAX_VALUE;
        Employee staff1 = null;
        Employee staff2 = null;
        for (Employee e: list1) {
            //updating the value of A
            e.leastDistance = e;
            Employee temp = e;
            int value = 0;
            while (temp != null) {
                if (temp.distanceValue == -1) {
                    temp.distanceValue = value;
                    temp.leastDistance = e;
                } else if (temp.distanceValue > value) {
                    temp.distanceValue = value;
                    temp.leastDistance = e;
                }
                //else we don't update distanceValue
                value++;
                temp = temp.manager;
            }
        }
        //finding the least distance
        for (Employee e: list2) {
            Employee temp = e;
            int value = 0;
            while (temp != null) {
                if (minDistance > temp.distanceValue + value
                        && (temp.distanceValue != -1)
                        && temp.leastDistance != e) {
                    minDistance = (temp.distanceValue + value);
                    staff2 = e;
                    staff1 = temp.leastDistance;
                }
                value++;
                temp = temp.manager;
            }
        }
        //we get the value of staff one and two
        //find the common manager of each staff
        //Worst time complexity: O(logN)
        ArrayList<Employee> m1 = new ArrayList<Employee>();
        ArrayList<Employee> m2 = new ArrayList<Employee>();
        m1.add(staff1);
        m2.add(staff2);
        while (m1.get(m1.size() - 1) != m2.get(m2.size() - 1)) {
            if (m1.get(m1.size() - 1).managerID
                    > m2.get(m2.size() - 1).managerID) {
                m1.add(m1.get(m1.size() - 1).manager);
            } else if (m1.get(m1.size() - 1).managerID
                    < m2.get(m2.size() - 1).managerID) {
                m2.add(m2.get(m2.size() - 1).manager);
            } else {
                m1.add(m1.get(m1.size() - 1).manager);
                m2.add(m2.get(m2.size() - 1).manager);
            }
        }
        
        //printing the connection
        //Worst time complexity: O(logN)
        if (staff1 != null && staff2 != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(m1.get(0).name + " (" + m1.get(0).employeeID + ")");
            for (int i = 1; i < m1.size(); i++) {
                sb.append(" -> " + m1.get(i).name
                        + " (" + m1.get(i).employeeID + ")");
            }
            if (m2.size() - 2 >= 0) {
                for (int i = m2.size() - 2; i >= 0; i--) {
                    sb.append(" <- " + m2.get(i).name
                            + " (" + m2.get(i).employeeID + ")");
                }
            }
            System.out.println(sb.toString());
        } else {
            System.out.println("Unable to find employee");
        }
        
    } //end of method
    
    /**
     * Sorting ArrayList of Employee.
     * Implemented the comparator to sort the list.
     * @param employees Arraylist of Employees in the organization
     * */
    public static void sort(ArrayList<Employee> employees) {
        Collections.sort(employees, new Comparator<Employee>() {
            @Override
            public int compare(final Employee o1, final Employee o2) {
                if (o1.employeeID == o2.employeeID
                        && o1.employeeID == o2.managerID) {
                    return 0;
                }
                if (o1.managerID == o2.managerID) {
                    if (o1.employeeID > o2.employeeID) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
                if (o1.managerID > o2.managerID)  {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
    }
}


