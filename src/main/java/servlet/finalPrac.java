/** *****************************************************************
    finalPrac.java   servlet example

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

// finalPrac class
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
@WebServlet(name = "finalPrac", urlPatterns = {"/finalexampractice"})
public class finalPrac extends HttpServlet
{


static String OperationAdd = "String1String2String3";
static String OperationAdd2 = "String1String3String2";
static String OperationAdd3 = "String2String1String3";
static String OperationAdd4 = "String2String3String1";
static String OperationAdd5 = "String3String1String2";
static String OperationAdd6 = "String3String2String1";
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
   String str1 = request.getParameter("STR1");
   String str2 = request.getParameter("STR2");
   String str3 = request.getParameter("STR3");
   String radop = request.getParameter("radiobtn");
   
   if (radop.equals(radioOption)) {
   		StringBuffer sbr1 = new StringBuffer(str1);
   		StringBuffer sbr2 = new StringBuffer(str2);
   		StringBuffer sbr3 = new StringBuffer(str3);
   		sbr1.reverse();
   		sbr2.reverse();
   		sbr3.reverse();
   		str1 = sbr1.toString();
   		str2 = sbr2.toString();
   		str3 = sbr3.toString();
   }
   
   if (operation.equals(OperationAdd))
   {
      addStr = new String(str1 + str2 + str3);
   }
   else if (operation.equals(OperationAdd2))
   {
      addStr = new String(str1 + str3 + str2);
   }
   else if (operation.equals(OperationAdd3))
   {
      addStr = new String(str2 + str1 + str3);
   }
   else if (operation.equals(OperationAdd4))
   {
      addStr = new String(str2 + str3 + str1);
   }
   else if (operation.equals(OperationAdd5))
   {
      addStr = new String(str3 + str1 + str2);
   }
   else if (operation.equals(OperationAdd6))
   {
      addStr = new String(str3 + str2 + str1);
   }

   response.setContentType("text/html");
   PrintWriter out = response.getWriter();
   PrintHead(out);
   //System.out.println(str1);
   PrintBody(out, str1, str2, str3, addStr);
   PrintTail(out);
}  // End doPost

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
   out.println("<title>Two strings example</title>");
   out.println(" <link rel=\"stylesheet\" type=\"text/css\" href=\"" + Style + "\">");
   out.println("</head>");
   out.println("");
} // End PrintHead

/** *****************************************************
 *  Prints the <BODY> of the HTML page with the form data
 *  values from the parameters.
********************************************************* */
private void PrintBody (PrintWriter out, String str1, String str2, String str3, String addStr)
{
   out.println("<body style=\"text-align:center; background-color:#e0bbe4;\">");
   out.println("<p style=\"text-align:center;\">");
   out.println("A simple example that demonstrates how to concatenate");
   out.println("three strings.");
   out.println("</p>");
   out.print  ("<form method=\"post\"");
   out.println(" action=\"finalexampractice\"");
   out.println("");
   out.println(" <table>");
   out.println("  <tr>");
   out.println("   <td>String 1:");
   out.println("   <td><input type=\"text\" name=\"STR1\" value=\"" + str1 + "\" size=10>");
   out.println("  </tr><br>");
   out.println("  <tr>");
   out.println("   <td>String 2:");
   out.println("   <td><input type=\"text\" name=\"STR2\" value=\"" + str2 + "\" size=10>");
   out.println("  </tr><br>");
   out.println("  <tr>");
   out.println("   <td>String 3:");
   out.println("   <td><input type=\"text\" name=\"STR3\" value=\"" + str3 + "\" size=10>");
   out.println("  </tr><br>");
   out.println("  <tr>");
   out.println("   <td>Reverse strings:");
   out.println("   <td><input type=\"radio\" id=\"yes\" name=\"radiobtn\" value=\"yes\">");
   out.println("   <label for=\"yes\">Yes");
   out.println("   </label>");
   out.println("   <td><input type=\"radio\" id=\"no\" name=\"radiobtn\" value=\"no\">");
   out.println("   <label for=\"no\">No");
   out.println("   </label><br>");
   out.println("   <input type=\"hidden\" name=\"radiobtn\" value=\"no\">");
   out.println("  </tr><br>");
   out.println("  <tr>");
   out.println("   <td>Result:");
   out.println("   <td><input type=\"text\" name=\"STR2\" value=\"" + addStr + "\" size=20>");
   out.println("  </tr><br>");
   out.println(" </table>");
   out.println(" <br>");
   out.println(" <br>");
   out.println(" <input type=\"submit\" value=\"" + OperationAdd + "\" name=\"Operation\">");
   out.println(" <input type=\"submit\" value=\"" + OperationAdd2 + "\" name=\"Operation\">");
   out.println(" <input type=\"submit\" value=\"" + OperationAdd3 + "\" name=\"Operation\">");
   out.println(" <input type=\"submit\" value=\"" + OperationAdd4 + "\" name=\"Operation\">");
   out.println(" <input type=\"submit\" value=\"" + OperationAdd5 + "\" name=\"Operation\">");
   out.println(" <input type=\"submit\" value=\"" + OperationAdd6 + "\" name=\"Operation\">");
   out.println("</form>");
   out.println("");
   out.println("</body>");
} // End PrintBody

/** *****************************************************
 *  Overloads PrintBody (out,lhs,rhs,rslt) to print a page
 *  with blanks in the form fields.
********************************************************* */
private void PrintBody (PrintWriter out)
{
   PrintBody(out, "", "", "", "");
}

/** *****************************************************
 *  Prints the bottom of the HTML page.
********************************************************* */
private void PrintTail (PrintWriter out)
{
   out.println("");
   out.println("</html>");
} // End PrintTail

}  // End finalPrac
