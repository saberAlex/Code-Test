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
 * @version 1.2
 * @since  04/10/2014
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
    
    //Another attributes for the second implementation:
    private int distanceValue;
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
        ManagerID = m;
        EmployeeID = e;
        Name = n;
        Manager = null;
        Subordinates = new ArrayList<Employee>();
        
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
            Employee.formConnection(listOfEmployees);
            //Finding the connection between two employees
            //Worst time complexity: O(NLogN)
            Employee.findPath(listOfEmployees,connection[0],connection[1]);

        } catch (FileNotFoundException e1) {
            System.err.print("Invalid File Name.");
        } catch (NumberFormatException e1) {
            System.err.print("Invalid ID");
        } catch (IOException e1) {
            System.err.print("Unable to load file.");
        } catch(ArrayIndexOutOfBoundsException e1) {
            System.err.print("Invalid Argument: too many arguments");
        } catch (NullPointerException e1) {
            System.err.print("Invalid Argument");
        }
        

    }

    /**
     *Print the path connection between Employee.
     *@param path ArrayList connection between two employees
     * */
    public static void printPath(final ArrayList<Employee> path) {
        //Worst time complexity: O(N)
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
        //Worst Time Complexity: O(NLogN)
        sort(staffs);
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
        //Path finding based on Dijkstra Algorithm
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
     * Find the shortest part between two employees and print it out.
     * @param s1 String Name employee 1
     * @param s2 String Name employee 2
     * */
    public static void findPath(ArrayList<Employee> A, String s1, String s2) {
        //Total time complexity: O(NLogN)
        //assume that string input is not null.
        //to cater the worst case of having employees with the same name.
        //array list of employee might not sorted, however the connection has formed
        //find the list of employee with name: s1 & s2
        //Worst time complexity: O(N)
        ArrayList<Employee> list1 = new ArrayList<Employee>();
        ArrayList<Employee> list2 = new ArrayList<Employee>();
        for(int i = 0; i < A.size(); i++) {
            if (s1.equalsIgnoreCase(A.get(i).Name.replaceAll("\\s", "")) ) {
                list1.add(A.get(i));
            }
            if (s2.equalsIgnoreCase(A.get(i).Name.replaceAll("\\s", ""))) {
                list2.add(A.get(i));
            }
        }
        
        if(list1.size() == 1 && list2.size() == 1 && list1.get(0) == list2.get(0)) {
            System.out.println("Only one employee found: "+ list1.get(0).Name + " (" + list1.get(0).EmployeeID + ")");
            return;
        } else if (list1.size() == 0 || list2.size() == 0) {
            System.out.println("Unable to find employee");
            return;
        }

        //Finding two employees with the shortest connection
        //Worst time complexity: O(NLogN)
        int MinDistance = Integer.MAX_VALUE;
        Employee staff1 = null;
        Employee staff2 = null;
        for( Employee e: list1) {
            //updating the value of A
            e.leastDistance = e;
            Employee temp = e;
            int value = 0;
            while(temp != null) {
                if(temp.distanceValue == -1) {
                    temp.distanceValue = value;
                    temp.leastDistance = e;
                } else if (temp.distanceValue > value) {
                    temp.distanceValue = value;
                    temp.leastDistance = e;
                }
                //else we don't update distanceValue
                value++;
                temp = temp.Manager;
            }
        }
        //finding the least distance
        for ( Employee e: list2) {
            Employee temp = e;
            int value = 0;
            while (temp != null) {
                if( MinDistance > temp.distanceValue + value && (temp.distanceValue != -1) && temp.leastDistance != e) {
                    MinDistance = (temp.distanceValue + value);
                    staff2 = e;
                    staff1 = temp.leastDistance;
                }
                value++;
                temp = temp.Manager;
            }
        }
        //we get the value of staff one and two
        //find the common manager of each staff
        //Worst time complexity: O(logN)
        ArrayList<Employee> m1 = new ArrayList<Employee>();
        ArrayList<Employee> m2 = new ArrayList<Employee>();
        m1.add(staff1);
        m2.add(staff2);
        while (m1.get(m1.size()-1) != m2.get(m2.size()-1)) {
            if (m1.get(m1.size()-1).ManagerID > m2.get(m2.size()-1).ManagerID) {
                m1.add(m1.get(m1.size()-1).Manager);
            } else if (m1.get(m1.size()-1).ManagerID < m2.get(m2.size()-1).ManagerID) {
                m2.add(m2.get(m2.size()-1).Manager);
            } else {
                m1.add(m1.get(m1.size()-1).Manager);
                m2.add(m2.get(m2.size()-1).Manager);
            }
        }

        //printing the connection
        //Worst time complexity: O(logN)
        if (staff1 != null && staff2 != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(m1.get(0).Name + " (" + m1.get(0).EmployeeID + ")");
            for (int i = 1; i < m1.size(); i++) {
                sb.append(" -> "+m1.get(i).Name + " (" + m1.get(i).EmployeeID + ")");
            }
            if(m2.size()-2 >= 0) {
                for (int i = m2.size()-2; i >=0; i--) {
                    sb.append(" <- "+m2.get(i).Name + " (" + m2.get(i).EmployeeID + ")");
                }
            }
            System.out.println(sb.toString());
        } else {
            System.out.println("Unable to find employee");
        }
        
    }//end of method
    
    
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


