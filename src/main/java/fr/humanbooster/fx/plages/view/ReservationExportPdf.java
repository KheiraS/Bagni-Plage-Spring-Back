package fr.humanbooster.fx.plages.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

import fr.humanbooster.fx.plages.business.Reservation;

public class ReservationExportPdf extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/pdf");
		PdfContentByte cb = writer.getDirectContent();
		BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
    	Reservation reservation = (Reservation) model.get("reservation");

    	afficherText("Récapitulatif de la réservation " + reservation.getId(), 200, 760, bf, 16, cb);
    	
    	afficherText("Nom du client : " + reservation.getClient().getNom(), 40, 660, bf, 12, cb);
    	afficherText("Date de début : " + reservation.getDateDebut(), 40, 640, bf, 12, cb);
    	afficherText("Date de fin : " + reservation.getDateFin(), 40, 620, bf, 12, cb);
		document.newPage();
		document.close();
	}
	
    private void afficherText(String nom, int x, int y, BaseFont baseFont, int fontSize, PdfContentByte cb) {
    	cb.beginText();
    	cb.moveText(x, y);
    	cb.setFontAndSize(baseFont, fontSize);
    	cb.showText(nom);
    	cb.endText();
	}

}
