import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TestEmployee {

    private static String fileName;
    private static String employee1;
    private static String employee2;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();


    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }



    @Test
    public void testInvalidFileName() {
        fileName = "./res/staffs_invalid.txt";
        String[] args = { fileName, " ", " " };
        Employee.main(args);
        assertEquals("Invalid File Name.", errContent.toString().trim());
    }
    
    @Test
    public void testInvalidID() {
        fileName = "./res/invalidEmployee.txt";
        String[] args = { fileName, "batman", "cat Woman" };
        Employee.main(args);
        assertEquals("Invalid ID", errContent.toString().trim());
    }
    
    @Test
    public void exampleOutput1() {
        fileName = "./res/staffs.txt";
        String[] args = { fileName, "Batman", "Super Ted" };
        Employee.listOfEmployees.clear();
        Employee.main(args);
        assertEquals("Batman (16) -> Black Widow (6) -> Gonzo the Great (2) -> Dangermouse (1) <- Invisible Woman (3) <- Super Ted (15)",outContent.toString().trim());
    }
    
    @Test
    public void exampleOutput2() {
        String[] args = { "./res/staffs.txt" ,  "batman",  "catwoman" };
        Employee.listOfEmployees.clear();
        Employee.main(args);
        assertEquals("Batman (16) -> Black Widow (6) <- Catwoman (17)",outContent.toString().trim());
    }

    @Test
    public void testAllEmployeesWithSameName() {
        String[] args = { "./res/staffs2.txt" ,  "Dangermouse",  "Dangermouse" };
        Employee.listOfEmployees.clear();
        Employee.main(args);
        assertEquals("Dangermouse (1) <- Dangermouse (2)",outContent.toString().trim());
    }
    
    @Test
    public void testFindingEmployeeshortestPath() {
        String[] args = { "./res/staffs3.txt" ,  "batman",  "batman" };
        Employee.listOfEmployees.clear();
        Employee.main(args);
        assertEquals("Batman (12) -> Invisible Woman (3) <- Batman (15)",outContent.toString().trim());
    }

    @Test
    public void testCompareName() {
        fileName = "./res/staffs.txt";
        String[] args = { fileName, "Bat     man", "SUPER TeD" };
        Employee.listOfEmployees.clear();
        Employee.main(args);
        assertEquals("Batman (16) -> Black Widow (6) -> Gonzo the Great (2) -> Dangermouse (1) <- Invisible Woman (3) <- Super Ted (15)",outContent.toString().trim());
    }
    
    @Test
    public void testOnlyFindOneValidEmployee() {
        fileName = "./res/staffs.txt";
        String[] args = { fileName, "Batman", "cute" };
        Employee.listOfEmployees.clear();
        Employee.main(args);
        assertEquals("Unable to find employee",outContent.toString().trim());
    }
    
    @Test
    public void testOnlyFindOneEmployeeinBothString() {
        fileName = "./res/staffs.txt";
        String[] args = { fileName, "Batman", "batman" };
        Employee.listOfEmployees.clear();
        Employee.main(args);
        assertEquals("Only one employee found: Batman (16)",outContent.toString().trim());

    }

    @Test
    public void testSubordinateMoreThanOne() {
        fileName = "./res/subordinatesMoreThan2.txt";
        String[] args = { fileName, "Batman", "CatWoman" };
        Employee.listOfEmployees.clear();
        Employee.main(args);
        assertEquals("Batman (16) -> Gundam (4) <- Catwoman (17)",outContent.toString().trim());

    }

}
