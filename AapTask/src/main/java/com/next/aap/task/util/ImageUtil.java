package com.next.aap.task.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

import javax.imageio.ImageIO;

public class ImageUtil {

	/**
	 * 
	 * @param args
	 * 
	 * @throws IOException
	 */

	public static void main(String[] args) throws IOException {
		for(int i=11;i<=11;i++){
			File outputfile = new File("d:\\tmp\\saved0"+i+".png");
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
					new FileOutputStream(outputfile));
			String date = "10-Jul-2013";

			String dayDonation = "3345678";

			String monthDonation = "11234567";

			String highestDonationOfTheDay = "11111";

			String highestDonor = "Naveen Maheshvari";

			String message = "Have you paid your EMI for better future?";
			String monthTarget="40000000";
			if(i==1){
				createTemplate01CoverImage(bufferedOutputStream, date, dayDonation,monthDonation, message);	
			}
			if(i==2){
				createTemplate02CoverImage(bufferedOutputStream, date, dayDonation,monthDonation, message);	
			}
			if(i==3){
				createTemplate03CoverImage(bufferedOutputStream, date, dayDonation,monthDonation, message);	
			}
			if(i==4){
				createTemplate04CoverImage(bufferedOutputStream, date, dayDonation,monthDonation, message);	
			}
			if(i==5){
				createTemplate05CoverImage(bufferedOutputStream, date, dayDonation,monthDonation, message);	
			}
			if(i==6){
				createTemplate06CoverImage(bufferedOutputStream, date, dayDonation,monthDonation, message);	
			}
			if(i==7){
				createTemplate07CoverImage(bufferedOutputStream, date, dayDonation,monthDonation, message);	
			}
			if(i==8){
				createPostTemplate01Image(bufferedOutputStream, date, dayDonation, monthDonation, monthTarget);
			}
			if(i==9){
				createPostTemplate02Image(bufferedOutputStream, date, dayDonation, monthDonation, monthTarget);
			}
			if(i==10){
				createPostTemplate03Image(bufferedOutputStream, date, dayDonation, monthDonation, monthTarget);
			}
			if(i==11){
				createPostTemplate04Image(bufferedOutputStream, date, dayDonation, monthDonation, monthTarget);
			}		 
			 bufferedOutputStream.close();
			
		}
		//InputStream in = new FileInputStream("D:\\tmp\\raj\\image01.jpg");//
		//createChange4IndiaImage07(bufferedOutputStream, in, message,42);
		
	}

	public static void createTemplate01PostImage(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,
			String highestDonationOfTheDay, String highestDonor, String message)
			throws IOException {

		String donationUrl = "Donate at : http://donate.aamaadmiparty.org/";

		String volunteer = "Created By : Nitin Pratap Singh, Ravi Sharma";

		BufferedImage image = null;

		try {

			InputStream in = ImageUtil.class.getClassLoader()
					.getResourceAsStream("template01.jpg");
			image = ImageIO.read(in);

		} catch (IOException e) {

		}

		// BufferedImage image = new BufferedImage(400, 300,
		// BufferedImage.TYPE_4BYTE_ABGR);

		Graphics2D graphics = image.createGraphics();

		Font font = new Font(GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAvailableFontFamilyNames()[1], Font.PLAIN, 40);

		graphics.setFont(font);

		graphics.setColor(Color.RED);

		graphics.drawChars(date.toCharArray(), 0, date.length(), 170, 165);

		// font = new
		// Font(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()[2],
		// Font.BOLD, 54);

		// graphics.setFont(font);

		graphics.drawChars(dayDonation.toCharArray(), 0, dayDonation.length(),
				580, 270);

		graphics.drawChars(monthDonation.toCharArray(), 0,
				monthDonation.length(), 580, 360);

		// font = new
		// Font(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()[2],
		// Font.BOLD, 54);

		// graphics.setFont(font);

		graphics.drawChars(highestDonationOfTheDay.toCharArray(), 0,
				highestDonationOfTheDay.length(), 580, 450);

		font = new Font(GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAvailableFontFamilyNames()[0], Font.BOLD, 28);

		graphics.setFont(font);

		graphics.drawChars(highestDonor.toCharArray(), 0,
				highestDonor.length(), 580, 540);

		// font = new
		// Font(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()[2],
		// Font.BOLD, 54);

		// graphics.setFont(font);

		graphics.setColor(Color.BLUE);

		font = new Font(GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAvailableFontFamilyNames()[0], Font.BOLD, 28);

		graphics.setFont(font);
		float[] hsbvals = new float[3];
		Color.RGBtoHSB(255, 128, 0, hsbvals);

		graphics.setColor(Color.getHSBColor(hsbvals[0], hsbvals[1], hsbvals[2]));

		graphics.drawChars(message.toCharArray(), 0, message.length(), 200, 610);

		graphics.setColor(Color.BLUE);

		font = new Font(GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAvailableFontFamilyNames()[0], Font.BOLD, 24);

		graphics.setFont(font);

		graphics.drawChars(donationUrl.toCharArray(), 0, donationUrl.length(),
				300, 690);

		graphics.drawLine(370, 695, 650, 695);

		font = new Font(GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAvailableFontFamilyNames()[2], Font.BOLD, 24);

		graphics.setFont(font);

		graphics.setColor(Color.BLACK);

		// graphics.drawChars(volunteer.toCharArray(), 0, volunteer.length(),
		// 100, 735);

		ImageIO.write(image, "png", outputStream);

	}

	public static void createTemplate01CoverImage(OutputStream outputStream,
			String date, String dayDonation, String monthDonation, String message)
			throws IOException {
		createTemplateCoverImage(outputStream, "covertemplate01.png", date, dayDonation, monthDonation, message);
	}
	public static void createTemplate02CoverImage(OutputStream outputStream,
			String date, String dayDonation, String monthDonation, String message)
			throws IOException {
		createTemplateCoverImage(outputStream, "covertemplate02.png", date, dayDonation, monthDonation, message);
	}

	public static void createTemplate03CoverImage(OutputStream outputStream,
			String date, String dayDonation, String monthDonation, String message)
			throws IOException {
		createTemplateCoverImage(outputStream, "covertemplate03.png", date, dayDonation, monthDonation, message);
	}
	public static void createTemplate04CoverImage(OutputStream outputStream,
			String date, String dayDonation, String monthDonation, String message)
			throws IOException {
		createTemplateCoverImage(outputStream, "covertemplate04.png", date, dayDonation, monthDonation, message);
	}
	public static void createTemplate05CoverImage(OutputStream outputStream,
			String date, String dayDonation, String monthDonation, String message)
			throws IOException {
		createTemplateCoverImage(outputStream, "covertemplate05.png", date, dayDonation, monthDonation, message);
	}
	public static void createTemplate06CoverImage(OutputStream outputStream,
			String date, String dayDonation, String monthDonation, String message)
			throws IOException {
		createTemplateCoverImage(outputStream, "covertemplate06.png", date, dayDonation, monthDonation, message);
	}
	public static void createTemplate07CoverImage(OutputStream outputStream,
			String date, String dayDonation, String monthDonation, String message)
			throws IOException {
		createTemplateCoverImage(outputStream, "covertemplate07.png", date, dayDonation, monthDonation, message);
	}
	
	public static void createTemplateCoverImage(OutputStream outputStream,String imageName,
			String date, String dayDonation, String monthDonation, String message)
			throws IOException {

		BufferedImage image = null;
		try {
			InputStream in = ImageUtil.class.getClassLoader()
					.getResourceAsStream(imageName);
			image = ImageIO.read(in);
			Graphics2D graphics = image.createGraphics();

			Font font = new Font("Microsoft Sans Serif", Font.PLAIN, 26);

			graphics.setFont(font);

			graphics.setColor(Color.RED);
			float[] hsbvals = new float[3];
			Color.RGBtoHSB(255, 128, 0, hsbvals);

			graphics.setColor(Color.getHSBColor(hsbvals[0], hsbvals[1], hsbvals[2]));
			graphics.drawChars(date.toCharArray(), 0, date.length(), 680, 75);
			graphics.drawChars(dayDonation.toCharArray(), 0, dayDonation.length(),
					640, 150);

			graphics.drawChars(monthDonation.toCharArray(), 0,
					monthDonation.length(), 640, 225);

			graphics.setColor(Color.WHITE);
			font = new Font("Segoe Print", Font.BOLD, 28);

			graphics.drawChars(message.toCharArray(), 0, message.length(), 50, 250);

			ImageIO.write(image, "png", outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void createTemplate02PostImage(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,
			String highestDonationOfTheDay, String highestDonor, String message)
			throws IOException {

		int fontSize = 72;
		String volunteer = "Created By : Nitin Pratap Singh, Ravi Sharma";

		BufferedImage image = null;

		try {

			InputStream in = ImageUtil.class.getClassLoader()
					.getResourceAsStream("template02.jpg");
			image = ImageIO.read(in);

		} catch (IOException e) {

		}

		Graphics2D graphics = image.createGraphics();

		Font font = new Font("Microsoft Sans Serif", Font.PLAIN, fontSize);

		graphics.setFont(font);

		graphics.setColor(Color.RED);
		float[] hsbvals = new float[3];
		Color.RGBtoHSB(255, 128, 0, hsbvals);

		graphics.setColor(Color.getHSBColor(hsbvals[0], hsbvals[1], hsbvals[2]));

		graphics.drawChars(date.toCharArray(), 0, date.length(), 900, 100);

		graphics.drawChars(dayDonation.toCharArray(), 0, dayDonation.length(),
				900, 590);

		graphics.drawChars(monthDonation.toCharArray(), 0,
				monthDonation.length(), 900, 825);

		graphics.drawChars(highestDonationOfTheDay.toCharArray(), 0,
				highestDonationOfTheDay.length(), 1100, 1100);

		graphics.drawChars(highestDonor.toCharArray(), 0,
				highestDonor.length(), 1100, 1350);
		// graphics.setColor(Color.WHITE);
		font = new Font("Segoe Print", Font.BOLD, 28);

		graphics.drawChars(message.toCharArray(), 0, message.length(), 300,
				1500);

		ImageIO.write(image, "png", outputStream);

		/*
		 * FacebookType publishPhotoResponse =
		 * facebookClient.publish("me/photos", FacebookType.class,
		 * BinaryAttachment.with("cat.png",
		 * getClass().getResourceAsStream("/cat.png")),
		 * Parameter.with("message", "Test cat"));
		 * 
		 * out.println("Published photo ID: " + publishPhotoResponse.getId());
		 * 
		 * // Publishing a video works the same way.
		 * 
		 * facebookClient.publish("me/videos", FacebookType.class,
		 * BinaryAttachment.with("cat.mov",
		 * getClass().getResourceAsStream("/cat.mov")),
		 * Parameter.with("message", "Test cat"));
		 */
	}

	public static void createTemplate03PostImage(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,
			String highestDonationOfTheDay, String highestDonor, String message)
			throws IOException {

		int fontSize = 32;
		String volunteer = "Created By : Suhail, Nitin Pratap Singh, Ravi Sharma";

		BufferedImage image = null;

		try {

			InputStream in = ImageUtil.class.getClassLoader()
					.getResourceAsStream("template03.jpg");
			image = ImageIO.read(in);

		} catch (IOException e) {

		}

		Graphics2D graphics = image.createGraphics();

		Font font = new Font("Microsoft Sans Serif", Font.BOLD, fontSize);

		graphics.setFont(font);

		graphics.setColor(Color.BLACK);
		/*
		 * float[] hsbvals = new float[3]; Color.RGBtoHSB(255, 128, 0, hsbvals);
		 * 
		 * graphics.setColor(Color.getHSBColor(hsbvals[0], hsbvals[1],
		 * hsbvals[2]));
		 */

		graphics.drawChars(date.toCharArray(), 0, date.length(), 150, 100);

		graphics.drawChars(dayDonation.toCharArray(), 0, dayDonation.length(),
				460, 170);

		graphics.drawChars(monthDonation.toCharArray(), 0,
				monthDonation.length(), 460, 250);

		graphics.drawChars(highestDonationOfTheDay.toCharArray(), 0,
				highestDonationOfTheDay.length(), 460, 320);

		graphics.drawChars(highestDonor.toCharArray(), 0,
				highestDonor.length(), 460, 380);
		// graphics.setColor(Color.WHITE);
		font = new Font("Segoe Print", Font.BOLD, 28);
		graphics.setColor(Color.BLUE);
		graphics.drawChars(message.toCharArray(), 0, message.length(), 40, 480);

		ImageIO.write(image, "png", outputStream);
	}
	
	public static void createPostTemplate01Image(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		float fontSize = 90.0f;
		BufferedImage image = null;

		try {

			InputStream in = ImageUtil.class.getClassLoader()
					.getResourceAsStream("posttemplate01.jpg");
			image = ImageIO.read(in);

		} catch (IOException e) {

		}

		Graphics2D graphics = image.createGraphics();

		//Font font = new Font("Microsoft Sans Serif", Font.BOLD, fontSize);
		Font font = graphics.getFont().deriveFont(fontSize);
		graphics.setFont(font);
		

		try{
			double monthDonationInt = Double.parseDouble(monthDonation);
			double monthTargetInt = Double.parseDouble(monthTarget);
			int percent = (int)((monthDonationInt / monthTargetInt) * 100);
			String message = "Have achieved "+percent+"%";
			
			font = font.deriveFont(40.0f);
			graphics.setColor(Color.WHITE);
			graphics.setFont(font);
			graphics.drawChars(message.toCharArray(), 0, message.length(), 1100, 1200);
		}catch(Exception ex){
			ex.printStackTrace();
		}

		graphics.setColor(Color.ORANGE);
		dayDonation = RsFormatter.formatAmount(dayDonation);
		monthDonation = RsFormatter.formatAmount(monthDonation);
		font = font.deriveFont(fontSize);
		graphics.setFont(font);
		graphics.drawChars(date.toCharArray(), 0, date.length(), 1000, 530);

		graphics.drawChars(dayDonation.toCharArray(), 0, dayDonation.length(), 1000, 730);

		graphics.drawChars(monthDonation.toCharArray(), 0, monthDonation.length(), 1000, 940);

		graphics.drawChars(monthTarget.toCharArray(), 0, monthTarget.length(), 1000, 1150);
		
		ImageIO.write(image, "png", outputStream);
	}
	
	public static void createPostTemplate02Image(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		float fontSize = 90.0f;
		BufferedImage image = null;

		try {

			InputStream in = ImageUtil.class.getClassLoader()
					.getResourceAsStream("posttemplate02.jpg");
			image = ImageIO.read(in);

		} catch (IOException e) {

		}

		Graphics2D graphics = image.createGraphics();

		//Font font = new Font("Microsoft Sans Serif", Font.BOLD, fontSize);
		Font font = graphics.getFont().deriveFont(fontSize);
		graphics.setFont(font);
		

		try{
			double monthDonationInt = Double.parseDouble(monthDonation);
			double monthTargetInt = Double.parseDouble(monthTarget);
			int percent = (int)((monthDonationInt / monthTargetInt) * 100);
			String message = "Have achieved "+percent+"%, together we can do 100%";
			
			font = font.deriveFont(40.0f);
			graphics.setColor(Color.WHITE);
			graphics.setFont(font);
			graphics.drawChars(message.toCharArray(), 0, message.length(), 600, 1250);
		}catch(Exception ex){
			ex.printStackTrace();
		}

		graphics.setColor(Color.ORANGE);
		dayDonation = RsFormatter.formatAmount(dayDonation);
		monthDonation = RsFormatter.formatAmount(monthDonation);
		font = font.deriveFont(fontSize);
		graphics.setFont(font);
		graphics.drawChars(date.toCharArray(), 0, date.length(), 850, 100);

		dayDonation = "Rs. "+dayDonation;
		graphics.drawChars(dayDonation.toCharArray(), 0, dayDonation.length(), 750, 580);
		monthDonation = "Rs. "+monthDonation;
		graphics.drawChars(monthDonation.toCharArray(), 0, monthDonation.length(), 750, 900);
		monthTarget = "Rs. "+monthTarget;
		graphics.drawChars(monthTarget.toCharArray(), 0, monthTarget.length(), 750, 1200);
		
		graphics.setColor(Color.RED);
		String messageHeader = "Message";
		graphics.drawChars(messageHeader.toCharArray(), 0, messageHeader.length(), 50, 500);
		
		String message = "Donate 213,2013,20013 or any other amount to ultimately reach the 2 Crores target for the month of August";
		
		font = font.deriveFont(65.f);
		graphics.setColor(Color.WHITE);
		drawCharInRectangle(graphics, font, 20, 600, 480, message);

		
		ImageIO.write(image, "png", outputStream);
	}
	public static void createPostTemplate03Image(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		float fontSize = 60.0f;
		BufferedImage image = null;

		try {

			InputStream in = ImageUtil.class.getClassLoader()
					.getResourceAsStream("posttemplate03.jpg");
			image = ImageIO.read(in);

		} catch (IOException e) {

		}

		Graphics2D graphics = image.createGraphics();

		//Font font = new Font("Microsoft Sans Serif", Font.BOLD, fontSize);
		Font font = graphics.getFont().deriveFont(fontSize);
		graphics.setFont(font);
		

		try{
			double monthDonationInt = Double.parseDouble(monthDonation);
			double monthTargetInt = Double.parseDouble(monthTarget);
			int percent = (int)((monthDonationInt / monthTargetInt) * 100);
			String message = "Have achieved "+percent+"%, together we can do 100%";
			
			font = font.deriveFont(20.0f);
			graphics.setColor(Color.RED);
			graphics.setFont(font);
			graphics.drawChars(message.toCharArray(), 0, message.length(), 1100, 1060);
		}catch(Exception ex){
			ex.printStackTrace();
		}

		graphics.setColor(Color.ORANGE);
		dayDonation = RsFormatter.formatAmount(dayDonation);
		monthDonation = RsFormatter.formatAmount(monthDonation);
		font = font.deriveFont(fontSize);
		graphics.setFont(font);
		graphics.drawChars(date.toCharArray(), 0, date.length(), 1230, 475);

		graphics.drawChars(dayDonation.toCharArray(), 0, dayDonation.length(), 1140, 680);
		graphics.drawChars(monthDonation.toCharArray(), 0, monthDonation.length(), 1140, 850);
		
		graphics.setColor(Color.RED);
		monthTarget = RsFormatter.formatAmount(monthTarget);
		graphics.drawChars(monthTarget.toCharArray(), 0, monthTarget.length(), 1140, 1025);
		
		
		//String message = "Clean Politics need Clean money.Please donate to the AAP  today so we can continue this battle and meet with electoral success in the Delhi elections this November.  Most importantly, get your friends and family members to donate as well.";
		//graphics.setColor(Color.RED);
		//drawCharInRectangle(graphics, font, 400, 600, 600, message);

		
		ImageIO.write(image, "png", outputStream);
	}
	public static void createPostTemplate04Image(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		//float fontSize = 60.0f;
		float fontSize = 60.0f;
		BufferedImage image = null;

		try {

			InputStream in = ImageUtil.class.getClassLoader()
					.getResourceAsStream("posttemplate04.jpg");
			image = ImageIO.read(in);

		} catch (IOException e) {

		}

		Graphics2D graphics = image.createGraphics();
		int fontNumber = 3;
		System.out.println(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()[fontNumber]);
		//Font font = new Font(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()[fontNumber], Font.BOLD, fontSize);
		Font font = graphics.getFont().deriveFont(fontSize);
		System.out.println(font.getName());
		try{
			font = Font.createFont(Font.TRUETYPE_FONT, ImageUtil.class.getClassLoader().getResourceAsStream("fonts/ALGER.TTF"));
			font = font.deriveFont(fontSize);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		System.out.println(font.getName());
		graphics.setFont(font);
		
		/*
		try{
			double monthDonationInt = Double.parseDouble(monthDonation);
			double monthTargetInt = Double.parseDouble(monthTarget);
			int percent = (int)((monthDonationInt / monthTargetInt) * 100);
			String message = "Have achieved "+percent+"%, together we can do 100%";
			
			font = font.deriveFont(20.0f);
			graphics.setColor(Color.RED);
			graphics.setFont(font);
			graphics.drawChars(message.toCharArray(), 0, message.length(), 1100, 1060);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	*/
		dayDonation = RsFormatter.formatAmount(dayDonation);
		monthDonation = RsFormatter.formatAmount(monthDonation);
		font = font.deriveFont(fontSize);
		font = font.deriveFont(Font.BOLD);
		graphics.setFont(font);
		
		dayDonation = "Rs. "+ dayDonation;
		graphics.setColor(Color.BLACK);
		graphics.drawChars(dayDonation.toCharArray(), 0, dayDonation.length(), 397, 427);
		graphics.setColor(Color.WHITE);
		graphics.drawChars(dayDonation.toCharArray(), 0, dayDonation.length(), 400, 430);
		monthDonation = "Rs. "+ monthDonation;
		graphics.setColor(Color.GREEN);
		graphics.drawChars(monthDonation.toCharArray(), 0, monthDonation.length(), 367, 607);
		graphics.setColor(Color.decode("#31B404"));
		graphics.drawChars(monthDonation.toCharArray(), 0, monthDonation.length(), 370, 610);
		
		/*
		graphics.setColor(Color.RED);
		monthTarget = RsFormatter.formatAmount(monthTarget);
		graphics.drawChars(monthTarget.toCharArray(), 0, monthTarget.length(), 1140, 1025);
		
		*/
		//String message = "Clean Politics need Clean money.Please donate to the AAP  today so we can continue this battle and meet with electoral success in the Delhi elections this November.  Most importantly, get your friends and family members to donate as well.";
		//graphics.setColor(Color.RED);
		//drawCharInRectangle(graphics, font, 400, 600, 600, message);

		
		ImageIO.write(image, "png", outputStream);
	}

	public static void createChange4IndiaImage01(OutputStream outputStream,
			InputStream personaImageInputStream, String message, int fontSize)
			throws IOException {

		Font font = new Font("Microsoft Sans Serif", Font.BOLD, fontSize);
		createChange4IndiaImage(outputStream, personaImageInputStream, "change4india01.png", 30, 40, 650, message,font);
	}
	public static void createChange4IndiaImage02(OutputStream outputStream,
			InputStream personaImageInputStream, String message, int fontSize)
			throws IOException {
		Font font = new Font("Microsoft Sans Serif", Font.BOLD, fontSize);
		createChange4IndiaImage(outputStream, personaImageInputStream, "change4india02.png", 10, 10, 310, message,font);
	}
	public static void createChange4IndiaImage03(OutputStream outputStream,
			InputStream personaImageInputStream, String message, int fontSize)
			throws IOException {
		Font font = new Font("Microsoft Sans Serif", Font.BOLD, fontSize);

		createChange4IndiaImage(outputStream, personaImageInputStream, "change4india03.png", 400, 370, 320, message,font);
	}
	public static void createChange4IndiaImage04(OutputStream outputStream,
			InputStream personaImageInputStream, String message, int fontSize)
			throws IOException {
		Font font = new Font("Microsoft Sans Serif", Font.BOLD, fontSize);

		createChange4IndiaImage(outputStream, personaImageInputStream, "change4india04.png", 5, 10, 270, message,font);
	}
	public static void createChange4IndiaImage05(OutputStream outputStream,
			InputStream personaImageInputStream, String message, int fontSize)
			throws IOException {
		Font font = new Font("Microsoft Sans Serif", Font.BOLD, fontSize);

		createChange4IndiaImage(outputStream, personaImageInputStream, "change4india05.png", 5, 360, 225, message,font);
	}
	public static void createChange4IndiaImage06(OutputStream outputStream,
			InputStream personaImageInputStream, String message, int fontSize)
			throws IOException {
		Font font = new Font("Microsoft Sans Serif", Font.BOLD, fontSize);

		createChange4IndiaImage(outputStream, personaImageInputStream, "change4india06.png", 15, 330, 235, message,font);
	}
	public static void createChange4IndiaImage07(OutputStream outputStream,
			InputStream personaImageInputStream, String message, int fontSize)
			throws IOException {
		Font font = new Font("Microsoft Sans Serif", Font.BOLD, fontSize);

		createChange4IndiaImage(outputStream, personaImageInputStream, "change4india07.png", 1000, 400, 1000, message,font);
	}
	public static void createChange4IndiaImage01(OutputStream outputStream,
			BufferedImage personaImage, String message, int fontSize)
			throws IOException {

		Font font = new Font("Microsoft Sans Serif", Font.BOLD, fontSize);
		createChange4IndiaImage(outputStream, personaImage, "change4india01.png", 30, 40, 650, message,font);
	}
	public static void createChange4IndiaImage02(OutputStream outputStream,
			BufferedImage personaImage, String message, int fontSize)
			throws IOException {
		Font font = new Font("Microsoft Sans Serif", Font.BOLD, fontSize);
		createChange4IndiaImage(outputStream, personaImage, "change4india02.png", 10, 10, 310, message,font);
	}
	public static void createChange4IndiaImage03(OutputStream outputStream,
			BufferedImage personaImage, String message, int fontSize)
			throws IOException {
		Font font = new Font("Microsoft Sans Serif", Font.BOLD, fontSize);

		createChange4IndiaImage(outputStream, personaImage, "change4india03.png", 400, 370, 320, message,font);
	}
	public static void createChange4IndiaImage04(OutputStream outputStream,
			BufferedImage personaImage, String message, int fontSize)
			throws IOException {
		Font font = new Font("Microsoft Sans Serif", Font.BOLD, fontSize);

		createChange4IndiaImage(outputStream, personaImage, "change4india04.png", 5, 10, 270, message,font);
	}
	public static void createChange4IndiaImage05(OutputStream outputStream,
			BufferedImage personaImage, String message, int fontSize)
			throws IOException {
		Font font = new Font("Microsoft Sans Serif", Font.BOLD, fontSize);

		createChange4IndiaImage(outputStream, personaImage, "change4india05.png", 5, 360, 225, message,font);
	}
	public static void createChange4IndiaImage06(OutputStream outputStream,
			BufferedImage personaImage, String message, int fontSize)
			throws IOException {
		Font font = new Font("Microsoft Sans Serif", Font.BOLD, fontSize);

		createChange4IndiaImage(outputStream, personaImage, "change4india06.png", 15, 330, 235, message,font);
	}
	public static void createChange4IndiaImage07(OutputStream outputStream,
			BufferedImage personaImage, String message, int fontSize)
			throws IOException {
		Font font = new Font("Microsoft Sans Serif", Font.BOLD, fontSize);

		createChange4IndiaImage(outputStream, personaImage, "change4india07.png", 1000, 400, 1000, message,font);
	}
	public static void createChange4IndiaImage(OutputStream outputStream,InputStream personaImageInputStream,String templateName,int x, int y, int width, String message,Font font)
			throws IOException {
		BufferedImage personalImage = null;
		try {
			personalImage = ImageIO.read(personaImageInputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		createChange4IndiaImage(outputStream, personalImage, templateName, x, y, width, message, font);
	}
	public static void createChange4IndiaImage(OutputStream outputStream,BufferedImage personalImage,String templateName,int x, int y, int width, String message,Font font)
			throws IOException {

		BufferedImage templateImage = null;

		try {
			InputStream templateInputStream = ImageUtil.class.getClassLoader()
					.getResourceAsStream(templateName);
			templateImage = ImageIO.read(templateInputStream);
			int type = personalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : personalImage.getType();
			personalImage = resizeImage(personalImage, templateImage.getWidth(), templateImage.getHeight(), type);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Graphics2D graphics = personalImage.createGraphics();

		graphics.setFont(font);

		graphics.setColor(Color.BLACK);

		graphics.drawImage(templateImage, 0, 0, templateImage.getWidth(),
				templateImage.getHeight(), 0, 0, templateImage.getWidth(),
				templateImage.getHeight(), new ImageObserver() {

					@Override
					public boolean imageUpdate(Image img, int infoflags, int x,
							int y, int width, int height) {
						return false;
					}
				});
		AttributedString styledText = new AttributedString(message);
		styledText.addAttribute(TextAttribute.FONT, font, 0, message.length());
		AttributedCharacterIterator m_iterator = styledText.getIterator();
		int m_start = m_iterator.getBeginIndex();
		int m_end = m_iterator.getEndIndex();

		FontRenderContext frc = graphics.getFontRenderContext();
		LineBreakMeasurer measurer = new LineBreakMeasurer(m_iterator, frc);
		measurer.setPosition(m_start);

		while (measurer.getPosition() < m_end) {
			TextLayout layout = measurer.nextLayout(width);

			y += layout.getAscent();
			float dx = layout.isLeftToRight() ? 0 : width
					- layout.getAdvance();
			graphics.setFont(font);
			layout.draw(graphics, x + dx, y);
			y += layout.getDescent() + layout.getLeading();
		}

		ImageIO.write(personalImage, "png", outputStream);
	}

	private static void drawCharInRectangle(Graphics2D graphics,Font font,int startX,int startY,float width,String message){
		AttributedString styledText = new AttributedString(message);
		styledText.addAttribute(TextAttribute.FONT, font, 0, message.length());
		AttributedCharacterIterator m_iterator = styledText.getIterator();
		int m_start = m_iterator.getBeginIndex();
		int m_end = m_iterator.getEndIndex();

		FontRenderContext frc = graphics.getFontRenderContext();
		LineBreakMeasurer measurer = new LineBreakMeasurer(m_iterator, frc);
		measurer.setPosition(m_start);

		while (measurer.getPosition() < m_end) {
			TextLayout layout = measurer.nextLayout(width);

			startY += layout.getAscent();
			float dx = layout.isLeftToRight() ? 0 : width
					- layout.getAdvance();
			graphics.setFont(font);
			layout.draw(graphics, startX + dx, startY);
			startY += layout.getDescent() + layout.getLeading();
		}
	}
	private static BufferedImage resizeImage(BufferedImage originalImage,int width, int height,int type) {
		BufferedImage resizedImage = new BufferedImage(width, height,type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();

		return resizedImage;
	}
}