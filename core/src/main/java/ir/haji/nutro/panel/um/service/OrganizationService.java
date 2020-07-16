package ir.haji.nutro.panel.um.service;

import ir.haji.nutro.exception.BadRequestException;
import ir.haji.nutro.panel.um.entity.Organization;
import ir.haji.nutro.panel.um.entity.OrganizationSpec;
import ir.haji.nutro.panel.um.repo.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Saeed on 2/13/17.
 */
@Component
public class OrganizationService {
    @Value("${panel.websiteOrganizationId:177}")
    public Long configWebsiteOrganizationId;
    @Value("${panel.adventureOrganizationId:8}")
    private Long adventureOrganizationId;

    public static Long websiteOrganizationId;

    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    private UserService userService;


    @PostConstruct
    public void postConstruct() {
        websiteOrganizationId = configWebsiteOrganizationId;
    }

    public Long getWebsiteOrganizationId() {
        return configWebsiteOrganizationId;
    }

    public Long getAdventureOrganizationId() {
        return adventureOrganizationId;
    }

    public void addOrganization(Organization organization) {
        if (organization.getName() == null || organization.getName().isEmpty())
            throw new BadRequestException("نام مشتری نباید خالی باشد");
        if (organization.getOfficialName() == null || organization.getOfficialName().isEmpty())
            throw new BadRequestException("نام رسمی مشتری نباید خالی باشد");
        organization.setId(null);
        organizationRepository.save(organization);
    }

    public Organization getOrganization(Long id) {
        Organization organization = organizationRepository.findById(id).get();
        if (organization == null)
            throw new BadRequestException("سازمان خواسته شده یافت نشد");
        return organization;
    }

    public Page<Organization> getOrganizationList(OrganizationSpec spec) {
        return organizationRepository.findAll(spec, spec.getPageable());
    }

    public void updateOrganization(Long id, Organization organization) {
        Organization dbOrg = getOrganization(id);
        dbOrg.setName(organization.getName());
        dbOrg.setAddress(organization.getAddress());
        dbOrg.setDescription(organization.getDescription());
        dbOrg.setCity(organization.getCity());
        dbOrg.setEconomicId(organization.getEconomicId());
        dbOrg.setNationalId(organization.getNationalId());
        dbOrg.setOfficialName(organization.getOfficialName());
        dbOrg.setPhone(organization.getPhone());
        dbOrg.setRegistrationId(organization.getRegistrationId());
        dbOrg.setPostalCode(organization.getPostalCode());
        dbOrg.setProvince(organization.getProvince());
        organizationRepository.save(dbOrg);
    }

}
