package inc.evil.medassist.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.*;
import com.tngtech.archunit.library.GeneralCodingRules;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "inc.evil", importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchUnitTest {
	private static final ArchCondition<JavaClass> CONTROLLER_ADVICE_SHOULD_HAVE_ORDERED_ANNOTATION =
			new ArchCondition<>("@ControllerAdvice should have also the @Order(Ordered.HIGHEST_PRECEDENCE)") {
				@Override
				public void check(JavaClass clazz, ConditionEvents events) {
					ControllerAdvice controllerAdviceAnnotation = clazz.getAnnotationOfType(ControllerAdvice.class);
					if (controllerAdviceAnnotation.assignableTypes().length > 0 &&
							!clazz.getName().contains("Global")) {
						if (!clazz.isAnnotatedWith(Order.class)) {
							String message = String.format(
									"Class %s is annotated with @ControllerAdvice should be also annotated " +
											"with @Order(Ordered.HIGHEST_PRECEDENCE)",
									clazz.getFullName());
							events.add(SimpleConditionEvent.violated(clazz, message));
						}
					}
				}
			};

	@ArchTest
	public static final ArchRule jpaRepositoriesMustBeAnnotatedWithTheRepositoryAnnotation =
			classes()
					.that()
					.areInterfaces()
					.and()
					.haveNameMatching(".*Repository")
					.and()
					.areAssignableTo(org.springframework.data.repository.Repository.class)
					.should()
					.beAnnotatedWith(Repository.class);

	@ArchTest
	public static final ArchRule jpaRepositoriesClassNameMustHaveRepositorySuffix =
			classes()
					.that()
					.areInterfaces()
					.and()
					.areAssignableTo(org.springframework.data.repository.Repository.class)
					.should()
					.haveNameMatching(".*Repository");

	@ArchTest
	public static final ArchRule servicesClassNamesMustHaveServiceOrServiceImplSuffix =
			classes()
					.that()
					.areAnnotatedWith(Service.class)
					.should()
					.haveNameMatching("(.*ServiceImpl)|(.*Service)");

	@ArchTest
	public static final ArchRule controllerClassNamesMustHaveControllerOrControllerImplSuffix =
			classes()
					.that()
					.areAnnotatedWith(RestController.class)
					.should()
					.haveNameMatching("(.*ControllerImpl)|(.*Controller)");

	@ArchTest
	public static final ArchRule controllersAreInTheControllerPackage =
			classes()
					.that()
					.areAnnotatedWith(RestController.class)
					.should()
					.resideInAPackage("..controller");

	@ArchTest
	public static final ArchRule servicesAreInTheServicePackage =
			classes()
					.that()
					.areAnnotatedWith(Service.class)
					.should()
					.resideInAPackage("..service");

    @ArchTest
    public static final ArchRule repositoriesAreInTheRepositoryPackage =
            classes()
                    .that()
                    .areAnnotatedWith(Repository.class)
                    .should()
                    .resideInAPackage("..repository");

	@ArchTest
	public static final ArchRule jpaEntitiesAreInTheModelPackage =
			classes()
					.that()
					.areAnnotatedWith(Entity.class)
					.should()
					.resideInAPackage("..model");

	@ArchTest
	public static final ArchRule controllersShouldNotTalkToRepositories =
			noClasses()
					.that()
					.resideInAPackage("..controller..")
					.should()
					.dependOnClassesThat()
					.resideInAnyPackage("..repository..");

	@ArchTest
	public static final ArchRule controllersShouldNotTalkToJpaEntities =
			noClasses()
					.that()
					.resideInAPackage("..controller..")
					.should()
					.dependOnClassesThat()
					.areAnnotatedWith(Entity.class);

	@ArchTest
	public static final ArchRule controllerSpecificControllerAdvicesShouldHaveAlsoTheOrderedAnnotation =
			classes()
					.that()
					.areAnnotatedWith(ControllerAdvice.class)
					.should(CONTROLLER_ADVICE_SHOULD_HAVE_ORDERED_ANNOTATION);

	@ArchTest
	public static final ArchRule loggingLibraryShouldBeUsedInsteadOfSystemOut =
			GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS
					.because("The preferred way of logging is via a logging library like logback");

	@ArchTest
	public static final ArchRule noFieldInjection = GeneralCodingRules.NO_CLASSES_SHOULD_USE_FIELD_INJECTION;

}
