# Test cases added:
```java
@Test
    public void valid_file() {
        /*
        user enters a CSV file, but without a .csv file extension.
        Since it wasn't specified in the program's README the 
        program should accept the file and generate the correct 
        output
        */

        CsvFile csv_file1 = RecordChecker.read_csv(
            "sample_file_1"
        );

        assertEquals(csv_file1.num_rows(), 1000);
        assertEquals(csv_file1.num_columns(), 5);
        assertArrayEquals(csv_file1.get_headers(), new String[] {
            "Customer ID#", "Account No.", "Currency",
            "Type", "Balance"
        });

        CsvFile csv_file3 = RecordChecker.read_csv(
            "sample_file_3"
        );

        assertEquals(csv_file3.num_rows(), 1000);
        assertEquals(csv_file3.num_columns(), 5);
        assertArrayEquals(csv_file3.get_headers(), new String[] {
            "Customer ID#", "Account No.", "Currency",
            "Type", "Balance"
        });
    }
```

# Testing results:
Charles' testing was very comprehensive and thorough.
No bugs were found.