package com.github.avatar21.generics.constants;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public final class Regexp {
	/**
	 * china mobile number regex
	 */
	public static final String CHINA_MOBILE_REGEX = "^1[3-9][0-9]\\d{8}$";
	/**
	 * numeric format regex, matching (-+0..9(.)0...9)[.00]
	 */
	public static final String NUMBER_REGEX = "^[-+]?\\d+(\\.\\d+)?$";
	/**
	 * China account no regex (matching 16-32 places int type)
	 */
	public static final String ACCOUNT_NO_REGEX = "[0-9]{16,32}";
	/**
	 * numeric format regex, matching yyyyMMdd
	 */
	public static final String DATE_NUMERIC_08_REGEX = "(\\d{4})(\\d{2})(\\d{2})";
	/**
	 * numeric format regex, matching yyyyMMddHHmmss
	 */
	public static final String DATE_NUMERIC_14_REGEX = "(\\d{4})(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})";
	/**
	 * date format "yyyy-MM-dd"
	 */
	public static final String DATE_FORMAT_DATE_ONLY_REGEX = "^([1-9]{1}[0-9]{3}-[0-2]{1}[0-9]{1}-[0-3]{1}[0-9]{1})$";
	/**
	 * date format "yyyy-MM-dd HH:mm:ss"
	 */
	public static final String DATE_FORMAT_FULL_WITH_SEC_REGEX = "^([1-9]{1}[0-9]{3}-[0-2]{1}[0-9]{1}-[0-3]{1}[0-9]{1}\\ [0-2]{1}[0-9]{1}:[0-5]{1}[0-9]{1}:[0-5]{1}[0-9]{1})$";
	/**
	 * date format "yyyy-MM-dd HH:mm:ss.SSS"
	 */
	//	public static final String DATE_FORMAT_FULL_WITH_MISEC_REGEX = "^((\\d{4})\\-(\\d{2})\\-(\\d{2})\\ (\\d{2}):(\\d{2}):(\\d{2})\\.(\\d{3}))$";
	public static final String DATE_FORMAT_FULL_WITH_MISEC_REGEX = "^([1-9]{1}[0-9]{3}-[0-2]{1}[0-9]{1}-[0-3]{1}[0-9]{1}\\ [0-2]{1}[0-9]{1}:[0-5]{1}[0-9]{1}:[0-5]{1}[0-9]{1}.[0-9]{3})$";
	/**
	 * emoji regex
	 */
	public static final String EMOJI_REGEX = "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
	/**
	 * phone number regex
	 */
	// TODO incorrect, fix this
	public static final String MOBILE = "1[3-9]\\d{9}$";
	/**
	 * Binary type regex
	 */
	public static final String BINARY_REGEX = "1|0";
	/**
	 * Java boolean type regex
	 */
	public static final String BOOLEAN_REGEX = "true|false";
	/**
	 * Java short type regex
	 */
	public static final String SHORT_REGEX = "^(0|(0?[1-9])|(\\-?[0-9])|(\\-?[1-9][0-9])|(\\-?[1-9][0-9][0-9])|(\\-?[1-9][0-9][0-9][0-9])|(\\-?[1-2][0-9][0-9][0-9][0-9])|(\\-?3[0-1][0-9][0-9][0-9])|(\\-?32[0-6][0-9][0-9])|(\\-?327[0-5][0-9])|(\\-?3276[0-7])|(\\-32768))$" ;
	/**
	 * Long type regex
	 */
	public static final String INTEGER_REGEX = "^(0|(0?[1-9])|(\\-?[0-9])|(\\-?[1-9][0-9])|(\\-?[1-9][0-9][0-9])|(\\-?[1-9][0-9][0-9][0-9])|(\\-?[1-9][0-9][0-9][0-9][0-9])|(\\-?[1-9][0-9][0-9][0-9][0-9][0-9])|(\\-?[1-9][0-9][0-9][0-9][0-9][0-9][0-9])|(\\-?[1-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9])|(\\-?[1-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9])|(\\-?1[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9])|(\\-?20[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9])|(\\-?21474[0-7][0-9][0-9][0-9][0-9])|(\\-?21[0-3][0-9][0-9][0-9][0-9][0-9][0-9][0-9])|(\\-?214[0-6][0-9][0-9][0-9][0-9][0-9][0-9])|(\\-?2147[0-3][0-9][0-9][0-9][0-9][0-9])|(\\-?214748[0-2][0-9][0-9][0-9])|(\\-?2147483[0-5][0-9][0-9])|(\\-?21474836[0-3][0-9])|(\\-?214748364[0-7])|(\\-2147483648))$";
	/**
	 * Long type regex
	 */
	public static final String LONG_REGEX = "^(0|(0?[1-9])|(\\-?[0-9])|(\\-?[1-9][0-9])|(\\-?[1-9][0-9][0-9])|(\\-?[1-9][0-9][0-9][0-9])|(\\-?[1-9][0-9][0-9][0-9][0-9])|(\\-?[1-9][0-9][0-9][0-9][0-9][0-9])|(\\-?[1-9][0-9][0-9][0-9][0-9][0-9][0-9])|(\\-?[1-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9])|(\\-?[1-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9])|(\\-?[1-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9])|(\\-?[1-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9])|(\\-?[1-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9])|(\\-?[1-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9])|(\\-?[1-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9])|(\\-?[1-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9])|(\\-?[1-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9])|(\\-?[1-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9])|(\\-?[1-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9])|(\\-?[1-8][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9])|(\\-?9[0-1][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9])|(\\-?92[0-1][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9])|(\\-?922[0-2][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9])|(\\-?9223[0-2][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9])|(\\-?92233[0-6][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9])|(\\-?922337[0-1][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9])|(\\-?92233720[0-2][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9])|(\\-?922337203[0-5][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9])|(\\-?9223372036[0-7][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9])|(\\-?92233720368[0-4][0-9][0-9][0-9][0-9][0-9][0-9][0-9])|(\\-?922337203685[0-3][0-9][0-9][0-9][0-9][0-9][0-9])|(\\-?9223372036854[0-6][0-9][0-9][0-9][0-9][0-9])|(\\-?92233720368547[0-6][0-9][0-9][0-9][0-9])|(\\-?922337203685477[0-4][0-9][0-9][0-9])|(\\-?9223372036854775[0-7][0-9][0-9])|(\\-?922337203685477580[0-7])|(\\-9223372036854775808))$";
	/**
	 * Java Byte type regex pattern
	 */
	public static final String BYTE_REGEX = "(^\\s*(\\p{XDigit}{1,2}))(\\s+\\p{XDigit}{2})*(\\s+\\p{XDigit})?";
	/**
	 * Short type regex pattern
	 */
	public static final Pattern SHORT_PATTERN = Pattern.compile(SHORT_REGEX);
	/**
	 * Integer type regex pattern
	 */
	public static final Pattern INTEGER_PATTERN = Pattern.compile(INTEGER_REGEX);
	/**
	 * Long type regex pattern
	 */
	public static final Pattern LONG_PATTERN = Pattern.compile(LONG_REGEX);
	/**
	 * number format regex pattern, matching (-+0..9(.)0...9)
	 */
	public static final Pattern NUMBER_PATTERN = Pattern.compile(NUMBER_REGEX);
	/**
	 * date format "yyyyMMdd"
	 */
	public static final SimpleDateFormat DATE_FORMAT_SHORT_08 = new SimpleDateFormat("yyyyMMdd");
	/**
	 * date format "yyyyMMdd"
	 */
	public static final SimpleDateFormat DATE_FORMAT_SHORT_10 = new SimpleDateFormat("yyyy.MM.dd");
	/**
	 * date format "yyyyMMddHHmmss"
	 */
	public static final SimpleDateFormat DATE_FORMAT_LONG_14 = new SimpleDateFormat("yyyyMMddHHmmss");
	/**
	 * date format "yyMMddHHmmssSSS"
	 */
	public static final SimpleDateFormat DATE_FORMAT_LONG_15 = new SimpleDateFormat("yyMMddHHmmssSSS");
	/**
	 * date format "yyMMddHH"
	 */
	public static final SimpleDateFormat DATE_FORMAT_LONG_10 = new SimpleDateFormat("yyyyMMddHH");
	/**
	 * date format "yyyyMMddHHmmssSSS"
	 */
	public static final SimpleDateFormat DATE_FORMAT_LONG_17 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	/**
	 * date format "yyyy-MM-dd"
	 */
	public static final SimpleDateFormat DATE_FORMAT_DATE_ONLY = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * date format "yyyy/MM/dd"
	 */
	public static final SimpleDateFormat DATE_FORMAT_DATE_ONLY_02 = new SimpleDateFormat("yyyy/MM/dd");
	/**
	 * date format "yyyy-MM-dd HH:mm"
	 */
	public static final SimpleDateFormat DATE_FORMAT_DATE_AND_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	/**
	 * date format "yyyy-MM-dd HH:mm:ss"
	 */
	public static final SimpleDateFormat DATE_FORMAT_FULL_WITH_SEC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * date format "yyyy-MM-dd HH:mm:ss.SSS"
	 */
	public static final SimpleDateFormat DATE_FORMAT_FULL_WITH_MISEC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	/**
	 * time format "m"
	 */
	public static final SimpleDateFormat TIME_FORMAT_MINUTE = new SimpleDateFormat("m");
	/**
	 * time format "HHmmss"
	 */
	public static final SimpleDateFormat TIME_FORMAT_06 = new SimpleDateFormat("HHmmss");
	/**
	 * numeric format(Integer) "9, 99, 999"
	 */
	public static DecimalFormat INTEGER_FORMAT = new DecimalFormat("0");
	/**
	 * amount format(2 decimals) "99.00, 9.90, 9999.99"
	 */
	public static DecimalFormat NUMBER_FORMAT_2_DECIMALS = new DecimalFormat("0.00");

	/**
	 * amount format(2 decimals) "99.00, 9.90, 9999.99"
	 */
	public static DecimalFormat NUMBER_FORMAT_6_DECIMALS = new DecimalFormat("0.000000");

	/**
	 * amount format e.g.："1,000,000.00"
	 */
	public static final DecimalFormat NUMBER_FORMAT_WITH_K_SEPERATOR = new DecimalFormat("#,##0.00");

	private Regexp() {
		super();
	}
}
