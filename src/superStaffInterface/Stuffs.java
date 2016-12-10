package superStaffInterface;

/**
 * Created by Alish on 07.12.2016.
 */
public class Stuffs {

    private int idRoots;
    private String login;
    private String password;
    private String RootName;

    public Stuffs (int idRoots, String login, String password, String rootName){
        this.idRoots = idRoots;
        this.login = login;
        this.password = password;
        this.RootName = rootName;
    }

    public int getIdRoots() {
        return idRoots;
    }

    public void setIdRoots(int idRoots) {
        this.idRoots = idRoots;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRootName() {
        return RootName;
    }

    public void setRootName(String rootName) {
        RootName = rootName;
    }
}
