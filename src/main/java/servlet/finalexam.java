/** *****************************************************************
    finalexam.java   servlet example

        @author Jeff Offutt
********************************************************************* */
package servlet;
// Import Java Libraries
import java.io.*;
import java.util.*;

//Import Servlet Libraries
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import javax.servlet.annotation.WebServlet;

// finalexam class
// CONSTRUCTOR: no constructor specified (default)
//
// ***************  PUBLIC OPERATIONS  **********************************
// public void doPost ()  --> prints a blank HTML page
// public void doGet ()  --> prints a blank HTML page
// private void PrintHead (PrintWriter out) --> Prints the HTML head section
// private void PrintBody (PrintWriter out) --> Prints the HTML body with
//              the form. Fields are blank.
// private void PrintBody (PrintWriter out, String lhs, String rhs, String rslt)
//              Prints the HTML body with the form.
//              Fields are filled from the parameters.
// private void PrintTail (PrintWriter out) --> Prints the HTML bottom
//***********************************************************************
@WebServlet(name = "finalexam", urlPatterns = {"/finalexamfile"})
public class finalexam extends HttpServlet
{


static ArrayList<Integer> intList = new ArrayList<Integer>();

static String OperationAdd = "Add";
static String OperationMean = "Average/Mean";
static String OperationMedian = "Median";
static String OperationMode = "Mode";
static String OperationSD = "Standard Deviation";
static String OperationReset = "Reset";

static String radioOption = "yes";

// Other strings.
static String Style ="https://www.cs.gmu.edu/~offutt/classes/432/432-style.css";

/** *****************************************************
 *  Overrides HttpServlet's doPost().
 *  Converts the values in the form, performs the operation
 *  indicated by the submit button, and sends the results
 *  back to the client.
********************************************************* */
public void doPost (HttpServletRequest request, HttpServletResponse response)
   throws ServletException, IOException
{
   String addStr   = new String("");
   String operation = request.getParameter("Operation");
   String usernumber = request.getParameter("STR1");
   String radop = request.getParameter("radiobtn");
   
   int tmp = Integer.parseInt(usernumber);  
   double result = 0;
   
   if (radop.equals(radioOption)) {
   		ArrayList<Integer> newList = new ArrayList<Integer>();
   		for (Integer element : intList) {
  
            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {
  
                newList.add(element);
            }
        }
        intList = newList;
   }
   
   if (operation.equals(OperationAdd)) {
   	//System.out.println(strInput);
   	intList.add(tmp);
   	//result = tmp;
   }
   
   if (operation.equals(OperationReset)) {
   	//System.out.println(strInput);
   	intList.clear();
   } 
   
   if (operation.equals(OperationMean)) {
   	
    result = mean(intList);
   }

   if (operation.equals(OperationMedian)) {
   	ArrayList<Integer> tmplist = (ArrayList<Integer>) intList.clone();
   	Collections.sort(tmplist);
   	double middle = tmplist.size()/2;
    if (tmplist.size()%2 == 0) {
        middle = (tmplist.get(tmplist.size()/2) + tmplist.get(tmplist.size()/2 - 1))/2;
    } else {
        middle = tmplist.get(tmplist.size() / 2);
    }
    result = middle;
   
   }
   
   if (operation.equals(OperationMode)) {
   	int maxValue = 0, maxCount = 0, i, j;

      for (i = 0; i < intList.size(); ++i) {
         int count = 0;
         for (j = 0; j < intList.size(); ++j) {
            if (intList.get(j) == intList.get(i))
            ++count;
         }

         if (count > maxCount) {
            maxCount = count;
            maxValue = intList.get(i);
         }
      }
   	result = maxValue;
    }

	if (operation.equals(OperationSD)) {
		result = sd(intList);
	
	}

   response.setContentType("text/html");
   PrintWriter out = response.getWriter();
   PrintHead(out);
   //System.out.println(str1);
   PrintBody(out, usernumber, result, intList);
   PrintTail(out);
}  // End doPost


public double mean(ArrayList<Integer> intList) {
	double total = 0;
   	double avg = 0;
    for(int i = 0; i < intList.size(); i++)
    {
        total += intList.get(i);
        avg = total / intList.size(); // finding ther average value
    }
	return avg;
}


public double sd(ArrayList<Integer> table) {
	double mean = mean(table);
    double temp = 0;

    for (int i = 0; i < table.size(); i++)
    {
        int val = table.get(i);

        // Step 2:
        double squrDiffToMean = Math.pow(val - mean, 2);

        // Step 3:
        temp += squrDiffToMean;
    }

    // Step 4:
    double meanOfDiffs = (double) temp / (double) (table.size());

    // Step 5:
    return Math.sqrt(meanOfDiffs);

}

/** *****************************************************
 *  Overrides HttpServlet's doGet().
 *  Prints an HTML page with a blank form.
********************************************************* */
public void doGet (HttpServletRequest request, HttpServletResponse response)
       throws ServletException, IOException
{
   response.setContentType("text/html");
   PrintWriter out = response.getWriter();
   PrintHead(out);
   PrintBody(out);
   PrintTail(out);
} // End doGet

/** *****************************************************
 *  Prints the <head> of the HTML page, no <body>.
********************************************************* */
private void PrintHead (PrintWriter out)
{
   out.println("<html>");
   out.println("");

   out.println("<head>");
   out.println("<title>Final Exam</title>");
   out.println(" <link rel=\"stylesheet\" type=\"text/css\" href=\"" + Style + "\">");
   out.println("</head>");
   out.println("");
} // End PrintHead

/** *****************************************************
 *  Prints the <BODY> of the HTML page with the form data
 *  values from the parameters.
********************************************************* */
private void PrintBody (PrintWriter out, String usernumber, double result, ArrayList<Integer> intList)
{
   out.println("<body style=\"text-align:center; background-color:#e0bbe4;\">");
   out.println("<h1> SWE 432 Final Exam! </h1>");
   out.println("<p style=\"text-align:center;\">");
   out.println("<h3> Instructions:</h3>");
   out.println("Please enter integers one at a time and add it to the list by clicking the \"Add\" button.<br>");
   out.println(" Next, choose if you want to remove duplicates or not from the list.<br>");
   out.println(" Lastly, click on one of the four stats functions to be performed on the list of integers and then see the result field.");
   out.println("</p><br>");
   out.print  ("<form method=\"post\"");
   out.println(" action=\"finalexamfile\"");
   out.println("");
   out.println(" <table>");
   out.println("  <tr>");
   out.println("   <td>Type an integer:");
   out.println("   <td><input type=\"text\" name=\"STR1\" value=\"" + usernumber + "\" size=10>");
   out.println("  </tr>");
   out.println(" <input type=\"submit\" value=\"" + OperationAdd + "\" name=\"Operation\">");
   out.println(" <input type=\"submit\" value=\"" + OperationReset + "\" name=\"Operation\"><br>");
   out.println("<p style=\"text-align:center;\">" + intList + "</p>");
   out.println(" </table>");
   out.println(" <br>");
   out.println("   <td>Remove duplicates:");
   out.println("   <td><input type=\"radio\" id=\"yes\" name=\"radiobtn\" value=\"yes\">");
   out.println("   <label for=\"yes\">Yes");
   out.println("   </label>");
   out.println("   <td><input type=\"radio\" id=\"no\" name=\"radiobtn\" value=\"no\">");
   out.println("   <label for=\"no\">No");
   out.println("   </label><br>");
   out.println("   <input type=\"hidden\" name=\"radiobtn\" value=\"no\">");
   out.println("  </tr><br>");
   out.println(" <input type=\"submit\" value=\"" + OperationMean + "\" name=\"Operation\">");
   out.println(" <input type=\"submit\" value=\"" + OperationMedian + "\" name=\"Operation\">");
   out.println(" <input type=\"submit\" value=\"" + OperationMode + "\" name=\"Operation\">");
   out.println(" <input type=\"submit\" value=\"" + OperationSD + "\" name=\"Operation\">");
   out.println("  <tr><br><br>");
   out.println("   <td>Result:");
   out.println("   <td><input type=\"text\" name=\"STR2\" value=\"" + result + "\" size=20>");
   out.println("  </tr><br>");
   out.println("</form>");
   out.println("<br>");
   out.println("<br>");
   out.println("<p style=\"text-align:center;\"> By: Yumna Fatima </p>");
   out.println("");
   out.println("</body>");
} // End PrintBody

/** *****************************************************
 *  Overloads PrintBody (out,lhs,rhs,rslt) to print a page
 *  with blanks in the form fields.
********************************************************* */
private void PrintBody (PrintWriter out)
{
   PrintBody(out, "", 0, intList);
}

/** *****************************************************
 *  Prints the bottom of the HTML page.
********************************************************* */
private void PrintTail (PrintWriter out)
{
   out.println("");
   out.println("</html>");
} // End PrintTail

}  // End finalexam
