//package ie.ait.agile.filmlibrary;
//
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.testcontainers.containers.MySQLContainer;
//
//import static java.lang.String.format;
//
//public class MySqlTestContainerBase {
//    static final String IMAGE_TAG = "mysql:8.0.12";
//
//    static {
//        new MySQLContainer<>(IMAGE_TAG)
//                .withReuse(true)
//                .start();
//    }
//
//    @DynamicPropertySource
//    static void setDataSourceUrl(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.initialization-mode", () -> "never");
//        registry.add("spring.datasource.url", () -> format(
//                "jdbc:tc:%s:///filmdb?TC_DAEMON=true;TC_TMPFS=/testtmpfs:rw", IMAGE_TAG));
//    }
//}
