package example.domain.shared.ddd;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.stereotype.Component;

@Component
@Retention(RetentionPolicy.RUNTIME)
public @interface DomainFactory {
}
