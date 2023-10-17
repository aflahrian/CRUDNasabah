package com.example.demo.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Nasabah;
import com.example.demo.service.NasabahService;

@RestController
@RequestMapping("/api/nasabah")
public class NasabahController {

    private final NasabahService nasabahService;

    public NasabahController(NasabahService nasabahService) {
        this.nasabahService = nasabahService;
    }

    @GetMapping
    public ResponseEntity<Page<Nasabah>> getAllNasabahs(Pageable pageable) {
        Page<Nasabah> nasabahs = nasabahService.getAllNasabahs(pageable);
        return new ResponseEntity<>(nasabahs, HttpStatus.OK);
    }

    @GetMapping("/{nasabahId}")
    public ResponseEntity<Nasabah> getNasabahById(@PathVariable Long nasabahId) {
        Nasabah nasabah = nasabahService.getNasabahById(nasabahId);
        if (nasabah != null) {
        	nasabah.setResponse("Pencarian Data Nasabah Berhasil");
        	nasabah.setStatus("200");
            return new ResponseEntity<>(nasabah, HttpStatus.OK);
        } else {
        	 Nasabah nasabah2=new Nasabah();
        	nasabah2.setStatus("201");
        	nasabah2.setResponse("Nasabah dengan id tersebut tidak di temukan pada database");
            return new ResponseEntity<Nasabah>(nasabah2,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Nasabah> createNasabah(@RequestBody Nasabah nasabah) {
        Nasabah createdNasabah = nasabahService.createNasabah(nasabah);
        if(createdNasabah!=null){
        	createdNasabah.setResponse("Data Nasabah sudah berhasil di input");
        	createdNasabah.setStatus("200");
        }
        return new ResponseEntity<>(createdNasabah, HttpStatus.CREATED);
    }

    @PutMapping("/{nasabahId}")
    public ResponseEntity<Nasabah> updateNasabah(
            @PathVariable Long nasabahId,
            @RequestBody Nasabah updatedNasabah
    ) {
        Nasabah nasabah = nasabahService.updateNasabah(nasabahId, updatedNasabah);
        if (nasabah != null) {
        	nasabah.setResponse("Perubahan Data Nasabah Berhasil");
        	nasabah.setStatus("200");
            return new ResponseEntity<>(nasabah, HttpStatus.OK);
        } else {
        	Nasabah  nasabah2 = new Nasabah();
        	nasabah2.setStatus("201");
        	nasabah2.setResponse("Nasabah dengan id tersebut tidak di temukan pada database");
            return new ResponseEntity<>(nasabah2,HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{nasabahId}")
    public ResponseEntity<Nasabah> deleteNasabah(@PathVariable Long nasabahId) {
    	  Nasabah nasabah = nasabahService.getNasabahById(nasabahId);
    	    if (nasabah != null) {
    	        nasabahService.deleteNasabah(nasabahId);
    	        nasabah.setResponse("Data Nasabah Berhasil di hapus");
    	        nasabah.setStatus("200");
    	        return new ResponseEntity<>(nasabah, HttpStatus.OK);
    	    } else {
    	        Nasabah nasabah2 = new Nasabah();
    	        nasabah2.setStatus("201");
    	        nasabah2.setResponse("Nasabah dengan id tersebut tidak di temukan pada database");
    	        return new ResponseEntity<>(nasabah2, HttpStatus.NOT_FOUND);
    	    }
      
       
    }

}
