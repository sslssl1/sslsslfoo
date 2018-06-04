package member.model.vo;

import javax.mail.PasswordAuthentication;

import javax.mail.Authenticator;

 

/**

 * @author viper9

 *

 */

public class SMTPAuthenticator extends Authenticator {

    public SMTPAuthenticator() {

        super();

    }

 

    public PasswordAuthentication getPasswordAuthentication() {

        String username = "khfooding@gmail.com";

        String password = "A!123456";

        return new PasswordAuthentication(username, password);

    }

}