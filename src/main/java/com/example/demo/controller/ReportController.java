package com.example.demo.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Transaction;
import com.example.demo.service.TransactionService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@RestController
@RequestMapping("/report")
public class ReportController {

    
    private final TransactionService transactionService;
    
    public ReportController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/buku-tabungan")
    public ResponseEntity<byte[]> cetakBukuTabungan(
            @RequestParam("AccountId") long accountId,
            @RequestParam("StartDate") @DateTimeFormat(pattern = "dd/MM/yyyy") Date startDate,
            @RequestParam("EndDate") @DateTimeFormat(pattern = "dd/MM/yyyy") Date endDate) {
    	try {
            List<Transaction> listTransaction = transactionService.findTransactionsByAccountIdAndDateRange(accountId, startDate, endDate);

            // Buat dokumen PDF
            ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, pdfStream);
            document.open();

            document.add(new Paragraph("Laporan Transaksi Nasabah"));
            document.add(new Paragraph("Nomor Nasabah: " + accountId));
            document.add(new Paragraph("Tanggal Awal: " + startDate));
            document.add(new Paragraph("Tanggal Akhir: " + endDate));
            document.add(new Paragraph(""));

            // Tambahkan detail transaksi ke PDF
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
            BigDecimal total = new BigDecimal(0);
            
            for (Transaction t : listTransaction) {
            	 total = t.getDebitCreditStatus().equalsIgnoreCase("C") ? total.add(t.getAmount()):total.subtract(t.getAmount());
            	 
                document.add(new Paragraph(dateFormat.format(t.getTransactionDate()) + " | " + t.getDescription() + " | " +
                        (t.getDebitCreditStatus().equalsIgnoreCase("C") ? rupiahFormat.format(t.getAmount()) : " --- ") + " | " +
                        (t.getDebitCreditStatus().equalsIgnoreCase("D") ? rupiahFormat.format(t.getAmount()) : " --- ") + " | " +
                        rupiahFormat.format(total)));
            }

            document.close();

            // Ambil byte array dari dokumen PDF
            byte[] pdfBytes = pdfStream.toByteArray();
            pdfStream.close();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", "buku-tabungan.pdf");
            headers.setContentLength(pdfBytes.length);

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    	
    }
}
