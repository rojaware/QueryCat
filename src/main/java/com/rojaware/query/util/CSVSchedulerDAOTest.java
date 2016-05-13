package com.rojaware.query.util;


import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

;

/**
 * The class CSVSchedulerDAO contains the implementation of ICSVSchedulerDAO
 * interface class,
 * 
 * @see IShowHideReportDAO
 */

public class CSVSchedulerDAOTest {


	
	private static final String PATH_SEPARATER = "/";
	private static final String COMMA_DELIMITER = ",";
	private static final char NEW_LINE = '\n';
	
	private static final String REPORT_FORMAT_DATE = "yyyyMMdd";
	private static final String REPORT_FORMAT_TIME = "hhmmss";
	
	private static String DELAY = "DELAY";
	private static String NO_DELAY = "NODELAY";
	private static String NA = "NA";
	private static String CUST_CPARS = "30062110";

	

	/**
	 * The Method is to write the data in the CSV File format
	 * 
	 * @param data
	 * @param csvSchedulerTO
	 * @return
	 * @throws BusinessException
	 */
	public Boolean generateCSVFile(List<String> data) {
		CSVSchedulerTO csvSchedulerTO =getCsvSchedulerTO();
		Boolean status = false;

		String accountIdList = "404154,703317,1056022,1549030"; //getCommaSepValueBuilder(csvSchedulerTO
				//.getUsAccountList());

		String[] array = null;
		String[] array1 = null;
		array=new String[data.size()];
		array1=data.toArray(new String[data.size()]);
		try {
			final File strPath = new File(csvSchedulerTO.getFileLocation());
			if (strPath.exists()) {
				String fileName = csvSchedulerTO.getFileLocation()
						+ PATH_SEPARATER + csvSchedulerTO.getFileName();
				CsvWriter csvOutput = new CsvWriter(new FileWriter(fileName,
						false), ',');
				String FILE_HEADER = "H"
						+ COMMA_DELIMITER
						+ convertDateToString(csvSchedulerTO.getFromDate(),
								REPORT_FORMAT_DATE)
						+ COMMA_DELIMITER
						+ convertDateToString(csvSchedulerTO.getToDate(),
								REPORT_FORMAT_DATE)
						+ COMMA_DELIMITER
						+ convertDateToString(new Date(), REPORT_FORMAT_DATE)
						+ COMMA_DELIMITER
						+ convertDateToString(new Date(), REPORT_FORMAT_TIME)
						+ COMMA_DELIMITER
						+ "0001"
						+ COMMA_DELIMITER
						+ ((csvSchedulerTO.getCustomerLoginId() == null) ? ""
								: csvSchedulerTO.getCustomerLoginId())
						+ COMMA_DELIMITER + "AUTOCONNECT";

				String FILE_DATA = "D";
				String FILE_FOOTER = "T";
				if (data != null && data.size() > 0) {
					//array = new String[data.size()];
					int index = 0;
					// String amount="0";
					String creditDebitFlag = "";
					String balancePrefix;
					String currencyCode = "";
					
					 
				 //	for (Iterator i = data.iterator(); i.hasNext();) {
						balancePrefix = "";
						//Object[] obj = (Object[]) i.next();
						Object[] obj = array1;
						String baiName;
						if ("FR".equalsIgnoreCase(csvSchedulerTO.getLanguage())) {
							baiName = ((obj[6] == null) ? "" : "\""
									+ obj[6].toString() + "\"");
						} else {
							baiName = ((obj[5] == null) ? "" : "\""
									+ obj[5].toString() + "\"");
						}
						array[index] = FILE_DATA
								+ COMMA_DELIMITER
								//+ ((obj[1] == null) ? "" : convertDateToString(
								//		obj[1], REPORT_FORMAT_DATE))
								+(obj[1])
								+ COMMA_DELIMITER;

						if (obj[24] != null
								&& "TRAN".equalsIgnoreCase(obj[24].toString()))
							array[index] = array[index]
									//+ convertDateToString(obj[2],
											+  (obj[2]);

						array[index] = array[index]
								+ COMMA_DELIMITER
								+ ((obj[19] == null) ? "" : obj[19].toString())
								+ COMMA_DELIMITER
								+ ((obj[7] == null) ? "" : obj[7].toString())
								+ COMMA_DELIMITER
								+ ((obj[9] == null) ? "" : "\""
										+ obj[9].toString() + "\"")
								+ COMMA_DELIMITER
								+ ((obj[8] == null) ? "" : "\""
										+ obj[8].toString() + "\"")
								+ COMMA_DELIMITER
								// + ((obj[3] == null) ? "" :
								// ((Integer.parseInt(obj[3].toString())>=0)?
								// obj[3].toString() : "NULL"))
								+ ((obj[3] == null) ? "" : ((obj[3].toString()
										.indexOf("-") >= 0) ? "NULL" : obj[3]
										.toString())) + COMMA_DELIMITER
								+ baiName + COMMA_DELIMITER;
						creditDebitFlag = ((obj[18] == null) ? "" : obj[18]
								.toString());
						currencyCode = ((obj[9] == null) ? "" : obj[9]
								.toString());
						if ("D".equalsIgnoreCase(creditDebitFlag)) {
							balancePrefix = "-";
						}
						if ("USD".equalsIgnoreCase(currencyCode)
								|| "CAD".equalsIgnoreCase(currencyCode)) {
							balancePrefix += "$";
						}
						if (obj[24] != null
								&& "TRAN".equalsIgnoreCase(obj[24].toString())) {
							// logger.info("Transaction Type"+((obj[17] == null)
							// ? "" :
							// balancePrefix+createDecimalValueForCurrencyAmount(obj[17].toString())));
							array[index] = array[index]
									+ ((obj[17] == null) ? ""
											: balancePrefix
													+ createDecimalValueForCurrencyAmount(obj[17]
															.toString()));
						} else {
							// logger.info("Balance/Summary Type"+((obj[23] ==
							// null) ? "" :
							// balancePrefix+createDecimalValueForCurrencyAmount(obj[23].toString())));

							String s = ((obj[23] == null) ? ""
									: createDecimalValueForCurrencyAmount(obj[23]
											.toString()));

							if (s.indexOf("-") < 0) {
								s = "$" + s;
							} else
								s = s.replace("-", "-$");

							array[index] = array[index] + s;
						}
						if (!accountIdList.equalsIgnoreCase("''")) {
							array[index] = array[index]
									+ COMMA_DELIMITER
									+ ((obj[20] == null) ? ""
											: balancePrefix
													+ createDecimalValueForCurrencyAmount(obj[20]
															.toString()))
									+ COMMA_DELIMITER
									+ ((obj[21] == null) ? ""
											: balancePrefix
													+ createDecimalValueForCurrencyAmount(obj[21]
															.toString()))
									+ COMMA_DELIMITER
									+ ((obj[22] == null) ? ""
											: balancePrefix
													+ createDecimalValueForCurrencyAmount(obj[22]
															.toString()));
						}
						array[index] = array[index]
								+ COMMA_DELIMITER
								+ ((obj[15] == null) ? "" : obj[15].toString()
										.trim().trim())
								+ COMMA_DELIMITER
								+ ((obj[16] == null) ? "" : obj[16].toString()
										.trim())
								+ COMMA_DELIMITER
								+ ((obj[4] == null) ? ""
										: "\""
												+ reFormatDesc((String) obj[4],
														csvSchedulerTO,
														(String) obj[9]) + "\"") 
						  + COMMA_DELIMITER;

						index++;
					 }

					FILE_FOOTER = FILE_FOOTER + COMMA_DELIMITER
							+ trailerPadded(data.size() + 2, 8);
					String csvFooter = csvSchedulerTO.getSchedularTrailer()
							+ (data.size() + 4);
					csvOutput.setTextQualifier('\0');
					csvOutput.setForceQualifier(false);
					csvOutput.setDelimiter(NEW_LINE);
					csvOutput.write(csvSchedulerTO.getSchedulerHeader());
					csvOutput.write(FILE_HEADER);
					csvOutput.writeRecord(array);
					csvOutput.write(FILE_FOOTER);
					csvOutput.write(csvFooter);
					csvOutput.endRecord();

					csvOutput.flush();
					csvOutput.close();

 			//	} else {
					FILE_FOOTER = FILE_FOOTER + COMMA_DELIMITER
							+ trailerPadded(2, 8);
					//String csvFooter = csvSchedulerTO.getSchedularTrailer() + (4);
					 csvFooter = csvSchedulerTO.getSchedularTrailer() + (4);
					csvOutput.setForceQualifier(false);
					csvOutput.setDelimiter(NEW_LINE);
					csvOutput.write(csvSchedulerTO.getSchedulerHeader());
					csvOutput.write(FILE_HEADER);
					csvOutput.write(FILE_FOOTER);
					csvOutput.write(csvFooter);
					csvOutput.endRecord();

					csvOutput.flush();
					csvOutput.close();
					// logger.trace("Empty CSV file genreated  ......");
 				}

				status = true;
			//} else {
				// throw new
				// SystemException("The file path could not be found."+csvSchedulerTO.getFileLocation());
			//}
		   
			 
		} catch (Exception e) {
			status = false;
			// logger.trace("CSV file not genreated ......");
			// logger.error("CSV file not genreated : generateCSVFile " + e);
			// throw new BusinessException(e);
		}

		return status;
	}

	/**
	 * The Method is to convert Date to String.
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	private String convertDateToString(Date date, String format) {
		DateFormat df = new SimpleDateFormat(format);
		String reportDate = df.format(date);
		return reportDate;
	}

	

	/**
	 * This method is for left padding zeros in report trailer.
	 * 
	 * @param x
	 * @param pad
	 * @return
	 */
	private String trailerPadded(int x, int pad) {
		String r = "";
		for (int i = 0; i < pad - (Integer.toString(x).length()); i++)
			r += "0";

		return r + x;
	}

	private String createDecimalValueForCurrencyAmount(String currencyAmount) {
		String decimalCurrencyAmount = currencyAmount;
		if (!currencyAmount.contains(".")) { // example '149' converted to
												// '149.00'
			decimalCurrencyAmount = currencyAmount + ".00";
		} else if (currencyAmount.substring(currencyAmount.indexOf(".") + 1)
				.length() == 1) { // example '149.6' converted to '149.60'
			decimalCurrencyAmount = currencyAmount + "0";
		}
		if ("0".equals(decimalCurrencyAmount.split("\\.")[0])) { // if 0.00 then
																	// convert
																	// to .00
			decimalCurrencyAmount = "." + decimalCurrencyAmount.split("\\.")[1];
		}
		return decimalCurrencyAmount;
	}

	// private void debugMe(CSVSchedulerTO csvSchedulerTO){
	// logger.info("csvSchedulerTO.getFileLocation()>>>>>>"+csvSchedulerTO.getFileLocation());
	//			
	// logger.info("csvSchedulerTO.getFileName()>>>>>>"+csvSchedulerTO.getFileName());
	//			
	// logger.info("csvSchedulerTO.getSchedulerHeader()>>>>>>"+csvSchedulerTO.getSchedulerHeader());
	//			
	// logger.info("csvSchedulerTO.getSchedularTrailer()>>>>>>"+csvSchedulerTO.getSchedularTrailer());
	//			
	// logger.info("csvSchedulerTO.getReportType()>>>>>>"+csvSchedulerTO.getReportType());
	//			
	// logger.info("csvSchedulerTO.getFrequency()>>>>>>"+csvSchedulerTO.getFrequency());
	//			
	// logger.info("csvSchedulerTO.getFormatType()>>>>>>"+csvSchedulerTO.getFormatType());
	//			
	// logger.info("csvSchedulerTO.getTypeOfDate()>>>>>>"+csvSchedulerTO.getTypeOfDate());
	//			
	// logger.info("csvSchedulerTO.getLanguage()>>>>>>"+csvSchedulerTO.getLanguage());
	//			
	// logger.info("csvSchedulerTO.getFromDate()>>>>>>"+csvSchedulerTO.getFromDate());
	//			
	// logger.info("csvSchedulerTO.getToDate()>>>>>>"+csvSchedulerTO.getToDate());
	//			
	// logger.info("csvSchedulerTO.getNewDataOnly()>>>>>>"+csvSchedulerTO.getNewDataOnly());
	//			
	// //public List<Long> getUsAccountList() {
	//			
	// //public List<Long> getCanAccountList() {
	//			
	// logger.info("csvSchedulerTO.getAcctSortColumn()>>>>>>"+csvSchedulerTO.getAcctSortColumn());
	//			
	// logger.info("csvSchedulerTO.getAcctSortOrder()>>>>>>"+csvSchedulerTO.getAcctSortOrder());
	//			
	// logger.info("csvSchedulerTO.getMaskAccount()>>>>>>"+csvSchedulerTO.getMaskAccount());
	//			
	// //public List<Long> getBalanceTypes() {
	//			
	// //public List<Long> getSummaryCodes() {
	//			
	// //public List<Long> getTransactions() {
	//			
	// logger.info("csvSchedulerTO.getSenderID()>>>>>>"+csvSchedulerTO.getSenderID());
	//			
	// logger.info("csvSchedulerTO.getGeneratedBy()>>>>>>"+csvSchedulerTO.getGeneratedBy());
	//			
	// //public List<Long> getSameDayAccountIdList() {
	//			
	// logger.info("csvSchedulerTO.getLastGenerated()>>>>>>"+csvSchedulerTO.getLastGenerated());
	//			
	// logger.info("csvSchedulerTO.getPrimaryRelation()>>>>>>"+csvSchedulerTO.getPrimaryRelation());
	//			
	// logger.info("csvSchedulerTO.getCustomerLoginId()>>>>>>"+csvSchedulerTO.getCustomerLoginId());
	//			
	// logger.info("csvSchedulerTO.getTransGroups()>>>>>>"+csvSchedulerTO.getTransGroups());
	//			
	// logger.info("csvSchedulerTO.getInclWireDtls()>>>>>>"+csvSchedulerTO.getInclWireDtls());
	//			
	// }
	private String reFormatDesc(String input, CSVSchedulerTO csvSchedulerTO,
			String accountId) {
		// logger.info("original text >>>"+input);
		if ( !(csvSchedulerTO.getInclWireDtls().equals(DELAY)
				&& (csvSchedulerTO.getCustomerLoginId().equals(CUST_CPARS))  
				&& (accountId.equals("00021068602") || accountId
						.equals("00024508342")))) {

			String[] temp = input.split("::");
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < temp.length; i++) {
				if (temp[i].trim().length() > 4) {
					sb.append("::");
					sb.append(temp[i]);
				}
			}
			// logger.info("new text >>>>"+sb.toString());
			return sb.toString().replace("::", " ");
		} else {
			return input.replace("::", " ");
		}
	}

	public static void main(String[] arg) {
		CSVSchedulerDAOTest cSVSchedulerDAOTest = new CSVSchedulerDAOTest();
		ArrayList<String> list =cSVSchedulerDAOTest.getResultset();
		cSVSchedulerDAOTest.generateCSVFile(list);
	}

	private  CSVSchedulerTO getCsvSchedulerTO() {
		CSVSchedulerTO csvSchedulerTO = this.new CSVSchedulerTO();
		

		Calendar fromCal = Calendar.getInstance();
		fromCal.set(Calendar.YEAR, 2015);
		fromCal.set(Calendar.MONTH, 10);
		fromCal.set(Calendar.DAY_OF_MONTH, 1);
		Date myFromDate = fromCal.getTime();
		
		Calendar toCal = Calendar.getInstance();
		toCal.set(Calendar.YEAR, 2015);
		toCal.set(Calendar.MONTH, 10);
		toCal.set(Calendar.DAY_OF_MONTH, 30);
		Date myToDate = toCal.getTime();
		
		
//		Date myFromDate = new Date(csvSchedulerTO.getToDate().getTime() - 185 * 24
//				* 60 * 60 * 1000);
//		Date myToDate = new Date(myDate.getTime() + 35 * 24 * 60 * 60 * 1000);
		
		ArrayList<Long> xx = new ArrayList<Long>();
		xx.add(new Long(404154));
		xx.add(new Long(703317));
		xx.add(new Long(1056022));
		xx.add(new Long(1549030));
		
		
		
		csvSchedulerTO.setFileLocation("c:\\temp");
		csvSchedulerTO.setFileName("schedulerReport");
		csvSchedulerTO.setSchedulerHeader("AUTOACCESS");
		csvSchedulerTO.setSchedularTrailer("AUTOACCESS TRAILER:");
		csvSchedulerTO.setReportType("ReconReport");
		csvSchedulerTO.setFrequency("Daily");
		csvSchedulerTO.setFormatType("CSV");
		//csvSchedulerTO.setTypeOfDate("PROCESSING");
		csvSchedulerTO.setTypeOfDate("VALUE");
		csvSchedulerTO.setLanguage("EN");
		//csvSchedulerTO.setFromDate("Tue Apr 19 23:59:59 EDT 2016");
		//csvSchedulerTO.setToDate("Wed May 04 23:59:59 EDT 2016");
		csvSchedulerTO.setFromDate(myFromDate);
		csvSchedulerTO.setToDate(myToDate);
		csvSchedulerTO.setNewDataOnly(false);
		// public List<Long> getUsAccountList() {

		csvSchedulerTO.setCanAccountList(xx);
		csvSchedulerTO.setAcctSortColumn("");
		csvSchedulerTO.setAcctSortOrder("");
		csvSchedulerTO.setMaskAccount(false);
		//csvSchedulerTO.setBalanceTypes(balList);
		// public List<Long> getSummaryCodes() {
		// public List<Long> getTransactions() {
		csvSchedulerTO.setSenderID("");
		csvSchedulerTO.setGeneratedBy("");
		// public List<Long> getSameDayAccountIdList() {
		csvSchedulerTO.setLastGenerated(null);
		csvSchedulerTO.setPrimaryRelation("US");
		csvSchedulerTO.setCustomerLoginId("10000006");
		csvSchedulerTO.setTransGroups("");
		csvSchedulerTO.setInclWireDtls("NODELAY");
		//csvSchedulerTO.setInclWireDtls("NODETAIL");

		return csvSchedulerTO;

	}
	private static ArrayList<String> getResultset() {
	 ArrayList<String> result = new ArrayList<String>();
	 // this is the result of 'select * from  tablexxx'
	 //5987748	15-11-17	15-11-17	195	::VDT=16-02-01::BNF=BENEFICIARY_NAME|BENEFICIARY_ADDRESS::ORG=ORDERING_CUSTOMER_NAME|ORDERING_CUSTOMER_ADDRESS::OGB=ORDERING_BANK_NAME|ORDERING_BANK_ADDRESS::OBI=PAYMENT_DETAILS::BBI=SENDER_TO_RECEIVER_INFO::REF=REFERENCE_NUMBER::ITM=16-02-01 12:33:30.000000	Incoming Wire Payment	Réception dun paiement	200	CAD DDA	CAD	BMO	53	DDA	39392.03	21399.9	145679	696000700	1000	C	BMO					tran	1	125
				
	 Calendar ddd = Calendar.getInstance();
	 ddd.set(Calendar.YEAR, 2015);
	 ddd.set(Calendar.MONTH, 10);
	 ddd.set(Calendar.DAY_OF_MONTH, 1);
	 Date myToDate = ddd.getTime();
	
//	 result.add(new Integer(5987748));
//	 result.add(myToDate);
//	 result.add(myToDate);
	 result.add("5987748");
	 result.add("15-11-17");
	 result.add("15-11-17");
	 result.add("195");
	// result.add(" VDT=16-02-01 BNF=BENEFICIARY_NAME|BENEFICIARY_ADDRESS ORG= OGB=ORDERING_BANK_NAME|ORDERING_BANK_ADDRESS OBI=PAYMENT_DETAILS BBI=SENDER_TO_RECEIVER_INFO REF=REFERENCE_NUMBER ITM=16-02-01 12:33:30.000000");
	// result.add("::VDT=16-02-01::BNF=BENEFICIARY_NAME|BENEFICIARY_ADDRESS::ORG=::OGB=ORDERING_BANK_NAME|ORDERING_BANK_ADDRESS::OBI=PAYMENT_DETAILS::BBI=SENDER_TO_RECEIVER_INFO::REF=REFERENCE_NUMBER::ITM=16-02-01 12:33:30.000000");
	 result.add("::VDT=::BNF=::ORG=::OGB=::OBI=::BBI=::REF=REFERENCE_NUMBER::ITM=16-02-01 12:33:30.000000");
		
	 result.add("Incoming Wire Payment");
	 result.add("Réception dun paiement");
	 result.add("200");
	 result.add("CAD DDA");
	 result.add("CAD"); //10
	 result.add("BMO");
	 result.add("53");
	 result.add("DDA");
//	 result.add(new Double(39392.03));
//	 result.add(new Double(21399.9));
	 result.add("39392.03");
	 result.add("21399.9");
	 result.add("145679");
	 result.add("696000700");
	 result.add("1000");
	 result.add("C");
	 result.add("BMO");//20
	 result.add(null); 
	 result.add(null); 
	 result.add(null); 
	 result.add(null); 
	 result.add("tran");
	 result.add("1");
	 result.add("125");

	 return result;
	}
	 
	
 

private class CSVSchedulerTO implements Serializable{

	private static final long serialVersionUID = 11L;

	private String fileLocation;
	private String fileName;
	private String schedulerHeader;
	private String schedularTrailer;
	private String reportType;
	private String frequency;
	private String formatType;
	private String typeOfDate;
	private String language;
	private Date fromDate;
	private Date toDate;
	private Boolean newDataOnly;
	private List<Long> usAccountList;
	private List<Long> canAccountList;
	

	private String acctSortColumn;
	private String acctSortOrder;
	private Boolean maskAccount;
	private List<Long> balanceTypes;
	private List<Long> summaryCodes;
	private List<Long> transactions;
	private String senderID;
	private String generatedBy;// this is the extra parameter-need to check whether it is needed or not
	
	private List<Long> sameDayAccountIdList;
	private Date lastGenerated;
	private String primaryRelation;
	private String customerLoginId;
	private String transGroups; 
	private String inclWireDtls;
	
	public String getGeneratedBy() {
		return generatedBy;
	}

	public void setGeneratedBy(String generatedBy) {
		this.generatedBy = generatedBy;
	}
	
	

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public List<Long> getSameDayAccountIdList() {
		return sameDayAccountIdList;
	}

	public void setSameDayAccountIdList(List<Long> sameDayAccountIdList) {
		this.sameDayAccountIdList = sameDayAccountIdList;
	}

	public Date getLastGenerated() {
		return lastGenerated;
	}

	public void setLastGenerated(Date lastGenerated) {
		this.lastGenerated = lastGenerated;
	}

	public String getPrimaryRelation() {
		return primaryRelation;
	}

	public void setPrimaryRelation(String primaryRelation) {
		this.primaryRelation = primaryRelation;
	}

	public String getCustomerLoginId() {
		return customerLoginId;
	}

	public void setCustomerLoginId(String customerLoginId) {
		this.customerLoginId = customerLoginId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSchedulerHeader() {
		return schedulerHeader;
	}

	public void setSchedulerHeader(String schedulerHeader) {
		this.schedulerHeader = schedulerHeader;
	}

	public String getSchedularTrailer() {
		return schedularTrailer;
	}

	public void setSchedularTrailer(String schedularTrailer) {
		this.schedularTrailer = schedularTrailer;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getFormatType() {
		return formatType;
	}

	public void setFormatType(String formatType) {
		this.formatType = formatType;
	}

	public String getTypeOfDate() {
		return typeOfDate;
	}

	public void setTypeOfDate(String typeOfDate) {
		this.typeOfDate = typeOfDate;
	}

	
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Boolean getNewDataOnly() {
		return newDataOnly;
	}

	public void setNewDataOnly(Boolean newDataOnly) {
		this.newDataOnly = newDataOnly;
	}

	public String getAcctSortColumn() {
		return acctSortColumn;
	}

	public void setAcctSortColumn(String acctSortColumn) {
		this.acctSortColumn = acctSortColumn;
	}

	public String getAcctSortOrder() {
		return acctSortOrder;
	}

	public void setAcctSortOrder(String acctSortOrder) {
		this.acctSortOrder = acctSortOrder;
	}

	public Boolean getMaskAccount() {
		return maskAccount;
	}

	public void setMaskAccount(Boolean maskAccount) {
		this.maskAccount = maskAccount;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public List<Long> getUsAccountList() {
		return usAccountList;
	}

	public void setUsAccountList(List<Long> usAccountList) {
		this.usAccountList = usAccountList;
	}
	
	public List<Long> getCanAccountList() {
		return canAccountList;
	}

	public void setCanAccountList(List<Long> canAccountList) {
		this.canAccountList = canAccountList;
	}

	public List<Long> getBalanceTypes() {
		return balanceTypes;
	}

	public void setBalanceTypes(List<Long> balanceTypes) {
		this.balanceTypes = balanceTypes;
	}

	public List<Long> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Long> transactions) {
		this.transactions = transactions;
	}

	public List<Long> getSummaryCodes() {
		return summaryCodes;
	}

	public void setSummaryCodes(List<Long> summaryCodes) {
		this.summaryCodes = summaryCodes;
	}

	public String getSenderID() {
		return senderID;
	}

	public void setSenderID(String senderID) {
		this.senderID = senderID;
	}
	
	
	public String getTransGroups() {
		return transGroups;
	}

	public void setTransGroups(String transGroups) {
		this.transGroups = transGroups;
	}

	public String getInclWireDtls() {
		return inclWireDtls;
	}

	public void setInclWireDtls(String inclWireDtls) {
		this.inclWireDtls = inclWireDtls;
	}
	
}
}

