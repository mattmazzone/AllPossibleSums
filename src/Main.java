import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList; // Import because I'm a C# enjoyer
import java.util.Scanner;   // Import to read input
import java.util.Arrays;    // Import to format console output


public class Main {

    static ArrayList<ArrayList<Double>> output = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        ArrayList<Double> numbers = read_input_file();

        double target = 1646.13;
        double variance = 0.5;

        // Time Logging
        long start = System.currentTimeMillis();
        Thread newThread = new Thread(() -> {
            while (true) {
                long finish = System.currentTimeMillis();
                long timeElapsed = finish - start;
                System.out.println(timeElapsed / 1000 + " seconds elapsed");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        newThread.start();


        // Start recursive task
        sum_up(new ArrayList<Double>(numbers), target);

        write_output_to_file();
    }


    static void sum_up_recursive(ArrayList<Double> numbers, double target, ArrayList<Double> partial) {
        double s = 0;
        for (double x : partial) s += x;

        //if (target - s < 0.5 && target - s > - 0.5)
        if (s == target) {
            System.out.println(Arrays.toString(partial.toArray()));
            output.add(partial);
        }

        if (s >= target) {
            return;
        }

        for (int i = 0; i < numbers.size(); i++) {
            ArrayList<Double> remaining = new ArrayList<Double>();
            double n = numbers.get(i);

            for (int j = i + 1; j < numbers.size(); j++) {
                remaining.add(numbers.get(j));
            }
            ArrayList<Double> partial_rec = new ArrayList<Double>(partial);
            partial_rec.add(n);
            sum_up_recursive(remaining, target, partial_rec);
        }
    }

    static void sum_up(ArrayList<Double> numbers, double target) {
        sum_up_recursive(numbers, target, new ArrayList<Double>());
    }

    public static ArrayList<Double> read_input_file() {
        int totalEntries = 0;
        ArrayList<Double> numbers = new ArrayList<>(130);
        try {
            File myObj = new File("src/data.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                numbers.add(Double.valueOf(data));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return numbers;
    }

    public static void write_output_to_file() throws IOException {
        PrintWriter writer = new PrintWriter("src/output.txt", StandardCharsets.UTF_8);

        for (ArrayList<Double> list : output) {
            writer.println(Arrays.toString(list.toArray()));
        }

        writer.close();
    }


//        ArrayList<Double> duplicates = new ArrayList<>(130);
//
//        for (double i:numbers) {
//            for (double j: numbers) {
//                if ((i+j-target < variance) && (i +j - target > -variance)){
//
//                    if(!duplicates.contains(i) || !duplicates.contains(j)) {
//                        System.out.println(i + " " + j);
//                    }
//                    duplicates.add(i);
//                    duplicates.add(j);
//                }
//            }
//        }
}
