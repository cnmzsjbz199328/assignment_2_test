## Practical 1

### Getting Started

**_It is important that you fully read the instructions before you begin to code!_**

### Task 1

The file `BmiCalculator.java` contains code to calculate the BMI after entering a weight (in kilograms) and a height (in
metres).  
Read the code to ensure you understand how this class works.

For this task you are to write tests to ensure the <samp>constructor</samp> and the <samp>accessor</samp> (getter)
methods are working correctly, and a test using the `@ParameterizedTest` annotation to ensure that the `calculateBMI`
method is behaving as it should.

> <hintfont><b><i>EXPECTATION:</i></b> You should have at least 2 tests to comprehensively test this class</hintfont>

---

### Task 2

The file `Factorial.java` contains a program that calls the factorial method to compute the factorials of integers
passed as a parameter.

**_Write a class with a_** <samp>main</samp> **_method to see how it works._**  <br><br>
Try several positive integers, then try a negative number. You should find that it works for small positive integers (
values < 21), but that it returns a large negative value for larger integers and that it always returns 1 for negative
integers.

1. Returning 1 as the factorial of any negative integer is not correct (mathematically, the factorial function is not
   defined for negative integers). To correct this, you could modify your factorial method to check if the argument is
   negative, but then what? The method must return a value, and even if it prints an error message, whatever value is
   returned could be misconstrued. Instead, it should throw an exception indicating that something went wrong, so it
   could not complete its calculation. You could define your own exception class, but there is already an exception
   appropriate for this situation -`IllegalArgumentException`, which extends RuntimeException. Modify your
   program as follows:
   - Modify the header of the `factorial` method to indicate that factorial can throw an `IllegalArgumentException`.
   - Modify the body of `factorial` to check the value of the argument and, if it is negative, throw an
     `IllegalArgumentException`.
      <details>
       <summary><hintfont><b>Hint:</b></hintfont></summary>

     > What you pass to <i>throw</i> is actually an instance of the <code>IllegalArgumentException</code> class,
     and that the constructor takes a <code>String</code> parameter. Use this parameter to be specific about what
     the problem is (see the output below).

      </details>
   - Run your Factorials program after making these changes. Now when you enter a negative
     number an exception will be thrown, terminating the program. The program ends because the exception
     is not caught, so it is thrown by the main method, causing a runtime error.
   - Modify the main method in your Factorials class to catch the exception thrown by factorial and print an
     appropriate message, but then continue with the loop. Think carefully about where you will need to put
     the `try` and `catch`.
2. Returning a negative number for values over 16 also is not correct. The problem is arithmetic overflow - the
   factorial is bigger than can be represented by an int. This can also be thought of as an `IllegalArgumentException` -
   _this factorial_ method is only defined for arguments up to 16. Modify your code in the `factorial` method to check
   for an argument over 16 as well as for a negative argument. You should throw an `IllegalArgumentException` in either
   case, but pass different messages to the constructor so that the problem is clear (see the output below).<br><br>

   A run of the program could look like the following (user input is shown <b><mark>highlighted</mark></b>):  <br><br>

   **<tt>
   Enter an integer: <mark>5</mark>  
   Factorial(5) = 120  
   Another factorial? (y/n): <mark>y</mark>  
   Enter an integer: <mark>-2</mark>  
   java.lang.IllegalArgumentException: Factorial is undefined for negative integers  
   Another factorial? (y/n): <mark>y</mark>  
   Enter an integer: <mark>25</mark>  
   java.lang.IllegalArgumentException: Factorial(25) is too large - overflow occurs!  
   Another factorial? (y/n): <mark>y</mark>  
   Enter an integer: <mark>8</mark>  
   Factorial(8) = 40320  
   Another factorial? (y/n): <mark>y</mark>  
   Enter an integer: <mark>20</mark>  
   Factorial(20) = 2432902008176640000   
   Another factorial? (y/n): <mark>n</mark>  
   </tt>**<br>

3. Once you have completed writing the <samp>factorial</samp> method, write tests to ensure that it behaves as it
   should.  <br><br>

   > <hintfont><b><i>EXPECTATION:</i></b> You should have at least 3 tests to comprehensively test this
   method</hintfont>

### Task 3
## Build and Test

This task requires you to **build** a simple Frog program and, in addition, **JUnit tests** to ensure that the
program conforms to the specifications.

The program is built in four stages, so one strategy might be to complete a stage and then write the tests.

However, you are free to build and test as you see fit. Marks will be awarded depending on how many stages you complete.

### Test Requirements

You will be expected to use a variety of tests to ensure the specifications are met, but at a minimum each test class
and test method will have a `@DisplayName` and you should, where practicable, use annotations such
as `@ParameterizedTest`, with possibly different sources of input.

You should also look at how the assertions are used, and you may need to use a variety of these, such as:
*  `assertAll`
*  `assertTrue`
*  `assertFalse`
*  `assertEquals`


You should aim for 100% coverage where possible.

### Build Specifications

#### Stage 1

The following class definitions are included:

```java
   public class FrogProgram {
       public static void main(String[] args) {
           Frog myFrog = new Frog();
           myFrog.setName("Fred");
           System.out.println(myFrog);
       }
   }
   
   public class Frog() {
       private String name = "Name not set";
       private final int id = -1;   // -1 used to indicate it has not been set
   
       public Frog() {
           // Constructor currently does nothing
           // not code in the body of the constructor
       }
   
       public void setName(String name) {
           this.name = name;
       }
   
       public String toString() {
           return "**** Start of print method ****\n "
                   + "Frog's name is " + name
                   + "Id is " + id;
       }
   }
```

Compile and run the application. It should produce the output:  <br><br>
__<samp>**** Start of print method ****  
Frog’s name is Fred  
Id is -1</samp>__

Add a method call to the **main** method which sets the instance variable `id`, contained in the object
referred to by `myFrog`, to 1000.

Compile and run the application. It should produce the output:   <br><br>
__<samp>
**** Start of print method ****  
Frog’s name is Fred  
Id is 1000  
</samp>__

Modify the program in the following ways.

1. Declare another instance variable in the class `Frog` to store a Frog’s age in days. Its initial value should be 0.
2. Add code to the **main** method to assign the instance variable `age`, in the object referred to in my `myFrog`, to
   22 (use the same strategy as for the `id`)
3. Define a method called `printAgeGroup` which has no formal parameters and which returns the age group of the frog
   according to the following table:

   | Age                | Age Group          |
   |:-------------------|:-------------------|
   | 0 to 20 days       | young              |
   | 21 to 50 days      | juvenile           |
   | more than 50 days  | adult              |

4. Add a (call) to the `printAgeGroup` method (in the object referred to by `myFrog`) to the end of the **main** method.
5. Run the application from IntelliJ . The following output should be produced:  <br><br>
   __<samp>
   **** Start of print method ****  
   Frog’s name is Fred  
   Id is 1000  
   Age group is juvenile
   </samp>__

---

#### Stage 2

Modify the program developed in Stage 1 in the following ways.

1. Add a constructor in the class `Frog` that has three formal parameters, `name`,
   `id` and `age`, and assigns the corresponding instance variables, `name`, `id` and `age` to the formal
   parameters.
2. Create the original `Frog` using this new constructor
3. Add a declaration and statements to the end of the **main** method to create another `Frog`
   with the `name` _Fran_, `id` of _1001_ and an `age` of _75_ (use the newly created constructor). The variable
   `myFrog1` should be used to store a reference to the new frog. Include calls to `print` and `printAgeGroup` for this
   frog.
4. Run the application. The following output should be produced:  <br><br>
   __<samp>**** Start of print method ****  
   Frog’s name is Fred  
   Id is 1000  
   Age group is juvenile  
   **** Start of print method ****  
   Frog’s name is Fran  
   Id is 1001  
   Age group is adult</samp>__  <br><br>

5. Add accessor (getter) methods for the `name`, `id` and `age` in the `Frog` class

---

#### Stage 3

Modify the program developed in Stage 2 in the following ways.

1. Add a definition of a method called `alterName` to the class `Frog` which **_returns_** the altered name using the
   following translation table:   
   (**_DO NOT CHANGE_** the name, the original name should still be printed if a call to `getName()` was made.)

   |                            |                            |
   |:--------------------------:|:--------------------------:|
   | **Original Letter** &emsp; | **Letter after _altered_** |  
   |             a              |             e              |
   |             n              |             e              |
   |             r              |             a              |
   |             F              |             a              |
   |             d              |             i              |

2. At the end of the **main** method add calls to the `alterName` method for both frogs and a call to print the `name`
   of each frog using the accessor methods created in Stage 2
3. Run the application. The following output should be produced:<br><br>

   __<samp>
   **** Start of print method ****  
   Frog's name is Fred  
   Id is 1000  
   Age group is juvenile  
   **** Start of print method ****  
   Frog's name is Fran  
   Id is 1001  
   Age group is adult  
   aaei  
   aaee  
   Frog's name is Fred  
   Frog's name is Fran   
   </samp>__

---

#### Stage 4

Modify the program developed in Stage 3 in the following ways.

1. Add a definition of a method called `firstAlphabetically` to the class `Frog` which takes another `Frog`
   as a formal parameter and returns the `frog` (object reference) whose name comes first alphabetically. For
   example,
   ```java
   public static void main (String args) {
    // code to create frogs ....
      Frog f = myFrog.firstAlphabetically(myFrog1);
      System.out.println(f);
   }
    ```
   Once you have completed the method, add the statements above (those between the asterisks) to the end of the <b>
   main</b> method which should cause the following lines to be printed: <br><br>
   __<samp>
   **** Start of print method ****  
   Frog's name is Fran  
   Id is 1001  
   </samp>__  <br>

2. Add a new method `shiftName(int shift)` so that instead of altering to a fixed letter in a Frog's name, it alters it
   to a letter that is `shift` characters later in the alphabet and returns the shifted name (a negative `shift` will
   throw an IndexOutOfBoundsException with the message "_Negative indexes are not supported_")  - see below for
   examples.
   <br><br>
   Example if `shift` was 5 then:

   |                            |                            |
   |:--------------------------:|:--------------------------:|
   | **Original Letter** &emsp; | **Letter after _altered_** |  
   |             a              |             f              |
   |             g              |             l              |
   |             n              |             s              |
   |             x              |             c              |
   |             z              |             e              |
   |             F              |             K              |

   and if we applied this as follows:

   ```java
    public static void main (String args) {
    // code to create frogs ....
        System.out.println(myFrog.shiftName(5));
        System.out.println(myFrog1.shiftName(15));
        System.out.println(myFrog1.shiftName(-5));
   }
   ```
   then the following output would be printed:  
   **<samp>
   Kwji  
   Ugpc   
   Exception in thread "main" java.lang.IndexOutOfBoundsException: Negative indexes are not supported    
   at Frog.shiftName(Frog.java:76)  
   at FrogProgram.main(FrogProgram.java:47)
   </samp>**

  <dl>
   <dt>Hint:</dt>
   <dd>
   In the ascii table <b><kbd>a</kbd></b> is the integer 97 and <b><kbd>z</kbd></b> is the integer 122. 
<br>Similarly, <b><kbd>A</kbd></b> is the integer 65 and <b><kbd>Z</kbd></b> is the integer 90. <br>
So one strategy might be to convert the characters to integers and apply the shift, then convert them back to characters.
   </dd>
  </dl>
   Add the following statements to the end of the <b>main</b> method.

   ```java
   public static void main (String args) {
    // code to create frogs ....
       System.out.println(myFrog.shiftName(5));
       System.out.println(myFrog.shiftName(10));
       System.out.println(myFrog.shiftName(15));
       System.out.println(myFrog1.shiftName(5));
       System.out.println(myFrog1.shiftName(10));
       System.out.println(myFrog1.shiftName(15));
       System.out.println(myFrog1.getName());
       System.out.println(myFrog1.shiftName(-5));
    }
   ```

Once completed the final output should be (line numbers in the exception may be different):

**<samp>
**** Start of print method ****  
Frog's name is Fred  
Id is 1000  
Age group is juvenile  
**** Start of print method ****  
Frog's name is Fran  
Id is 1001  
Age group is adult  
**** Start of print method ****  
Frog's name is Fran  
Id is 1001  
Kwji  
Pbon  
Ugts  
Kwfs  
Pbkx  
Ugpc  
Fran  
Exception in thread "main" java.lang.IndexOutOfBoundsException: Negative indexes are not supported  
at Frog.shiftName(Frog.java:76)  
at FrogProgram.main(FrogProgram.java:47)  
</samp>**

---

<style>
hintfont {
color: red
}
</style>