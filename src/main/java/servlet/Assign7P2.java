/** *********************************************************
    twoButtons.java   servlet example
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

import java.util.ArrayList;

// twoButtons class
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
@WebServlet(name = "Assign7P2", urlPatterns = {"/getStrings2"})
public class Assign7P2 extends HttpServlet
{

// Button labels

 static ArrayList<String> strList = new ArrayList<String>();
 static ArrayList<String> pickedAlready = new ArrayList<String>();
 static ArrayList<String> outputList = new ArrayList<String>();

 static String OperationAdd = "Add";
 static String OperationGetItem = "Get Selected Item";
 static String OperationReset = "Reset";
 static String OperationSubmit = "Submit";


 static String Option1 = "Random String"; 
 static String Option2 = "Random String with Replacement";
 static String Option3 = "Random String without Replacement";
 static String Option4 = "Sorted Order";
 static String Option5 = "Reverse Sorted Order";
 static String Option6 = "Numeric Sorted Order";
 static String Option7 = "Eliminate Duplicates Sorted Order";
// Other strings.
static String Style ="";

/** *****************************************************
 *  Overrides HttpServlet's doPost().
 *  Converts the values in the form, performs the operation
 *  indicated by the submit button, and sends the results
 *  back to the client.
********************************************************* */
public void doPost (HttpServletRequest request, HttpServletResponse response)
   throws ServletException, IOException
{

   Object mainlist = request.getAttribute("mainlist");
   Object pickedlist = request.getAttribute("pickedlist");
   Object outputlist = request.getAttribute("outputlist");
   
   String tmp = "";

   outputList.clear();

   String operation = request.getParameter("Operation");
   String strInput = request.getParameter("strInput");
   String op = request.getParameter("Options");


   if (operation.equals(OperationAdd)) {
   	//System.out.println(strInput);
   	strList.add(strInput);
   }
   
   if (operation.equals(OperationReset)) {
   	//System.out.println(strInput);
   	strList.clear();
   	pickedAlready.clear();
   } 
   
   //System.out.println(operation);
   if (operation.equals(OperationSubmit)) {

   if (op.equals(Option1) || op.equals(Option2)) {
   	//call random string method
   	tmp = getRandomString(strList);
   	pickedAlready.add(tmp);
   	outputList.add(tmp);

   }
   if (op.equals(Option3)) {
   	//call without replac

	for (String str: strList) {
		if (!pickedAlready.contains(str)){
			//System.out.println(str);
			outputList.add(str);
		}
	}
	//System.out.println(outputList);
	tmp = getRandomString(outputList);
	pickedAlready.add(tmp);
	outputList.clear();
	outputList.add(tmp);

   }
   if (op.equals(Option4)) {
   	// sort list
   	outputList = (ArrayList<String>) strList.clone();
   	Collections.sort(outputList);
   }
   if (op.equals(Option5)) {
   	// reserve sort list
    outputList = (ArrayList<String>) strList.clone();
   	Collections.sort(outputList);
   	Collections.reverse(outputList);
   }

   if (op.equals(Option6)) {
		outputList = numericSort(strList);	
	}

	if (op.equals(Option7)) {
		outputList = remDupSort(strList);	
	}

   }

   response.setContentType("text/html");
   PrintWriter out = response.getWriter();
   PrintHead(out);
   PrintBody(out, strList, outputList);
   
}  // End doPost


public String getRandomString(ArrayList<String> lis) {

	if (lis.size() == 0) {
		return "";
	}
    return lis.get(new Random().nextInt(lis.size()));
}

public ArrayList<String> remDupSort(ArrayList<String> list){ 

	Set<String> rmSet = new HashSet(list); 
	ArrayList<String> rmList = new ArrayList<String>(rmSet); 
	Collections.sort(rmList);
	return rmList;
}

public ArrayList<String> numericSort(ArrayList<String> list) { 
	ArrayList<Integer> res = new ArrayList<Integer>();
	ArrayList<String> rtn = new ArrayList<String>();
	try {
		for (String i : list) {
    	res.add(Integer.parseInt(i)); 
    	Collections.sort(res); //sort new list of ints
    	}
    	for (Integer q: res) {
    		rtn.add(String.valueOf(q));
		}
	} catch (Exception e) {
		rtn.add("no numeric strings present in the list.");
	}

    return rtn;
}

/** *****************************************************
 *  Prints the <head> of the HTML page, no <body>.
********************************************************* */
private void PrintHead (PrintWriter out)
{
		out.println("<html>");
		out.println("");

		out.println("<head>");
		out.println("<title>SWE 432</title>");
		out.println("</head>");
		out.println("");
} // End PrintHead

/** *****************************************************
 *  Prints the <BODY> of the HTML page with the form data
 *  values from the parameters.
********************************************************* */
private void PrintBody (PrintWriter out, ArrayList<String> strList, ArrayList<String> outputList)
{

		out.println("<body>");
		out.println("<h1> Welcome to SWE 432! </h1>");
		out.println("<p>Instructions:</p><p>Type one string at a time and click the add button</p>");
		out.print  ("<form method=\"post\" action=\"getStrings2\">");
		out.println("<label> Enter a string: </label>");
		out.println(" <input type=\"text\" name=\"strInput\">");
		out.println(" <input type=\"submit\" value=\"" + OperationAdd + "\" name=\"Operation\">");
		out.println(" <input type=\"submit\" value=\"" + OperationReset + "\" name=\"Operation\">");
		out.println("<p>" + strList + "</p>");
		out.println("<br>");
		out.println("<br>");
		out.println("<label> Choose one to display: </label>");
		out.println("<select id=\"options\" name=\"Options\" >");
		out.println("<option value=\"" + Option1 + "\"> Random String </option>");
		out.println("<option value=\"" + Option2 + "\">  Random String with Replacement </option>");
		out.println("<option value=\"" + Option3 + "\">  Random String without Replacement </option>");
		out.println("<option value=\"" + Option4 + "\">  Sorted Order </option>");
		out.println("<option value=\"" + Option5 + "\">  Reverse Sorted Order </option>");
		out.println("<option value=\"" + Option6 + "\">  Numeric Sorted Order</option>");
		out.println("<option value=\"" + Option7 + "\">  Eliminate Duplicates Sorted Order</option>");
		out.println("</select>");
		out.println(" <input type=\"submit\" value=\"" + OperationSubmit + "\" name=\"Operation\">"); 
		out.println("<br>");
		out.println("<br>");
		out.println("<label> Result: </label>");
		out.println("<p id=\"result\">" + outputList + "</p>");
		out.println("</form>");
		out.println("<br>");
		out.println("<br>");
		out.println("<p> Yumna Fatima, Shruti Gupta, Samapriya Dandibhotla </p>");
		out.println("<p> We had two meetings together to work on this assignment 7. One person shared the screen and the other two gave ideas and helped write the code. Also, all design decisions were made collaboratively. Both of out servlet files Assign7P1 and Assign7P2 cna be found in our Github repository. </p>");
		out.println("</body>");
		out.println("</html>");

} // End PrintBody

}  // End twoButtons