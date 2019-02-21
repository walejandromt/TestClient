package testClient;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.elpagofacil.ear.ejb.HelloEjb;

public class RunClientEJB {

	private Context context = null;

    public void createInitialContext() throws NamingException {
        Properties prop = new Properties();
        prop.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        prop.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        prop.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:8081");
        prop.put(Context.SECURITY_PRINCIPAL, "adminAA");
        prop.put(Context.SECURITY_CREDENTIALS, "adminTT");
        prop.put("jboss.naming.client.ejb.context", false);

        context = new InitialContext(prop);
    }

    public HelloEjb lookup() throws NamingException {
        String toLookup = "elpagofacil-ear-2.1.2.RELEASE/HelloEjbImpl!org.elpagofacil.ear.ejb.HelloEjb";
        return (HelloEjb) context.lookup(toLookup);
    }

    public String getEJBRemoteMessage() {
        try {
            // 1. Obtaining Context
            createInitialContext();
            // 2. Generate JNDI Lookup name and caste
            HelloEjb helloWorld = lookup();
            return helloWorld.sayHello();
        } catch (NamingException e) {
            e.printStackTrace();
            return "";
        } finally {
            try {
                closeContext();
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeContext() throws NamingException {
        if (context != null) {
            context.close();
        }
    }

    public static void main(String args[]){
    	RunClientEJB helloEjb = new RunClientEJB();
        System.out.println(helloEjb.getEJBRemoteMessage());
    }


}
