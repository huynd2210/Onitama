package engine;

import com.opencsv.CSVWriter;
import data.StateData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IOEngine {
    public static void createFile(String path) {
        try {
            File file = new File(path);
            if (!file.createNewFile()) {
                System.out.println("File already existed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void writeToFile(List<StateData> content, String path) throws IOException {
        try {
            FileWriter myWriter = new FileWriter(path);
            for (StateData s : content) {
                myWriter.write(s.toString());
            }

            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readTest(String path) throws FileNotFoundException {
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
//            System.out.println(scanner.nextLine());
            String[] tmp = scanner.nextLine().split(";");
            for (String s : tmp){
                System.out.print(s + " ");
            }
            StateData sd = new StateData(tmp);
            System.out.println("Printing resulting StateData");
            System.out.println(sd);

//            while (scanner.hasNextLine()) {
//                String string = scanner.nextLine();
//                System.out.println(string);
//            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
