import os
import subprocess
import unittest
import pandas as pd
from faker import Faker
import random

class TestDataReconProgram(unittest.TestCase):

    def setUp(self) -> None:
        # compile the program
        os.system("javac DataRecon.java")

        return super().setUp()

    def test_fuzzing(self):
        
        def remove_brackets(lst):
            new_lst = []
            for entry in lst:
                new_entry = entry.replace("{", "").replace('}', "")
                new_lst.append(new_entry)
            return new_lst

        def generate_random_csv(filename:str):
            no_of_columns = random.randint(1, 20)
            no_of_rows = random.randint(1, 20)

            column_flavours = ('{{binary}}', '{{null_boolean}}', '{{image}}', '{{name}}', '{{address}}', '{{json}}', '{{md5}}', '{{password}}', '{{sha1}}', '{{sha256}}', '{{tar}}', '{{uuid4}}', '{{zip}}', '{{ssn}}', "{{color}}", "{{safe_hex_color}}", "{{http_method}}", "{{ipv4}}", "{{mac_address}}")

            fake = Faker()

            columns = [column_flavours[random.randint(0, len(column_flavours)-1)] for x in range(no_of_columns)]

            csv = fake.csv(header = remove_brackets(columns), data_columns=columns, num_rows=no_of_rows, include_row_ids=False)

            with open(filename, "w") as fout:
                fout.write(csv)

            return no_of_columns

        # run the program 2 million times with random csv files, check if the program crashes
        # in any of the iterations
        for i in range(2000000):
            # generate 2 random csv files first
            cols1 = generate_random_csv("test_file1.csv")
            cols2 = generate_random_csv("test_file2.csv")

            # generate the script file
            with open("test_script.txt", "w") as fout:
                fout.write("test_file1.csv\n")
                fout.write("test_file2.csv\n")
                num_of_columns = random.randint(1, min(cols1, cols2)-1)
                columns = [random.randint(0, min(cols1, cols2)-1) for x in range(num_of_columns)]
                fout.write(",".join(str(x) for x in columns))

            # load the script file
            input_file = open("test_script.txt", "r")

            data_recon_prog = subprocess.Popen(
                ["java", "DataRecon"],
                stdin=input_file, 
                stdout=subprocess.DEVNULL
            )
            data_recon_prog.wait()
            input_file.close()

            # check that the program successfully exited
            self.assertEqual(data_recon_prog.returncode, 0)

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


