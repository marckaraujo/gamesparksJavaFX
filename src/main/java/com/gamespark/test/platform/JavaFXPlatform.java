/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamespark.test.platform;

import com.gamespark.test.model.User;
import com.gamesparks.sdk.GS;
import com.gamesparks.sdk.IGSPlatform;
import java.io.File;
import java.util.Base64;
import java.util.List;
import javafx.application.Platform;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.kairos.core.Context;

/**
 *
 * @author marcus
 */
public class JavaFXPlatform implements IGSPlatform {

    private Context ctx;
    private static GS gs;
    private static final String PERSISTENCE = "persistence";
    private static EntityManager manager;
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE);
 
    public static GS initialise(final Context ctx, String apiKey, String secret, boolean liveMode, boolean autoUpdate) {
        if (gs == null) {
            JavaFXPlatform gsFXPlatform = new JavaFXPlatform(ctx);

            gs = new GS(apiKey, secret, liveMode, autoUpdate, gsFXPlatform);
        }

        return gs;
    }

    private JavaFXPlatform(Context ctx) {
        this.ctx = ctx;
    }

    public static GS gs() {
        return gs;
    }

    @Override
    public File getWritableLocation() {
        return new File(System.getProperty("java.io.tmpdir"));
    }

    @Override
    public void executeOnMainThread(Runnable job) {
        Platform.runLater(job);
    }

    @Override
    public String getPlayerId() {
        manager = emf.createEntityManager();
        List<User> result = (List<User>) manager.createQuery("FROM User").getResultList();
        return result.get(0).getPlayerId();
    }

    @Override
    public String getAuthToken() {
        manager = emf.createEntityManager();
        List<User> result = (List<User>) manager.createQuery("FROM User").getResultList();
        return result.get(0).getToken();
    }

    @Override
    public void setPlayerId(String string) {
        manager = emf.createEntityManager();
        List<User> result = (List<User>) manager.createQuery("FROM User").getResultList();
        manager.getTransaction().begin();
        result.get(0).setPlayerId(string);
        manager.getTransaction().commit();
    }

    @Override
    public void setAuthToken(String string) {
        System.out.println("AuthToken: " + string);
        manager = emf.createEntityManager();
        List<User> result = (List<User>) manager.createQuery("FROM User").getResultList();
        manager.getTransaction().begin();
        result.get(0).setToken(string);
        manager.getTransaction().commit();
    }

    @Override
    public Object getHmac(String nonce, String secret) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            return Base64.getEncoder().encodeToString(sha256_HMAC.doFinal(nonce.getBytes("UTF-8")));
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public void logMessage(String msg) {
        System.out.println(msg);
    }

    @Override
    public void logError(Throwable t) {
        System.out.println(t.getMessage());
    }

}

