# Testing
2 forms of testing was performed on the Data Reconciliation program.

## Unit Testing
Unit testing was implemented using Junit5 and it can be found in the DataReconTest.java.
To run the unit tests, make sure to change the working directory of the run configuration to src for the tests to run correctly.

## System Testing
System testing was implemented using python, using the os and subprocess modules. 
Scripts are written in advance to enter the inputs for the Data Reconciliation program.

The program is tested using different sample files and the results are checked against the pre-computed results stored under test_files.

The program is also tested usign wrong inputs to check the robustness of the program.
