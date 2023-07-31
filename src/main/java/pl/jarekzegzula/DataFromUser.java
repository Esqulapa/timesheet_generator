package pl.jarekzegzula;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class DataFromUser {

    private static final int minimumNumberOfHours = 1;

    private static final int maximumNumberOfHours = 12;

    private static final int minimumWorkingDaysInWeek = 1;
    private static final int maximumWorkingDaysInWeek = 5;

    private static final int earliestYear = 2010;

    private static final int latestYear = 2029;


    public static Locale getLocaleFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                Please select language for your Time Sheet:                                
                1.Polish.                                
                2.English.""");

        Locale locale = null;

        while (locale == null) {

            try {
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1 -> locale = new Locale("pl", "PL");
                    case 2 -> locale = Locale.ENGLISH;
                    default -> System.out.println("Invalid choice. Please try again.\n");
                }
            }catch (InputMismatchException e){
                System.out.println("Invalid choice. Please try again.\n");
                scanner.nextLine();
            }
        }

        return locale;




    }

    public static LocalDate convertToDate(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        YearMonth yearMonth = YearMonth.parse(input, formatter);
        return yearMonth.atDay(1);
    }

    public static LocalDate getFirstDateFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the first date in MM/yyyy format");

        LocalDate startDate = null;
        boolean validInput = false;

        while (!validInput) {
            try {
                String date = scanner.next();

                startDate = convertToDate(date);

                if (startDate.getYear() < earliestYear) {
                    System.out.println("Date is too old. Please enter a date after year 2010: ");
                    continue;
                }
                validInput = true;

            } catch (InputMismatchException e) {
                System.out.println("Invalid format. Try again.");

            } catch (DateTimeParseException e) {
                System.out.println("Invalid format. Try again.");
                scanner.nextLine();
            }
        }

        return startDate;
    }

    public static LocalDate getSecondDateFromUser(LocalDate firstDate) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the second date in MM/yyyy format");

        LocalDate startDate = null;
        boolean validInput = false;


        while (!validInput) {
            try {
                String date = scanner.next();

                startDate = convertToDate(date);

                if (startDate.getYear() >= latestYear) {
                    System.out.println("Date is too old. Please enter a date before year 2030: ");
                    continue;
                }
                if (startDate.isAfter(firstDate)) {
                    validInput = true;
                } else {
                    System.out.println("Date must be after first date. Please try again.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid format. Try again.");
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format. Try again.");
                scanner.nextLine();
            }

        }


        return startDate;
    }

    public static WorkingHours getNumberOfWorkingHoursFromUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Number of hours allowed in the range: 1 - 12");
        System.out.println("Enter the number of hours you want to work on a working day");
        boolean validInput = false;
        int numberOfHours = 0;

        while (!validInput) {

            try {
                numberOfHours = scanner.nextInt();

                if (numberOfHours > minimumNumberOfHours && numberOfHours < maximumNumberOfHours) {
                    validInput = true;
                } else System.out.println("The given value does not meet the rules");

            } catch (InputMismatchException e) {
                System.out.println("The given value is invalid. Please try again.");
                scanner.nextLine();
            }

        }
        return new WorkingHours(numberOfHours);

    }

    public static WorkingDays getWorkingDaysFromUser() {

        Scanner scanner = new Scanner(System.in);

        LinkedList<DayOfWeek> selectedDays = new LinkedList<>();
        LinkedList<DayOfWeek> remainingDays = new LinkedList<>(List.of(DayOfWeek.values()));

        System.out.println("Available days: " + remainingDays);

        while (true) {
            System.out.print("Enter a day of the week (or 'done' to finish): ");
            String input = scanner.nextLine().trim();


            try {
                if (input.equalsIgnoreCase("done")){
                    break;
                }

                DayOfWeek selectedDay = DayOfWeek.valueOf(input.toUpperCase());


                if (selectedDays.contains(selectedDay)) {
                    System.out.println("Day already selected. Please choose a different day.");
                    continue;

                } else if (selectedDay.getValue() > maximumWorkingDaysInWeek) {
                    System.out.println("Limit of working days in a week has been reached");
                    continue;

                } else if (selectedDay.getValue() < minimumWorkingDaysInWeek){
                    System.out.println("There has to be at least one working day");

                }

                selectedDays.add(selectedDay);

                remainingDays.remove(selectedDay);

                System.out.println("Selected days: " + selectedDays + "\n");
                System.out.println("Remaining days: " + remainingDays);

            } catch (IllegalArgumentException | NullPointerException e) {
                System.out.println("Invalid day or no day selected");}


        }

        return new WorkingDays(selectedDays);
    }
}





