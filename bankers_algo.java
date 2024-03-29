import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class bankers_algo {
    public static void main(String[] args) {
        Map<String, int[]> jobs = new HashMap<>();
        Set<String> processedJobs = new HashSet<>();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter total resources: ");
        int totalResources = scanner.nextInt();
        System.out.print("Enter Number Of Jobs: ");
        int numberOfJobs = scanner.nextInt();

        for (int i = 0; i < numberOfJobs; i++) {
            scanner.nextLine(); // Consume newline
            System.out.print("Enter Job Name: ");
            String name = scanner.nextLine().toUpperCase();
            System.out.print("Enter Job Allocation and Max: ");
            int allocation = scanner.nextInt();
            int maxAllocation = scanner.nextInt();
            int jobNeed = maxAllocation - allocation;
            jobs.put(name, new int[]{allocation, maxAllocation, jobNeed});
        }

        int totalOfAllocations = 0;
        for (int[] values : jobs.values()) {
            totalOfAllocations += values[0];
        }

        int available = totalResources - totalOfAllocations;
        while (available < totalResources) {
            for (Map.Entry<String, int[]> entry : jobs.entrySet()) {
                String jobName = entry.getKey();
                int[] values = entry.getValue();
                if (processedJobs.contains(jobName)) {
                    // Skip already processed job
                    continue;
                }
                if (available < values[2]) {
                    // Do nothing
                } else if (available < 0) {
                    available -= values[2];
                    System.out.printf("%-5s %-5d %-5s %-5d%n", jobName, values[2], "", available);
                    values[1]=0 ;
                    available += values[1];

                    System.out.printf("%-5s %-5s %-5d %-5d%n", jobName + " exit", "", values[1], available);
                    System.out.print("Unsafe State: Resource Deadlock");
                    return;
                } else {
                    available -= values[2];
                    System.out.printf("%-5s %-5d %-5s %-5d%n", jobName, values[2], "", available);

                    int rtrn = values[1];
                    available += values[1];

                    System.out.printf("%-5s %-5s %-5d %-5d%n", jobName + " exit", "", rtrn, available);
                    processedJobs.add(jobName);
                    System.out.println("Safe State");
                }
            } 
        }
    }
}       