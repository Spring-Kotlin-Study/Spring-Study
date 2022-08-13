예제로 배우는 스프링 입문(개정판)
===========================================

### Section 3. Spring AOP (흩어진 코드를 한 곳으로 모을 수 있게함.)
- Spring Triangle: IoC, AOP, PSA가 있다.
- AOP란 'Aspect Oriented Programming'의 줄임말이다.

---
**- 다양한 AOP 구현 방법**
1. java file에서 class파일로 변환될 때 조작하는 방법: A.java ----(AOP)---> A.class   

   참고: [AspectJ](https://www.eclipse.org/aspectj/)
   
2. classloader가 A.class를 읽어와서 메모리에 올릴 때 조작하는 방법: A.java -> A.class ---(AOP)---> 메모리

3. 프록시 패턴을 사용하는 방법: 
    - 기존의 코드를 건드리지 않고, 어떻게 새로운 기능을 넣을 수 있도록 함.
    - client 코드에 영향을 적게 주면서, 다른 객체로 변경할 수 있도록 함.


---
### Section 4. Spring PSA (잘 만든 인터페이스)
- POA란 'Portable Service Abstraction의 줄임말이다.
- ‘HttpServlet’ class를 직접사용하지 않고도, 쉽게 mapping을 할 수 있도록 해줌.
```Java
@GetMapping("/owners/new")
	@LogExecutionTime
	public String initCreationForm(Map<String, Object> model) {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		Owner owner = new Owner();
		model.put("owner", owner);

		stopWatch.stop();

		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/owners/new")
	@LogExecutionTime
	public String processCreationForm(@Valid Owner owner, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		}
		else {
			this.owners.save(owner);
			return "redirect:/owners/" + owner.getId();
		}
	}
```

- ‘@Transactional’을 붙이면, DB transaction을 처리하기위해 명시적으로 commit이나 rollback을 하지 않아도 됨.
