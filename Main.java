import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

class Employee {
    int id;
    String name;
    String department;
    String designation;
    double salary;
    LocalDate joiningDate;

    Employee(int id, String name, String department,
             String designation, double salary, LocalDate joiningDate) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.designation = designation;
        this.salary = salary;
        this.joiningDate = joiningDate;
    }

    public String toString() {
        return "ID: " + id +
               " | Name: " + name +
               " | Department: " + department +
               " | Designation: " + designation +
               " | Salary: " + String.format("%.2f", salary) +
               " | Joining Date: " + joiningDate;
    }
}

public class Main { 

    static ArrayList<Employee> employees = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    static final String FILE_NAME = "employees.txt";

    public static void main(String[] args) {

        loadEmployees();

        int choice;

        do {
            System.out.println("\n==================================");
            System.out.println("   EMPLOYEE MANAGEMENT SYSTEM");
            System.out.println("==================================");
            System.out.println("1. Add Employee");
            System.out.println("2. Display All Employees");
            System.out.println("3. Search Employee by ID");
            System.out.println("4. Search Employee by Department");
            System.out.println("5. Search Employee by Designation");
            System.out.println("6. Update Employee");
            System.out.println("7. Delete Employee");
            System.out.println("8. Highest Salary Employee");
            System.out.println("9. Calculate Average Salary");
            System.out.println("10. Employees Joined After a Date");
            System.out.println("11. Save Employee Information");
            System.out.println("12. Load Employee Information");
            System.out.println("0. Exit");
            System.out.println("==================================");

            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    displayEmployees();
                    break;
                case 3:
                    searchById();
                    break;
                case 4:
                    searchByDepartment();
                    break;
                case 5:
                    searchByDesignation();
                    break;
                case 6:
                    updateEmployee();
                    break;
                case 7:
                    deleteEmployee();
                    break;
                case 8:
                    highestSalary();
                    break;
                case 9:
                    averageSalary();
                    break;
                case 10:
                    employeesAfterDate();
                    break;
                case 11:
                    saveEmployees();
                    break;
                case 12:
                    loadEmployees();
                    break;
                case 0:
                    saveEmployees();
                    System.out.println(
                        "Exiting Employee Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 0);

        sc.close();
    }

    // Read an integer safely
    static int readInt(String message) {
        while (true) {
            System.out.print(message);
            try {
                int value = Integer.parseInt(sc.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    // Read a salary safely
    static double readSalary(String message) {
        while (true) {
            System.out.print(message);
            try {
                double salary = Double.parseDouble(sc.nextLine().trim());

                if (Double.isFinite(salary) && salary >= 0) {
                    return salary;
                }

                System.out.println("Salary must be a non-negative number.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid salary.");
            }
        }
    }

    // Read a date in yyyy-MM-dd format
    static LocalDate readDate(String message) {
        while (true) {
            System.out.print(message);
            try {
                return LocalDate.parse(sc.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.println(
                    "Invalid date. Use the format yyyy-MM-dd.");
            }
        }
    }

    // Find employee by ID
    static Employee findById(int id) {
        for (Employee e : employees) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }

    // 1. Add employee
    static void addEmployee() {

        int id = readInt("Enter Employee ID: ");

        if (findById(id) != null) {
            System.out.println("Employee ID already exists.");
            return;
        }

        System.out.print("Enter Name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter Department: ");
        String department = sc.nextLine().trim();

        System.out.print("Enter Designation: ");
        String designation = sc.nextLine().trim();

        double salary = readSalary("Enter Salary: ");

        LocalDate joiningDate =
            readDate("Enter Joining Date (yyyy-MM-dd): ");

        if (name.isEmpty() || department.isEmpty()
                || designation.isEmpty()) {
            System.out.println(
                "Name, department and designation cannot be empty.");
            return;
        }

        Employee e = new Employee(
            id, name, department, designation, salary, joiningDate);

        employees.add(e);

        System.out.println("Employee added successfully.");
    }

    // 2. Display all employees
    static void displayEmployees() {

        if (employees.isEmpty()) {
            System.out.println("No employee records found.");
            return;
        }

        System.out.println("\n----- Employee Details -----");

        for (Employee e : employees) {
            System.out.println(e);
        }
    }

    // 3. Search by employee ID
    static void searchById() {

        int id = readInt("Enter Employee ID to search: ");

        Employee e = findById(id);

        if (e != null) {
            System.out.println(e);
        } else {
            System.out.println("Employee not found.");
        }
    }

    // 4. Search by department
    static void searchByDepartment() {

        System.out.print("Enter Department: ");
        String department = sc.nextLine().trim();

        boolean found = false;

        for (Employee e : employees) {
            if (e.department.equalsIgnoreCase(department)) {
                System.out.println(e);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No employees found in this department.");
        }
    }

    // 5. Search by designation
    static void searchByDesignation() {

        System.out.print("Enter Designation: ");
        String designation = sc.nextLine().trim();

        boolean found = false;

        for (Employee e : employees) {
            if (e.designation.equalsIgnoreCase(designation)) {
                System.out.println(e);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No employees found with this designation.");
        }
    }

    // 6. Update employee
    static void updateEmployee() {

        int id = readInt("Enter Employee ID to update: ");

        Employee e = findById(id);

        if (e == null) {
            System.out.println("Employee not found.");
            return;
        }

        System.out.println("Current Details:");
        System.out.println(e);

        System.out.print("Enter New Name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter New Department: ");
        String department = sc.nextLine().trim();

        System.out.print("Enter New Designation: ");
        String designation = sc.nextLine().trim();

        double salary = readSalary("Enter New Salary: ");

        LocalDate date =
            readDate("Enter New Joining Date (yyyy-MM-dd): ");

        if (name.isEmpty() || department.isEmpty()
                || designation.isEmpty()) {
            System.out.println("Fields cannot be empty.");
            return;
        }

        e.name = name;
        e.department = department;
        e.designation = designation;
        e.salary = salary;
        e.joiningDate = date;

        System.out.println("Employee updated successfully.");
    }

    // 7. Delete employee
    static void deleteEmployee() {

        int id = readInt("Enter Employee ID to delete: ");

        Employee e = findById(id);

        if (e != null) {
            employees.remove(e);
            System.out.println("Employee deleted successfully.");
        } else {
            System.out.println("Employee not found.");
        }
    }

    // 8. Find employee with highest salary
    static void highestSalary() {

        if (employees.isEmpty()) {
            System.out.println("No employee records available.");
            return;
        }

        double maxSalary = employees.get(0).salary;

        for (Employee e : employees) {
            if (e.salary > maxSalary) {
                maxSalary = e.salary;
            }
        }

        System.out.println("\nEmployee(s) with highest salary:");

        for (Employee e : employees) {
            if (Double.compare(e.salary, maxSalary) == 0) {
                System.out.println(e);
            }
        }
    }

    // 9. Calculate average salary
    static void averageSalary() {

        if (employees.isEmpty()) {
            System.out.println("No employee records available.");
            return;
        }

        double total = 0;

        for (Employee e : employees) {
            total += e.salary;
        }

        double average = total / employees.size();

        System.out.printf("Average Salary: %.2f%n", average);
    }

    // 10. Employees who joined after a particular date
    static void employeesAfterDate() {

        LocalDate date =
            readDate("Enter Date (yyyy-MM-dd): ");

        boolean found = false;

        for (Employee e : employees) {
            if (e.joiningDate.isAfter(date)) {
                System.out.println(e);
                found = true;
            }
        }

        if (!found) {
            System.out.println(
                "No employees joined after this date.");
        }
    }

    // 11. Save employee information to a file
    static void saveEmployees() {

        try (PrintWriter writer =
                 new PrintWriter(new FileWriter(FILE_NAME))) {

            for (Employee e : employees) {
                writer.println(
                    e.id + "|" +
                    e.name + "|" +
                    e.department + "|" +
                    e.designation + "|" +
                    e.salary + "|" +
                    e.joiningDate);
            }

            System.out.println("Saved " + employees.size()
                    + " employee record(s) to "
                    + new File(FILE_NAME).getAbsolutePath());

        } catch (IOException e) {
            System.out.println("Error saving employee information: "
                    + e.getMessage());
        }
    }

    // 12. Load employee information from a file
    static void loadEmployees() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("No saved employee file found yet.");
            return;
        }

        ArrayList<Employee> loadedEmployees = new ArrayList<>();

        try (BufferedReader reader =
                 new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {
                try {
                    String[] data = line.split("\\|", -1);

                    if (data.length != 6) {
                        continue;
                    }

                    int id = Integer.parseInt(data[0]);
                    String name = data[1];
                    String department = data[2];
                    String designation = data[3];
                    double salary = Double.parseDouble(data[4]);
                    LocalDate date = LocalDate.parse(data[5]);

                    if (id <= 0 || name.isEmpty()
                            || department.isEmpty()
                            || designation.isEmpty()
                            || !Double.isFinite(salary)
                            || salary < 0) {
                        continue;
                    }

                    boolean duplicate = false;

                    for (Employee existing : loadedEmployees) {
                        if (existing.id == id) {
                            duplicate = true;
                            break;
                        }
                    }

                    if (!duplicate) {
                        loadedEmployees.add(
                            new Employee(id, name, department,
                                designation, salary, date));
                    }

                } catch (IllegalArgumentException ex) {
                    System.out.println(
                        "Skipping an invalid employee record.");
                }
            }

            employees.clear();
            employees.addAll(loadedEmployees);

            System.out.println("Loaded " + employees.size()
                    + " employee record(s) from "
                    + file.getAbsolutePath());

        } catch (IOException e) {
            System.out.println("Error loading employee information: "
                    + e.getMessage());
        }
    }
}