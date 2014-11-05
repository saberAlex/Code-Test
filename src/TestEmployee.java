import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * TestEmployee, The test cases and test data for Employee.java.
 * @author Agastya Silvina
 * @version 1.0
 * */
public class TestEmployee {

    /**
     * input filename.
     * (required) Specifies the path
     * */
    private static String fileName;
    /**
     * variable to keep the console output.
     * */
    private final ByteArrayOutputStream
    outContent = new ByteArrayOutputStream();
    /**
     * variable to keep the console error output.
     * */
    private final ByteArrayOutputStream
    errContent = new ByteArrayOutputStream();

    /**
     * Set up stream to get the value of console output.
     * */
    @Before
    public final void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    /**
     * Reset the value of outContent and errContent.
     * */
    @After
    public final void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }



    /**
     * Testing invalid input file.
     * */
    @Test
    public final void testInvalidFileName() {
        fileName = "./res/staffs_invalid.txt";
        String[] args = {fileName, " ", " "};
        Employee.main(args);
        assertEquals("Invalid File Name.", errContent.toString().trim());
    }
    
    /**
     * Testing of data consists of invalid ID
     * */
    @Test
    public final void testInvalidID() {
        fileName = "./res/invalidEmployee.txt";
        String[] args = {fileName, "batman", "cat Woman"};
        Employee.main(args);
        assertEquals("Invalid ID", errContent.toString().trim());
    }
    
    /**
     * re-testing the example test cases.
     * */
    @Test
    public final void exampleOutput1() {
        fileName = "./res/staffs.txt";
        String[] args = {fileName, "Batman", "Super Ted"};
        Employee.listOfEmployees.clear();
        Employee.main(args);
        assertEquals("Batman (16) -> Black Widow (6) -> Gonzo the Great (2) "
                + "-> Dangermouse (1) <- Invisible Woman (3) <- Super Ted (15)"
                , outContent.toString().trim());
    }
    
    /**
     * re-testing the example test cases.
     * */
    @Test
    public final void exampleOutput2() {
        String[] args = {"./res/staffs.txt" ,  "batman",  "catwoman"};
        Employee.listOfEmployees.clear();
        Employee.main(args);
        assertEquals("Batman (16) -> Black Widow (6) "
                + "<- Catwoman (17)", outContent.toString().trim());
    }

    /**
     * Test cases for input file consist of all employees share the same name.
     * */
    @Test
    public final void testAllEmployeesWithSameName() {
        String[] args = {"./res/staffs2.txt" ,  "Dangermouse",  "Dangermouse"};
        Employee.listOfEmployees.clear();
        Employee.main(args);
        assertEquals("Dangermouse (1) <- Dangermouse (2)"
                , outContent.toString().trim());
    }
    
    /**
     * Choosing shortest path for employees who share the same name.
     * */
    @Test
    public final void testFindingEmployeeshortestPath() {
        String[] args = {"./res/staffs3.txt" ,  "batman",  "batman"};
        Employee.listOfEmployees.clear();
        Employee.main(args);
        assertEquals("Batman (12) -> Invisible Woman (3) <- Batman (15)"
                , outContent.toString().trim());
    }

    /**
     * Test cases for all possible variant of input string name.
     * */
    @Test
    public final void testCompareName() {
        fileName = "./res/staffs.txt";
        String[] args = {fileName, "Bat     man", "SUPER TeD"};
        Employee.listOfEmployees.clear();
        Employee.main(args);
        assertEquals("Batman (16) -> Black Widow (6) -> Gonzo the Great (2) "
                + "-> Dangermouse (1) <- Invisible Woman (3) <- Super Ted (15)"
                , outContent.toString().trim());
    }
    
    /**
     * Test cases for having only one valid employee's name in the list.
     * */
    @Test
    public final void testOnlyFindOneValidEmployee() {
        fileName = "./res/staffs.txt";
        String[] args = {fileName, "Batman", "cute"};
        Employee.listOfEmployees.clear();
        Employee.main(args);
        assertEquals("Unable to find employee"
                , outContent.toString().trim());
    }
    
    /**
     * Test cases for having only one employee found.
     * */
    @Test
    public final void testOnlyFindOneEmployeeinBothString() {
        fileName = "./res/staffs.txt";
        String[] args = {fileName, "Batman", "batman"};
        Employee.listOfEmployees.clear();
        Employee.main(args);
        assertEquals("Only one employee found: Batman (16)"
                , outContent.toString().trim());

    }

    /**
     * Test cases for having more than two subordinates.
     * */
    @Test
    public final void testSubordinateMoreThanTwo() {
        fileName = "./res/subordinatesMoreThan2.txt";
        String[] args = {fileName, "Batman", "CatWoman"};
        Employee.listOfEmployees.clear();
        Employee.main(args);
        assertEquals("Batman (16) -> Gundam (4) <- Catwoman (17)"
                , outContent.toString().trim());

    } //end of test
}
