package pet.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
import pet.store.service.PetStoreService;

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {

	@Autowired
	public PetStoreService petStoreService;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreData createStoreData(@RequestBody PetStoreData petStoreData)
			throws Exception {
		log.info("Creating pet_store {}", petStoreData);

		return petStoreService.savePetStore(petStoreData);

	}

	@PutMapping("/petStoreId {}")
	public PetStoreData updatePetStore(
			@PathVariable Long petStoreId, @RequestBody PetStoreData petStoreData)
			throws Exception {
		log.info("Recived request to update PetStore with ID: {}", petStoreId);

		petStoreData.setPetStoreId(petStoreId);

		PetStoreData updatePetStore = petStoreService.savePetStore(petStoreData);

		return updatePetStore;
	}

}
