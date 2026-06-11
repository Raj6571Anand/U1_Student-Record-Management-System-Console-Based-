import java.util.Scanner;

/**
 * Student Record Management System
 * 
 * A console-based Java application that manages student records using arrays.
 * Supports adding, displaying, searching, and calculating average marks.
 * 
 * @author Student
 */
public class StudentRecordManagement {

    // Maximum number of students the system can hold
    static final int MAX_STUDENTS = 100;

    // Parallel arrays to store student details
    static int[] studentIds = new int[MAX_STUDENTS];
    static String[] studentNames = new String[MAX_STUDENTS];
    static int[] studentAges = new int[MAX_STUDENTS];
    static String[] studentDepartments = new String[MAX_STUDENTS];
    static double[] studentMarks = new double[MAX_STUDENTS];

    // Tracks how many students are currently stored
    static int studentCount = 0;

    // Scanner for reading user input
    static Scanner scanner = new Scanner(System.in);

    // =========================================================================
    // Main Method – Entry Point
    // =========================================================================

    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("   STUDENT RECORD MANAGEMENT SYSTEM");
        System.out.println("==============================================");

        boolean isRunning = true;

        while (isRunning) {
            displayMenu();
            int choice = readIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    displayAllStudents();
                    break;
                case 3:
                    searchStudentById();
                    break;
                case 4:
                    calculateAverageMarks();
                    break;
                case 5:
                    isRunning = false;
                    System.out.println("\nThank you for using the Student Record Management System.");
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("\n[ERROR] Invalid choice. Please enter a number between 1 and 5.\n");
            }
        }

        scanner.close();
    }

    // =========================================================================
    // Menu Display
    // =========================================================================

    /**
     * Displays the main menu options to the user.
     */
    static void displayMenu() {
        System.out.println("\n----------------------------------------------");
        System.out.println("                  MAIN MENU                   ");
        System.out.println("----------------------------------------------");
        System.out.println("  1. Add New Student");
        System.out.println("  2. Display All Students");
        System.out.println("  3. Search Student by ID");
        System.out.println("  4. Calculate Average Marks");
        System.out.println("  5. Exit");
        System.out.println("----------------------------------------------");
    }

    // =========================================================================
    // 1. Add a New Student
    // =========================================================================

    /**
     * Collects student details from the user and stores them in the arrays.
     * Validates that the student ID is unique and the array is not full.
     */
    static void addStudent() {
        System.out.println("\n--- Add New Student ---\n");

        // Check if the array is full
        if (studentCount >= MAX_STUDENTS) {
            System.out.println("[ERROR] Cannot add more students. Maximum capacity (" + MAX_STUDENTS + ") reached.");
            return;
        }

        // Read and validate Student ID
        int id = readIntInput("Enter Student ID : ");

        if (isIdDuplicate(id)) {
            System.out.println("[ERROR] A student with ID " + id + " already exists. Please use a unique ID.");
            return;
        }

        // Read remaining details
        System.out.print("Enter Student Name       : ");
        String name = scanner.nextLine().trim();

        int age = readIntInput("Enter Student Age        : ");
        if (age <= 0 || age > 150) {
            System.out.println("[ERROR] Invalid age. Please enter a realistic age.");
            return;
        }

        System.out.print("Enter Department         : ");
        String department = scanner.nextLine().trim();

        double marks = readDoubleInput("Enter Marks (0 - 100)    : ");
        if (marks < 0 || marks > 100) {
            System.out.println("[ERROR] Marks must be between 0 and 100.");
            return;
        }

        // Store in parallel arrays
        studentIds[studentCount] = id;
        studentNames[studentCount] = name;
        studentAges[studentCount] = age;
        studentDepartments[studentCount] = department;
        studentMarks[studentCount] = marks;
        studentCount++;

        System.out.println("\n[SUCCESS] Student \"" + name + "\" (ID: " + id + ") added successfully!");
    }

    // =========================================================================
    // 2. Display All Students
    // =========================================================================

    /**
     * Prints all stored student records in a formatted table.
     */
    static void displayAllStudents() {
        System.out.println("\n--- All Student Records ---\n");

        if (studentCount == 0) {
            System.out.println("[INFO] No student records found. Add students first.");
            return;
        }

        printTableHeader();

        for (int i = 0; i < studentCount; i++) {
            printStudentRow(i);
        }

        printTableFooter();
        System.out.println("Total Students: " + studentCount);
    }

    // =========================================================================
    // 3. Search Student by ID
    // =========================================================================

    /**
     * Searches for a student by their ID and displays the record if found.
     */
    static void searchStudentById() {
        System.out.println("\n--- Search Student by ID ---\n");

        if (studentCount == 0) {
            System.out.println("[INFO] No student records found. Add students first.");
            return;
        }

        int searchId = readIntInput("Enter Student ID to search: ");
        int index = findStudentIndexById(searchId);

        if (index == -1) {
            System.out.println("\n[NOT FOUND] No student found with ID: " + searchId);
        } else {
            System.out.println("\n[FOUND] Student record:\n");
            printTableHeader();
            printStudentRow(index);
            printTableFooter();
        }
    }

    // =========================================================================
    // 4. Calculate Average Marks
    // =========================================================================

    /**
     * Calculates and displays the average marks of all students,
     * along with the highest and lowest scoring students.
     */
    static void calculateAverageMarks() {
        System.out.println("\n--- Average Marks Calculation ---\n");

        if (studentCount == 0) {
            System.out.println("[INFO] No student records found. Add students first.");
            return;
        }

        double totalMarks = 0;
        int highestIndex = 0;
        int lowestIndex = 0;

        for (int i = 0; i < studentCount; i++) {
            totalMarks += studentMarks[i];

            if (studentMarks[i] > studentMarks[highestIndex]) {
                highestIndex = i;
            }
            if (studentMarks[i] < studentMarks[lowestIndex]) {
                lowestIndex = i;
            }
        }

        double averageMarks = totalMarks / studentCount;

        System.out.printf("  Total Students     : %d%n", studentCount);
        System.out.printf("  Total Marks        : %.2f%n", totalMarks);
        System.out.printf("  Average Marks      : %.2f%n", averageMarks);
        System.out.println();
        System.out.printf("  Highest Marks      : %.2f (%s, ID: %d)%n",
                studentMarks[highestIndex], studentNames[highestIndex], studentIds[highestIndex]);
        System.out.printf("  Lowest Marks       : %.2f (%s, ID: %d)%n",
                studentMarks[lowestIndex], studentNames[lowestIndex], studentIds[lowestIndex]);
    }

    // =========================================================================
    // Helper Methods
    // =========================================================================

    /**
     * Checks whether a given student ID already exists in the records.
     *
     * @param id The student ID to check.
     * @return true if the ID is a duplicate, false otherwise.
     */
    static boolean isIdDuplicate(int id) {
        for (int i = 0; i < studentCount; i++) {
            if (studentIds[i] == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds the array index of a student by their ID.
     *
     * @param id The student ID to search for.
     * @return The index of the student, or -1 if not found.
     */
    static int findStudentIndexById(int id) {
        for (int i = 0; i < studentCount; i++) {
            if (studentIds[i] == id) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Reads an integer from the user with a prompt.
     * Handles invalid (non-integer) input gracefully.
     *
     * @param prompt The message to display to the user.
     * @return The integer value entered by the user.
     */
    static int readIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Please enter a valid integer.\n");
            }
        }
    }

    /**
     * Reads a double from the user with a prompt.
     * Handles invalid (non-numeric) input gracefully.
     *
     * @param prompt The message to display to the user.
     * @return The double value entered by the user.
     */
    static double readDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Please enter a valid number.\n");
            }
        }
    }

    // =========================================================================
    // Table Formatting Helpers
    // =========================================================================

    /**
     * Prints the header row of the student records table.
     */
    static void printTableHeader() {
        System.out.println("+--------+----------------------+------+------------------+--------+");
        System.out.printf("| %-6s | %-20s | %-4s | %-16s | %-6s |%n",
                "ID", "Name", "Age", "Department", "Marks");
        System.out.println("+--------+----------------------+------+------------------+--------+");
    }

    /**
     * Prints a single student row in the formatted table.
     *
     * @param index The array index of the student to print.
     */
    static void printStudentRow(int index) {
        System.out.printf("| %-6d | %-20s | %-4d | %-16s | %-6.2f |%n",
                studentIds[index],
                truncateString(studentNames[index], 20),
                studentAges[index],
                truncateString(studentDepartments[index], 16),
                studentMarks[index]);
    }

    /**
     * Prints the footer line of the student records table.
     */
    static void printTableFooter() {
        System.out.println("+--------+----------------------+------+------------------+--------+");
    }

    /**
     * Truncates a string to a maximum length, appending "..." if truncated.
     *
     * @param text      The original string.
     * @param maxLength The maximum allowed length.
     * @return The truncated string.
     */
    static String truncateString(String text, int maxLength) {
        if (text == null) {
            return "";
        }
        if (text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength - 3) + "...";
    }
}
