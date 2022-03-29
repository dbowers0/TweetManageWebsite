package servletPackage;

import java.io.IOException;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class simpleServlet
 */
@WebServlet("/simpleServlet")
public class simpleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static int visitCount;
	private static TweetCollection myData;
	private Set<Long> ids;

	public simpleServlet() {
		super();
		myData = new TweetCollection("./servletPackage/testProcessed.txt",200);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		visitCount++;
		String submit = (String)request.getParameter("submit_parameter");
		String username = (String)request.getParameter("text_parameter");
		//System.out.println(username);
		String password = request.getParameter("text_parameter2");
		//System.out.println(password);
		if(submit!=null)
		{
			if(username.equals("md") && password.equals("pw"))
			{
				RequestDispatcher rd2=request.getRequestDispatcher("/index2.html");
				rd2.forward(request,response);
			}
			else
			{
				RequestDispatcher rd2=request.getRequestDispatcher("/index.html");
				rd2.forward(request,response);
			}

		}	
		String x = request.getParameter("submitUser");

		if (x != null && x.equals("submit")) {
			String label = "tweetUser";
			String labelValue = request.getParameter("tweetUsername");
			ids = myData.getTweetIdsByUser(request.getParameter("tweetUsername"));
			String[] idsStr = new String[ids.size()];
			int index = 0;
			for(Long long1 : ids)
			{
				idsStr[index] = long1.toString();
				index++;
			}
			//System.out.println(idsStr);
			
			String label1="username";
			String label1Value = "";
			index = 0;
			for(Long long1: ids)
			{
				label1Value += long1.toString() + " ";
			}
			System.out.println("WHAT");
			System.out.println(label1);
			System.out.println(label1Value);
			request.setAttribute(label, labelValue);
			request.setAttribute(label1,label1Value);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/display.jsp");
			dispatcher.forward(request, response);
		}
		String button = request.getParameter("addTweet");
		if(button != null && button.equals("submit"))
		{
			String id = request.getParameter("tweetId");
			Long id1 = Long.parseLong(id);
			String username1 = request.getParameter("tweetUsername1");
			String tweet = request.getParameter("tweet");
			String polarity = request.getParameter("radio_parameter");
			int polarity1 = Integer.parseInt(polarity);

			Tweet t = new Tweet(polarity1,id1,username1,tweet);
			myData.addTweet(t);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/addTweet.jsp");
			dispatcher.forward(request, response);
		}
		String remove = request.getParameter("Remove");
		if(remove != null && remove.equals("submit"))
		{
			String id2 = request.getParameter("tweetId2");
			Long id3 = Long.parseLong(id2);
			myData.removeTweet(id3);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/remove.jsp");
			dispatcher.forward(request, response);
			//System.out.println(myData);
		}
		String predict = request.getParameter("predictButton");
		if(predict != null && predict.equals("submit"))
		{
			String getTweet = request.getParameter("predictTweet");
			int prediction1 = myData.predict1(getTweet);
			String prediction = String.valueOf(prediction1);
			String predictLabel = "Prediction";
			request.setAttribute(predictLabel, prediction);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/predict.jsp");
			dispatcher.forward(request, response);
		}
		String logout = request.getParameter("Logout");
		if(logout!=null)
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("/index.html");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
