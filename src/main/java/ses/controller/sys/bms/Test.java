package ses.controller.sys.bms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;

public class Test {

    @NotNull(message = "名称不能为空")   
	private String name;
    @Pattern(regexp="^-?[1-9]\\d*$", message="只能输入汉字")
	private String password;
    @Email(message = "邮箱格式不正确")  
	private String email;
    @Pattern(regexp="^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$", message="只能输入汉字")
	private String idNumer;
    @Pattern(regexp="^(1)[0-9]{10}$", message="手机格式错误")
    private String mobile;
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdNumer() {
		return idNumer;
	}

	public void setIdNumer(String idNumer) {
		this.idNumer = idNumer;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
}
