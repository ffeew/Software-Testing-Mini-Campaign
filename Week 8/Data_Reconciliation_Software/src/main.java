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
        try
        {
            System.out.println("\nPlease ensure that your CSV files are in the same directory as this Data Reconciliation Software \n");
            System.out.println("The CSV files should be under the folder: 'Data Reconciliation Software' \n");
            System.out.println("Please enter the filename of your first CSV file: ");
            Scanner sc = new Scanner(System.in);
            String file1 = sc.nextLine();

            System.out.println("Please enter the filename of your second CSV file: ");
            String file2 = sc.nextLine();

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
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return;
        }

        // check each row in CSV1 against all the rows in CSV2
        for (int i = 0; i<CSV1.size(); i++){

            List<String> CSV1Row = CSV1.get(i);

            // iterate through all the rows in CSV2
            for (int j = 0; j < CSV2.size(); j++) {
                int discrepancy = 0;
                List<String> CSV2Row = CSV2.get(j);

                // check of discrepancies within the row
                for (int k = 0; k <CSV2Row.size() ; k++) {
                    String CSV1Cell = CSV1Row.get(k);
                    String CSV2Cell = CSV2Row.get(k);
                    if (!CSV1Cell.equals(CSV2Cell)){
                        discrepancy++;
                    }
                }
                // if there is only 1 mismatch for the whole row
                // record down the rows as mismatches
                if (discrepancy==1){
                    exceptions.add(CSV1Row);
                    exceptions.add(CSV2Row);
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
