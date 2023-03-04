package project1;

import java.util.Scanner;
import java.util.StringTokenizer;
/**
 * Reads all user input and runs valid commands until terminated.
 * Valid commands, besides termination command, are performed by Roster class.
 * Commands include Add, Remove, Print roster in a Roster as well as Termination.
 * @author Sharukh Khan, Hamad Naveed
 */

public class RosterManager {
    public static final int COUNT = 5;
    public static final int COUNT1 = 3;
    public static final int COUNT2 = 4;

    /**
     * Reads through user input and performs valid commands until terminated.
     * If command is valid StringTokenizer separates the user input line into tokens for readability.
     * The appropriate method is called to perform the command.
     * While loop terminated by Q command.
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        Roster roster = new Roster();
        System.out.println("Roster Manager running...");
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            StringTokenizer input = new StringTokenizer(command);
            if (input.hasMoreTokens()) {
                switch (input.nextToken()) {
                    case "A":
                        addStudent(roster, input);
                        break;
                    case "R":
                        removeStudentFromRoster(roster, input);
                        break;
                    case "P":
                        roster.print();
                        break;
                    case "PS":
                        roster.printByStanding();
                        break;
                    case "PC":
                        roster.printBySchoolMajor();
                        break;
                    case "L":
                        listBySchool(roster, input);
                        break;
                    case "C":
                        changeMajor(roster, input);
                        break;
                    case "Q":
                        endRoasterManager();
                        break;
                    default:
                        System.out.println(command + " is a invalid command!");
                }
            }
        }
    }

    /**
     * Creates a student object to be added to the Roster class roster array.
     * Method sends student input to buildStudent method.
     * If input is invalid the output is the appropriate statement.
     * If student is valid and not already in the student it gets added to the array in the Roster class and the appropriate statement is output.
     * If student is already in the Roster - student does not get added to the array and the appropriate statement is output.
     *
     * @param roster object that correlates to the array
     * @param input  user student input
     */
    private void addStudent(Roster roster, StringTokenizer input) {
        Student studentToBeAdded = buildStudent(input);
        if (studentToBeAdded == null) {
        } else if (roster.add(studentToBeAdded)) {
            System.out.println(studentToBeAdded.getProfile() + " added to the roster.");
        } else {
            System.out.println(studentToBeAdded.getProfile() + " is already in the roster.");
        }
    }

    /**
     * Removes student from the Roster class array.
     * If student exist in the array it is deleted and the appropriate statement is printed.
     * If student is not in the array the appropriate statement is printed.
     *
     * @param roster object that correlates to the array
     * @param input  user student input
     */
    private void removeStudentFromRoster(Roster roster, StringTokenizer input) {
        Student studentToBeDeleted = buildStudent(input);
        if (studentToBeDeleted == null) {
        } else if (roster.remove(studentToBeDeleted)) {
            System.out.println(studentToBeDeleted.getProfile() + " removed from the roster.");
        } else {
            System.out.println(studentToBeDeleted.getProfile() + " is not in the roster.");
        }
    }

    /**
     * Creates student object using user input.
     *
     * @param input user student input.
     * @return the student object if it is valid and created otherwise returns null.
     */
    private Student buildStudent(StringTokenizer input) {
        if (input.countTokens() == COUNT) {
            String fname = input.nextToken();
            String lname = input.nextToken();
            Date dob = new Date(input.nextToken());
            if (!dob.isValid()) {
                return null;
            }
            Profile profile = new Profile(lname, fname, dob);
            Major major = getMajor(input.nextToken());

            if (major == null) {
                return null;
            }

            int creditCompleted;
            String creditInput = input.nextToken();
            boolean isNegative = creditInput.startsWith("-");
            boolean isInteger = true;
            for (int i = isNegative ? 1 : 0; i < creditInput.length(); i++) {
                if (!Character.isDigit(creditInput.charAt(i))) {
                    isInteger = false;
                    break;
                }
            }
            if (!isInteger) {
                System.out.println("Credits completed invalid: not an integer!");
                return null;
            }
            creditCompleted = Integer.parseInt(creditInput);
            if (isNegative) {
                System.out.println("Credits completed invalid: cannot be negative!");
                return null;
            }
            return new Student(profile, major, creditCompleted);

        } else if (input.countTokens() == COUNT1) {
            String fname = input.nextToken();
            String lname = input.nextToken();
            Date dob = new Date(input.nextToken());
            if (!dob.isValid()) {
                return null;
            }
            Profile profile = new Profile(lname, fname, dob);
            return new Student(profile);

        } else if (input.countTokens() == COUNT2) {
            String fname = input.nextToken();
            String lname = input.nextToken();
            Date dob = new Date(input.nextToken());
            if (!dob.isValid()) {
                return null;
            }
            Profile profile = new Profile(lname, fname, dob);
            Major major = getMajor(input.nextToken());

            if (major == null) {
                return null;
            }
            return new Student(profile, major);
        }
        return null;
    }

    public void listBySchool(Roster roster, StringTokenizer input) {
        String schoolName = input.nextToken();
        schoolName = schoolName.toUpperCase();

        if(schoolName.equals("SAS") || schoolName.equals("SC&I")
                || schoolName.equals("RBS") || schoolName.equals("SOE")) {
            System.out.println("* Students in " + schoolName + " *");
            for (Student student : roster.getRoster()) {
                if (student != null && student.getMajor().getSchoolName().equals(schoolName)) {
                    System.out.println(student);
                }
            }
        }else{
            System.out.println("School doesn't exist: " + schoolName);
            return;
        }
        System.out.println("* end of list **");
    }

    /**
     * Allows user to enter major in lower-case and return the correlating Major type.
     * @param major string input.
     * @return the major data type.
     */
    private Major getMajor(String major) {
        switch (major.toLowerCase()) {
            case "cs":
                return Major.CS;
            case "math":
                return Major.MATH;
            case "iti":
                return Major.ITI;
            case "bait":
                return Major.BAIT;
            case "ee":
                return Major.EE;
            default:
                System.out.println("Major code invalid: " + major);
                return null;
        }
    }

    /**
     * Removes student from the Roster class array.
     * If student exist in the array it's major is changed
     * If student is not in the array the appropriate statement is printed.
     * @param roster object that correlates to the array
     * @param input  user student input
     */
    private void changeMajor(Roster roster, StringTokenizer input) {
        Student studentToChangeMajor = buildStudent(input);
        if (studentToChangeMajor == null) {

        } else if (studentToChangeMajor != null) {
            String newMajor = String.valueOf(studentToChangeMajor.getMajor().name());
            if (roster.changeMajorCode(studentToChangeMajor, String.valueOf(newMajor))) {
                System.out.println(studentToChangeMajor.getProfile() + " major changed to " + newMajor);
            } else {
                System.out.println(studentToChangeMajor.getProfile() + " is not in the roster.");
            }
        }

    }

    /**
     * Terminates program and prints termination statement.
     */
    private static void endRoasterManager() {
        System.out.println("This line is a garbage line to test the Q command.");
        System.exit(0);
    }

}