package com.raddle.crud.dao.naming;

import java.util.Hashtable;
import java.util.Map;

import javax.naming.NamingException;

import org.springframework.mock.jndi.SimpleNamingContextBuilder;

/**
 *
 * @author xurong
 *
 */
public class SimpleNamingContext implements org.springframework.beans.factory.InitializingBean {

    private static SimpleNamingContextBuilder contextBuilder = null;
    static {
        try {
            contextBuilder = SimpleNamingContextBuilder.emptyActivatedContextBuilder();
        } catch (NamingException e) {
            throw new RuntimeException("Simple JNDI created failed", e);
        }
    }

    private Map<String, Object> jndiObjects = new Hashtable<String, Object>();

    @Override
    public void afterPropertiesSet() throws IllegalArgumentException, NamingException {
        if (jndiObjects != null) {
            for (String key : jndiObjects.keySet()) {
                contextBuilder.bind(key, jndiObjects.get(key));
            }
        }
    }

    public final Map<String, Object> getJndiObjects() {
        return jndiObjects;
    }

    public void setJndiObjects(Map<String, Object> jndiObjects) {
        this.jndiObjects = jndiObjects;
    }

}
