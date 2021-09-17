package example.domain.iam.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import example.domain.iam.model.Role;
import example.domain.iam.model.RoleIdentifier;
import example.domain.iam.model.RoleRepository;
import example.domain.shared.bootstrap.BootstrapEvent;
import example.domain.shared.bootstrap.BootstrapOrder;

@Component
public class RoleBootstrapListener implements ApplicationListener<BootstrapEvent>, Ordered {

	public static final RoleIdentifier USER = new RoleIdentifier("ROLE_USER");
	public static final RoleIdentifier ADMIN = new RoleIdentifier("ROLE_ADMIN");

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void onApplicationEvent(BootstrapEvent event) {
		if (!rolesExist()) {
			Role user = new Role(USER, "Application User");
			Role admin = new Role(ADMIN, "Application Admin");

			roleRepository.save(Lists.newArrayList(user, admin));
			roleRepository.flush();
		}
	}

	@Override
	public int getOrder() {
		return BootstrapOrder.IAM_ROLES;
	}

	public boolean rolesExist() {
		return roleRepository.count() > 0;
	}

}
