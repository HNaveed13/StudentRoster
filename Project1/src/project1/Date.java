package project1;

import java.util.Calendar;
import java.util.StringTokenizer;
/**
 * Checks if DOB object dates are valid.
 * Dates are declared invalid if they do not follow the mm/dd/yyyy format, are a false leap year,
 * go beyond today's date, age < 16, and or have invalid months, days, or years.
 * @author Sharukh Khan, Hamad Naveed
 */
public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;

    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;
    public static final int FEBRUARY_LEAP_YEAR_MONTH_END = 29;
    public static final int MONTH_START = 1;
    public static final int MONTH_END = 31;
    public static final int MIN_AGE = 16;

    /**
     * Creates Date object using user input date.
     * @param date entered by user.
     */
    public Date(String date) {
        StringTokenizer st = new StringTokenizer(date, "/");
        month = Integer.parseInt(st.nextToken());
        day = Integer.parseInt(st.nextToken());
        year = Integer.parseInt(st.nextToken());
    }

    /**
     * Creates date object using today's date.
     */
    public Date() {
        Calendar rightNow = Calendar.getInstance();
        month = rightNow.get(Calendar.MONTH) + 1;
        day = rightNow.get(Calendar.DAY_OF_MONTH);
        year = rightNow.get(Calendar.YEAR);
    }

    /**
     * Creates date object using pre-existing date object information.
     * @param date object created from user input.
     */
    public Date(Date date) {
        month = date.getMonth();
        day = date.getDay();
        year = date.getYear();
    }


    /**
     * Method returns date object of today's date.
     * @return new date object.
     */
    public Date today() {
        return new Date();
    }

    /**
     * Checks if date is valid.
     * Checks if student's age is <16
     * For leap year dates calls isLeapYear method to validate the date.
     * @return false if date is invalid or age < 16, true if date is valid
     */
    public boolean isValid() {
        Calendar studentDate = Calendar.getInstance();
        studentDate.set(year, month - 1, day);
        Calendar currentDate = Calendar.getInstance();
        int age = currentDate.getWeekYear() - year;
        String studentDateString = String.format("%02d/%02d/%04d", month, day, year);
        if ((month > currentDate.get(Calendar.MONTH))
                || (month == currentDate.get(Calendar.MONTH) &&
                day > currentDate.get(Calendar.DAY_OF_MONTH))){
            age--;
        }
        if (studentDate.after(currentDate) || (age < MIN_AGE)){
            System.out.println("DOB invalid: " + studentDateString + " younger than 16 years old.");
            return false;
        }

        if (day > MONTH_END || day < MONTH_START) {
            System.out.println("DOB invalid: 2/29/2003 not a valid calendar date!");
            return false;
        }

        switch (month - 1) {
            case Calendar.JANUARY:
            case Calendar.MARCH:
            case Calendar.MAY:
            case Calendar.OCTOBER:
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.DECEMBER:
                if (day <= MONTH_END) {
                    return true;
                } else {
                    System.out.println("DOB invalid: 2/29/2003 not a valid calendar date!");
                    return false;
                }
            case Calendar.APRIL:
            case Calendar.JUNE:
            case Calendar.SEPTEMBER:
            case Calendar.NOVEMBER:
                if (day != MONTH_END) {
                    return true;
                } else {
                    System.out.println("DOB invalid: " +  studentDateString + " not a valid calendar date!");
                    return false;
                }
            case Calendar.FEBRUARY:
                if (day > FEBRUARY_LEAP_YEAR_MONTH_END) {
                    System.out.println("DOB invalid: " +  studentDateString + " not a valid calendar date!");
                    return false;
                } else if (isLeapYear()) {
                    if (day <= FEBRUARY_LEAP_YEAR_MONTH_END) {
                        return true;
                    } else {
                        System.out.println("DOB invalid: " +  studentDateString + " not a valid calendar date!");
                        return false;
                    }
                } else {
                    if (day < FEBRUARY_LEAP_YEAR_MONTH_END) {
                        return true;
                    } else {
                        System.out.println("DOB invalid: " +  studentDateString + " not a valid calendar date!");
                        return false;
                    }
                }
            default:
                System.out.println("DOB invalid: " +  studentDateString + " not a valid calendar date!");
                return false;
        }
    }

    /**
     * Verifies if the user date is a valid leap year date.
     * @return true if date is valid a leap year date, false if date is an invalid leap year date.
     */
    public boolean isLeapYear() {
        if (year % QUATERCENTENNIAL == 0) {
            return false;
        } else if (year % QUADRENNIAL == 0 || year % CENTENNIAL == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * String formed by combining month, day, and year into date format.
     */
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return month + "/" + day + "/" + year;
    }

    /**
     * Returns date object's day.
     * @return day in string form.
     */
    public int getDay() {
        return day;
    }

    /**
     * Returns date object/s month.
     * @return month in string form.
     */
    public int getMonth() {
        return month;
    }

    /**
     * Returns date object's year.
     * @return year in string form.
     */
    public int getYear() {
        return year;
    }


    /**
     * Compares this date to another date.
     * @param other The date to compare to.
     * @return A negative integer, zero, .
     */
    @Override
    public int compareTo(Date other) {
        if (this.year != other.year) {
            return this.year - other.year;
        } else if (this.month != other.month) {
            return this.month - other.month;
        } else {
            return this.day - other.day;
        }
    }

    /**
     * Compares date objects.
     * @param o
     * @returns true if date objects are equal, returns false if they are different.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Date)) {
            return false;
        }
        Date other = (Date) o;
        return this.year == other.year && this.month == other.month && this.day == other.day;
    }

    /**
     * Main method holds test mains.
     * @param args test mains.
     */
    public static void main(String[] args){
        // Test Case #1
        System.out.println("Running Test Case #1:");
        Date testCase1 = new Date("2/29/2003");
        if (testCase1.isValid()) {
            System.out.println("Test Case #1: " + testCase1 + " valid calendar date! Passed");
        } else {
            System.out.println("Test Case #1: " + testCase1 + " not a valid calendar date! Failed");
        }

        // Test Case #2
        System.out.println("Running Test Case #2:");
        Date testCase2 = new Date("4/31/2003");
        if (testCase2.isValid()) {
            System.out.println("Test Case #2: " + testCase2 + " valid calendar date! Passed");
        } else {
            System.out.println("Test Case #2: " + testCase2 + " not a valid calendar date! Failed");
        }

        // Test Case #3
        System.out.println("Running Test Case #3:");
        Date testCase3 = new Date("13/31/2003");
        if (testCase3.isValid()) {
            System.out.println("Test Case #3: " + testCase3 + " valid calendar date! Passed");
        } else {
            System.out.println("Test Case #3: " + testCase3 + " not a valid calendar date! Failed");
        }

        // Test Case #4
        System.out.println("Running Test Case #4:");
        Date testCase4 = new Date("3/32/2003");
        if (testCase4.isValid()) {
            System.out.println("Test Case #4: " + testCase4 + " valid calendar date! Passed");
        } else {
            System.out.println("Test Case #4: " + testCase4 + " not a valid calendar date! Failed");
        }

        // Test Case #5
        System.out.println("Running Test Case #5:");
        Date testCase5 = new Date("-1/31/2003");
        if (testCase5.isValid()) {
            System.out.println("Test Case #5: " + testCase5 + " valid calendar date! Passed");
        } else {
            System.out.println("Test Case #5: " + testCase5 + " not a valid calendar date! Failed");
        }

        // Test Case #6
        System.out.println("Running Test Case #6:");
        Date testCase6 = new Date("4/3/2003");
        if (testCase6.isValid()) {
            System.out.println("Test Case #6: " + testCase6 + " valid calendar date! Passed");
        } else {
            System.out.println("Test Case #6: " + testCase6 + " not a valid calendar date! Failed");
        }

        // Test Case #7
        System.out.println("Running Test Case #7:");
        Date testCase7 = new Date("1/20/2003");
        if (testCase7.isValid()) {
            System.out.println("Test Case #7: " + testCase7 + " valid calendar date! Passed");
        } else {
            System.out.println("Test Case #7: " + testCase7 + " not a valid calendar date! Failed");
        }

    }

}
