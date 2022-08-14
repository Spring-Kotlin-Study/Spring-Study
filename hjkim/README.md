# 1. Spring IOC
## 1) Spring IOC (Inversion Of Control)
- 일반적인 경우, 자기의 의존성은 자기가 만들어서 사용
  - 예시
  ```Java
  class OwnerController {
   private OwnerRepository repository = new OwnerRepository();
  }
  ```
- 의존성에 대한 제어권을 밖에서 넘겨 받아 사용하는 것. 즉, 의존성 관리는 밖에서 이루어진다.
- Dependency Injection : 외부에서 의존성을 주입
  - 예시
  ```Java
  class OwnerController {
     private OwnerRepository owners;

    public OwnerController(OwnerRepository clinicService) {
      this.owners = clinicService;
    }
     // owners 사용
  }
  class OwnerControllerTest {
     @Test
     public void create() {
           OwnerRepository repo = new OwnerRepository();
           OwnerController controller = new OwnerController(repo);
     }
  }
  ```
  - OwnerController에서 OwnerRepository 객체를 생성하지 않고 파라미터로 받아와서 사용

## 2) Spring IOC Container
- ApplicationContext (BeanFactory)
- 빈을 만들고 의존성을 엮어주며 제공해준다.
- 의존성 주입은 빈끼리만 가능하다. (Spring IOC Container 안에 들어있는 빈끼리만 의존성 주입이 가능)

## 3) 빈 (Bean)
- IOC Container가 관리하는 객체
- 빈을등록하는 방법
  - Component Scan : @Component, @Controller, ... 등의 어노테이션이 붙어있는 클래스를 찾아서 등록
  - 직접 등록 : XML이나 Java 설정 파일에서
    - 예시
    - ```Java
      ...
      // SampleConfig 파일에 의해 SampleContoller가 빈으로 설정된다.
      @Configuration
      public class SampleConfig {
        @Bean
        public SampleController sampleController() {
          return new SampleController();
        }
      }
      ...
      ```
      
 - 빈을 사용하는 방법
    - AutoWired 어노테이션 활용하는 방법
      ```Java      
      @AutoWired
      private OwerRepository owners;
      
      // 혹은 setter에 @AutoWired 붙여도 된다.
      ```
    - ApplicationContext에서 꺼내쓰는 방법
      ```Java
      public calss SampleControllerTest {
        @Test
        public void testID() {
          SampleController bean = applicationContext.getBean(SampleConroller.class);
          assertThat(bean).IsNotNull();
        }
      }
      ```
      
## 4) Dependency Injection
- 외부에서 의존성을 주입 (필드나 setter에 @AutoWired 붙이거나 생성자를 통해 주입)
- Spring 4.3이상부터는 어떤 클래스의 생성자가 하나뿐이고 그 생성자로 주입받는 변수가 Bean으로 등록되어 있다면 @AutoWired 어노테이션 없이 자동으로 빈을 주입

# 2. Spring AOP (Aspect Oriented Programming)
- 흩어진 코드를 한 곳으로 모은다.
- AOP 구현 방법
  - 컴파일 : A.java----(AOP)---->A.class (AspectJ)
  - 바이트코드 조작 : A.java -> A.class --(AOP)--> 메모리 (AspectJ)
  - 프록시 패턴 : 스프링 AOP가 사용하는 방법, 클라이언트 코드는 변경X
    <img src="https://refactoring.guru/images/patterns/diagrams/proxy/live-example.png?id=a268c57fdaf073ee81cf4dfc7239eae2" width="80%" height="40%"></img> 

    - 예제
    ```Java
      public interface Payment {
        void pay(int amount); 
      }
    ```
    ```Java
      public class Store {
        Payment payment;
        
        public Store(Payment paymet) {
          this.payment = payment;
        }
        
        public void buySomething(int amount)  {
          payment.pay(amount);
        }
      }
    ```
    ```Java
      public class Cash implements Payment {
        @Override
        public void pay(int amount) {
          // print
        }
      }
      ```
    ```Java
      public class CashPerf implements Payment {
        Payment cash = new Cash();
        
        @Override
        public void pay() {
          StopWatch stopwatch = new Stopwatch();
          stopwatch.start();
          
          cash.pay(100);
          
          stopwatch.stop();
          System.out.println(stopWatch.prettyPrint());          
        }
      }
    ```


# 3. Spring PSA (Portable Service Abstraction)
-  하나의 추상화로 여러 서비스를 묶어둔 것
-  추상화 계층을 사용하여 어떤 기술을 내부에 숨기고 개발자에게 편의성을 제공해주는 것
