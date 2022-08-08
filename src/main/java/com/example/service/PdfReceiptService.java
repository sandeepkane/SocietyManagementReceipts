package com.example.service;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.example.dto.Receipt;
import com.example.utils.AmountTranslate;
import com.itextpdf.io.font.constants.StandardFonts;

import java.io.File;

public class PdfReceiptService {

	String RECEIPT = "RECEIPT";
	String RECEIPT_NO = "No.";
	String DATE = "Date:";
	String INVOICE_NO = "Invoice No.:";
	String AUTHORISED_SIGNATORY = "Authorised Signatory";
	String MEMBERS_SIGN = "Member's Sign.";
	String BUILDING_NO = "Building No.:";
	String FLAT_NO = "Flat No.:";
	String RS = "Rs.:";
	String RUPEES = "Rupees.:";
	String OTHERS = "Others : ";
	String RECEIVED_THANKS_FROM = "Received with thanks from :";
	
	String TOWARDS ="Towards";
	String MAINTENANCE = "Maintenance";
	String SINKING_FUND = "Sinking Fund";
	String MAJOR_REPAIR_FUND = "Major Repair Fund";
	String WATER_BILL = "Water Bill";

	String REGISTRATION_NO_LABEL = "Registration No:.";
	String REGISTRATION_NO = "PNA/PNA (06)/HSG (TC)/19495/2019";

	String REGISTRATION_DATE_LABEL = "Registration Date:";
	String REGISTRATION_DATE = "04/04/2019";

	String SOCIETY_NAME = "SUKHWANI COLORONIC D&E WING CO-OPERATIVE HOUSING SOCIETY LIMITED";
	String SOCIETY_ADDRESS = "Address: Sr. No. 90/5, 91/1, Aundh Ravet BRT Road, Ravet, Pune - 412101. Maharashtra, India Email - coloronicmembersravet@gmail.com";

	String SOCIETY_LOGO = "src/main/resources/static/LOGO.png";
	public static final String DEST = "C:\\Users\\Yogesh\\Desktop\\receipt\\receipt.pdf";
	

	public void generateReceipt(Receipt receipt) throws Exception {
		File file = new File(DEST);
		file.getParentFile().mkdirs();

		new PdfReceiptService().manipulatePdf(receipt, DEST);
	}

	protected void manipulatePdf(Receipt receipt, String dest) throws Exception {
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));

		PdfFont timesRomanFont = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
		Document doc = new Document(pdfDoc);
		doc.setFont(timesRomanFont).setFontSize(10);

		Table table = new Table(UnitValue.createPercentArray(8)).useAllAvailableWidth();
		Border border = new SolidBorder(ColorConstants.BLACK, 1);
		table.setBorderLeft(border);
		table.setBorderRight(border);
		table.setBorderTop(border);
		table.setBorderBottom(border);

		Style style1 = new Style();
		style1.setFont(timesRomanFont).setFontColor(ColorConstants.BLACK).setFontSize(13).setBold();

		Style style2 = new Style();
		style2.setFont(timesRomanFont).setFontColor(ColorConstants.WHITE).setBackgroundColor(ColorConstants.BLACK)
				.setFontSize(13).setBold().setWidth(60);

		// HEADER
		Cell headerLeftCell = new Cell(10, 4);
		headerLeftCell.add(new Paragraph(REGISTRATION_NO_LABEL.concat(REGISTRATION_NO)));
		headerLeftCell.add(new Paragraph(REGISTRATION_DATE_LABEL.concat(REGISTRATION_DATE)));
		headerLeftCell.add(new Paragraph(new Text(RECEIPT)).addStyle(style2));
		headerLeftCell.add(
				new Paragraph(new Text(RECEIPT_NO).addStyle(style2)).add(Integer.toString(receipt.getReceiptNo())));
		headerLeftCell.setBorder(Border.NO_BORDER);
		table.addCell(headerLeftCell);

		Cell headerMiddleCell = new Cell(10, 1);
		ImageData imageData = ImageDataFactory.create(SOCIETY_LOGO);
		Image image = new Image(imageData);
		image.setWidth(70);
		image.setHeight(60);
		image.setBorder(Border.NO_BORDER);
		headerMiddleCell.add(image);
		headerMiddleCell.setBorder(Border.NO_BORDER);
		table.addCell(headerMiddleCell);

		Cell headerRightCell = new Cell(10, 3);
		headerRightCell.add(new Paragraph(SOCIETY_NAME).addStyle(style1));
		headerRightCell.add(new Paragraph(SOCIETY_ADDRESS).setFontSize(9));
		headerRightCell.setBorder(Border.NO_BORDER);
		table.addCell(headerRightCell);

		Border borderBottom = new SolidBorder(ColorConstants.BLACK, 1);
		Cell headerSeparatorCell1 = new Cell(1, 8);
		headerSeparatorCell1.setBorder(Border.NO_BORDER);
		headerSeparatorCell1.setBorderBottom(borderBottom);
		table.addCell(headerSeparatorCell1);

		// BODY
		Cell bodyTopLeftCell = new Cell(1, 4);
		bodyTopLeftCell.add(new Paragraph(DATE).add(new Text(receipt.getReceiptDate().toString())));
		bodyTopLeftCell.setBorder(Border.NO_BORDER);
		table.addCell(bodyTopLeftCell);

		Cell bodyTopRightCell = new Cell(1, 4);
		bodyTopRightCell.add(new Paragraph(INVOICE_NO).add(Integer.toString(receipt.getInvoiceNo())));
		bodyTopRightCell.setBorder(Border.NO_BORDER);
		table.addCell(bodyTopRightCell);

		Cell headerSeparatorCell2 = new Cell(1, 8);
		headerSeparatorCell2.setBorder(Border.NO_BORDER);
		headerSeparatorCell2.setBorderBottom(borderBottom);
		table.addCell(headerSeparatorCell2);

		Cell bodyLine1Cell = new Cell(10, 8);
		Paragraph bodyLine1Paragraph = new Paragraph();
		bodyLine1Paragraph.setPaddingTop(8);
		bodyLine1Paragraph.add(new Text(RECEIVED_THANKS_FROM));
		bodyLine1Paragraph.add(new Text(receipt.getFlatOwner()));
		bodyLine1Cell.add(bodyLine1Paragraph);
		bodyLine1Cell.setBorder(Border.NO_BORDER);
		table.addCell(bodyLine1Cell);

		Cell bodyLine2CellLeft = new Cell(10, 4);
		Paragraph bodyLine2ParagraphLeft = new Paragraph();
		bodyLine2ParagraphLeft.add(new Text(BUILDING_NO));
		bodyLine2ParagraphLeft.add(new Text(receipt.getBuildingNo()));
		bodyLine2CellLeft.add(bodyLine2ParagraphLeft);
		bodyLine2CellLeft.setBorder(Border.NO_BORDER);
		table.addCell(bodyLine2CellLeft);

		Cell bodyLine2CellRight = new Cell(10, 4);
		Paragraph bodyLine2ParagraphRight = new Paragraph();
		bodyLine2ParagraphRight.add(new Text(FLAT_NO));
		bodyLine2ParagraphRight.add(new Text(receipt.getFlatNo()));
		bodyLine2CellRight.add(bodyLine2ParagraphRight);
		bodyLine2CellRight.setBorder(Border.NO_BORDER);
		table.addCell(bodyLine2CellRight);

		Cell bodyLine3CellLeft = new Cell(10, 2);
		Paragraph bodyLine3ParagraphLeft = new Paragraph();
		bodyLine3ParagraphLeft.add(new Text(RS));
		bodyLine3ParagraphLeft.add(new Text(Double.toString(receipt.getAmount())));
		bodyLine3CellLeft.setBorder(Border.NO_BORDER);
		bodyLine3CellLeft.add(bodyLine3ParagraphLeft);
		table.addCell(bodyLine3CellLeft);

		Cell bodyLine3CellRight = new Cell(10, 6);
		Paragraph bodyLine3ParagraphRight = new Paragraph();
		bodyLine3ParagraphRight.add(new Text("("));
		bodyLine3ParagraphRight.add(new Text(RUPEES));
		bodyLine3ParagraphRight.add(new Text(AmountTranslate.translate(Double.toString(receipt.getAmount()))));
		bodyLine3ParagraphRight.add(new Text(")"));
		bodyLine3CellRight.setBorder(Border.NO_BORDER);
		bodyLine3CellRight.add(bodyLine3ParagraphRight);
		table.addCell(bodyLine3CellRight);

		PdfFont zapfdingbats = PdfFontFactory.createFont(StandardFonts.ZAPFDINGBATS);
		Text checkSquareText = new Text("o").setFont(zapfdingbats).setFontSize(20);

		Style marginStyle = new Style();
		marginStyle.setMarginLeft(10);
		marginStyle.setMarginRight(10);

		Cell bodyLine4Cell = new Cell(10, 8);
		Paragraph bodyLine4Paragraph = new Paragraph();
		bodyLine4Paragraph.add(new Text(TOWARDS).addStyle(marginStyle));
		bodyLine4Paragraph.add(checkSquareText);
		bodyLine4Paragraph.add(new Text(MAINTENANCE).addStyle(marginStyle));
		bodyLine4Paragraph.add(checkSquareText);
		bodyLine4Paragraph.add(new Text(SINKING_FUND).addStyle(marginStyle));
		bodyLine4Paragraph.add(checkSquareText);
		bodyLine4Paragraph.add(new Text(MAJOR_REPAIR_FUND).addStyle(marginStyle));
		bodyLine4Paragraph.add(checkSquareText);
		bodyLine4Paragraph.add(new Text(WATER_BILL).addStyle(marginStyle));
		bodyLine4Cell.setBorder(Border.NO_BORDER);
		bodyLine4Cell.add(bodyLine4Paragraph);
		table.addCell(bodyLine4Cell);

		Cell bodyLine5Cell = new Cell(10, 8);
		Paragraph bodyLine5Paragraph = new Paragraph();
		bodyLine5Paragraph.add(new Text(OTHERS));
		bodyLine5Paragraph.add(new Text(receipt.getOthers()));
		bodyLine5Cell.add(bodyLine5Paragraph);
		bodyLine5Cell.setBorder(Border.NO_BORDER);
		table.addCell(bodyLine5Cell);

		Cell emptyCell = new Cell(10, 8);
		emptyCell.setBorder(Border.NO_BORDER);
		table.addCell(emptyCell);

		// FOOTER
		Cell footerLeftCell = new Cell(10, 4);
		footerLeftCell.add(new Paragraph(AUTHORISED_SIGNATORY).setFontSize(10).setBold());
		footerLeftCell.setTextAlignment(TextAlignment.CENTER);
		footerLeftCell.setBorder(Border.NO_BORDER);
		table.addCell(footerLeftCell);

		Cell footerRightCell = new Cell(10, 4);
		footerRightCell.add(new Paragraph(MEMBERS_SIGN).setFontSize(10).setBold());
		footerRightCell.setTextAlignment(TextAlignment.CENTER);
		footerRightCell.setBorder(Border.NO_BORDER);
		table.addCell(footerRightCell);

		doc.add(table);

		doc.close();
	}

}
