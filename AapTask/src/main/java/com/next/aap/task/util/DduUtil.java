package com.next.aap.task.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.Calendar;

import javax.imageio.ImageIO;

public class DduUtil {

	/**
	 * 
	 * @param args
	 * 
	 * @throws IOException
	 */

	public static void main(String[] args) throws IOException {
		// for(int i=5;i<=22;i++){
		
		for (int i = 0; i <= 0; i++) {
			// File outputfile = new File("d:\\tmp\\saved0"+i+".png");
			File outputfile = new File("/Users/ravi/Desktop/saved0" + i + ".png");
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(outputfile));
			String date = "10-Jul-2013";

			String dayDonation = "2744227";

			String monthDonation = "6052921";

			String message = "Have you paid your EMI for better future?";
			String monthTarget = "70000000";

			createTemplate02DDU(bufferedOutputStream, date, dayDonation, monthDonation, monthTarget);
			bufferedOutputStream.close();

		}
		
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    Font[] fonts = e.getAllFonts(); // Get the fonts
	    for (Font f : fonts) {
	      System.out.println(f.getFontName());
	    }
		// InputStream in = new FileInputStream("D:\\tmp\\raj\\image01.jpg");//
		// createChange4IndiaImage07(bufferedOutputStream, in, message,42);

	}

	public static void createBoardTemplateImageForJoinApp(OutputStream outputStream, String date, String dayDonation, String monthDonation, String monthTarget)
			throws IOException {
		String templateFileName = "ddu/joinaap.jpg";
		createBoardTemplateImageForDecember(outputStream, date, dayDonation, monthDonation, monthTarget, templateFileName);
	}

	public static void createBoardTemplateImageForDecember(OutputStream outputStream, String date, String dayDonation, String monthDonation,
			String monthTarget, String templateFileName) throws IOException {
		float dateFontSize = 60.0f;
		float amountFontSize = 80.0f;
		BufferedImage image = null;

		try {

			InputStream in = DduUtil.class.getClassLoader().getResourceAsStream(templateFileName);
			image = ImageIO.read(in);

		} catch (IOException e) {

		}

		Graphics2D graphics = image.createGraphics();

		// Font font = new Font("Microsoft Sans Serif", Font.BOLD, fontSize);
		Font font = graphics.getFont();

		try {
			double monthDonationInt = Double.parseDouble(monthDonation);
			double monthTargetInt = Double.parseDouble(monthTarget);
			int percent = (int) ((monthDonationInt / monthTargetInt) * 100);
			String message = "Have achieved " + percent + "%, together we can do 100%";

			font = font.deriveFont(30.0f);
			graphics.setColor(Color.RED);
			graphics.setFont(font);
			graphics.drawChars(message.toCharArray(), 0, message.length(), 1300, 970);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// graphics.drawChars(monthDonation.toCharArray(), 0,
		// monthDonation.length(), 1400, 550);

		// graphics.setColor(Color.RED);
		dayDonation = RsFormatter.formatAmount(dayDonation);
		monthDonation = RsFormatter.formatAmount(monthDonation);
		font = font.deriveFont(dateFontSize);
		graphics.setFont(font);
		graphics.setColor(Color.decode("#663300"));
		graphics.drawChars(date.toCharArray(), 0, date.length(), 1500, 330);

		font = font.deriveFont(amountFontSize);
		graphics.setFont(font);

		monthTarget = RsFormatter.formatAmount(monthTarget);

		
		
		graphics.setColor(Color.RED);
		graphics.drawChars(dayDonation.toCharArray(), 0, dayDonation.length(), 1398, 518);
		graphics.drawChars(monthDonation.toCharArray(), 0, monthDonation.length(), 1398, 718);
		graphics.drawChars(monthTarget.toCharArray(), 0, monthTarget.length(), 1398, 908);

		graphics.setColor(Color.decode("#663300"));
		graphics.drawChars(dayDonation.toCharArray(), 0, dayDonation.length(), 1400, 520);
		graphics.drawChars(monthDonation.toCharArray(), 0, monthDonation.length(), 1400, 720);
		graphics.drawChars(monthTarget.toCharArray(), 0, monthTarget.length(), 1400, 910);

		try {
			font = Font.createFont(Font.TRUETYPE_FONT, ImageUtil.class.getClassLoader().getResourceAsStream("fonts/ALGER.TTF"));
		} catch (FontFormatException e) {
			e.printStackTrace();
		}
		ImageIO.write(image, "png", outputStream);
	}

	public static void createTemplate01DDU(OutputStream outputStream, String date, String dayDonation, String monthDonation, String monthTarget)
			throws IOException {
		String templateFileName = "ddu/template01.jpg";
		float dateFontSize = 20.0f;
		float amountFontSize = 30.0f;
		BufferedImage image = null;

		InputStream in = DduUtil.class.getClassLoader().getResourceAsStream(templateFileName);
		image = ImageIO.read(in);

		Graphics2D graphics = image.createGraphics();

		// Font font = new Font("Microsoft Sans Serif", Font.BOLD, fontSize);
		Font font = graphics.getFont();

		try {
			double monthDonationInt = Double.parseDouble(monthDonation);
			double monthTargetInt = Double.parseDouble(monthTarget);
			int percent = (int) ((monthDonationInt / monthTargetInt) * 100);
			String message = "Have achieved " + percent + "%, together we can do 100%";

			font = font.deriveFont(15.0f);
			graphics.setColor(Color.RED);
			font = font.deriveFont(Font.BOLD);
			graphics.setFont(font);
			graphics.drawChars(message.toCharArray(), 0, message.length(), 10, 410);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// graphics.drawChars(monthDonation.toCharArray(), 0,
		// monthDonation.length(), 1400, 550);

		// graphics.setColor(Color.RED);
		dayDonation = RsFormatter.formatAmount(dayDonation);
		monthDonation = RsFormatter.formatAmount(monthDonation);
		font = font.deriveFont(dateFontSize);
		font = font.deriveFont(Font.BOLD);
		graphics.setFont(font);
		graphics.setColor(Color.ORANGE);
		graphics.drawChars(date.toCharArray(), 0, date.length(), 300, 120);
		graphics.setColor(Color.decode("#663300"));
		graphics.drawChars(date.toCharArray(), 0, date.length(), 302, 122);

		font = font.deriveFont(amountFontSize);
		graphics.setFont(font);

		monthTarget = RsFormatter.formatAmount(monthTarget);
		font = font.deriveFont(Font.BOLD);
		graphics.setColor(Color.WHITE);
		
		dayDonation = "₹" +dayDonation;
		monthDonation = "₹" +monthDonation;
		monthTarget = "₹" +monthTarget;
		graphics.drawChars(monthDonation.toCharArray(), 0, dayDonation.length(), 170, 170);
		graphics.drawChars(dayDonation.toCharArray(), 0, monthDonation.length(), 170, 245);
		graphics.drawChars(monthTarget.toCharArray(), 0, monthTarget.length(), 170, 320);
		/*
		graphics.setColor(Color.decode("#663300"));
		graphics.drawChars(dayDonation.toCharArray(), 0, dayDonation.length(), 142, 172);
		graphics.drawChars(monthDonation.toCharArray(), 0, monthDonation.length(), 142, 247);
		graphics.drawChars(monthTarget.toCharArray(), 0, monthTarget.length(), 142, 322);
		*/
		
	}
	
	public static void createTemplate02DDU(OutputStream outputStream, String date, String dayDonation, String monthDonation, String monthTarget)
			throws IOException {
		String templateFileName = "ddu/template02.jpg";
		float dateFontSize = 30.0f;
		float amountFontSize = 45.0f;
		BufferedImage image = null;

		InputStream in = DduUtil.class.getClassLoader().getResourceAsStream(templateFileName);
		image = ImageIO.read(in);

		Graphics2D graphics = image.createGraphics();

		// Font font = new Font("Microsoft Sans Serif", Font.BOLD, fontSize);
		Font font = graphics.getFont();

		try {
			font = Font.createFont(Font.TRUETYPE_FONT, ImageUtil.class.getClassLoader().getResourceAsStream("fonts/NexaBold.otf"));
		} catch (FontFormatException e) {
			e.printStackTrace();
		}
		// graphics.drawChars(monthDonation.toCharArray(), 0,
		// monthDonation.length(), 1400, 550);

		// graphics.setColor(Color.RED);
		dayDonation = RsFormatter.formatAmount(dayDonation);
		monthDonation = RsFormatter.formatAmount(monthDonation);
		font = font.deriveFont(dateFontSize);
		font = font.deriveFont(Font.BOLD);
		graphics.setFont(font);
		graphics.setColor(Color.ORANGE);
		graphics.drawChars(date.toCharArray(), 0, date.length(), 780, 590);
		graphics.setColor(Color.decode("#663300"));
		graphics.drawChars(date.toCharArray(), 0, date.length(), 782, 592);

		String donationUrl = "donate.aamaadmiparty.org";
		graphics.setColor(Color.YELLOW);
		graphics.drawChars(donationUrl.toCharArray(), 0, donationUrl.length(), 700, 672);
		graphics.drawChars(donationUrl.toCharArray(), 0, donationUrl.length(), 450, 190);
		
		font = font.deriveFont(amountFontSize);
		graphics.setFont(font);

		monthTarget = RsFormatter.formatAmount(monthTarget);
		font = font.deriveFont(Font.BOLD);
		graphics.setColor(Color.WHITE);
		
		//dayDonation = "₹" +dayDonation;
		//monthDonation = "₹" +monthDonation;
		//monthTarget = "₹" +monthTarget;
		graphics.drawChars(dayDonation.toCharArray(), 0, dayDonation.length(), 310, 370);
		graphics.drawChars(monthDonation.toCharArray(), 0, monthDonation.length(), 730, 370);
		graphics.drawChars(monthTarget.toCharArray(), 0, monthTarget.length(), 290, 620);
		/*
		graphics.setColor(Color.decode("#663300"));
		graphics.drawChars(dayDonation.toCharArray(), 0, dayDonation.length(), 142, 172);
		graphics.drawChars(monthDonation.toCharArray(), 0, monthDonation.length(), 142, 247);
		graphics.drawChars(monthTarget.toCharArray(), 0, monthTarget.length(), 142, 322);
		*/
		
		ImageIO.write(image, "png", outputStream);
	}

	private static void drawCharInRectangle(Graphics2D graphics, Font font, int startX, int startY, float width, String message) {
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
			float dx = layout.isLeftToRight() ? 0 : width - layout.getAdvance();
			graphics.setFont(font);
			layout.draw(graphics, startX + dx, startY);
			startY += layout.getDescent() + layout.getLeading();
		}
	}

	private static BufferedImage resizeImage(BufferedImage originalImage, int width, int height, int type) {
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();

		return resizedImage;
	}
}