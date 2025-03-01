package pet.store.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pet.store.controller.model.PetStoreData;
import pet.store.dao.PetStoreDao;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {

	@Autowired
	private PetStoreDao petStoreDao;

	public PetStoreData savePetStore(PetStoreData petStoreData) throws Exception {

		if (petStoreData == null) {
			throw new Exception("PetStoreData cannot be null.");
		}

		Long petStoreId = petStoreData.getPetStoreId();

		PetStore petStore;

		if (petStoreId == null) {
			petStore = findOrCreatePetStore(petStoreId);
		} else {
			petStore = findPetStoreById(petStoreId);
		}

		petStore.setPetStoreName(petStoreData.getPetStoreName());

		petStore = petStoreDao.save(petStore);

		return petStoreData;
	}

	private PetStore findPetStoreById(Long petStoreId) {
		Optional<PetStore> petStoreOptional = petStoreDao.findById(petStoreId);

		if (petStoreOptional.isPresent()) {
			return petStoreOptional.get();
		} else {
			throw new NoSuchElementException("No PetStore found with ID" + petStoreId);
		}

	}

	private PetStore findOrCreatePetStore(Long petStoreId) {

		return new PetStore();
	}

	private PetStoreData converToPetStoreData(PetStore petStore) {
		PetStoreData petStoreData = new PetStoreData();
		petStoreData.setPetStoreId(petStore.getPetStoreId());
		petStoreData.setPetStoreAddress(petStore.getPetStoreAddress());
		petStoreData.setPetStoreCity(petStore.getPetStoreCity());
		petStoreData.setPetStoreName(petStore.getPetStoreName());
		petStoreData.setPetStoreZip(petStore.getPetStoreZip());
		petStoreData.setPetStorePhone(petStore.getPetStorePhone());

		return petStoreData;
	}

	private void copyPetStoreFeilds(PetStore petStore, PetStoreData petStoreData) {
		petStore.setPetStoreName(petStore.getPetStoreName());
		petStore.setPetStoreId(petStore.getPetStoreId());
		petStore.setPetStoreAddress(petStore.getPetStoreAddress());
		petStore.setPetStoreCity(petStore.getPetStoreCity());
		petStore.setPetStoreZip(petStore.getPetStoreZip());
		petStore.setPetStorePhone(petStore.getPetStorePhone());

	}

}
