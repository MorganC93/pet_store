package pet.store.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreCustomer;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {

	@Autowired
	private PetStoreDao petStoreDao;

	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private CustomerDao customerDao;

	@Transactional(readOnly = true)
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

	private PetStoreData convertToPetStoreData(PetStore petStore) {
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

	@Transactional(readOnly = true)
	public List<PetStoreData> retriveAllPetStores() {

		List<PetStore> petStores = petStoreDao.findAll();
		List<PetStoreData> result = new LinkedList<>();

		for (PetStore petStore : petStores) {
			PetStoreData psd = new PetStoreData(petStore);
			psd.getCustomers().clear();
			psd.getEmployees().clear();

			result.add(psd);

		}

		return result;
	}

	@Transactional(readOnly = false)
	public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {

		if (petStoreEmployee == null) {
			throw new NoSuchElementException("PetStoreEmployee cannot be null.");
		}

		Long employeeId = petStoreEmployee.getEmployeeId();

		Employee employee;

		if (petStoreEmployee == null) {
			employee = findOrCreateEmployee(employeeId, employeeId);
		} else {
			employee = findEmployeeById(employeeId, employeeId);
		}

		setEmployeeFeilds(employee, petStoreEmployee);

		employee = employeeDao.save(employee);

		return new PetStoreEmployee(employeeDao.save(employee));

	}

	private Employee findEmployeeById(Long petStoreId, Long employeeId) {

		Optional<Employee> optionalEmployee = employeeDao.findById(employeeId);

		if (optionalEmployee.isEmpty()) {
			throw new NoSuchElementException("Employee with ID " + employeeId + " was not found.");
		}

		Employee employee = optionalEmployee.get();

		if (!employee.getPetStore().getPetStoreId().equals(employee)) {
			throw new IllegalArgumentException(
					"Employee with ID " + employeeId + " does not belong to the pet store with ID " + petStoreId);
		}

		return employee;

	}

	private Employee findOrCreateEmployee(Long employeeId, Long petStoreId) {

		if (employeeId == null) {
			return new Employee();
		}

		return findEmployeeById(petStoreId, employeeId);

	}

	public Employee setEmployeeFeilds(Employee employee, PetStoreEmployee petStoreEmployee) {
		employee.setEmployeeFirstName(petStoreEmployee.getEmployeeFirstName());
		employee.setEmployeeLastName(petStoreEmployee.getEmployeeLastName());
		employee.setEmployeeJobTitle(petStoreEmployee.getEmployeeJobTitle());
		employee.setEmployeePhone(petStoreEmployee.getEmployeePhone());
		employee.setEmployeeId(petStoreEmployee.getEmployeeId());

		return employee;
	}

	@Transactional(readOnly = false)
	public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreCustomer petStoreCustomer) {

		if (petStoreCustomer == null) {
			throw new NoSuchElementException("PetStoreEmployee cannot be null.");
		}

		Long customerId = petStoreCustomer.getCustomerId();

		Customer customer;

		if (petStoreCustomer == null) {
			customer = findOrCreateCustomer(customerId, petStoreId);
		} else {
			customer = findCustomerById(customerId, petStoreId);
		}

		setCustomerFeilds(customer, petStoreCustomer);

		return petStoreCustomer;

	}

	private Customer setCustomerFeilds(Customer customer, PetStoreCustomer petStoreCustomer) {
		customer.setCustomerFirstName(petStoreCustomer.getCustomerFirstName());
		customer.setCustomerLastName(petStoreCustomer.getCustomerLastName());
		customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());
		customer.setCustomerId(petStoreCustomer.getCustomerId());

		return customer;

	}

	@SuppressWarnings("unlikely-arg-type")
	private Customer findCustomerById(Long customerId, Long petStoreId) {

		Optional<Customer> optionalCustomer = customerDao.findById(customerId);

		if (optionalCustomer.isEmpty()) {
			throw new NoSuchElementException("Customer with ID " + customerId + " was not found.");
		}

		Customer customer = optionalCustomer.get();

		if (!customer.getPetStore().equals(customer)) {
			throw new IllegalArgumentException(
					"Customer with ID " + customerId + " does not belong to the pet store with ID " + petStoreId);
		}

		return customer;

	}

	private Customer findOrCreateCustomer(Long customerId, Long petStoreId) {

		if (customerId == null) {
			return new Customer();
		}

		return findCustomerById(petStoreId, customerId);
	}

	public void deletePetStoreById(Long petStoreId) {
		PetStore petStore = findPetStoreById(petStoreId);
		petStoreDao.delete(petStore);
	}

}
