import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * BT Code Test.
 * This Employee class is the implementation of BT's programming task:
 * Organization Chart Traversal
 * Our main objective is to print out the name of all the people in the
 * chain between two employees
 *
 * @author Agastya Silvina
 * @version 1.0
 * @since  03/10/2014
 * */
public final class  Employee {
    private static final int EMPLOYEE_NAME = 2;
    private static final int EMPLOYEE_ID = 1;
    private static final int MANAGER_ID = 3;
    private static final int TOTAL_ATTRIBUTE = 4;
    private int ManagerID;
    private int EmployeeID;
    private String Name;
    private Employee Manager;
    private ArrayList<Employee> Subordinates;
    /**
     * ArrayList contains all information of employees within organization.
     * */
    private static ArrayList<Employee>
    listOfEmployees = new ArrayList<Employee>();

    /**
     * Employee Constructor.
     * @param m integer of manager ID
     * @param e integer of Employee ID
     * @param n String Employee's name
     * */
    private Employee(final int m, final int e, final String n) {
        ManagerID = m;
        EmployeeID = e;
        Name = n;
        Manager = null;
        Subordinates = new ArrayList<Employee>();
    }
    
    /**
     * Main method of the Employee class.
     * @param args input value from user
     * */
    public static void main(final String[] args)  {
        String fileName = args[0];
        String [] connection = new String[2];
        for (int i = 1; i < args.length; i++) {
            connection[i - 1] = args[i].replaceAll("\\s", "").toLowerCase();
        }

        BufferedReader reader;
        try {
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
            Employee.formConnection(listOfEmployees);
            //Search the Employee
            int index = 0;
            Employee[] staff = new Employee[2];
            boolean[] getValue = new boolean[2];
            for (Employee e: listOfEmployees) {
                if (connection[0].equalsIgnoreCase(e.Name.replaceAll("\\s", ""))
                        && !getValue[0]) {
                    staff[0] = e;
                    getValue[0] = true;
                    index++;
                    continue;
                }
                if (connection[1].equalsIgnoreCase(e.Name.replaceAll("\\s", ""))
                        && !getValue[1]) {
                    staff[1] = e;
                    getValue[1] = true;
                    index++;
                }

                if (index == 2)  {
                    break;
                }
            }
            if (staff[0] != null && staff[1] != null) {
                ArrayList<Employee> path = Employee.getPath(staff[0], staff[1]);
                Employee.printPath(path);
            } else {
                System.out.println("Unable to find Employee");
            }
        } catch (FileNotFoundException e1) {
            System.out.println("Invalid File Name.");
        } catch (NumberFormatException e1) {
            System.out.println("Invalid ID");
        } catch (IOException e1) {
            System.out.println("Unable to load file.");
        }
    }
    
    /**
     *Print the path connection between Employee.
     *@param path ArrayList connection between two employees
     * */
    public static void printPath(final ArrayList<Employee> path) {
        if (path == null) {
            System.out.println("Unable to Find Employee connection");
        }
        if (path.size() == 1) {
            System.out.println(path.get(0).Name);
        } else {
            System.out.print(path.get(0).Name
                    + " (" + path.get(0).EmployeeID + ") ");
            for (int i = 1; i < path.size(); i++) {
                if (path.get(i - 1).ManagerID > path.get(i).ManagerID) {
                    System.out.print("->" + " " + path.get(i).Name
                            + " (" + path.get(i).EmployeeID + ") ");
                } else {
                    System.out.print("<-" + " " + path.get(i).Name
                            + " (" + path.get(i).EmployeeID + ") ");
                }
            }
        }
    }
    
    /**
     * Forming connection within Employees.
     * Set the value of the manager or subordinates.
     * @param staffs ArrayList of Staff within company
     * */
    public static void formConnection(ArrayList<Employee> staffs) {
        sort(staffs); //Time Complexity: O(NLogN)
        Employee manager = staffs.get(0);
        int sId = staffs.get(1).ManagerID;
        int mIndex = 0;
        for (int i = 1; i < staffs.size(); i++) {
            if (staffs.get(i).ManagerID != sId) {
                sId =  staffs.get(i).ManagerID;
                mIndex++;
                manager = staffs.get(mIndex);
                staffs.get(i).Manager = manager;
                manager.Subordinates.add(staffs.get(i));
            } else {
                staffs.get(i).Manager = manager;
                manager.Subordinates.add(staffs.get(i));
            }
        }
    }
    /**
     * Print the connection within Employees (organizational Chart).
     * @param ceo Employee with highest rank
     * */
    public static void printConnection(final Employee ceo) {
        Queue<Employee> frontier = new LinkedList<Employee>();
        ArrayList<Employee> explored = new ArrayList<Employee>();
        frontier.add(ceo);
        explored.add(ceo);
        System.out.println("Name: " + ceo.Name + "ManagerId: " + ceo.ManagerID);
        while (!frontier.isEmpty()) {
            Employee current = frontier.poll();
            for (Employee sub : current.Subordinates) {
                if (explored.contains(sub)) {
                    continue;
                }
                System.out.println("Name:" + sub.Name);
                frontier.add(sub);
                explored.add(sub);
            }
        }
    }
    /**
     * Getting the path connection between two employees.
     * @param start Employee
     * @param finish Employee
     * @return ArrayList of Employee
     * */
    public static  ArrayList<Employee> getPath(final Employee start,
            final Employee finish) {
        HashMap<Employee, ArrayList<Employee>> paths =
                new HashMap<Employee, ArrayList<Employee>>();
        Queue<Employee> frontier = new LinkedList<Employee>();
        ArrayList<Employee> p = new ArrayList<Employee>();
        p.add(start);
        paths.put(start, p);
        frontier.add(start);
        if (start.equals(finish)) {
            return p;
        }
        while (!frontier.isEmpty()) {
            Employee current = frontier.poll();
            if (!paths.containsKey(current.Manager)
                    && current.Manager != null) {
                frontier.add(current.Manager);
                ArrayList<Employee> path =
                        (ArrayList<Employee>) paths.get(current).clone();
                path.add(current.Manager);
                if (current.Manager.equals(finish)) {
                    return path;
                }
                paths.put(current.Manager, path);
            }
            for (Employee e: current.Subordinates) {
                if (paths.containsKey(e)) {
                    continue;
                }
                frontier.add(e);
                ArrayList<Employee> path =
                        (ArrayList<Employee>) paths.get(current).clone();
                path.add(e);
                if (e.equals(finish)) {
                    return path;
                }
                paths.put(e, path);
            }
        }
        return null;
    }
    
    /**
     * Sorting ArrayList of Employee.
     * Implemented the comparator to sort the list.
     * @param employees
     * */
    public static void sort(ArrayList<Employee> employees) {
        Collections.sort(employees, new Comparator<Employee>() {
            @Override
            public int compare(final Employee o1, final Employee o2) {
                if (o1.EmployeeID == o2.EmployeeID
                        && o1.EmployeeID == o2.ManagerID) {
                    return 0;
                }
                if (o1.ManagerID == o2.ManagerID) {
                    if (o1.EmployeeID > o2.EmployeeID) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
                if (o1.ManagerID > o2.ManagerID)  {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
    }
}


