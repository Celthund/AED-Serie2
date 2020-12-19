package serie2;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.lang.System.nanoTime;

public class ProcessPointsColections {

    public static void main(String[] args) {

        double start_time = nanoTime() * 1e-6;
        // If args.length is less than 1 it means that is empty so returns a warning and exits the program
        if (args.length < 1) {
            System.out.println("Missing operation.");
            System.exit(0);
        }

        String operation = args[0];

        // Checks if the string in the args matches the exit command
        if (operation.equals("exit"))
            System.exit(0);

        // Checks the size of the args if is less than 3 it means all the instruction doesnt fit so it returns
        //an error and exits the program
        if (args.length < 3) {
            System.out.println("Missing input files.");
            System.exit(0);
        }

        // It will get the files names from the correspondent args position
        String file1 = args[1];
        String file2 = args[2];

        // If is less than 4 it mean there is no output file so it will return an error
        if (args.length < 4) {
            System.out.println("Missing output file.");
            System.exit(0);
        }

        //Stores the output file
        String output_file = args[3];


        //Stores the file1 content
        Scanner scanner1 = null;
        try {
            //Passes the content of the file1 to the scanner1
            scanner1 = new Scanner(new FileReader(file1));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File missing: " + file1);
            System.exit(-1);
        }

        //Stores the file2 content
        Scanner scanner2 = null;
        try {
            //Passes the content of the file2 to the scanner2
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
                // Calls the fileToHasMap() method twice so it can make the union of the 2 files
                FileToHashMap(hashmap, scanner1);
                FileToHashMap(hashmap, scanner2);
                break;
            case "difference":
                // Method that stores all the content of the file1 on to the hashmap
                FileToHashMap(hashmap, scanner1);

                // Will subtract from the hashmap the contents that are in both files
                difference(hashmap, scanner2);
                break;
            case "intersection":
                // Since intersection is commutative we start with the file with less points so its faster to create the hashmap.
                int tmp1 = NodesOnFile(scanner1), tmp2 = NodesOnFile(scanner2);
                if (tmp1 < tmp2){ ;
                    // Method that stores all the content of the file1 on to the hashmap
                    FileToHashMap(hashmap, scanner1);
                    //hashmap will contains the contents that are in both files
                    hashmap = intersection(hashmap, scanner2);
                } else {
                    FileToHashMap(hashmap, scanner2);
                    hashmap = intersection(hashmap, scanner1);
                }
                break;
            default:
                System.out.println("No valid operation.");
                //Leaves the program
                System.exit(0);
        }

        // Writes to the output file
        writeToFile(out, hashmap);

        // Closes all files
        scanner1.close();
        scanner2.close();
        out.close();

        double end_time = nanoTime() * 1e-6;
        System.out.printf("%.2f\n", end_time - start_time);
        HashMap<Point, Point> map = new HashMap<>();

    }

    private static int NodesOnFile(Scanner scanner) {
        String[] curr;
        while (scanner.hasNextLine()) {
            // v 1 -73530767 41085396
            curr = scanner.nextLine().split(" ");
            if (!curr[0].equals("p")) {
                continue;
            }
            //p aux sp co 264346
            return Integer.parseInt(curr[4]);
            //c graph contains 264346 nodes
        }
        return 0;
    }

    private static HashMap<Point, Integer> intersection(HashMap<Point, Integer> hashmap, Scanner scanner) {
        // Stores the current line being read on the file
        String[] curr;

        // Creates the HashMap that will be used to compare both files
        HashMap<Point, Integer> tmp = new HashMap<>();

        // Runs through all the lines of the file being read by the scanner
        while (scanner.hasNextLine()) {

            // Separates the lines every time it encounters a space character
            curr = scanner.nextLine().split(" ");

            if (!curr[0].equals("v")) {
                continue;
            }

            // Creates a new Point class that stores the x value an y value in the current line of the file
            Point point = new Point(curr[2], curr[3]);

            // Will check if both the hashmap contains the point it means both files have that the point
            if (hashmap.containsKey(point)){
                // If tmp also contains the point it means it has already read that point previous in the file so
                //it will increment the value in the tmp, else increments the value in the hashmap
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
        // Stores the current line being read on the file
        String[] curr;
        // Runs through all the lines of the file being read by the scanner
        while (scanner.hasNextLine()) {
            // Separates the lines every time it encounters a space character
            curr = scanner.nextLine().split(" ");
            // If encounters a character different from 'v' it means its not in a coordinates line so it will
            //move on to the next line
            if (!curr[0].equals("v")) {
                continue;
            }
            Point point = new Point(curr[2], curr[3]);
            // Will remove the point it got from the current line being read (in case that point exists on the hashmap)
            hashmap.remove(point);
        }
    }

    // Reads the file on the scanner variable and passes to a hashmap
    private static void FileToHashMap(HashMap<Point, Integer> hashmap, Scanner scanner) {
        // Stores the current line being read on the file
        String[] curr;

        // Runs through all the lines of the file being read by the scanner
        while (scanner.hasNextLine()) {
            // Separates the lines every time it encounters a space character
            curr = scanner.nextLine().split(" ");
            // If encounters a character different from 'v' it means its not in a coordinates line so it will
            //move on to the next line
            if (!curr[0].equals("v")) {
                continue;
            }
            Point point = new Point(curr[2], curr[3]);
            // If the point being read from the current line doesnt exist on the hashmap it will add to it
            //if it exists it will increment the counter
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

        // Prints the value from the hashmap on to the file
        for(Map.Entry<Point, Integer> entry : hashmap.entrySet()) {
            Point key = entry.getKey();
            Integer value = entry.getValue();
            out.printf("v %d %s %s Number of occurrences : %d\n", i++, key.x, key.y, value);
        }
    }

}
