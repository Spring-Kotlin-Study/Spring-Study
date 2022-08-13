예제로 배우는 스프링 입문(개정판)
===========================================

### Section 2. 스프링 IoC
- IoC는 'Inversion of Control'의 줄임말로서, '제어권의 역전'이라는 뜻이다.
  - 일반적인 의존성에 대한 제어권: "내가 사용할 의존성은 내가 만든다."
  ```Java
  /* class OwnerController */
  class OwnerController {
     private OwnerRepository repository = new OwnerRepository();
  }
  ```
  
  - IoC에서의 제어권: "내가 사용할 의존성을 누군가 알아서 주겠지."
  ```Java
  /* class OwnerController */
  class OwnerController {
     private OwnerRepository repo;

     // 유일한 생성자로서, 'repo'를 null이 안되도록 만들어준다.
     public OwnerController(OwnerRepository repo) {
         this.repo = repo;
     } 

     // Use 'repo'
  }

  /* class OwnerControllerTest */
  class OwnerControllerTest {
     @Test
     public void create() {
           OwnerRepository repo = new OwnerRepository();
           OwnerController controller = new OwnerController(repo);
     }
  }
  ```
--- 
- IoC Container
  - 주로 'ApplicationContext' 또는 'BeanFactory'라고 불린다.
  - 다양한 방법으로 등록된 빈들은, 의존성 주입을 IoC 컨테이너가 해준다.
  - 의존성 주입은 빈끼리만 가능하므로, IoC 컨테이너 안에 있는 객체들끼리만 의존성 주입이 가능하다.
  - 빈(bean)을 만들어주고, 엮어주며 제공해주는 역할을 수행한다.     

---
- bean으로 만드는 방법
  1. Component Scan (@Component, @Repository, @Service, @Controller, @Configuration)
  2. 직접 XML이나 Java 설정 파일에 등록
---
- bean 사용 방법
  1. @Autowired 또는 @Inject
  ```Java
  @Autowired
  private OwnerRepository owners;
  ```
  2. ApplicationContext에서 getBean()으로 직접 꺼내기
  ```Java
  @Autowired
  ApplicationContext applicationContext;

  SampleController bean = applicationContext.getBean(SampleController.class);
  ```
---  
- 의존성 주입(Dependency Injection)
  - 의존성 주입에는 세 가지 방법이 있다: 각각 1)생성자, 2)필드, 3)setter를 이용하는 방법
  - 이 중 생성자를 이용하여 injection 하는 방법을 권장하는데, 아래 두 이유가 있다.
  1) 필수적으로 사용해야 하는 reference 없이는 instance를 못만들기 때문에
  2) 다른 type의 reference로 변경되는 것을 막을 수 있기 때문에
  ```Java
  @Controller
  class OwnerController {
    private final OwnerRepository owners;

    public OwnerController(OwnerRepository clinicService) {
      this.owners = clinicService;
    }
  }
  
  ```
