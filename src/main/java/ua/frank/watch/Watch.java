package ua.frank.watch;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

/**
* calculates angle between hour, minute, second arrows.
* @author frank
* @version 1.2
* @since 26.04.2016
* */
public class Watch {
	/**
	 * timeFormat
	 * */
	private String timeFormat;
	/**
	 * time given from scanner by user.
	 * */
	private String time;
	/**
	 * hour converted from String time.
	 * */
	private double hour;
	/**
	 * minute converted from String time.
	 * */
	private double minute;
	/**
	 * second converted from String time.
	 * */
	private double second;
	/**
	 * hourAngle calculated angle of hour hand.
	 * */
	private double hourAngle;
	/**
	 * minuteAngle calculated angle of minute hand.
	 * */
	private double minuteAngle;
	/**
	 * secondAngle calculated angle of second hand.
	 * */
	private double secondAngle;
	
	/**
	 * launch application.
	 * @param args
	 * */
	public static void main(String[] args) {
		Watch test = new Watch();
		Scanner scanner = new Scanner(System.in);
		System.out.println("type time in 12-hour format \nhour:minute:second");
		test.time = scanner.nextLine();
		try {
			test.timeFormat = test.readPropertyFile("config.properties", "time");
			test.convertTime();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		scanner.close();
		test.initializeAngle();
		System.out.println("angle between minute and second is: " 
				+ test.getAngleBetweenMinuteAndSecond(test.minuteAngle, test.secondAngle));
		System.out.println("angle between minute and hour is: " 
				+ test.getAngleBetweenMinuteAndHour(test.minuteAngle, test.hourAngle));
		System.out.println("angle between hour and second is: " 
				+ test.getAngleBetweenHourAndSecond(test.hourAngle, test.secondAngle));
	}
	
	/**
	 * this method creates thread and gets configuration from property file.
	 * @param source to property file
	 * @param key which returns value from property file
	 * @return time value from properties file
	 * @throws IOException 
	 * */
	public String readPropertyFile(String source,String key) throws IOException {
		try (InputStream inputStream = Watch.class.getClassLoader().getResourceAsStream(source)) {
			Properties properties = new Properties();
			properties.load(inputStream);
			return properties.getProperty(key);
		}
	}
	
	/**
	 * converts from format to time given by user.
	 * */
	public void convertTime() throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat (timeFormat); 
		Date date = simpleDateFormat.parse(time);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		second = Calendar.SECOND;
		minute = Calendar.MINUTE;
		hour = Calendar.HOUR;
	}

	/**
	 * based on given time, this method is calculating hand angles and adds them to array.
	 * @return angleTime calculated angles of arrows
	 * */
	public void initializeAngle() {
		/**
		 * one step angle of hour hand.
		 * */
		final double HOUR_ANGLE = 30; //made this variables local cause class doesn't use them outside initializeAngle() method
		/**
		 * one step angle of second and minute hand.
		 * */
		final double SEC_MIN_ANGLE = 6;
		/**
		 * one step second hand is + 0.1 to minute hand, correction to minute angle.
		 * */
		final double CORRECTION_MINUTE = 0.1;
		/**
		 * one step minute hand is + 0.5 to hour hand, correction to hour angle.
		 * */
		final double CORRECTION_HOUR = 0.5;
		
		secondAngle = second * SEC_MIN_ANGLE;
		minuteAngle = minute * SEC_MIN_ANGLE + second * CORRECTION_MINUTE;
		hourAngle = hour * HOUR_ANGLE + minute * CORRECTION_HOUR;
	}

	/**
	 * calculates angle between hands - second and hour.
	 * @param secondAngle angle of second hand
	 * @param hourAngle angle of hour hand
	 * @return angle between second and hour hands
	 * */
	public double getAngleBetweenHourAndSecond(double secondAngle, double hourAngle) {
		return Math.abs(secondAngle - hourAngle);
	}
	
	/**
	 * calculate and outputs angle between hands - minute and hour.
	 * @param hourAngle angle of hour hand
	 * @param minuteAngle angle of minute hand
	 * @return angle between minute and hour hands
	 * */
	public double getAngleBetweenMinuteAndHour(double hourAngle, double minuteAngle) {
		return Math.abs(hourAngle - minuteAngle);
	}

	/**
	 * calculate and outputs angle between hands - minute and second.
	 * @param minuteAngle angle of minute hand
	 * @param secondAngle angle of second hand
	 * @return angle between minutes and seconds hands
	 * */
	public double getAngleBetweenMinuteAndSecond(double minuteAngle, double secondAngle) {
		return Math.abs(minuteAngle - secondAngle);
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public double getHour() {
		return hour;
	}

	public double getMinute() {
		return minute;
	}

	public double getSecond() {
		return second;
	}

	public double getHourAngle() {
		return hourAngle;
	}

	public double getMinuteAngle() {
		return minuteAngle;
	}

	public double getSecondAngle() {
		return secondAngle;
	}

	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}
}
