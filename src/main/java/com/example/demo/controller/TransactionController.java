package com.example.demo.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.ListTransactionPoint;
import com.example.demo.entity.Nasabah;
import com.example.demo.entity.Transaction;
import com.example.demo.entity.TransactionPoint;
import com.example.demo.service.NasabahService;
import com.example.demo.service.TransactionService;

@RestController
@RequestMapping("/api/transaksi")
public class TransactionController {
	 private final TransactionService transactionService;
	 private final  NasabahService nasabahService;
	    public TransactionController(TransactionService transactionService,NasabahService nasabahService) {
	        this.transactionService = transactionService;
			this.nasabahService = nasabahService;
	    }
	   


	    @PostMapping
	    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
	        Transaction createdTransaction = transactionService.createTransaction(transaction);
	        if(createdTransaction!=null){
	        	createdTransaction.setResponse("Data Transaction sudah berhasil di input");
	        	createdTransaction.setStatus("200");
	        }
	        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
	    }
	    @GetMapping("/point")
	    public ResponseEntity<TransactionPoint> getTransactionById()  {
	    	TransactionPoint transactionPoint=new TransactionPoint();
	    	
	    	
	    	List<Nasabah> allNasabah = nasabahService.getAllNasabah();
	    	
	        if (allNasabah != null) {
	        	try {
	        		for(Nasabah t : allNasabah) {
		        		ListTransactionPoint listTransactionPoint=new ListTransactionPoint();
		        		Long accountId= t.getAccountId();
		        		
		        	    BigDecimal totalBayarListrik =  transactionService.getTotalAmountByDescriptionAndAccountId(t.getAccountId(),"Bayar Listrik");
		        	    BigDecimal totalBeliPulsa =  transactionService.getTotalAmountByDescriptionAndAccountId(t.getAccountId(),"Beli Pulsa");
		        	    
		        	    
		        	    if (totalBayarListrik == null) {
		        	    	totalBayarListrik = BigDecimal.ZERO;
		                }
		        	    if (totalBeliPulsa == null) {
		        	    	totalBeliPulsa = BigDecimal.ZERO;
		                }
		        		Nasabah nasabah = nasabahService.getNasabahById(accountId);
		        		if(nasabah!=null) {
		        			listTransactionPoint.setAccountId(nasabah.getAccountId());
		        			listTransactionPoint.setName(nasabah.getName());
		        		}
		        		int poin = hitungPoinPembayaranListrik(totalBayarListrik.intValue());
		        		int poin2 = hitungPoinBeliPulsa(totalBeliPulsa.intValue());
		        		int totalPoint = poin+poin2;
		        		listTransactionPoint.setTotalPoint(totalPoint);
		        		transactionPoint.getData().add(listTransactionPoint);
		        		
		        	}
				} catch (Exception e) {
					e.printStackTrace();
				}
	        	
	        	transactionPoint.setResponse("Data Point Berhasil di cetak");
        		transactionPoint.setStatus("200");
	            return new ResponseEntity<>(transactionPoint, HttpStatus.OK);
	        } else {
	        	transactionPoint.setStatus("201");
	        	transactionPoint.setResponse("Transaksi dengan id tersebut tidak di temukan pada database");
	            return new ResponseEntity<TransactionPoint>(transactionPoint,HttpStatus.NOT_FOUND);
	        }
	    }
	    
	    @GetMapping("/report")
	    public ResponseEntity<TransactionPoint> getTransactionReportById()  {
	    	TransactionPoint transactionPoint=new TransactionPoint();
	    	
	    	
	    	List<Nasabah> allNasabah = nasabahService.getAllNasabah();
	    	
	        if (allNasabah != null) {
	        	try {
	        		for(Nasabah t : allNasabah) {
		        		ListTransactionPoint listTransactionPoint=new ListTransactionPoint();
		        		Long accountId= t.getAccountId();
		        		
		        	    BigDecimal totalBayarListrik =  transactionService.getTotalAmountByDescriptionAndAccountId(t.getAccountId(),"Bayar Listrik");
		        	    BigDecimal totalBeliPulsa =  transactionService.getTotalAmountByDescriptionAndAccountId(t.getAccountId(),"Beli Pulsa");
		        	    
		        	    
		        	    if (totalBayarListrik == null) {
		        	    	totalBayarListrik = BigDecimal.ZERO;
		                }
		        	    if (totalBeliPulsa == null) {
		        	    	totalBeliPulsa = BigDecimal.ZERO;
		                }
		        		Nasabah nasabah = nasabahService.getNasabahById(accountId);
		        		if(nasabah!=null) {
		        			listTransactionPoint.setAccountId(nasabah.getAccountId());
		        			listTransactionPoint.setName(nasabah.getName());
		        		}
		        		int poin = hitungPoinPembayaranListrik(totalBayarListrik.intValue());
		        		int poin2 = hitungPoinBeliPulsa(totalBeliPulsa.intValue());
		        		int totalPoint = poin+poin2;
		        		listTransactionPoint.setTotalPoint(totalPoint);
		        		transactionPoint.getData().add(listTransactionPoint);
		        		
		        	}
				} catch (Exception e) {
					e.printStackTrace();
				}
	        	
	        	transactionPoint.setResponse("Data Point Berhasil di cetak");
        		transactionPoint.setStatus("200");
	            return new ResponseEntity<>(transactionPoint, HttpStatus.OK);
	        } else {
	        	transactionPoint.setStatus("201");
	        	transactionPoint.setResponse("Transaksi dengan id tersebut tidak di temukan pada database");
	            return new ResponseEntity<TransactionPoint>(transactionPoint,HttpStatus.NOT_FOUND);
	        }
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
		private int hitungPoinBeliPulsa(int tagihan) {
			BigDecimal poin = BigDecimal.ZERO;

		    BigDecimal tagihanDecimal = new BigDecimal(tagihan);

		    if (tagihanDecimal.compareTo(new BigDecimal(10000)) <= 0) {
		        poin = BigDecimal.ZERO;
		    }
		    else if (tagihanDecimal.compareTo(new BigDecimal(30000)) <= 0) {
		        poin = tagihanDecimal.subtract(new BigDecimal(10000)).divide(new BigDecimal(1000), 0, BigDecimal.ROUND_DOWN);
		    }
		    else {
		        poin = new BigDecimal(20); 
		        BigDecimal tambahanPoin = tagihanDecimal.subtract(new BigDecimal(30000)).divide(new BigDecimal(1000), 0, BigDecimal.ROUND_DOWN);
		        poin = poin.add(tambahanPoin.multiply(new BigDecimal(2))); 
		    }

		    return poin.intValue();
		}
		private int hitungPoinPembayaranListrik(int tagihan) {
			BigDecimal poin = BigDecimal.ZERO;

		    BigDecimal tagihanDecimal = new BigDecimal(tagihan);

		    if (tagihanDecimal.compareTo(new BigDecimal(50000)) <= 0) {
		        poin = BigDecimal.ZERO;
		    }
		    else if (tagihanDecimal.compareTo(new BigDecimal(100000)) <= 0) {
		        poin = tagihanDecimal.subtract(new BigDecimal(50000)).divide(new BigDecimal(2000), 0, BigDecimal.ROUND_DOWN);
		    }
		    else {
		        poin = new BigDecimal(25); 
		        BigDecimal tambahanPoin = tagihanDecimal.subtract(new BigDecimal(100000)).divide(new BigDecimal(2000));
		        poin = poin.add(tambahanPoin.multiply(new BigDecimal(2))); 
		    }

		    return poin.intValue();
		}    
	    
}
