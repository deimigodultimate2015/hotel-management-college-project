package controller.ui;

import java.net.URL;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.dao.NhanVienCRUD;
import model.dao.NhanVienDAO;
import model.entities.NhanVien;
import model.helper.CreateDialog;
import model.helper.DialogCreator;

public class ForgotPasswordController implements Initializable {

    @FXML
    private Button btnExit;

    @FXML
    private Button btnChangeCommit;

    @FXML
    private TextField txtEmail;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnExit.setOnAction(e -> {
			DangNhapController.stage.close();
		});
		
		btnChangeCommit.setOnAction(e -> {
			NhanVien nv = new NhanVienCRUD().getNhanVienByEmail(txtEmail.getText());
			if(nv == null) {
				CreateDialog.createStandarDialog("Đổi mật khẩu", "Đổi mật khẩu thất bại", "Không tìm thấy tài khoản chứa email", AlertType.WARNING);
				new DialogCreator().makeThatTextfieldError(txtEmail);
				return;
			}
			
			sendMail(nv);
			
			
		});
	}
	public void sendMail(NhanVien nv) {
		Random rnd = new Random();
    	int n = 100000 + rnd.nextInt(900000);
    	nv.setMatKhau(n+"");
    	new NhanVienDAO().update(nv);
    	String getEmail = nv.getEmail();
    	String usernameSS = nv.getManv();
    	
    	try {
    		Properties props = System.getProperties();

            // ******************** FOR PROXY ******************

            // props.setProperty("proxySet","true");
            // props.setProperty("socksProxyHost","9.10.11.12");
            // props.setProperty("socksProxyPort","80");
            // props.setProperty("socksProxyVersion","5");

            props.setProperty("mail.smtp.host", "smtp.gmail.com");

            // ******************** FOR SSL ******************
            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
            props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            props.setProperty("mail.smtp.port", "465");
            props.setProperty("mail.smtp.socketFactory.port", "465");

            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");
            props.put("mail.debug", "true");
            props.put("mail.store.protocol", "pop3");
            props.put("mail.transport.protocol", "smtp");
            final String username = "thotx1999@gmail.com";
            final String password = "orga000000";
            Session session = Session.getDefaultInstance(props,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            // -- Create a new message --
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("thotx1999@gmail.com"));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(getEmail, false));
            msg.setSubject("Yêu cầu đổi mật khẩu "+usernameSS);
            msg.setSentDate(new Date());

            // **************** Without Attachments ******************
            msg.setText("Đây là mật khẩu mới của bạn: "+n);


            Transport.send(msg);
            System.out.println("Message sent.");

    	} catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	CreateDialog.createStandarDialog("Đổi mật khẩu", "Đổi mật khẩu thành công", "Mật khẩu mới đã được gửi tới email của bạn", AlertType.INFORMATION);
    	btnExit.fire();
	}

}
