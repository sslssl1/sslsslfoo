package wrapper;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.servlet.http.*;


public class EnCrytionWrapper extends HttpServletRequestWrapper {

	public EnCrytionWrapper(HttpServletRequest request) {
		super(request);
	}
	
	/**request 안에 전달된 이름을 꺼내는 일...*/
	@Override
	public String getParameter(String name) {
		String result = null;
		if(name != null && name.equals("userPwd"))
		{
			result = getSha512(super.getParameter("userPwd"));
		}else 
		{
			result = super.getParameter(name);
		
		}
		
		return result;
	}
	
	//암호화 처리용 메소드
	private static String getSha512(String passwd)
	{
		String enCrypPwd = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			byte[] pwdBytes = passwd.getBytes(Charset.forName("UTF-8"));
			md.update(pwdBytes);
			
			enCrypPwd=Base64.getEncoder().encodeToString(md.digest());
		} catch (NoSuchAlgorithmException e) {
			 e.printStackTrace();
		}

		return enCrypPwd;
	}
	

}
