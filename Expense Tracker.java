import java.util.*;
class Expense {
	private String category;
	private double amount;
	public Expense(String category, double amount) {
		this.category = category;
		this.amount = amount;
	}
	public String getCategory() {
		return category;
	}
	public double getAmount() {
		return amount;
	}
	public String toString() {
		return "Category: " + category + ", Amount: " + String.format("%.2f", amount);
	}
}
public class Main{
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Map<Integer, List<Expense>> monthlyExpenses = new HashMap<>();
		boolean exit = false;
		System.out.print("Enter your total salary for the year: ");
		double salary = scanner.nextDouble();
		double monthlySalary = salary / 12;
		double remainingSalary = salary;
		scanner.nextLine();
		while (!exit) {
			System.out.println("\nChoose an option:");
			System.out.println("1. Add Expenses");
			System.out.println("2. View Total Expenses for the Year");
			System.out.println("3. View Expenses for Each Month");
			System.out.println("4. Exit");
			System.out.print("Enter your choice: ");
			int choice = scanner.nextInt();
			scanner.nextLine();
			switch (choice) {
				case 1:
					System.out.print("Enter the month number (1 for January, 12 for December): ");
					int month = scanner.nextInt();
					scanner.nextLine();

					if (month < 1 || month > 12) {
						System.out.println("Invalid month number. Please enter a number between 1 and 12.");
						break;
					}
					while (true) {
						System.out.print("Enter category: ");
						String category = scanner.nextLine();
						System.out.print("Enter amount: ");
						double amount = scanner.nextDouble();
						scanner.nextLine();
						double totalExpensesForMonth = monthlyExpenses.getOrDefault(month, new ArrayList<>()).stream()
								.mapToDouble(Expense::getAmount)
								.sum();
						if (totalExpensesForMonth + amount > remainingSalary) {
							System.out.println("Warning: This expense exceeds your remaining salary!");
						}
						monthlyExpenses.putIfAbsent(month, new ArrayList<>());
						monthlyExpenses.get(month).add(new Expense(category, amount));
						remainingSalary -= amount;
						System.out.println("Expense added successfully!");
						System.out.println("Remaining Salary: " + String.format("%.2f", remainingSalary));
						System.out.print("Do you want to add another expense for month " + month + "? (yes/no): ");
						String moreExpenses = scanner.nextLine().trim().toLowerCase();
						if (!moreExpenses.equals("yes")) {
							break;
						}
					}
					break;
				case 2:
					double totalExpensesForYear = 0;
					for (int m = 1; m <= 12; m++) {
						List<Expense> expensesForMonth = monthlyExpenses.get(m);
						if (expensesForMonth != null) {
							totalExpensesForYear += expensesForMonth.stream().mapToDouble(Expense::getAmount).sum();
						}
					}
					System.out.println("\nTotal Expenses for the Year:");
					System.out.println("Total Expenses: " + String.format("%.2f", totalExpensesForYear));
					double amountSaved = salary - totalExpensesForYear;
					System.out.println("Amount Saved: " + String.format("%.2f", Math.max(0, amountSaved)));
					System.out.println("Amount Spent: " + String.format("%.2f", totalExpensesForYear));
					System.out.println("Percentage of Salary Spent: " + String.format("%.2f", (totalExpensesForYear / salary) * 100) + "%");
					System.out.println("Percentage of Salary Saved: " + String.format("%.2f", Math.max(0, (amountSaved / salary) * 100)) + "%");
					break;
				case 3:
					System.out.println("\nExpenses for Each Month:");
					for (int m = 1; m <= 12; m++) {
						List<Expense> expensesForMonth = monthlyExpenses.get(m);
						System.out.println("Month " + m + ":");
						if (expensesForMonth != null && !expensesForMonth.isEmpty()) {
							double totalExpensesForMonth = expensesForMonth.stream().mapToDouble(Expense::getAmount).sum();
							System.out.println("  Total Expenses: " + String.format("%.2f", totalExpensesForMonth));
							double spentPercentage = (totalExpensesForMonth / monthlySalary) * 100;
							double savedPercentage = 100 - spentPercentage;
							System.out.println("  Percentage of Salary Spent: " + String.format("%.2f", spentPercentage) + "%");
							System.out.println("  Percentage of Salary Saved: " + String.format("%.2f", Math.max(0, savedPercentage)) + "%");
							for (Expense expense : expensesForMonth) {
								System.out.println("    " + expense);
							}
						} else {
							System.out.println("  No expenses recorded.");
						}
					}
					break;
				case 4:
					System.out.println("Exiting Expense Tracker. Goodbye!");
					exit = true;
					break;

				default:
					System.out.println("Invalid choice. Please try again.");
			}
		}
		scanner.close();
	}
}
