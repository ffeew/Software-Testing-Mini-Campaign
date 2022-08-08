import os
import subprocess
import unittest

class TestDataReconProgram(unittest.TestCase):

    def setUp(self) -> None:
        # compile the program
        os.system("javac DataRecon.java")

        return super().setUp()

    def test_normal_working_behavior_1_3(self):
        """
        test_files/sample_file_1.csv
        test_files/sample_file_2.csv
        0
        """
        input_file = open("system_test_scripts/system_test_script1", "r")

        data_recon_prog = subprocess.Popen(
            ["java", "DataRecon"],
            stdin=input_file, 
            stdout=subprocess.DEVNULL
        )
        data_recon_prog.wait()
        input_file.close()
        self.assertEqual(data_recon_prog.returncode, 0)

        # open up the exceptions CSV after it has been generated
        f1 = open("Exceptions.csv", "r")
        exceptions = f1.read()
        f1.close()

        f2 = open("test_files/expectation_1_3_0.csv", "r")
        expectations = f2.read()
        f2.close()

        self.assertEqual(exceptions, expectations)
    
    def test_normal_working_behavior_1_2(self):
        """
        test_files/sample_file_1.csv
        test_files/sample_file_3.csv
        0
        """
        input_file = open("system_test_scripts/system_test_script2", "r")

        data_recon_prog = subprocess.Popen(
            ["java", "DataRecon"],
            stdin=input_file, 
            stdout=subprocess.DEVNULL
        )
        data_recon_prog.wait()
        input_file.close()

        # open up the exceptions CSV after it has been generated
        f1 = open("Exceptions.csv", "r")
        exceptions = f1.read()
        f1.close()

        f2 = open("test_files/expectation_1_2_0.csv", "r")
        expectations = f2.read()
        f2.close()

        self.assertEqual(exceptions, expectations)

    def test_normal_working_behavior_1_2_01_more_than_1_column(self):
        """
        test_files/sample_file_1.csv
        test_files/sample_file_2.csv
        0,1
        """
        input_file = open("system_test_scripts/system_test_script3", "r")

        data_recon_prog = subprocess.Popen(
            ["java", "DataRecon"],
            stdin=input_file, 
            stdout=subprocess.DEVNULL
        )
        data_recon_prog.wait()
        input_file.close()

        # open up the exceptions CSV after it has been generated
        f1 = open("Exceptions.csv", "r")
        exceptions = f1.read()
        f1.close()

        f2 = open("test_files/expectation_1_2_01.csv", "r")
        expectations = f2.read()
        f2.close()

        self.assertEqual(exceptions, expectations)

    def test_normal_working_behavior_1_3_01_more_than_1_column(self):
        """
        test_files/sample_file_1.csv
        test_files/sample_file_3.csv
        0,1
        """
        input_file = open("system_test_scripts/system_test_script4", "r")

        data_recon_prog = subprocess.Popen(
            ["java", "DataRecon"],
            stdin=input_file, 
            stdout=subprocess.DEVNULL
        )
        data_recon_prog.wait()
        input_file.close()

        # open up the exceptions CSV after it has been generated
        f1 = open("Exceptions.csv", "r")
        exceptions = f1.read()
        f1.close()

        f2 = open("test_files/expectation_1_3_01.csv", "r")
        expectations = f2.read()
        f2.close()

        self.assertEqual(exceptions, expectations)
    
    def test_wrong_file_extension(self):
        """
        test_files/sample_file_1.csv
        test_files/sample_file_3.csv
        0,1
        """
        input_file = open("system_test_scripts/system_test_script5", "r")

        data_recon_prog = subprocess.Popen(
            ["java", "DataRecon"],
            stdin=input_file,
            stdout=subprocess.DEVNULL,
            stderr=subprocess.DEVNULL
        )
        data_recon_prog.wait()
        self.assertNotEqual(data_recon_prog.returncode, 0)
        input_file.close()

    def test_file_not_found(self):
        """
        sample_file_1.csv
        sample_file_3.csv
        0,1
        """
        input_file = open("system_test_scripts/system_test_script6", "r")

        data_recon_prog = subprocess.Popen(
            ["java", "DataRecon"],
            stdin=input_file,
            stdout=subprocess.DEVNULL,
            stderr=subprocess.DEVNULL
        )
        data_recon_prog.wait()
        self.assertNotEqual(data_recon_prog.returncode, 0)
        input_file.close()

    def tearDown(self) -> None:
        try:
            # check if Exceptions.csv is present
            fin = open("Exceptions.csv", "r")
            fin.close()
            os.remove("Exceptions.csv")
        except:
            pass
        return super().tearDown()

unittest.main()


