package com.next.aap.task.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
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

public class DduUtilBackup {

	/**
	 * 
	 * @param args
	 * 
	 * @throws IOException
	 */

	public static void main(String[] args) throws IOException {
		//for(int i=5;i<=22;i++){
		for(int i=27;i<=29;i++){
			//File outputfile = new File("d:\\tmp\\saved0"+i+".png");
			File outputfile = new File("/Users/ravi/Desktop/saved0"+i+".png");
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
					new FileOutputStream(outputfile));
			String date = "10-Jul-2013";

			String dayDonation = "2744227";

			String monthDonation = "6052921";

			String message = "Have you paid your EMI for better future?";
			String monthTarget="70000000";
			
			switch(i){
			case 0:
				createPostTemplateImageForShazia(bufferedOutputStream, date, dayDonation, monthDonation, monthTarget);
				break;
			case 1:
				createPostTemplateImageForSurender(bufferedOutputStream, date, dayDonation, monthDonation, monthTarget);
				break;
			case 2:
				createPostTemplateImageForManishSisodiya(bufferedOutputStream, date, dayDonation, monthDonation, monthTarget);
				break;
			case 3:
				createPostTemplateImageForGoaplRai(bufferedOutputStream, date, dayDonation, monthDonation, monthTarget);
				break;
			case 4:
				createPostTemplateImageForArvindKejriwal(bufferedOutputStream, date, dayDonation, monthDonation, monthTarget);
				break;
			case 5:
				createDiaryTemplateImageForAkhileshpatiTripathi(bufferedOutputStream, date, dayDonation, monthDonation, monthTarget);
				break;
			case 6:
				createDiaryTemplateImageForColDevenderSingh(bufferedOutputStream, date, dayDonation, monthDonation, monthTarget);
				break;
			case 7:
				createDiaryTemplateImageForCommondo(bufferedOutputStream, date, dayDonation, monthDonation, monthTarget);
				break;
			case 8:
				createDiaryTemplateImageForFarana(bufferedOutputStream, date, dayDonation, monthDonation, monthTarget);
				break;
			case 9:
				createDiaryTemplateImageForGajanand(bufferedOutputStream, date, dayDonation, monthDonation, monthTarget);
				break;
			case 10:
				createDiaryTemplateImageForGulab(bufferedOutputStream, date, dayDonation, monthDonation, monthTarget);
				break;
			case 11:
				createDiaryTemplateImageForKrishnaKumar(bufferedOutputStream, date, dayDonation, monthDonation, monthTarget);
				break;
			case 12:
				createDiaryTemplateImageForManoj(bufferedOutputStream, date, dayDonation, monthDonation, monthTarget);
				break;
			case 13:
				createDiaryTemplateImageForMukeshDagar(bufferedOutputStream, date, dayDonation, monthDonation, monthTarget);
				break;
			case 14:
				createDiaryTemplateImageForRajesh(bufferedOutputStream, date, dayDonation, monthDonation, monthTarget);
				break;
			case 15:
				createDiaryTemplateImageForRajni(bufferedOutputStream, date, dayDonation, monthDonation, monthTarget);
				break;
			case 16:
				createDiaryTemplateImageForRaju(bufferedOutputStream, date, dayDonation, monthDonation, monthTarget);
				break;
			case 17:
				createDiaryTemplateImageForRavi(bufferedOutputStream, date, dayDonation, monthDonation, monthTarget);
				break;
			case 18:
				createDiaryTemplateImageForSanjeevJha(bufferedOutputStream, date, dayDonation, monthDonation, monthTarget);
				break;
			case 19:
				createDiaryTemplateImageForShazia(bufferedOutputStream, date, dayDonation, monthDonation, monthTarget);
				break;
			case 20:
				createDiaryTemplateImageForSomdutt(bufferedOutputStream, date, dayDonation, monthDonation, monthTarget);
				break;
			case 21:
				createDiaryTemplateImageForSomnath(bufferedOutputStream, date, dayDonation, monthDonation, monthTarget);
				break;
			case 22:
				createDiaryTemplateImageForBaljeetSingh(bufferedOutputStream, date, dayDonation, monthDonation, monthTarget);
				break;
			case 23:
				createBoardTemplateImageForDeviDayal(bufferedOutputStream, date, dayDonation, monthDonation, monthTarget);
				break;
			case 24:
				createBoardTemplateImageForArvind(bufferedOutputStream, date, dayDonation, monthDonation, monthTarget);
				break;
			case 25:
				createBoardTemplateImageFoSushilChauhan(bufferedOutputStream, date, dayDonation, monthDonation, monthTarget);
				break;
			case 26:
				createBoardTemplateImageForKapilDhama(bufferedOutputStream, date, dayDonation, monthDonation, monthTarget);
				break;
			case 27:
				createBoardTemplateImageForArvindAndAskingSMSVote(bufferedOutputStream, date, dayDonation, monthDonation, monthTarget);
				break;
			case 28:
				createBoardTemplateImageForSantoshKoli(bufferedOutputStream, date, dayDonation, monthDonation, monthTarget);
				break;
			case 29:
				createBoardTemplateImageForNoBribe(bufferedOutputStream, date, dayDonation, monthDonation, monthTarget);
				break;

			}
			
						 
			 bufferedOutputStream.close();
			
		}
		//InputStream in = new FileInputStream("D:\\tmp\\raj\\image01.jpg");//
		//createChange4IndiaImage07(bufferedOutputStream, in, message,42);
		
	}
	
	public static void createPostTemplateImageForShazia(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		createPostTemplateImage(outputStream, date, dayDonation, monthDonation, monthTarget, "canddu/01shazia.jpg");
	}
	public static void createPostTemplateImageForSurender(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		createPostTemplateImage(outputStream, date, dayDonation, monthDonation, monthTarget, "canddu/02surendersingh.jpg");
	}
	public static void createPostTemplateImageForManishSisodiya(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		createPostTemplateImage(outputStream, date, dayDonation, monthDonation, monthTarget, "canddu/03manishsisodiya.jpg");
	}
	public static void createPostTemplateImageForGoaplRai(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		createPostTemplateImage(outputStream, date, dayDonation, monthDonation, monthTarget, "canddu/04gopalrai.jpg");
	}
	public static void createPostTemplateImageForArvindKejriwal(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		createPostTemplateImage(outputStream, date, dayDonation, monthDonation, monthTarget, "canddu/05arvindkejriwal.jpg");
	}
	
	public static void createDiaryTemplateImageForAkhileshpatiTripathi(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		createDiaryPostTemplateImage(outputStream, date, dayDonation, monthDonation, monthTarget, "canddu/06akhileshpatitripathi.jpg");
	}
	public static void createDiaryTemplateImageForColDevenderSingh(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		createDiaryPostTemplateImage(outputStream, date, dayDonation, monthDonation, monthTarget, "canddu/07coldev.jpg");
	}
	public static void createDiaryTemplateImageForCommondo(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		createDiaryPostTemplateImage(outputStream, date, dayDonation, monthDonation, monthTarget, "canddu/08commondo.jpg");
	}
	public static void createDiaryTemplateImageForFarana(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		createDiaryPostTemplateImage(outputStream, date, dayDonation, monthDonation, monthTarget, "canddu/09farana.jpg");
	}
	public static void createDiaryTemplateImageForGajanand(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		createDiaryPostTemplateImage(outputStream, date, dayDonation, monthDonation, monthTarget, "canddu/10gajanand.jpg");
	}
	public static void createDiaryTemplateImageForGulab(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		createDiaryPostTemplateImage(outputStream, date, dayDonation, monthDonation, monthTarget, "canddu/11gulab.jpg");
	}
	public static void createDiaryTemplateImageForKrishnaKumar(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		createDiaryPostTemplateImage(outputStream, date, dayDonation, monthDonation, monthTarget, "canddu/12krishnakumar.jpg");
	}
	public static void createDiaryTemplateImageForManoj(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		createDiaryPostTemplateImage(outputStream, date, dayDonation, monthDonation, monthTarget, "canddu/13manoj.jpg");
	}
	public static void createDiaryTemplateImageForMukeshDagar(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		createDiaryPostTemplateImage(outputStream, date, dayDonation, monthDonation, monthTarget, "canddu/14mukeshdagar.jpg");
	}
	public static void createDiaryTemplateImageForRajesh(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		createDiaryPostTemplateImage(outputStream, date, dayDonation, monthDonation, monthTarget, "canddu/15rajesh.jpg");
	}
	public static void createDiaryTemplateImageForRajni(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		createDiaryPostTemplateImage(outputStream, date, dayDonation, monthDonation, monthTarget, "canddu/16rajni.jpg");
	}
	public static void createDiaryTemplateImageForRaju(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		createDiaryPostTemplateImage(outputStream, date, dayDonation, monthDonation, monthTarget, "canddu/17raju.jpg");
	}
	public static void createDiaryTemplateImageForRavi(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		createDiaryPostTemplateImage(outputStream, date, dayDonation, monthDonation, monthTarget, "canddu/18ravi.jpg");
	}
	public static void createDiaryTemplateImageForSanjeevJha(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		createDiaryPostTemplateImage(outputStream, date, dayDonation, monthDonation, monthTarget, "canddu/19sanjeevjha.jpg");
	}
	public static void createDiaryTemplateImageForShazia(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		createDiaryPostTemplateImage(outputStream, date, dayDonation, monthDonation, monthTarget, "canddu/20shazia.jpg");
	}
	public static void createDiaryTemplateImageForSomdutt(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		createDiaryPostTemplateImage(outputStream, date, dayDonation, monthDonation, monthTarget, "canddu/21somdutt.jpg");
	}
	public static void createDiaryTemplateImageForSomnath(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		createDiaryPostTemplateImage(outputStream, date, dayDonation, monthDonation, monthTarget, "canddu/22somnath.jpg");
	}
	public static void createDiaryTemplateImageForBaljeetSingh(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		createDiaryPostTemplateImage(outputStream, date, dayDonation, monthDonation, monthTarget, "canddu/23baljeetsingh.jpg");
	}
	
	public static void createDiaryPostTemplateImage(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget,String templateFileName)
			throws IOException {
		float dateFontSize = 20.0f;
		float amountFontSize = 40.0f;
		BufferedImage image = null;

		try {

			InputStream in = DduUtilBackup.class.getClassLoader()
					.getResourceAsStream(templateFileName);
			image = ImageIO.read(in);

		} catch (IOException e) {

		}

		Graphics2D graphics = image.createGraphics();

		//Font font = new Font("Microsoft Sans Serif", Font.BOLD, fontSize);
		Font font = graphics.getFont();
		

		try{
			double monthDonationInt = Double.parseDouble(monthDonation);
			double monthTargetInt = Double.parseDouble(monthTarget);
			int percent = (int)((monthDonationInt / monthTargetInt) * 100);
			String message = "Have achieved "+percent+"%, together we can do 100%";
			
			font = font.deriveFont(20.0f);
			graphics.setColor(Color.RED);
			graphics.setFont(font);
			graphics.drawChars(message.toCharArray(), 0, message.length(), 110, 718);
		}catch(Exception ex){
			ex.printStackTrace();
		}

		graphics.setColor(Color.ORANGE);
		dayDonation = RsFormatter.formatAmount(dayDonation);
		monthDonation = RsFormatter.formatAmount(monthDonation);
		font = font.deriveFont(dateFontSize);
		graphics.setFont(font);
		graphics.drawChars(date.toCharArray(), 0, date.length(), 380, 78);

		font = font.deriveFont(amountFontSize);
		graphics.setFont(font);
		graphics.drawChars(dayDonation.toCharArray(), 0, dayDonation.length(), 200, 530);
		graphics.drawChars(monthDonation.toCharArray(), 0, monthDonation.length(), 200, 630);
		
		
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, ImageUtil.class.getClassLoader().getResourceAsStream("fonts/ALGER.TTF"));
		} catch (FontFormatException e) {
			e.printStackTrace();
		}
		font = font.deriveFont(40.0f);
		graphics.setFont(font);
		String mothTitle = "Month Target";
		monthTarget = RsFormatter.formatAmount(monthTarget);
		
		
	/*	
		graphics.setColor(Color.GREEN);
		graphics.drawChars(mothTitle.toCharArray(), 0, mothTitle.length(), 178, 223);
		graphics.drawChars(monthTarget.toCharArray(), 0, monthTarget.length(), 218, 273);
*/
		graphics.setColor(Color.WHITE);
		graphics.drawChars(mothTitle.toCharArray(), 0, mothTitle.length(), 180, 225);
		graphics.drawChars(monthTarget.toCharArray(), 0, monthTarget.length(), 220, 275);
		
		
		//String message = "Clean Politics need Clean money.Please donate to the AAP  today so we can continue this battle and meet with electoral success in the Delhi elections this November.  Most importantly, get your friends and family members to donate as well.";
		//graphics.setColor(Color.RED);
		//drawCharInRectangle(graphics, font, 400, 600, 600, message);

		
		ImageIO.write(image, "png", outputStream);
	}
	
	public static void createBoardTemplateImageForDeviDayal(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		createBoardPostTemplateImage(outputStream, date, dayDonation, monthDonation, monthTarget, "canddu/24devidayal.jpg");
	}
	public static void createBoardTemplateImageForKapilDhama(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		createBoardPostTemplateImage(outputStream, date, dayDonation, monthDonation, monthTarget, "canddu/25kapildhama.jpg");
	}
	public static void createBoardTemplateImageForArvind(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		createBoardPostTemplateImage(outputStream, date, dayDonation, monthDonation, monthTarget, "canddu/26newdduak.jpg");
	}
	public static void createBoardTemplateImageFoSushilChauhan(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		createBoardPostTemplateImage(outputStream, date, dayDonation, monthDonation, monthTarget, "canddu/27sushilchauhan.jpg");
	}
	public static void createBoardTemplateImageFoMukeshHooda(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		createBoardPostTemplateImage(outputStream, date, dayDonation, monthDonation, monthTarget, "canddu/28mukeshhooda.jpg");
	}
	
	public static void createBoardTemplateImageForArvindAndAskingSMSVote(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		String templateFileName = "canddu/29arvindwithsms.jpg";
		createBoardTemplateImageForDecember(outputStream, date, dayDonation, monthDonation, monthTarget, templateFileName);
	}
	public static void createBoardTemplateImageForSantoshKoli(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		String templateFileName = "canddu/30santoshkoli.jpg";
		createBoardTemplateImageForDecember(outputStream, date, dayDonation, monthDonation, monthTarget, templateFileName);
	}
	public static void createBoardTemplateImageForJoinApp(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		String templateFileName = "ddu/joinaap.jpg";
		createBoardTemplateImageForDecember(outputStream, date, dayDonation, monthDonation, monthTarget, templateFileName);
	}
	public static void createBoardTemplateImageForNoBribe(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		String templateFileName = "canddu/ddu_nobribe.jpg";
		createBoardTemplateImageForDecember(outputStream, date, dayDonation, monthDonation, monthTarget, templateFileName);
	}
	public static void createBoardTemplateImageForXmas(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		String templateFileName = "canddu/31christmas.jpg";
		createBoardTemplateImageForDecember(outputStream, date, dayDonation, monthDonation, monthTarget, templateFileName);
	}
	public static void createBoardTemplateImageForXmas2(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget)
			throws IOException {
		String templateFileName = "canddu/31xmas.jpg";
		createBoardTemplateImageForDecember(outputStream, date, dayDonation, monthDonation, monthTarget, templateFileName);
	}
	
	public static void createBoardTemplateImageForDecember(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget,String templateFileName)
			throws IOException {
		float dateFontSize = 60.0f;
		float amountFontSize = 80.0f;
		BufferedImage image = null;

		try {

			InputStream in = DduUtilBackup.class.getClassLoader()
					.getResourceAsStream(templateFileName);
			image = ImageIO.read(in);

		} catch (IOException e) {

		}

		Graphics2D graphics = image.createGraphics();

		//Font font = new Font("Microsoft Sans Serif", Font.BOLD, fontSize);
		Font font = graphics.getFont();
		

		try{
			double monthDonationInt = Double.parseDouble(monthDonation);
			double monthTargetInt = Double.parseDouble(monthTarget);
			int percent = (int)((monthDonationInt / monthTargetInt) * 100);
			String message = "Have achieved "+percent+"%, together we can do 100%";
			
			font = font.deriveFont(30.0f);
			graphics.setColor(Color.RED);
			graphics.setFont(font);
			graphics.drawChars(message.toCharArray(), 0, message.length(), 1300, 970);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		//graphics.drawChars(monthDonation.toCharArray(), 0, monthDonation.length(), 1400, 550);

		//graphics.setColor(Color.RED);
		dayDonation = RsFormatter.formatAmount(dayDonation);
		monthDonation = RsFormatter.formatAmount(monthDonation);
		font = font.deriveFont(dateFontSize);
		graphics.setFont(font);
		graphics.setColor(Color.decode("#663300"));
		graphics.drawChars(date.toCharArray(), 0, date.length(), 1500, 330);

		font = font.deriveFont(amountFontSize);
		graphics.setFont(font);

		String  totalDays = new Integer(daysBetween()).toString();
		
		
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
		/*
		font = font.deriveFont(140.0f);
		graphics.setFont(font);
		
		graphics.setColor(Color.RED);
		graphics.drawChars(totalDays.toCharArray(), 0, totalDays.length(), 1468, 1008);

		graphics.setColor(Color.WHITE);
		graphics.drawChars(totalDays.toCharArray(), 0, totalDays.length(), 1470, 1010);
		*/
		ImageIO.write(image, "png", outputStream);
	}

	public static void createBoardPostTemplateImage(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget,String templateFileName)
			throws IOException {
		float dateFontSize = 60.0f;
		float amountFontSize = 80.0f;
		BufferedImage image = null;

		try {

			InputStream in = DduUtilBackup.class.getClassLoader()
					.getResourceAsStream(templateFileName);
			image = ImageIO.read(in);

		} catch (IOException e) {

		}

		Graphics2D graphics = image.createGraphics();

		//Font font = new Font("Microsoft Sans Serif", Font.BOLD, fontSize);
		Font font = graphics.getFont();
		

		try{
			double monthDonationInt = Double.parseDouble(monthDonation);
			double monthTargetInt = Double.parseDouble(monthTarget);
			int percent = (int)((monthDonationInt / monthTargetInt) * 100);
			String message = "Have achieved "+percent+"%, together we can do 100%";
			
			font = font.deriveFont(30.0f);
			graphics.setColor(Color.RED);
			graphics.setFont(font);
			graphics.drawChars(message.toCharArray(), 0, message.length(), 1300, 770);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		//graphics.drawChars(monthDonation.toCharArray(), 0, monthDonation.length(), 1400, 550);

		//graphics.setColor(Color.RED);
		dayDonation = RsFormatter.formatAmount(dayDonation);
		monthDonation = RsFormatter.formatAmount(monthDonation);
		font = font.deriveFont(dateFontSize);
		graphics.setFont(font);
		graphics.setColor(Color.decode("#663300"));
		graphics.drawChars(date.toCharArray(), 0, date.length(), 580, 60);

		font = font.deriveFont(amountFontSize);
		graphics.setFont(font);

		String  totalDays = new Integer(daysBetween()).toString();
		
		
		monthTarget = RsFormatter.formatAmount(monthTarget);

		graphics.setColor(Color.RED);
		graphics.drawChars(dayDonation.toCharArray(), 0, dayDonation.length(), 1398, 368);
		graphics.drawChars(monthDonation.toCharArray(), 0, monthDonation.length(), 1398, 548);
		graphics.drawChars(monthTarget.toCharArray(), 0, monthTarget.length(), 1398, 728);

		graphics.setColor(Color.decode("#663300"));
		graphics.drawChars(dayDonation.toCharArray(), 0, dayDonation.length(), 1400, 370);
		graphics.drawChars(monthDonation.toCharArray(), 0, monthDonation.length(), 1400, 550);
		graphics.drawChars(monthTarget.toCharArray(), 0, monthTarget.length(), 1400, 730);
		
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, ImageUtil.class.getClassLoader().getResourceAsStream("fonts/ALGER.TTF"));
		} catch (FontFormatException e) {
			e.printStackTrace();
		}
		font = font.deriveFont(140.0f);
		graphics.setFont(font);
		
		graphics.setColor(Color.RED);
		graphics.drawChars(totalDays.toCharArray(), 0, totalDays.length(), 1468, 1008);

		graphics.setColor(Color.WHITE);
		graphics.drawChars(totalDays.toCharArray(), 0, totalDays.length(), 1470, 1010);
		
		ImageIO.write(image, "png", outputStream);
	}
	public static int daysBetween(){
		Calendar electionCal = Calendar.getInstance();
		
		electionCal.set(Calendar.DATE, 4);
		electionCal.set(Calendar.MONTH, Calendar.DECEMBER);
		
		
		Calendar todayCalendar = Calendar.getInstance();
        return (int)( (electionCal.getTime().getTime() - todayCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
	}
	
	public static void createPostTemplateImage(OutputStream outputStream,
			String date, String dayDonation, String monthDonation,String monthTarget,String templateFileName)
			throws IOException {
		float fontSize = 60.0f;
		BufferedImage image = null;

		try {

			InputStream in = DduUtilBackup.class.getClassLoader()
					.getResourceAsStream(templateFileName);
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
			
			font = font.deriveFont(28.0f);
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