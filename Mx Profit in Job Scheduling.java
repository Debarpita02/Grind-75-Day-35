import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;

class Job {
    int start;
    int end;
    int profit;

    public Job(int start, int end, int profit) {
        this.start = start;
        this.end = end;
        this.profit = profit;
    }
}

public class Solution {
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        Job[] jobs = new Job[n];

        for (int i = 0; i < n; i++) {
            jobs[i] = new Job(startTime[i], endTime[i], profit[i]);
        }

        // Sort jobs by their end times
        Arrays.sort(jobs, Comparator.comparingInt(job -> job.end));

        // Create an array to store the maximum profit for each job
        int[] dp = new int[n];
        dp[0] = jobs[0].profit;

        for (int i = 1; i < n; i++) {
            int currentProfit = jobs[i].profit;
            int prevJobIndex = binarySearch(jobs, i);

            if (prevJobIndex != -1) {
                currentProfit += dp[prevJobIndex];
            }

            dp[i] = Math.max(currentProfit, dp[i - 1]);
        }

        return dp[n - 1];
    }

    private int binarySearch(Job[] jobs, int index) {
        int low = 0, high = index - 1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (jobs[mid].end <= jobs[index].start) {
                if (jobs[mid + 1].end <= jobs[index].start) {
                    low = mid + 1;
                } else {
                    return mid;
                }
            } else {
                high = mid - 1;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] startTime1 = {1, 2, 3, 3};
        int[] endTime1 = {3, 4, 5, 6};
        int[] profit1 = {50, 10, 40, 70};
        System.out.println(solution.jobScheduling(startTime1, endTime1, profit1)); // Output: 120

        int[] startTime2 = {1, 2, 3, 4, 6};
        int[] endTime2 = {3, 5, 10, 6, 9};
        int[] profit2 = {20, 20, 100, 70, 60};
        System.out.println(solution.jobScheduling(startTime2, endTime2, profit2)); // Output: 150

        int[] startTime3 = {1, 1, 1};
        int[] endTime3 = {2, 3, 4};
        int[] profit3 = {5, 6, 4};
        System.out.println(solution.jobScheduling(startTime3, endTime3, profit3)); // Output: 6
    }
}
