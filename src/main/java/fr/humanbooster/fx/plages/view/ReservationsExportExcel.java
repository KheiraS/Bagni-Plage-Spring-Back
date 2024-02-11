package fr.humanbooster.fx.plages.view;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import fr.humanbooster.fx.plages.business.Reservation;

public class ReservationsExportExcel extends AbstractXlsView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.addHeader("Content-Disposition", "attachment; filename=Reservations.xls");
		
		@SuppressWarnings("unchecked")
		List<Reservation> reservations = (List<Reservation>) model.get("reservations");
		int compteur = 0;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		Sheet sheet = workbook.createSheet("Réservations");
		for (Reservation reservation : reservations) {
			Row entete = sheet.createRow(compteur);

			Cell cell00 = entete.createCell(0);
			cell00.setCellValue(reservation.getClient().getNom());

			Cell cell01 = entete.createCell(1);
			cell01.setCellValue(reservation.getDateDebut().format(dtf));
			
			Cell cell02 = entete.createCell(2);
			cell02.setCellValue(reservation.getDateFin().format(dtf));

			compteur++;
		}
	}


}
