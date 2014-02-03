To Run this project locally you need to define following Env variables


export dbserver=local server where you mysql instance is running
export dbport=local server Port where you mysql instance is running
export dbuser=local server user name of mysql instance
export dbpassword=local server password of mysql instance
export AapCentralDbName=local server dbname of mysql instance

export aap_facebook_app_id=create a facebook app on developer.facebook.com and use that app id here
export aap_facebook_app_secret=create a facebook app on developer.facebook.com and use that app secret here
export voa_facebook_app_id=create another facebook app on on developer.facebook.com  or user above one and use that app id here
export voa_facebook_app_secret=create another facebook app on on developer.facebook.com  or user above one and use that app secret here
export server_domain_and_context=ur tomcat url i.e. http://localhost:8080/aap , where aap is app context i.e. if u have aap.war
export twitter_consumer_key=create a twitter app and use key here
export twitter_consumer_secret=create a twitter app and use secret here

//Google Analytics Setup - aap-ga.xml
Then create an API key in https://cloud.google.com/console/project
	Create a project
	Click APIs & auth
		Turn on Analytics API
		Click credentials & Create Client Key, select service account
		Provide the service email address in ga.serviceEmailId property
		Download the p12 primary key into the src/main/resource/ga folder, copy the name of the p12 file into ga.privateKeyFileName property
Create a google analytics account and create a tracking key (https://www.google.com/analytics/web)
	Provide the tracking id in the property ga.trackingId property
Link the api key and the analytics account
	Goto the analytics account, Admin, User Management
	Provide the Service Email id from the API key above in here and give the read & analyze permission
Setup the viewId
	Goto the analytics account, Admin, View Settings
	Copy the view id into the ga.viewId property	 
Useful Links:
https://developers.google.com/apis-explorer/#p/
https://developers.google.com/analytics/devguides/reporting/core/dimsmets#mode=api&cats=visitor,session,traffic_sources,adwords,goal_conversions,platform_or_device,geo_network,system,social_activities,page_tracking,internal_search,site_speed,app_tracking,event_tracking,ecommerce,social_interactions,user_timings,exceptions,content_experiments,custom_variables_or_columns,time,audience,adsense
