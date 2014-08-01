package org.landcycle;

import org.landcycle.repository.UserEntity;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackageClasses = UserEntity.class)
@Import(InfrastructureConfig.class)
public class PlainJpaConfig {

}
