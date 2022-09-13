import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;



public class Main {
    public static void main(String[] args) {
        int totalEntries = 0;
        ArrayList<Double> numbers = new ArrayList<>(130);
        try {
            File myObj = new File("/Users/matteo/dev/github/adamNums/src/data.txt");
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

//        for (double number: numbers) {
//            System.out.println(number);
//        }

        double target = 1646.13;
        double variance = 0.5;
        long start = System.currentTimeMillis();

        Thread newThread = new Thread(() -> {
            while (true){

                long finish = System.currentTimeMillis();
                long timeElapsed = finish - start;
                System.out.println(timeElapsed);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        newThread.start();

        sum_up(new ArrayList<Double> (numbers), target);
    }



    static void sum_up_recursive(ArrayList<Double> numbers, double target, ArrayList<Double> partial) {
        double s = 0;
        for (double x: partial) s += x;

        //if (target - s < 0.5 && target - s > - 0.5)
        if (s == target)
            System.out.println("sum("+Arrays.toString(partial.toArray())+")="+target);
        if (s >= target)
            return;
        for(int i=0;i<numbers.size();i++) {
            ArrayList<Double> remaining = new ArrayList<Double>();
            double n = numbers.get(i);
            for (int j=i+1; j<numbers.size();j++) remaining.add(numbers.get(j));
            ArrayList<Double> partial_rec = new ArrayList<Double>(partial);
            partial_rec.add(n);
            sum_up_recursive(remaining,target,partial_rec);
        }
    }
    static void sum_up(ArrayList<Double> numbers, double target) {
        sum_up_recursive(numbers,target,new ArrayList<Double>());
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
