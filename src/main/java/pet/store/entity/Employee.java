package pet.store.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer employeeId;
	private String employeeFirstName;
	private String employeeLastName;
	private Integer employeePhone;
	private String employeeJobTile;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@Embedded
	private Long petStoreId;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "pet_store_id")
	private PetStore petStore;

}
