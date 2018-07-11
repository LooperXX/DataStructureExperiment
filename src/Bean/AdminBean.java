package Bean;

public class AdminBean {
    private String email;
    private String psw;

    public AdminBean() {
    }

    public AdminBean(String email, String psw) {

        this.email = email;
        this.psw = psw;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    @Override
    public String toString() {
        return "AdminBean{" +
                "email='" + email + '\'' +
                ", psw='" + psw + '\'' +
                '}';
    }
}
