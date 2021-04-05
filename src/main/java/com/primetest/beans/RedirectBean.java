package com.primetest.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ManagedBean(name = RedirectBean.BEAN_NAME)
@SessionScoped
public class RedirectBean implements Serializable {
    static final String BEAN_NAME = "redirect";
    private final FacesContext facesContext = FacesContext.getCurrentInstance();

    public void doRedirect(String url) {
        try {
            facesContext.getExternalContext().redirect(url);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
