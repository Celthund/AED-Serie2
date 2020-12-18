package serie2;

import java.io.*;
import java.util.Iterator;
import java.util.Scanner;

import static java.lang.System.nanoTime;

public class OurProcessPointsColections {

    public static void main(String[] args) {

        double start_time = nanoTime() * 1e-6;
        if (args.length < 1) {
            System.out.println("Missing operation.");
            System.exit(0);
        }

        String operation = args[0];

        if (operation == "exit")
            System.exit(0);

        if (args.length < 3) {
            System.out.println("Missing input files.");
            System.exit(0);
        }
        String file1 = args[1];
        String file2 = args[2];

        if (args.length < 4) {
            System.out.println("Missing output file.");
            System.exit(0);
        }
        //Stores the input file
        String output_file = args[3];


        //Will store all the input files
        Scanner scanner1 = null;
        try {
            //Passes the information of the files to the scanners
            scanner1 = new Scanner(new FileReader(file1));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File missing: " + file1);
            System.exit(-1);
        }
        Scanner scanner2 = null;
        try {
            //Passes the information of the files to the scanners
            scanner2 = new Scanner(new FileReader(file2));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File missing: " + file2);
            System.exit(-1);
        }

        //Will write to the output file
        PrintWriter out = null;
        try {
            out = new PrintWriter(new FileWriter(output_file));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to save: " + output_file);
            System.exit(-2);
        }

        HashMap<Point, Integer> hashmap = new HashMap<>();
        //Switch case to know which tasks the user wants to do
        switch (operation) {
            case "union":
                //Calls the get() method to show the k words that occur more times
                FileToHashMap(hashmap, scanner1);
                FileToHashMap(hashmap, scanner2);
                break;
            case "difference":
                //Calls the sort() method that orders by alphabetic order the k words
                //that occur more times
                FileToHashMap(hashmap, scanner1);
                difference(hashmap, scanner2);
                break;
            case "intersection":
                //Calls the sort() method that orders by alphabetic order the k words
                //that occur more times
                FileToHashMap(hashmap, scanner1);
                hashmap = intersection(hashmap, scanner2);
                break;
            default:
                System.out.println("No valid operation.");
                //Leaves the program
                System.exit(0);
        }

        writeToFile(out, hashmap);

        scanner1.close();
        scanner2.close();
        out.close();

        double end_time = nanoTime() * 1e-6;
        System.out.printf("%.2f\n", end_time - start_time);
        HashMap<Point, Point> map = new HashMap<>();

    }

    private static HashMap<Point, Integer> intersection(HashMap<Point, Integer> hashmap, Scanner scanner) {
        String[] curr;
        HashMap<Point, Integer> tmp = new HashMap<>();
        while (scanner.hasNextLine()) {
            // v 1 -73530767 41085396
            curr = scanner.nextLine().split(" ");
            if (!curr[0].equals("v")) {
                continue;
            }
            Point point = new Point(curr[2], curr[3]);
            if (hashmap.containsKey(point)){
                if (tmp.containsKey(point)){
                    tmp.put(point, tmp.get(point) + 1);
                } else {
                    tmp.put(point, hashmap.get(point) + 1);
                }
            }
        }
        return tmp;
    }

    private static void difference(HashMap<Point, Integer> hashmap, Scanner scanner) {
        String[] curr;
        while (scanner.hasNextLine()) {
            // v 1 -73530767 41085396
            curr = scanner.nextLine().split(" ");
            if (!curr[0].equals("v")) {
                continue;
            }
            Point point = new Point(curr[2], curr[3]);
            hashmap.remove(point);
        }
    }

    private static void FileToHashMap(HashMap<Point, Integer> hashmap, Scanner scanner) {
        String[] curr;
        while (scanner.hasNextLine()) {
            // v 1 -73530767 41085396
            curr = scanner.nextLine().split(" ");
            if (!curr[0].equals("v")) {
                continue;
            }
            Point point = new Point(curr[2], curr[3]);
            if (hashmap.containsKey(point)){
                hashmap.put(point, hashmap.get(point) + 1);
            } else {
                hashmap.put(point, 1);
            }
        }
    }

    private static void writeToFile(PrintWriter out, HashMap<Point, Integer> hashmap) {
        //c 9th Implementation Challenge: Shortest Paths
        //c
        //p aux sp co 264346
        //c graph contains 264346 nodes
        //c
        //v 1 -73530767 41085396
        out.println("c 9th Implementation Challenge: Shortest Paths");
        out.println("c");
        out.printf("p aux sp co %d\n", hashmap.size());
        out.printf("c graph contains %d nodes\n", hashmap.size());
        out.println("c");
        int i = 1;
        Iterator<HashNode<Point, Integer>> iter = hashmap.iterator();
        HashNode<Point, Integer> node;
        while (iter.hasNext()){
            node = iter.next();
            Point key = node.getKey();
            Integer value = node.getValue();
            out.printf("v %d %s %s Number of occurrences : %d\n", i++, key.x, key.y, value);
        }
    }

}
