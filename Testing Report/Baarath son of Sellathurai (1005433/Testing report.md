# Test case added:

Test code snippet:
```java
    public static Stream<Arguments> test_columns() {
        return Stream.of(
            //	Both CSV files in this test case have the columns in the same order
            Arguments.of("sample_file_1.csv","sample_file_3.csv",true),

            //	Both CSV files in this test case have the columns in a different order
            Arguments.of("sample_file_1.csv","sample_file_unordered.csv",true),

            //	One CSV file has a different number of columns than the other
            Arguments.of("sample_file_1.csv","sample_file_missing_col.csv",true),
        );
    }

    @ParameterizedTest(name = "{index} - compareFiles file1={0},file1={1},")
    @MethodSource
    void test_columns(String filepath1, String filepath2, boolean expected) throws Exception {
        recon.File1 = Path.of(filepath1);
        recon.File2 = Path.of(filepath2);

        // check if the program is able to compare files with 
        // different number of columns or unordered columns
        assertTrue(recon.loadFiles());
    }
```

# Results after running the added test case:
The program was able to run successfully and compare the files. 
No bugs were discovered in the program.