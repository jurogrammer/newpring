package juro.newpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.Id;
import jakarta.servlet.http.HttpServletRequest;

@SpringBootApplication
public class NewpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewpringApplication.class, args);
	}

}

@RestController
class PersonController {
	private final CustomerRepository customerRepository;

	public PersonController(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@GetMapping("/customers")
	public Iterable<Customer> customers() {
		return customerRepository.findAll();
	}

	@GetMapping("/customers/{name}")
	public Iterable<Customer> customersByName(@PathVariable String name) {
		Assert.state(Character.isUpperCase(name.charAt(0)), "the name must be start with a capital letter!");
		return customerRepository.findByName(name);
	}
}

@RestControllerAdvice
class ErrorHandlingControllerAdvice {

	@ExceptionHandler
	ProblemDetail handle(IllegalStateException ise, HttpServletRequest httpServletRequest) {
		// httpServletRequest 의 패키지는 jakarta

		var pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST.value());
		pd.setDetail(ise.getLocalizedMessage());
		return pd;
	}
}

interface CustomerRepository extends CrudRepository<Customer, Long> {
	Iterable<Customer> findByName(String name);
}

// hibernate 사용할 경우 record 사용 어려움. @Entity 는 디폴트 생성자를 사용하기 때문
record Customer(@Id Long id, String name) {
}
