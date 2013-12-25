package com.next.aap.web.util;

import java.net.MalformedURLException;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookLink;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class FbTestUtil {

	/**
	 * @param args
	 * @throws MalformedURLException 
	 */
	public static void main(String[] args) throws MalformedURLException {
		Facebook facebook = new FacebookTemplate("CAAHXYzhpziIBAHZBgZAmoCJzZBERGth97Qk2NiIOpFeRfN8vdkFLoI1riIvmurmQDPHl6RwXr7VV7h7HGSZAZBZBa3wrICPpiwccJUDB8ImnEZCbgrTTBmp24w2LkmnTddxL4Qyi8HiPkZBPqUknVcvjQHZB3znUauBkYrehyFM0RSFAuuFNXjVTX0tZBDhAtY3YcZD");
		//facebook.publish(objectId, connectionName, data)
		FacebookLink facebookLink = new FacebookLink("http://www.google.com", "Name of Egypt", "caption of Egypt", "This is long description to explain what this image is aboiut, This is long description to explain what this image is aboiut");
		//facebook.feedOperations().postLink("Test", facebookLink);
		
		//Resource photo = new UrlResource("https://lh6.googleusercontent.com/-G8Ikvc4xlmE/ToqQJiMd_LI/AAAAAAAAGtE/icBNFtCSwI4/s640/1226230024559.jpg");
		//facebook.mediaOperations().postPhoto(photo, "Great Egypt");
		
		
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.set("link", "http://www.google.com");
		map.set("name", facebookLink.getName());
		map.set("caption", facebookLink.getCaption());
		map.set("description", facebookLink.getDescription());
		map.set("message", "Testing, dont like it :)");
		map.set("access_token", "CAAHXYzhpziIBAHZBgZAmoCJzZBERGth97Qk2NiIOpFeRfN8vdkFLoI1riIvmurmQDPHl6RwXr7VV7h7HGSZAZBZBa3wrICPpiwccJUDB8ImnEZCbgrTTBmp24w2LkmnTddxL4Qyi8HiPkZBPqUknVcvjQHZB3znUauBkYrehyFM0RSFAuuFNXjVTX0tZBDhAtY3YcZD");
		map.add("picture", "https://lh6.googleusercontent.com/-G8Ikvc4xlmE/ToqQJiMd_LI/AAAAAAAAGtE/icBNFtCSwI4/s640/1226230024559.jpg");
		facebook.publish("me", "feed", map);
		/*
		MultiValueMap<String, Object> data = new LinkedMultiValueMap<>();
		data.add("picture", "https://lh6.googleusercontent.com/-G8Ikvc4xlmE/ToqQJiMd_LI/AAAAAAAAGtE/icBNFtCSwI4/s640/1226230024559.jpg");
		data.add("message", "Test Message for a pic");
		data.add("source", "Source");
		data.add("link", "http://www.google.com");
		data.add("caption", "caption is EGYPT");
		data.add("name", "name is EGYPT");
		data.add("description", "This is long description to explain what this image is aboiut, This is long description to explain what this image is aboiut");
		facebook.publish("691358626", "", data);
		*/
	}

}
