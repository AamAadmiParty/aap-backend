package com.next.aap.web.controller.login;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.WebRequest;

import com.next.aap.core.util.EnvironmentUtil;

@Configuration
public class SocialConfig {
	
	private static final String appId = "618283854899839";
	private static final String appSecret = "4a8d9a3005c298dc9176b0990a996ea6";
	private static final String localAppId = "372184272903954";
	private static final String localAppSecret = "bec883d1ffb415fff01248f8c46f78f9";
	
	private String twitterConsumerKey = "QwAtfFGQ4XyH2qSFX0UOg";
	private String twitterConsumerSecret = "5fmM9fVoDTIgHqKb8OeZ9cZullLdbL0uSrcC3mrTyM";
	
	@Autowired
	private DataSource dataSource;

    @Bean
    public ConnectionFactoryLocator connectionFactoryLocator() {
        ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
        if(EnvironmentUtil.isProductionEnv()){
        	registry.addConnectionFactory(new FacebookConnectionFactory(appId,appSecret));
        }else{
            registry.addConnectionFactory(new FacebookConnectionFactory(localAppId,localAppSecret));
        }
        
        registry.addConnectionFactory(new TwitterConnectionFactory(twitterConsumerKey, twitterConsumerSecret));
        return registry;
    }
    
    @Bean
    @Scope(value="singleton", proxyMode=ScopedProxyMode.INTERFACES)
    public UsersConnectionRepository usersConnectionRepository() {
        return new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator(), textEncryptor());
    }
    
    @Bean
    public ConnectController connectController() {
        ConnectController controller = new ConnectController(
            connectionFactoryLocator(), usersConnectionRepository().createConnectionRepository("ravit"));
        //controller.setApplicationUrl(environment.getProperty("application.url");
        controller.addInterceptor(new ConnectInterceptor<Twitter>() {

			@Override
			public void preConnect(
					ConnectionFactory<Twitter> connectionFactory,
					MultiValueMap<String, String> parameters, WebRequest request) {
				System.out.println("preConnect twitter");
			}

			@Override
			public void postConnect(Connection<Twitter> connection,
					WebRequest request) {
				System.out.println("postConnect Twitetr");
				//usersConnectionRepository().createConnectionRepository("ravit").addConnection(connection);
			}

			
		});
        controller.addInterceptor(new ConnectInterceptor<Facebook>() {

			@Override
			public void preConnect(
					ConnectionFactory<Facebook> connectionFactory,
					MultiValueMap<String, String> parameters, WebRequest request) {
				System.out.println("preConnect facebook");
				
			}

			@Override
			public void postConnect(Connection<Facebook> connection,
					WebRequest request) {
				System.out.println("postConnect facebook");
				
			}

			
		});
        return controller;
    }
    
    @Bean
    public TextEncryptor textEncryptor() {
        return Encryptors.noOpText();
    }

}