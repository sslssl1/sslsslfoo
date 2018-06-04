package member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.vo.SMTPAuthenticator;

/**
 * Servlet implementation class BusinessApprovaSendEmailServlet
 */
@WebServlet("/bapprovsendemail")
public class BusinessApprovaSendEmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BusinessApprovaSendEmailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			String mail_from = "FOODING" + "<khfooding@gmail.com>";
			String mail_to = request.getParameter("business_email");
			String title = null;
			String contents = null;
		
				title = "FOODING BUSINESS APPROVAL EMAIL";
				contents = "축하합니다! FOODING 사업자 승인이 완료되셨습니다! "
						+ "회원님은 사이트를 자유롭게 이용하실수 있습니다.";
			

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
			if( request.getAttribute("business_email")!=null) {
			PrintWriter out = response.getWriter();
			System.out.println("실행됨");
			out.append("축하합니다! FOODING 사업자 승인이 완료되셨습니다! "
					+ "회원님은 사이트를 자유롭게 이용하실수 있습니다.");
			
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("request_failed.jsp");

		} finally {

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
