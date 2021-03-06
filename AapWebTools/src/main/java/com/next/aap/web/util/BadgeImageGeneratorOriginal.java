package com.next.aap.web.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public class BadgeImageGeneratorOriginal {

	public static void main(String[] args) throws Exception {
		//File outputfile = new File("d:\\tmp\\savedother.png");
		File outputfile = new File("/Users/ravi/Desktop/savedother.png");
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
				new FileOutputStream(outputfile));
		String date = "12-Jun-2013";

		String dayDonation = "Rs 182115";

		String monthDonation = "Rs 2027128";

		String highestDonationOfTheDay = "Rs 11111";

		String highestDonor = "Naveen Maheshvari";

		String message = "Have you paid your EMI for better future? do you know moreyou pay better party can work on,Have you paid your EMI for better future? do you know moreyou pay better party can work on";
		// createTemplate02CoverImage(bufferedOutputStream, date, dayDonation,
		// monthDonation, highestDonationOfTheDay, highestDonor, message);
		//InputStream in = new FileInputStream("D:\\tmp\\raj\\image01.jpg");//
		//InputStream in = new FileInputStream("/Users/ravi/Desktop/image01.jpg");
        createImageTemplate09(bufferedOutputStream, "30001", "Yogeshwari Ravi Sharma", "02 SEP 2013", "CCS00FF00123");
		bufferedOutputStream.close();
		//createImageForAllFont();
	}
	private static void createImageForAllFont(){
		int i=0;
		for(String oneFont:GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()){
			/*
			File outputfile = new File("d:\\tmp\\test\\saved"+i+".png");
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
					new FileOutputStream(outputfile));
			System.out.println("Font : "+oneFont);
			createImageTemplate01(bufferedOutputStream, "100", "Ravi Sharm","","#dc8253");
			bufferedOutputStream.close();
			i++;*/
			System.out.println(i+"="+oneFont);
			i++;
		}
		
		
	}
	public static void createImageTemplate01(OutputStream outputStream,String donationAmount,String donorName, String date,
			String transactionId){
		//createImageForAllFont();
		BufferedImage templateImage = null;
		String templateName = "dctemplate/donations01.jpg";
		String color ="#FF0033";
		int amountX = 1220;
		int transactionX = 995;
		int dateX = 1670;
		int centreX = 1324;
		try {
			InputStream templateInputStream = BadgeImageGeneratorOriginal.class.getClassLoader().getResourceAsStream(templateName);
			templateImage = ImageIO.read(templateInputStream);
			Graphics2D graphics = templateImage.createGraphics();
			Font font = Font.createFont(Font.TRUETYPE_FONT, BadgeImageGeneratorOriginal.class.getClassLoader().getResourceAsStream("fonts/BRUSHSCI.TTF"));
			font = font.deriveFont(150.0f);
			//Font font = new Font(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()[fontCount], Font.PLAIN, 120);
			
			System.out.println("font="+font);
			graphics.setFont(font);
			graphics.setColor(Color.decode(color));

			FontMetrics fm = graphics.getFontMetrics();
            int x = centreX - fm.stringWidth(donorName)/2;
            
			graphics.drawChars(donorName.toCharArray(), 0, donorName.length(), x, 690);
			graphics.drawChars(donationAmount.toCharArray(), 0, donationAmount.length(), amountX, 1000);
			
			font = new Font(GraphicsEnvironment.getLocalGraphicsEnvironment()
					.getAvailableFontFamilyNames()[0], Font.BOLD, 45);
			graphics.setFont(font);
			graphics.setColor(Color.black);
			transactionId = "Txn ID : "+transactionId;
			graphics.drawChars(transactionId.toCharArray(), 0, transactionId.length(), transactionX, 50);
			date = "Date : "+date;
			graphics.drawChars(date.toCharArray(), 0, date.length(), dateX, 50);

            font = font.deriveFont(60.0f);
            font = font.deriveFont(Font.BOLD);
            graphics.setFont(font);
            graphics.setColor(Color.BLACK);

            graphics.drawChars("#iFundHonestParty".toCharArray(), 0, "#iFundHonestParty".length(), 80, 50);

			ImageIO.write(templateImage, "png", outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void createImageTemplate02(OutputStream outputStream,String donationAmount,String donorName, String date,
			String transactionId){
		BufferedImage templateImage = null;
		String templateName = "dctemplate/donations02.jpg";
		//int fontCount = 49;
		int amountX = 860;
		int transactionX = 1400;
		int dateX = 1400;
		int centreX = 930;
		try {
			InputStream templateInputStream = BadgeImageGeneratorOriginal.class.getClassLoader().getResourceAsStream(templateName);
			templateImage = ImageIO.read(templateInputStream);
			Graphics2D graphics = templateImage.createGraphics();
			Font font = Font.createFont(Font.TRUETYPE_FONT, BadgeImageGeneratorOriginal.class.getClassLoader().getResourceAsStream("fonts/BRUSHSCI.TTF"));
			if(donorName.length() >= 26){
				font = font.deriveFont(80.0f);	
			}else if(donorName.length() >= 20){
				font = font.deriveFont(90.0f);
			}else if(donorName.length() >= 0){
				font = font.deriveFont(100.0f);
			}
			

			//Font font = new Font(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()[fontCount], Font.PLAIN, 40);
			
			graphics.setFont(font);
			//graphics.setColor(Color.decode(color));
			graphics.setColor(Color.YELLOW);

			FontMetrics fm = graphics.getFontMetrics();
            int x = centreX - fm.stringWidth(donorName)/2;
            
			graphics.drawChars(donorName.toCharArray(), 0, donorName.length(), x, 550);
			graphics.setColor(Color.RED);
			font = font.deriveFont(90.0f);
			graphics.setFont(font);
			graphics.drawChars(donationAmount.toCharArray(), 0, donationAmount.length(), amountX, 700);
			
			font = new Font(GraphicsEnvironment.getLocalGraphicsEnvironment()
					.getAvailableFontFamilyNames()[0], Font.BOLD, 25);
			graphics.setFont(font);
			graphics.setColor(Color.black);
			transactionId = "Txn ID : "+transactionId;
			graphics.drawChars(transactionId.toCharArray(), 0, transactionId.length(), transactionX, 150);
			date = "Date : "+date;
			graphics.drawChars(date.toCharArray(), 0, date.length(), dateX, 180);

            font = font.deriveFont(28.0f);
            font = font.deriveFont(Font.BOLD);
            graphics.setFont(font);
            graphics.setColor(Color.BLACK);

            graphics.drawChars("#iFundHonestParty".toCharArray(), 0, "#iFundHonestParty".length(), 80, 450);

			ImageIO.write(templateImage, "png", outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void createImageTemplate03(OutputStream outputStream,String donationAmount,String donorName, String date,
			String transactionId){
		BufferedImage templateImage = null;
		String templateName = "dctemplate/donations03.jpg";
		//int fontCount = 49;
		String color ="#DD6600";
		int centreX = 590;
		int amountX = 450;
		int transactionX = 480;
		int dateX = 480;
		try {
			InputStream templateInputStream = BadgeImageGeneratorOriginal.class.getClassLoader().getResourceAsStream(templateName);
			templateImage = ImageIO.read(templateInputStream);
			Graphics2D graphics = templateImage.createGraphics();
			
			Font font = Font.createFont(Font.TRUETYPE_FONT, BadgeImageGeneratorOriginal.class.getClassLoader().getResourceAsStream("fonts/BRUSHSCI.TTF"));
			font = font.deriveFont(90.0f);
			//Font font = new Font(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()[fontCount], Font.PLAIN, 70);
			
			graphics.setFont(font);
			graphics.setColor(Color.decode(color));

			FontMetrics fm = graphics.getFontMetrics();
            int x = centreX - fm.stringWidth(donorName)/2;
            
			graphics.drawChars(donorName.toCharArray(), 0, donorName.length(), x, 750);
			graphics.drawChars(donationAmount.toCharArray(), 0, donationAmount.length(), amountX, 1015);
			
			font = new Font(GraphicsEnvironment.getLocalGraphicsEnvironment()
					.getAvailableFontFamilyNames()[0], Font.PLAIN, 45);
			graphics.setFont(font);
			graphics.drawChars(date.toCharArray(), 0, date.length(), dateX, 1330);
			graphics.drawChars(transactionId.toCharArray(), 0, transactionId.length(), transactionX, 1420);

            font = font.deriveFont(54.0f);
            font = font.deriveFont(Font.BOLD);
            graphics.setFont(font);
            graphics.setColor(Color.white);

            graphics.drawChars("#iFundHonestParty".toCharArray(), 0, "#iFundHonestParty".length(), 50, 50);

            graphics.drawChars("#iFundHonestParty".toCharArray(), 0, "#iFundHonestParty".length(), 1460, 50);

            ImageIO.write(templateImage, "png", outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void createImageTemplate04(OutputStream outputStream,String donationAmount,String donorName, String date,
			String transactionId){
		BufferedImage templateImage = null;
		String templateName = "dctemplate/donations04.jpg";
		//int fontCount = 49;
		String color ="#DD6600";
		int amountX = 1390;
		int transactionX = 450;
		int dateX = 450;
		int centreX = 1550;
		try {
			InputStream templateInputStream = BadgeImageGeneratorOriginal.class.getClassLoader().getResourceAsStream(templateName);
			templateImage = ImageIO.read(templateInputStream);
			Graphics2D graphics = templateImage.createGraphics();
			
			Font font = Font.createFont(Font.TRUETYPE_FONT, BadgeImageGeneratorOriginal.class.getClassLoader().getResourceAsStream("fonts/BRUSHSCI.TTF"));
			font = font.deriveFont(90.0f);
			//Font font = new Font(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()[fontCount], Font.PLAIN, 70);
			
			graphics.setFont(font);
			graphics.setColor(Color.decode(color));

			FontMetrics fm = graphics.getFontMetrics();
            int x = centreX - fm.stringWidth(donorName)/2;
            
			graphics.drawChars(donorName.toCharArray(), 0, donorName.length(), x, 450);
			graphics.drawChars(donationAmount.toCharArray(), 0, donationAmount.length(), amountX, 690);
			
			font = new Font(GraphicsEnvironment.getLocalGraphicsEnvironment()
					.getAvailableFontFamilyNames()[0], Font.PLAIN, 45);
			graphics.setFont(font);
			graphics.drawChars(date.toCharArray(), 0, date.length(), dateX, 1250);
			graphics.drawChars(transactionId.toCharArray(), 0, transactionId.length(), transactionX, 1345);

            font = font.deriveFont(54.0f);
            font = font.deriveFont(Font.BOLD);
            graphics.setFont(font);

            graphics.drawChars("#iFundHonestParty".toCharArray(), 0, "#iFundHonestParty".length(), 200, 1150);

			ImageIO.write(templateImage, "png", outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void createImageTemplate05(OutputStream outputStream,String donationAmount,String donorName, String date,
			String transactionId){
		BufferedImage templateImage = null;
		String templateName = "dctemplate/donations05.jpg";
		//int fontCount = 49;
		String color ="#FFFF33";
		int amountX = 690;
		int transactionX = 550;
		int dateX = 1100;
		int centreX = 852;
		try {
			InputStream templateInputStream = BadgeImageGeneratorOriginal.class.getClassLoader().getResourceAsStream(templateName);
			templateImage = ImageIO.read(templateInputStream);
			Graphics2D graphics = templateImage.createGraphics();
			
			Font font = Font.createFont(Font.TRUETYPE_FONT, BadgeImageGeneratorOriginal.class.getClassLoader().getResourceAsStream("fonts/BRUSHSCI.TTF"));
			font = font.deriveFont(90.0f);
			//Font font = new Font(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()[fontCount], Font.PLAIN, 70);
			
			graphics.setFont(font);
			graphics.setColor(Color.decode(color));

			FontMetrics fm = graphics.getFontMetrics();
            int x = centreX - fm.stringWidth(donorName)/2;
            
			graphics.drawChars(donorName.toCharArray(), 0, donorName.length(), x, 640);
			graphics.setColor(Color.decode("#FFFFFF"));
			amountX = centreX - fm.stringWidth(donationAmount)/2;
			graphics.drawChars(donationAmount.toCharArray(), 0, donationAmount.length(), amountX, 750);
			
			font = new Font(GraphicsEnvironment.getLocalGraphicsEnvironment()
					.getAvailableFontFamilyNames()[0], Font.PLAIN, 45);
			graphics.setFont(font);
			graphics.drawChars(date.toCharArray(), 0, date.length(), dateX, 40);
			graphics.drawChars(transactionId.toCharArray(), 0, transactionId.length(), transactionX, 40);

            font = font.deriveFont(40.0f);
            graphics.setFont(font);

            graphics.drawChars("#iFundHonestParty".toCharArray(), 0, "#iFundHonestParty".length(), 20, 40);

			ImageIO.write(templateImage, "png", outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void createImageTemplate06(OutputStream outputStream,String donationAmount,String donorName, String date,
			String transactionId){
		BufferedImage templateImage = null;
		String templateName = "dctemplate/donations06.jpg";
		//int fontCount = 49;
		String color ="#990033";
		int amountX = 690;
        int transactionX = 50;
        int dateX = 630;
		int centreX = 422;
		try {
			InputStream templateInputStream = BadgeImageGeneratorOriginal.class.getClassLoader().getResourceAsStream(templateName);
			templateImage = ImageIO.read(templateInputStream);
			Graphics2D graphics = templateImage.createGraphics();
			
			Font font = Font.createFont(Font.TRUETYPE_FONT, BadgeImageGeneratorOriginal.class.getClassLoader().getResourceAsStream("fonts/ALGER.TTF"));
			font = font.deriveFont(36.0f);
			//Font font = new Font(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()[fontCount], Font.PLAIN, 70);
			
			graphics.setFont(font);
			graphics.setColor(Color.decode(color));

			FontMetrics fm = graphics.getFontMetrics();
            int x = centreX - fm.stringWidth(donorName)/2;
            
			graphics.drawChars(donorName.toCharArray(), 0, donorName.length(), x, 300);
			//graphics.setColor(Color.decode("#FFFFFF"));
			amountX = centreX - fm.stringWidth(donationAmount)/2;
			graphics.drawChars(donationAmount.toCharArray(), 0, donationAmount.length(), amountX, 370);
			
			font = new Font(GraphicsEnvironment.getLocalGraphicsEnvironment()
					.getAvailableFontFamilyNames()[0], Font.PLAIN, 20);
			graphics.setFont(font);
			graphics.drawChars(date.toCharArray(), 0, date.length(), dateX, 40);
			graphics.drawChars(transactionId.toCharArray(), 0, transactionId.length(), transactionX, 40);


            // Font font = new Font(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()[fontCount], Font.PLAIN, 70);
            font = font.deriveFont(28.0f);
            graphics.setFont(font);

            graphics.drawChars("#iFundHonestParty".toCharArray(), 0, "#iFundHonestParty".length(), 280, 40);
            font = font.deriveFont(36.0f);
            graphics.setFont(font);
            graphics.drawChars("#iFundHonestParty".toCharArray(), 0, "#iFundHonestParty".length(), 270, 590);
			ImageIO.write(templateImage, "png", outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void createImageTemplate07(OutputStream outputStream,String donationAmount,String donorName, String date,
			String transactionId){
		BufferedImage templateImage = null;
		String templateName = "dctemplate/donations07.jpg";
		//int fontCount = 49;
		String color ="#990033";
		int amountX = 690;
		int transactionX = 100;
		int dateX = 730;
		int centreX = 480;
		try {
			InputStream templateInputStream = BadgeImageGeneratorOriginal.class.getClassLoader().getResourceAsStream(templateName);
			templateImage = ImageIO.read(templateInputStream);
			Graphics2D graphics = templateImage.createGraphics();
			
			Font font = Font.createFont(Font.TRUETYPE_FONT, BadgeImageGeneratorOriginal.class.getClassLoader().getResourceAsStream("fonts/NexaBold.otf"));
			font = font.deriveFont(36.0f);
			//Font font = new Font(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()[fontCount], Font.PLAIN, 70);
			
			graphics.setFont(font);
			graphics.setColor(Color.decode(color));

			FontMetrics fm = graphics.getFontMetrics();
            int x = centreX - fm.stringWidth(donorName)/2;
            
			graphics.drawChars(donorName.toCharArray(), 0, donorName.length(), x, 310);
			//graphics.setColor(Color.decode("#FFFFFF"));
			amountX = centreX - fm.stringWidth(donationAmount)/2;
			graphics.drawChars(donationAmount.toCharArray(), 0, donationAmount.length(), amountX, 370);
			
			font = new Font(GraphicsEnvironment.getLocalGraphicsEnvironment()
					.getAvailableFontFamilyNames()[0], Font.PLAIN, 20);
			graphics.setFont(font);
			graphics.drawChars(date.toCharArray(), 0, date.length(), dateX, 630);
			graphics.drawChars(transactionId.toCharArray(), 0, transactionId.length(), transactionX, 630);

            font = font.deriveFont(24.0f);
            font = font.deriveFont(Font.BOLD);
            graphics.setFont(font);
            graphics.setColor(Color.WHITE);
            graphics.drawChars("#iFundHonestParty".toCharArray(), 0, "#iFundHonestParty".length(), 5, 600);
            graphics.drawChars("#iFundHonestParty".toCharArray(), 0, "#iFundHonestParty".length(), dateX - 50, 600);

			ImageIO.write(templateImage, "png", outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public static void createImageTemplate08(OutputStream outputStream, String donationAmount, String donorName, String date, String transactionId) {
        BufferedImage templateImage = null;
        String templateName = "dctemplate/donations08.jpg";
        // int fontCount = 49;
        String color = "#250000";
        int amountX = 1290;
        int transactionX = 2000;
        int dateX = 100;
        int centreX = 1412;
        try {
            InputStream templateInputStream = BadgeImageGeneratorOriginal.class.getClassLoader().getResourceAsStream(templateName);
            templateImage = ImageIO.read(templateInputStream);
            Graphics2D graphics = templateImage.createGraphics();

            Font font = Font.createFont(Font.TRUETYPE_FONT, BadgeImageGeneratorOriginal.class.getClassLoader().getResourceAsStream("fonts/RageItalic.ttf"));
            // Font font = Font.createFont(Font.TRUETYPE_FONT, BadgeImageGenerator.class.getClassLoader().getResourceAsStream("fonts/NexaBold.otf"));
            font = font.deriveFont(172.0f);
            // Font font = new Font(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()[fontCount], Font.PLAIN, 70);

            graphics.setFont(font);
            graphics.setColor(Color.decode(color));

            FontMetrics fm = graphics.getFontMetrics();
            int x = centreX - fm.stringWidth(donorName) / 2;

            graphics.drawChars(donorName.toCharArray(), 0, donorName.length(), x, 1910);
            // graphics.setColor(Color.decode("#FFFFFF"));
            // amountX = centreX - fm.stringWidth(donationAmount) / 2;
            graphics.drawChars(donationAmount.toCharArray(), 0, donationAmount.length(), amountX, 2310);
            graphics.setColor(Color.decode("#a9a9a9"));

            font = new Font(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()[0], Font.PLAIN, 20);
            font = font.deriveFont(108.0f);
            graphics.setFont(font);
            graphics.drawChars(date.toCharArray(), 0, date.length(), dateX, 3500);
            graphics.drawChars(transactionId.toCharArray(), 0, transactionId.length(), transactionX, 3500);

            font = font.deriveFont(108.0f);
            font = font.deriveFont(Font.BOLD);
            graphics.setColor(Color.WHITE);
            graphics.setFont(font);
            x = centreX - fm.stringWidth("#iFundHonestParty") / 2;
            graphics.drawChars("#iFundHonestParty".toCharArray(), 0, "#iFundHonestParty".length(), x, 3100);

            ImageIO.write(templateImage, "png", outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createImageTemplate09(OutputStream outputStream, String donationAmount, String donorName, String date, String transactionId) {
        BufferedImage templateImage = null;
        String templateName = "dctemplate/donations09.jpg";
        // int fontCount = 49;
        String color = "#250000";
        int amountX = 490;
        int transactionX = 820;
        int dateX = 100;
        int centreX = 512;
        try {
            InputStream templateInputStream = BadgeImageGeneratorOriginal.class.getClassLoader().getResourceAsStream(templateName);
            templateImage = ImageIO.read(templateInputStream);
            Graphics2D graphics = templateImage.createGraphics();

            Font font = Font.createFont(Font.TRUETYPE_FONT, BadgeImageGeneratorOriginal.class.getClassLoader().getResourceAsStream("fonts/RageItalic.ttf"));
            // Font font = Font.createFont(Font.TRUETYPE_FONT, BadgeImageGenerator.class.getClassLoader().getResourceAsStream("fonts/NexaBold.otf"));
            font = font.deriveFont(50.0f);
            // Font font = new Font(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()[fontCount], Font.PLAIN, 70);

            graphics.setFont(font);
            graphics.setColor(Color.decode(color));

            FontMetrics fm = graphics.getFontMetrics();
            int x = centreX - fm.stringWidth(donorName) / 2;

            graphics.drawChars(donorName.toCharArray(), 0, donorName.length(), x, 410);
            // graphics.setColor(Color.decode("#FFFFFF"));
            // amountX = centreX - fm.stringWidth(donationAmount) / 2;
            graphics.drawChars(donationAmount.toCharArray(), 0, donationAmount.length(), amountX, 520);
            graphics.setColor(Color.RED);

            font = new Font(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()[0], Font.PLAIN, 20);
            font = font.deriveFont(32.0f);
            graphics.setFont(font);
            graphics.drawChars(date.toCharArray(), 0, date.length(), dateX, 750);
            graphics.drawChars(transactionId.toCharArray(), 0, transactionId.length(), transactionX, 750);

            font = font.deriveFont(32.0f);
            font = font.deriveFont(Font.BOLD);
            graphics.setColor(Color.WHITE);
            graphics.setFont(font);
            x = 10;
            graphics.drawChars("#iFundHonestParty".toCharArray(), 0, "#iFundHonestParty".length(), x, 70);

            ImageIO.write(templateImage, "png", outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
