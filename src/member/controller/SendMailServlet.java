package member.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import member.model.vo.*;
import java.math.*;

/**
 * Servlet implementation class sendMailServlet
 */
@WebServlet("/sendmail")
public class SendMailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SendMailServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");

		int vCode = 0;
		try {
			String mail_from = "FOODING" + "<khfooding@gmail.com>";
			String mail_to = request.getParameter("email");
			String title = null;
			String contents = null;
			
			System.out.println("attr : "+ request.getAttribute("userCSId"));
	
			if ((request.getAttribute("userCSId")!=null || request.getAttribute("userBSId")!=null) ) {
				// 아이디 찾기에서 실행
				System.out.println("메일 아이디 찾기");
				title = "FOODING FIND ID EMAIL";
				if(request.getAttribute("userCSId")!=null) {
				contents = "푸딩 아이디 찾기 결과 <br>일반 회원 아이디 : " + request.getAttribute("userCSId");
				}else if(request.getAttribute("userBSId")!=null) {
				contents = "푸딩 아이디 찾기 결과 <br>사업자 회원 아이디 : "+request.getAttribute("userBSId");					
				}
				
				
				
			} else if (request.getAttribute("userPwd") != null) {
				// 비밀번호 찾기에서 실행
				System.out.println("메일 비번 찾기");
				title = "FOODING FIND PASSWORD EMAIL";
				contents = "비밀번호 변경을 시도하셨습니다.";
			} else {
				// 인증번호 보내기
				System.out.println("메일 인증번호");
				title = "FOODING VERIFICATION CODE EMAIL";
				vCode = (int) (Math.random() * 8999 + 1000);
				contents = "Verification Code : " + vCode;
			}

			
			mail_from = new String(mail_from.getBytes("UTF-8"), "UTF-8");
			mail_to = new String(mail_to.getBytes("UTF-8"), "UTF-8");
			Properties props = new Properties();
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "465");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", "false");
			props.put("mail.smtp.auth", "true");
			Authenticator auth = new SMTPAuthenticator();
			Session sess = Session.getDefaultInstance(props, auth);
			MimeMessage msg = new MimeMessage(sess);
			msg.setFrom(new InternetAddress(mail_from));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(mail_to));
			msg.setSubject(title, "UTF-8");
			msg.setContent(contents, "text/html; charset=UTF-8");
			msg.setHeader("Content-type", "text/html; charset=UTF-8");
			Transport.send(msg);
			
			
			
			
			
			if( request.getAttribute("userCSId")==null && request.getAttribute("userBSId")==null && request.getAttribute("userPwd")==null) {
				
			PrintWriter out = response.getWriter();
			out.append(String.valueOf(vCode));
			
			}
			else if(  request.getAttribute("userCSId")!=null || request.getAttribute("userBSId")!=null) {
				RequestDispatcher view=request.getRequestDispatcher("views/logIn/logIn.jsp");
				view.forward(request, response);
			}else if ( request.getAttribute("userPwd")!=null  ){
				String userId = String.valueOf( request.getAttribute("userId"));
				RequestDispatcher view=request.getRequestDispatcher("views/logIn/pwd_Edit.jsp");
				request.setAttribute("userId", userId);
				System.out.println("sddafasdas");
				System.out.println(userId);
				view.forward(request, response);
			}
			

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("request_failed.jsp");

		} finally {

		}
		
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
