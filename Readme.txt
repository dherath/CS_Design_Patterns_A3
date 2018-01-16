## CS 542 - Assignment 3

Name - Jerome Dinal Herath Muthukumaranage

Assuming you are in the directory containing this README:

## Initial steps:
Before cleaning, compiling & running follow the steps below:
    1. navigate into the directory airportSecurityState starting from
    the directory containing the README.txt
        (use cd airportSecurityState from terminal for linux)

## To clean:
ant -buildfile src/build.xml clean

--------------------------------------------------------
## To compile: 
ant -buildfile src/build.xml all

--------------------------------------------------------
## To run by specifying arguments from command line

ant -buildfile src/build.xml run -Darg0=src/input.txt -Darg1=src/output.txt -Darg2=0 

(assuming the input file is "input.txt" and "output.txt" the output file 
within the src/ directory)

The input parameter for -Darg2 should be integer values between 0 & 4.
Each integer value would trigger different logger levels.

    0 - No output would be printed on terminal.(output will be written to file only)
    1 - prints the parameters for no.of days, no.of travelers & no.of prohibited items
        for each line.
    2 - prints the values for calculated results for average traffic per day & average 
        prohibited items per day for each line.
    3 - prints the changes of airport states when it occurs.
    4 - prints when the main classes are constructed.   

1)  place the text file within the src folder for the exact commands to work.
    Otherwise please note that the absolute path for  files must be given.
2)  Assuming the output.txt files were not present at runtime then the program will create 
    such text files within the specified path.Even then the absolute path must be given.
3)  If input arguments are invalid, an exception will be thrown and the program exits.

--------------------------------------------------------
## Note :

1) The following exceptions are handled within this program.
    a. If any input line is empty an exception is thrown and the program exits.
    b. If the format for each line is not Day:<d1>;TOD:<d1>;Airline:<d1>;Item:<d1>
        an exception is thrown and the program exits.
    c. For the data member for 'Day', the days must be in ascending order.Therefore 
       an entry with value '1' cannot exist after say '3'.
    d. For the data member for TOD, the format for 24 hour clock is strictly followed.
        (9:30 is considered invalid, it must be 09:30)    
    e. Therefore conditions for hour<=24 and minutes<=60 must be strictly followed.           
    f. Similar to the case of Day, the same ascending order must be followed for time
       entries for a specific day. 
    g. The data entry for Day, TOD(hour,minutes) must be a numeric value.
    {If any of these conditions get violated an exception would be thrown and the program exits.}

2) Cases when an entry for a day is not present is considered to be valid.(i.e: day 2 might not
    exist between day 1 or day 3)

3) For a given day, there could be two entries with the same TOD.(there could be travelers who 
   arrive at same time)    

4) Only items in the list {Gun, NailCutter, Blade, Knife} are considered to be prohibited.

--------------------------------------------------------
"I have done this assignment completely on my own. I have not copied
it, nor have I given my solution to anyone else. I understand that if
I am involved in plagiarism or cheating I will have to sign an
official form that I have cheated and that this form will be stored in
my official university record. I also understand that I will receive a
grade of 0 for the involved assignment for my first offense and that I
will receive a grade of F for the course for any additional
offense.”

[Date: 10/19/2017]

--------------------------------------------------------


