import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        String line;
        String delimiter = ",";
        List<List<String>> CSV1 = new ArrayList<>();
        List<List<String>> CSV2 = new ArrayList<>();
        List<List<String>> exceptions = new ArrayList<>();
        String file1;
        String file2;
        List<String> unique_combination = new ArrayList<>();


        System.out.println("\nPlease ensure that your CSV files are in the same directory as this Data Reconciliation Software \n");
        System.out.println("The CSV files should be under the folder: 'Data Reconciliation Software' \n");

        // simple data validation to ensure that the file entered has a CSV extension
        while(true){
            System.out.println("Please enter the filename of your first CSV file: ");
            System.out.println("Example: sample_file_1.csv");
            Scanner sc = new Scanner(System.in);
            file1 = sc.nextLine();

            System.out.println("Please enter the filename of your second CSV file: ");
            System.out.println("Example: sample_file_2.csv");
            file2 = sc.nextLine();

            System.out.println("Please enter the column headers (case-sensitive) to be referenced: ");
            System.out.println("The program expects the headers to be in this format: \"Header1\", \"Header2\", .....");
            System.out.println("Example: \"ID\", \"Account\", \"Name\"");
            String[] result = sc.nextLine().split(",");
            for (String header : result){
                unique_combination.add(header.trim());
            }

            // close scanner after the file names are obtained
            sc.close();

            // check if the files have .csv extension
            try {
                String file1_ext = file1.split("\\.")[1];
                String file2_ext = file2.split("\\.")[1];

                if (file1_ext.equals("csv") && file2_ext.equals("csv")){
                    break;
                } else{
                    System.out.println("\nPlease enter a CSV file, other file formats are not supported\n");
                }
            }
            catch(IndexOutOfBoundsException e){
                System.out.println("Please enter the filename with its extension, example: test.csv");
            }
        }

        // parse the files into 2d arrays
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(file1));
            while ((line = br.readLine()) != null)
            {
                String[] row = line.split(delimiter);
                CSV1.add(Arrays.asList(row));
            }
            br.close();

            BufferedReader br2 = new BufferedReader(new FileReader(file2));
            while ((line = br2.readLine()) != null)
            {
                String[] row = line.split(delimiter);
                CSV2.add(Arrays.asList(row));
            }
            br2.close();
        } catch (IOException e)
        {
            System.out.println("Program is unable to read the files");
            return;
        }

        // Check if both files contains the headers listed in the unique combination
        List<String> CSV1Row = CSV1.get(0);
        List<String> CSV2Row = CSV2.get(0);
        if (!CSV1Row.containsAll(unique_combination)){
            System.out.println("The first CSV file does not contain the necessary headers as specified in your unique combination");
            return;
        } else if (!CSV2Row.containsAll(unique_combination)){
            System.out.println("The second CSV file does not contain the necessary headers as specified in your unique combination");
            return;
        }

        // get the corresponding column number in each CSV file for the values in unique_combination
        ArrayList<Integer> indexes1 = new ArrayList<>();
        for (String header : unique_combination) {
            Integer index = CSV1Row.indexOf(header);
            indexes1.add(index);
        }

        ArrayList<Integer> indexes2 = new ArrayList<>();
        for (String header : unique_combination) {
            Integer index = CSV2Row.indexOf(header);
            indexes2.add(index);
        }

        // generate the map of headers from CSV1 to CSV2
        ArrayList<Integer> headerMap = new ArrayList<>();
        for (String header : CSV1Row){
            Integer index = CSV2Row.indexOf(header);
            headerMap.add(index);
        }

        // check each row in CSV1 against all the rows in CSV2
        for (int i = 0; i<CSV1.size(); i++){

            CSV1Row = CSV1.get(i);

            // add the values of the unique combination into data_combination for search in CSV2
            List<String> data_combination1 = new ArrayList<>();
            for (Integer index: indexes1){
                data_combination1.add(CSV1Row.get(index));
            }

            // iterate through all the rows in CSV2
            for (int j = 0; j < CSV2.size(); j++) {

                CSV2Row = CSV2.get(j);
                List<String> data_combination2 = new ArrayList<>();
                for (Integer index: indexes2){
                    data_combination2.add(CSV2Row.get(index));
                }

                // check if the data that corresponds to the unique combination matches
                // if not, move on to the next row
                if (!data_combination1.equals(data_combination2)){
                    continue;
                }

                // check if the row from CSV1 can be found in CSV2 and if all elements match
                // if not, add to exceptions
                for (int k = 0; k < CSV1Row.size(); k++) {
                    String CSV1Cell = CSV1Row.get(k);
                    String CSV2Cell = CSV2Row.get(headerMap.get(k));
                    if (!CSV1Cell.equals(CSV2Cell)){
                        exceptions.add(CSV1Row);
                        exceptions.add(CSV2Row);
                        break;
                    }
                }
            }
        }

        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("Exceptions.csv"));
            for (int i = 0; i < exceptions.size(); i++) {
                List<String> row = exceptions.get(i);

                StringBuilder str = new StringBuilder("");

                // Traversing the ArrayList
                for (String cell : row) {

                    // Each element in ArrayList is appended
                    // followed by comma
                    str.append(cell).append(",");
                }
                line = str.toString();
                System.out.println(line);
                bw.write(line);
                bw.write("\n");
            }
            bw.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }
}
