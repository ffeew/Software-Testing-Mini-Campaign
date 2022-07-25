import org.junit.jupiter.api.Assertions;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// change the working directory in the run configuration to be src before running the test
class DataReconTest {

    @org.junit.jupiter.api.Test
    void isCSVValid() {
        String filename = "Test.csv";
        Assertions.assertEquals(true ,DataRecon.isCSV(filename));
    }

    @org.junit.jupiter.api.Test
    void isCSVInvalid() {
        String filename = "Test.tsv";
        Assertions.assertEquals(false, DataRecon.isCSV(filename));
    }

    @org.junit.jupiter.api.Test
    void parseCSVValid() {
        // change working directory in program run configuration to src
        Assertions.assertDoesNotThrow(
                () -> {
                    DataRecon.parseCSV("./test_files/test.csv", ",");
                });
        String[][] expectation = {{"Customer ID#","Account No.","Currency","Type","Balance"},{"ID1","BOS963211","USD","SAVINGS","962510"}};
        try{
            Assertions.assertEquals(Arrays.deepToString(expectation), DataRecon.parseCSV("./test_files/test.csv", ",").toString());
        } catch (IOException e){
            Assertions.assertEquals(null, e);
        }

    }

    @org.junit.jupiter.api.Test
    void parseCSVInvalid() {
        Assertions.assertThrows(IOException.class,
                () -> {
                    DataRecon.parseCSV("./test_files/sample_file_1.xlsx", ",");
                });

    }

    @org.junit.jupiter.api.Test
    void generate_unique_combinationValid() {
        String[] col_no = {"0", "1"};
        String[][] sample_csv = {{"Customer ID#","Account No.","Currency","Type","Balance"},{"ID1","BOS963211","USD","SAVINGS","962510"}};
        String[] expectation = {"Customer ID#", "Account No."};
        List<List<String>> sample_csv_list = new ArrayList<>();
        // convert sample_csv into List for generate_unique_combinationValid
        int i = 0;
        for (String[] row: sample_csv){
            for (String cell: row){
                try{
                    sample_csv_list.get(i).add(cell);
                } catch (IndexOutOfBoundsException e){
                    sample_csv_list.add(new ArrayList<>());
                    sample_csv_list.get(i).add(cell);
                }
            }
            i++;
        }
        List<String> unique_combination = DataRecon.generate_unique_combination(sample_csv_list, col_no);
        Assertions.assertArrayEquals(expectation, unique_combination.toArray());
    }

    @org.junit.jupiter.api.Test
    void generate_unique_combinationInvalid() {
        String[] col_no = {"0", "1"};
        String[][] sample_csv = {{"Customer ID#","Account No.","Currency","Type","Balance"},{"ID1","BOS963211","USD","SAVINGS","962510"}};
        String[] unexpected = {"Customer ID#"};
        List<List<String>> sample_csv_list = new ArrayList<>();
        // convert sample_csv into List for generate_unique_combinationValid
        int i = 0;
        for (String[] row: sample_csv){
            for (String cell: row){
                try{
                    sample_csv_list.get(i).add(cell);
                } catch (IndexOutOfBoundsException e){
                    sample_csv_list.add(new ArrayList<>());
                    sample_csv_list.get(i).add(cell);
                }
            }
            i++;
        }
        List<String> unique_combination = DataRecon.generate_unique_combination(sample_csv_list, col_no);
        Assertions.assertFalse(Arrays.equals(unexpected, unique_combination.toArray()));
    }

    @org.junit.jupiter.api.Test
    void satisfyHeaderRequirementsValid() {
        // csv1 = [[Customer, Date, Time],
        //        [1,        2,     3]]
        // csv2 = [[Customer, Date, Time],
        //        [1,        2,     3  ]]
        // unique_combination = [Customer]
        List<String> header1 = new ArrayList<>();
        header1.add("Customer");
        header1.add("Date");
        header1.add("Time");
        List<String> header2 = new ArrayList<>();
        header2.add("Customer");
        header2.add("Date");
        header2.add("Time");
        List<String> unique_combination = new ArrayList<>();
        unique_combination.add("Customer");
        List<String> row2 = new ArrayList<>();
        row2.add("1");
        row2.add("2");
        row2.add("3");
        List<List<String>> csv1 = new ArrayList<>();
        csv1.add(header1);
        csv1.add(row2);
        List<List<String>> csv2 = new ArrayList<>();
        csv2.add(header2);
        csv2.add(row2);
        Assertions.assertTrue(DataRecon.satisfyHeaderRequirements(csv1,csv2,unique_combination));
    }

    @org.junit.jupiter.api.Test
    void satisfyHeaderRequirementsInvalid1() {
        // csv1 = [[Customer, Date, Time],
        //        [1,        2,     3]]
        // csv2 = [[Customer, Date, Time],
        //        [1,        2,     3  ]]
        // unique_combination = [Customer, Special]
        List<String> header1 = new ArrayList<>();
        header1.add("Customer");
        header1.add("Date");
        header1.add("Time");
        List<String> header2 = new ArrayList<>();
        header2.add("Customer");
        header2.add("Date");
        header2.add("Time");
        List<String> unique_combination = new ArrayList<>();
        unique_combination.add("Customer");
        unique_combination.add("Special");
        List<String> row2 = new ArrayList<>();
        row2.add("1");
        row2.add("2");
        row2.add("3");
        List<List<String>> csv1 = new ArrayList<>();
        csv1.add(header1);
        csv1.add(row2);
        List<List<String>> csv2 = new ArrayList<>();
        csv2.add(header2);
        csv2.add(row2);
        Assertions.assertFalse(DataRecon.satisfyHeaderRequirements(csv1,csv2,unique_combination));
    }

    @org.junit.jupiter.api.Test
    void satisfyHeaderRequirementsInvalid2() {
        // csv1 = [[Customer, Date, Day],
        //        [1,        2,     3]]
        // csv2 = [[Customer, Date, Time],
        //        [1,        2,     3  ]]
        // unique_combination = [Customer, Special]
        List<String> header1 = new ArrayList<>();
        header1.add("Customer");
        header1.add("Date");
        header1.add("Day");
        List<String> header2 = new ArrayList<>();
        header2.add("Customer");
        header2.add("Date");
        header2.add("Time");
        List<String> unique_combination = new ArrayList<>();
        unique_combination.add("Customer");
        unique_combination.add("Special");
        List<String> row2 = new ArrayList<>();
        row2.add("1");
        row2.add("2");
        row2.add("3");
        List<List<String>> csv1 = new ArrayList<>();
        csv1.add(header1);
        csv1.add(row2);
        List<List<String>> csv2 = new ArrayList<>();
        csv2.add(header2);
        csv2.add(row2);
        Assertions.assertFalse(DataRecon.satisfyHeaderRequirements(csv1,csv2,unique_combination));
    }

    @org.junit.jupiter.api.Test
    void getIndicesofUniqueCombinationValid() {
        // csv1 = [[Customer, Date, Day],
        //        [1,        2,     3]]
        // unique_combination = [Customer, Day]
        List<String> header1 = new ArrayList<>();
        header1.add("Customer");
        header1.add("Date");
        header1.add("Day");
        List<String> unique_combination = new ArrayList<>();
        unique_combination.add("Customer");
        unique_combination.add("Day");
        List<String> row2 = new ArrayList<>();
        row2.add("1");
        row2.add("2");
        row2.add("3");
        List<List<String>> csv1 = new ArrayList<>();
        csv1.add(header1);
        csv1.add(row2);
        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(2);
        Assertions.assertEquals(expected, DataRecon.getIndicesofUniqueCombination(csv1, unique_combination));
    }

    @org.junit.jupiter.api.Test
    void getIndicesofUniqueCombinationInvalid() {
        // csv1 = [[Customer, Date, Day],
        //        [1,        2,     3]]
        // unique_combination = [Customer, Special]
        List<String> header1 = new ArrayList<>();
        header1.add("Customer");
        header1.add("Date");
        header1.add("Day");
        List<String> unique_combination = new ArrayList<>();
        unique_combination.add("Customer");
        unique_combination.add("Special");
        List<String> row2 = new ArrayList<>();
        row2.add("1");
        row2.add("2");
        row2.add("3");
        List<List<String>> csv1 = new ArrayList<>();
        csv1.add(header1);
        csv1.add(row2);
        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(2);
        Assertions.assertFalse(expected.equals(DataRecon.getIndicesofUniqueCombination(csv1, unique_combination)));
    }

    @org.junit.jupiter.api.Test
    void generate_header_mapValid1() {
        // csv1 = [[Customer, Date, Day],
        //        [1,        2,     3]]
        // csv2 = [[Customer, Date, Day],
        //        [1,        2,     3  ]]
        // expectation = [0, 1, 2]
        List<String> header1 = new ArrayList<>();
        header1.add("Customer");
        header1.add("Date");
        header1.add("Day");
        List<String> header2 = new ArrayList<>();
        header2.add("Customer");
        header2.add("Date");
        header2.add("Day");
        List<String> row2 = new ArrayList<>();
        row2.add("1");
        row2.add("2");
        row2.add("3");
        List<List<String>> csv1 = new ArrayList<>();
        csv1.add(header1);
        csv1.add(row2);
        List<List<String>> csv2 = new ArrayList<>();
        csv2.add(header2);
        csv2.add(row2);
        List<Integer> expectation = new ArrayList<>();
        expectation.add(0);
        expectation.add(1);
        expectation.add(2);
        Assertions.assertEquals(expectation, DataRecon.generate_header_map(csv1, csv2));
    }

    @org.junit.jupiter.api.Test
    void generate_header_mapValid2() {
        // csv1 = [[Customer, Date, Day],
        //        [1,        2,     3]]
        // csv2 = [[Customer, Date, Day],
        //        [1,        2,     3  ]]
        // expectation = [0, 2, 1]
        List<String> header1 = new ArrayList<>();
        header1.add("Customer");
        header1.add("Day");
        header1.add("Date");
        List<String> header2 = new ArrayList<>();
        header2.add("Customer");
        header2.add("Date");
        header2.add("Day");
        List<String> row2 = new ArrayList<>();
        row2.add("1");
        row2.add("2");
        row2.add("3");
        List<List<String>> csv1 = new ArrayList<>();
        csv1.add(header1);
        csv1.add(row2);
        List<List<String>> csv2 = new ArrayList<>();
        csv2.add(header2);
        csv2.add(row2);
        List<Integer> expectation = new ArrayList<>();
        expectation.add(0);
        expectation.add(2);
        expectation.add(1);
        Assertions.assertEquals(expectation, DataRecon.generate_header_map(csv1, csv2));
    }

    @org.junit.jupiter.api.Test
    void generate_exceptionsValid1() {
        // csv1 = [[Customer, Day, Date],
        //        [1,        2,     3]]
        // csv2 = [[Customer, Date, Day],
        //        [1,        2,     3  ]]
        // unique_combination = [Customer]
        // expectation = [[1,2,3],
        //                [1,3,2]]
        List<String> header1 = new ArrayList<>();
        header1.add("Customer");
        header1.add("Day");
        header1.add("Date");
        List<String> header2 = new ArrayList<>();
        header2.add("Customer");
        header2.add("Date");
        header2.add("Day");
        List<String> row2 = new ArrayList<>();
        row2.add("1");
        row2.add("2");
        row2.add("3");
        List<List<String>> csv1 = new ArrayList<>();
        csv1.add(header1);
        csv1.add(row2);
        List<List<String>> csv2 = new ArrayList<>();
        csv2.add(header2);
        csv2.add(row2);
        List<List<String>> expectation = new ArrayList<>();
        List<String> expectation_row1 = new ArrayList<>();
        expectation_row1.add("1");
        expectation_row1.add("2");
        expectation_row1.add("3");
        List<String> expectation_row2 = new ArrayList<>();
        expectation_row2.add("1");
        expectation_row2.add("3");
        expectation_row2.add("2");
        expectation.add(expectation_row1);
        expectation.add(expectation_row2);
        List<String> unique_combination = new ArrayList<>();
        unique_combination.add("Customer");
        List<Integer> indexes1 = DataRecon.getIndicesofUniqueCombination(csv1, unique_combination);
        List<Integer> indexes2 = DataRecon.getIndicesofUniqueCombination(csv2, unique_combination);
        List<Integer> headerMap = DataRecon.generate_header_map(csv1, csv2);
        Assertions.assertEquals(expectation, DataRecon.generate_exceptions(csv1, csv2, indexes1, indexes2, headerMap));
    }

    @org.junit.jupiter.api.Test
    void generate_exceptionsValid2() {
        // csv1 = [[Customer, Date, Day],
        //        [1,        2,     4]]
        // csv2 = [[Customer, Date, Day],
        //        [1,        2,     3  ]]
        // unique_combination = [Customer]
        // expectation = [[1,2,4],
        //                [1,2,3]]
        List<String> header1 = new ArrayList<>();
        header1.add("Customer");
        header1.add("Date");
        header1.add("Day");
        List<String> header2 = new ArrayList<>();
        header2.add("Customer");
        header2.add("Date");
        header2.add("Day");
        List<String> row2 = new ArrayList<>();
        row2.add("1");
        row2.add("2");
        row2.add("3");
        List<String> row1 = new ArrayList<>();
        row1.add("1");
        row1.add("2");
        row1.add("4");
        List<List<String>> csv1 = new ArrayList<>();
        csv1.add(header1);
        csv1.add(row1);
        List<List<String>> csv2 = new ArrayList<>();
        csv2.add(header2);
        csv2.add(row2);
        List<List<String>> expectation = new ArrayList<>();
        List<String> expectation_row1 = new ArrayList<>();
        expectation_row1.add("1");
        expectation_row1.add("2");
        expectation_row1.add("4");
        List<String> expectation_row2 = new ArrayList<>();
        expectation_row2.add("1");
        expectation_row2.add("2");
        expectation_row2.add("3");
        expectation.add(expectation_row1);
        expectation.add(expectation_row2);
        List<String> unique_combination = new ArrayList<>();
        unique_combination.add("Customer");
        List<Integer> indexes1 = DataRecon.getIndicesofUniqueCombination(csv1, unique_combination);
        List<Integer> indexes2 = DataRecon.getIndicesofUniqueCombination(csv2, unique_combination);
        List<Integer> headerMap = DataRecon.generate_header_map(csv1, csv2);
        Assertions.assertEquals(expectation, DataRecon.generate_exceptions(csv1, csv2, indexes1, indexes2, headerMap));
    }

}