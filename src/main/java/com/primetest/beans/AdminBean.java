package com.primetest.beans;

import com.primetest.Utils.Utils;
import com.primetest.admin.Account;
import com.primetest.admin.Password;
import com.primetest.database.DBLoadData;
import com.primetest.database.DBUploadData;
import org.primefaces.PrimeFaces;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;


@ManagedBean(name = AdminBean.BEAN_NAME)
@SessionScoped
public class AdminBean implements Serializable {
    static final String BEAN_NAME = "accountAdminManagedBean";
    private PrimeFaces instance = PrimeFaces.current();
    private boolean isLogin = false;
    private static Account account;
    private Account createAccount;
    private String message = "";
    private String user = "";
    private String pass = "";

    public AdminBean() {
        account = new Account();
        this.createAccount = new Account();
    }

    public void verifyLogin() {
        if (!this.isLogin) {
            doRedirect("login.xhtml");
        }
    }

    public void login() {
        if (!(this.user.equals("")) && !(this.pass.equals(""))) {
            if (Password.checkPassword(user, pass)) {
                this.isLogin = true;
                this.message = "";
                this.account = DBLoadData.getAccount(user);
                doRedirect("index.xhtml");
            } else {
                this.message = "Account's Invalid";
                doRedirect("login.xhtml");
            }
        } else {
            this.message = "Textarea's is empty!";
        }
    }

    public void createNewAccount(){
        if (this.createAccount!=null) {
            this.createAccount.setId(Utils.generateID());
            DBUploadData.createNewUser(this.createAccount);
            closeDialogCreateUser();
        }
    }

    public void openDialogCreateUser() {
        instance.executeScript("PF('newUser').show()");
    }

    public void closeDialogCreateUser() {
        instance.executeScript("PF('newUser').hide()");
    }

    public void logout() {
        this.isLogin = false;
        doRedirect("login.xhtml");
    }

    private void doRedirect(String url) {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.getExternalContext().redirect(url);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /* ---  Getters & Setters --- */

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public static Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Account getCreateAccount() {
        return createAccount;
    }

    public void setCreateAccount(Account createAccount) {
        this.createAccount = createAccount;
    }


}