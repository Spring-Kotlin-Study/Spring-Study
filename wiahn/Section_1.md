예제로 배우는 스프링 입문(개정판)
===========================================

### Section1. 스프링 예제 프로젝트 PetClinic
**1. LastName 대신 FirstName으로 검색할 수 있게 만들기**
  - main/resources/templates/findOwners.html
  ```HTML
  <label class="col-sm-2 control-label">First name </label>     <!-- line 12 -->
  <input class="form-control" th:field="*{firstName}" size="30" <!-- line 14 --> 
  ```

  - main/java/owner/OwnerController.java
  ```java
  @GetMapping("/owners")
	public String processFindForm(@RequestParam(defaultValue = "1") int page, Owner owner, BindingResult result,
			Model model) {

		// allow parameterless GET request for /owners to return all records
		if (owner.getFirstName() == null) {
			owner.setFirstName(""); // empty string signifies broadest possible search
		}

		// find owners by first name
		Page<Owner> ownersResults = findPaginatedForOwnersFirstName(page, owner.getFirstName());
		if (ownersResults.isEmpty()) {
			// no owners found
			result.rejectValue("firstName", "notFound", "not found");
			return "owners/findOwners";
		}
		else if (ownersResults.getTotalElements() == 1) {
			// 1 owner found
			owner = ownersResults.iterator().next();
			return "redirect:/owners/" + owner.getId();
		}
		else {
			// multiple owners found
			return addPaginationModel(page, model, ownersResults);
		}
	}

  ...

  private Page<Owner> findPaginatedForOwnersFirstName(int page, String firstName) {
		int pageSize = 5;
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		return owners.findByFirstName(firstName, pageable);
	}
  ```

- 'main/java/owner/OwnerRepository.java' (Interface)
  ```Java
  @Query("SELECT DISTINCT owner FROM Owner owner left join  owner.pets WHERE owner.firstName LIKE :firstName% ")
	@Transactional(readOnly = true)
	Page<Owner> findByFirstName(@Param("firstName") String firstName, Pageable pageable);
  ```

---
**2. 일부만 일치하더라도, 검색할 수 있도록 만들기**
```Java
@Query("SELECT DISTINCT owner FROM Owner owner left join  owner.pets WHERE owner.firstName LIKE %:firstName% ")
	@Transactional(readOnly = true)
	Page<Owner> findByFirstName(@Param("firstName") String firstName, Pageable pageable);
```
---
**3. Owner에 age column을 추가하기**

- 'main/java/model/owner/Owner.java'
```Java
private Integer age;


public Integer getAge() {
	return age;
}

public void setAge(Integer age) {
	this.age = age;
}
```

- 'main/java/resources/db/hsqldb/schema.sql'과 'main/java/resources/db/hsqldb/data.sql'
```SQL
-- main/java/resources/db/hsqldb/schema.sql
CREATE TABLE owners (
  id         INTEGER IDENTITY PRIMARY KEY,
  first_name VARCHAR(30),
  last_name  VARCHAR_IGNORECASE(30),
  age        INTEGER,
  address    VARCHAR(255),
  city       VARCHAR(80),
  telephone  VARCHAR(20)
);

-- main/java/resources/db/hsqldb/data.sql ('Age' Column을 'last_name' 다음에 추가)
INSERT INTO owners VALUES (1, 'George', 'Franklin', 20, '110 W. Liberty St.', 'Madison', '6085551023');
INSERT INTO owners VALUES (2, 'Betty', 'Davis', 21, '638 Cardinal Ave.', 'Sun Prairie', '6085551749');
INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', 22, '2693 Commerce St.', 'McFarland', '6085558763');
INSERT INTO owners VALUES (4, 'Harold', 'Davis', 23, '563 Friendly St.', 'Windsor', '6085553198');
INSERT INTO owners VALUES (5, 'Peter', 'McTavish', 24, '2387 S. Fair Way', 'Madison', '6085552765');
INSERT INTO owners VALUES (6, 'Jean', 'Coleman', 25, '105 N. Lake St.', 'Monona', '6085552654');
INSERT INTO owners VALUES (7, 'Jeff', 'Black', 26, '1450 Oak Blvd.', 'Monona', '6085555387');
INSERT INTO owners VALUES (8, 'Maria', 'Escobito', 27, '345 Maple St.', 'Madison', '6085557683');
INSERT INTO owners VALUES (9, 'David', 'Schroeder', 28, '2749 Blackhawk Trail', 'Madison', '6085559435');
INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', 32, '2335 Independence La.', 'Waunakee', '6085555487');
```

- 'main/resources/templates/owners/createOrUpdateOwnerForm.html'
```HTML
<input th:replace="~{fragments/inputField :: input ('Age', 'age', 'text')}" />
```

- 'main/resources/templates/owner/ownerList.html'
```HTML
<td th:text="${owner.age}"/>
```

- 'main/resources/templates/owner/ownerList.html'
```HTML
<tr>
        <th>Age</th>
        <td th:text="*{age}"></td>
      </tr>
```
