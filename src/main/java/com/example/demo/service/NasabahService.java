package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Nasabah;
import com.example.demo.repository.NasabahRepository;

@Service
public class NasabahService {
	private final NasabahRepository nasabahRepository;
	
	 public NasabahService(NasabahRepository nasabahRepository) {
	        this.nasabahRepository = nasabahRepository;
	    }
	 
	 public Page<Nasabah> getAllNasabahs(Pageable pageable) {
	        return nasabahRepository.findAll(pageable);
	    }


	    public Nasabah createNasabah(Nasabah nasabah) {
	        return nasabahRepository.save(nasabah);
	    }

	    public Nasabah updateNasabah(Long nasabahId, Nasabah updatedNasabah) {
	        Nasabah existingNasabah = nasabahRepository.findById(nasabahId).orElse(null);
	        if (existingNasabah != null) {
	            existingNasabah.setName(updatedNasabah.getName());
	            return nasabahRepository.save(existingNasabah);
	        }
	        return null;
	    }

	    public void deleteNasabah(Long nasabahId) {
	            nasabahRepository.deleteById(nasabahId);
	    }

	    public Nasabah getNasabahById(Long nasabahId) {
	        Optional<Nasabah> optionalNasabah = nasabahRepository.findById(nasabahId);

	        if (optionalNasabah.isPresent()) {
	            Nasabah nasabah = optionalNasabah.get();
	            return nasabah;
	        } else {
	            return null; 
	        }
	    }

		public List<Nasabah> getAllNasabah() {
			return nasabahRepository.findAll();
		}
}
