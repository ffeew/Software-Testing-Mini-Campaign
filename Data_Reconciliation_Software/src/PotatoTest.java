import static org.junit.Assert.*;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/*
change the working directory in the run configuration
to be src before running the test
*/
public class PotatoTest {
    @Test
    public void test_file_name() {
        boolean is_csv = DataRecon.isCSV("test.csv");
        assertTrue(is_csv);
    }

    @Test
    public void bad_file_name() {
        try {
            DataRecon.isCSV("test");
        } catch (Exception e) {
            assertTrue(e instanceof IndexOutOfBoundsException);
            return;
        }

        fail("isCsv should thrown an error");
    }

    @Test
    public void chain_file_name() {
        boolean is_csv = DataRecon.isCSV("test.example.csv");
        assertTrue(is_csv);
    }

    @Test
    public void chain_file_name_v2() {
        boolean is_csv = DataRecon.isCSV("test.csv.py");
        assertFalse(is_csv);
    }

    @Test
    public void fuzz_file_name() {
        // fuzz that isCsv returns true for random string inputs
        // that end in .csv
        Random generator = new Random();

        for (int k=0; k<100; k++) {
            int length = 5 + generator.nextInt(5);
            String rand = RandomString.generate(length);
            String filename = rand + ".csv";
            boolean is_csv = DataRecon.isCSV(filename);
            assertTrue(is_csv);
        }
    }

    @Test
    public void test_recon() {
        InputStream stdin = System.in;
        String[] inputs = (new String[] {
            "sample_file_1.csv",
            "sample_file_2.csv",
            "0"
        });

        String input_data = String.join("\n", inputs);

        try {
            System.setIn(new ByteArrayInputStream(input_data.getBytes()));
            DataRecon.main(new String[]{});
        } catch (Exception e) {
            System.out.println("TEST EXAMPLE FAILED");
            e.printStackTrace();
            fail("TEST EXAMPLE FAILED");
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    public void test_recon_neg() {
        InputStream stdin = System.in;
        String[] inputs = (new String[] {
            "sample_file_1.csv",
            "sample_file_2.csv",
            "-1"
        });

        String input_data = String.join("\n", inputs);

        try {
            System.setIn(new ByteArrayInputStream(input_data.getBytes()));
            DataRecon.main(new String[]{});
        } catch (Exception e) {
            System.out.println("TEST EXAMPLE FAILED");
            e.printStackTrace();
            fail("TEST EXAMPLE FAILED");
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    public void test_recon_neg_space() {
        InputStream stdin = System.in;
        String[] inputs = (new String[] {
                "sample_file_1.csv",
                "sample_file_2.csv",
                " -1"
        });

        String input_data = String.join("\n", inputs);

        try {
            System.setIn(new ByteArrayInputStream(input_data.getBytes()));
            DataRecon.main(new String[]{});
        } catch (Exception e) {
            System.out.println("TEST EXAMPLE FAILED");
            e.printStackTrace();
            fail("TEST EXAMPLE FAILED");
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    public void test_recon_commas() {
        InputStream stdin = System.in;
        String[] inputs = (new String[] {
            "sample_file_1.csv",
            "sample_file_2.csv",
            "1, 2"
        });

        String input_data = String.join("\n", inputs);

        try {
            System.setIn(new ByteArrayInputStream(input_data.getBytes()));
            DataRecon.main(new String[]{});
        } catch (Exception e) {
            System.out.println("TEST EXAMPLE FAILED");
            e.printStackTrace();
            fail("TEST EXAMPLE FAILED");
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    public void test_recon_space() {
        InputStream stdin = System.in;
        String[] inputs = (new String[] {
            "sample_file_1.csv ",
            "sample_file_2.csv",
            "1"
        });

        String input_data = String.join("\n", inputs);

        try {
            System.setIn(new ByteArrayInputStream(input_data.getBytes()));
            DataRecon.main(new String[]{});
        } catch (Exception e) {
            System.out.println("TEST EXAMPLE FAILED");
            e.printStackTrace();
            fail("TEST EXAMPLE FAILED");
        } finally {
            System.setIn(stdin);
        }
    }
}