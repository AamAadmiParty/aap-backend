package com.next.aap.task;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.linkedin.connect.LinkedInConnectionFactory;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

@Configuration
public class SocialConfig {
	@Value("${aap_facebook_app_id}")
	private String aapFacebookAppId;
	@Value("${aap_facebook_app_secret}")
	private String aapFacebookAppSecret;
	
	@Value("${twitter_consumer_key}")
	private String twitterConsumerKey;
	
	@Value("${twitter_consumer_secret}")
	private String twitterConsumerSecret;
	
	private String googleConsumerKey = "662676448711-fpah28e0auvm8unvq0skoa25006u5odq.apps.googleusercontent.com";
	private String googleConsumerSecret = "jvLAiFhYDpqS1a4H6w-bKHKr";

	private String linkedinConsumerKey = "77iix57w8j325q";
	private String linkedinConsumerSecret = "eKRuijXF6RCiqbH5";
	
	
	@Autowired
	private DataSource dataSource;

    @Bean
    public ConnectionFactoryLocator connectionFactoryLocator() {

        ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
        registry.addConnectionFactory(new FacebookConnectionFactory(aapFacebookAppId,aapFacebookAppSecret));
        registry.addConnectionFactory(new TwitterConnectionFactory(twitterConsumerKey, twitterConsumerSecret));
        registry.addConnectionFactory(new GoogleConnectionFactory(googleConsumerKey,googleConsumerSecret));
        registry.addConnectionFactory(new LinkedInConnectionFactory(linkedinConsumerKey, linkedinConsumerSecret));
        return registry;
    }
    
    /*
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
			}

			@Override
			public void postConnect(Connection<Twitter> connection,
					WebRequest request) {
				//usersConnectionRepository().createConnectionRepository("ravit").addConnection(connection);
			}

			
		});
        controller.addInterceptor(new ConnectInterceptor<Facebook>() {

			@Override
			public void preConnect(
					ConnectionFactory<Facebook> connectionFactory,
					MultiValueMap<String, String> parameters, WebRequest request) {
				
			}

			@Override
			public void postConnect(Connection<Facebook> connection,
					WebRequest request) {
				
			}

			
		});
        return controller;
    }
    
    
    @Bean
    public TextEncryptor textEncryptor() {
        return Encryptors.noOpText();
    }
    */

}