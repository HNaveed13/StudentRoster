package project1;
/**
 * Collects and manages Student objects in roster array.
 * Performs functions called by RosterManager class.
 * Functions include adding and removing Student objects from the array,
 * and printing the array roster in no specified order, by student major, and or by student standing
 * @author Sharukh Khan, Hamad Naveed
 */

public class Roster {
    private Student[] roster;
    private int size;
    private static final int NOT_FOUND = -1;

    /**
     * Roster object array created.
     */
    public Roster() {
        this.roster = new Student[4];
        this.size = 0;
    }

    /**
     * Returns number of size in roster array.
     * @return number of size.
     */
    public int getSize() {return size;}

    /**
     * Returns roster array.
     * @return roster.
     */
    public Student[] getRoster() { return roster; }

    /**
     * Finds if a student already exists in array roster.
     * @param student object being searched for.
     * @return index of where the student object is in the array.
     */
    private int find(Student student) {
        for (int i = 0; i < size; i++) {
            if (roster[i].equals(student))
                return i;
        }
        return NOT_FOUND;
    }

    /**
     * Grows array length by 4 if array is full.
     */
    private void grow() {
        Student[] tmp = new Student[size + 4];
        for (int i = 0; i < size; i++) {
            tmp[i] = roster[i];
        }
        roster = tmp;
    }

    /**
     * Adds student object to roster array.
     * @param student object to be added.
     * @return false if student is not added, true if it is added.
     */
    public boolean add(Student student) {
        if (contains(student)) {
            return false;
        }
        if (size == roster.length)
            grow();
        roster[size++] = student;
        return true;
    }

    /**
     * Removes student from array if it exists in the array.
     * @param student Student object to be deleted
     * @return false if student is not in array, true if it exists and is removed.
     */
    public boolean remove(Student student) {
        int index = find(student);
        if (index == NOT_FOUND) {
            return false;
        }
        for (int i = index; i < size - 1; i++) {
            roster[i] = roster[i + 1];
        }
        size--;
        return true;
    }

    /**
     * Checks to see if there is any
     * @param student object to be deleted.
     * @return false if student is not in array, true if it exists and is removed.
     */
    public boolean contains(Student student) {
        return find(student) != NOT_FOUND;
    }

    /**
     * Prints all student in array, if array is not empty, sorted by last name, first name, DOB.
     */
    public void print () {
        if (size != 0) {
            System.out.println("* Student roster sorted by last name, first name, DOB **");
            for (Student student : roster)
                if (student != null)
                    System.out.println(student);
            System.out.println("* end of roster **");
        } else {
            System.out.println("Student roster is empty!");
        }
    }

    /**
     * Prints all student in array, if array is not empty, in order of major.
     */
    public void printBySchoolMajor() {
        if (size == 0) {
            System.out.println("The roster is empty!");
            return;
        }
        Student[] sort = new Student[size];
        int numStudents = 0;
        for (Major major : Major.values()) {
            for (Student student : roster) {
                if (student != null && student.getMajor() == major) {
                    sort[numStudents++] = student;
                }
            }
        }
        System.out.println("* Student roster sorted by school, major **");
        for (int i = 0; i < numStudents; i++) {
            System.out.println(sort[i]);
        }
        System.out.println("* end of roster **");
    }

    /**
     * Prints all student in array, if array is not empty, in order of standing.
     */
    public void printByStanding() {
        for (int i = 0; i < size - 1; i++) {
            int swapIndex = i;
            for (int j = i + 1; j < size; j++) {
                if (roster[j] != null && roster[i] != null) {
                    if (compareStanding(roster[j].getStanding(), roster[swapIndex].getStanding()) < 0) {
                        swapIndex = j;
                    }
                }
            }
            Student tmpStudent = roster[swapIndex];
            roster[swapIndex] = roster[i];
            roster[i] = tmpStudent;
        }
        if (size != 0) {
            System.out.println("* Student roster sorted by standing *");
            for (int i = 0; i < size; i++) {
                if (roster[i] != null) {
                    System.out.println(roster[i]);
                }
            }
            System.out.println("* end of roster **");
        } else {
            System.out.println("The roster is empty!");
        }
    }

    /**
     * compare Standing
     */
    private int compareStanding(String s1, String s2) {
        if (s1.equals("Senior")) {
            if (s2.equals("Senior")) {
                return 0;
            } else {
                return 1;
            }
        } else if (s1.equals("Junior")) {
            if (s2.equals("Senior")) {
                return -1;
            } else if (s2.equals("Junior")) {
                return 0;
            } else {
                return 1;
            }
        } else if (s1.equals("Sophomore")) {
            if (s2.equals("Freshman")) {
                return -1;
            } else if (s2.equals("Sophomore")) {
                return 0;
            } else {
                return 1;
            }
        } else {
            if (s2.equals("Freshman")) {
                return 0;
            } else {
                return -1;
            }
        }
    }

    /**
     * Changes the major code of a student in the roster
     * @param student The student whose major code needs to be changed
     * @param newMajor The new major code for the student
     * @return true if the major code was changed successfully, false otherwise
     */
    public boolean changeMajorCode(Student student, String newMajor) {
        int index = find(student);
        if (index == NOT_FOUND) {
            return false;
        }

        roster[index].setMajor(Major.valueOf(newMajor));
        return true;
    }
}
